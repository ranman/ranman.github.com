#ifndef BOUNDEDBUFFER_H
#define BOUNDEDBUFFER_H
#include <pthread.h>

typedef struct {
  char* data;
  int capacity;
  int start;
  int end;
  int size;
  pthread_mutex_t mutex;
  pthread_cond_t cond;
} BoundedBuffer;

void bufferInit(BoundedBuffer* buffer, int capacity);
void bufferWrite(BoundedBuffer* buffer, char* data, int count);
void bufferRead(BoundedBuffer* buffer, char* data, int count);
void bufferDeallocate(BoundedBuffer* buffer);

#endif
