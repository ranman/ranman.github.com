#include "BoundedBuffer.h"
#include <stdio.h>
#include <stdlib.h>

void bufferInit(BoundedBuffer* buffer, int capacity) {
  buffer->data = calloc(capacity, sizeof(char));
  buffer->capacity = capacity;
  buffer->start = 0; 
  buffer->end = 0;
  buffer->size = 0;
  pthread_mutex_init(&buffer->mutex, NULL);
  pthread_cond_init(&buffer->cond, NULL);
}

void bufferWrite(BoundedBuffer* buffer, char* data, int count) { 
  pthread_mutex_lock(&buffer->mutex);
  while (count > (buffer->capacity - buffer->size)) {
    pthread_cond_wait(&buffer->cond, &buffer->mutex);
  }
  int i;
  if (buffer->start == -1) { buffer->start = buffer->end; }
  for (i = 0; i < count; i++) {
    buffer->data[(i+buffer->end) % buffer->capacity] = data[i]; 
    buffer->end = (buffer->end + 1) % buffer->capacity;
    buffer->size++;
  }
  if (buffer->start == -1) { buffer->start = buffer->end; }
  pthread_mutex_unlock(&buffer->mutex);
  pthread_cond_broadcast(&buffer->cond);
}

void bufferRead(BoundedBuffer* buffer, char* data, int count) { 
  pthread_mutex_lock(&buffer->mutex);
  while(count > (buffer->capacity - buffer->size))  {
    pthread_cond_wait(&buffer->cond, &buffer->mutex);
  }
  int i;
  if (buffer->start == buffer->end) { buffer->start = -1; }
  for (i = 0; i < count; i++) {
    data[i] = buffer->data[(i+buffer->start) % buffer->capacity]; 
    buffer->start = (buffer->start + 1) % buffer->capacity;
    buffer->size--;
  }
  if (buffer->start == buffer->end) { buffer->start = -1; }
  pthread_mutex_unlock(&buffer->mutex);
  pthread_cond_broadcast(&buffer->cond);
}

void bufferDeallocate(BoundedBuffer* buffer) { 
  pthread_mutex_lock(&buffer->mutex);
  free(buffer->data);
  pthread_mutex_unlock(&buffer->mutex);
  pthread_mutex_destroy(&buffer->mutex);
  pthread_cond_destroy(&buffer->cond);
}
