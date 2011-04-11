#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <stdint.h>
#include <sys/time.h>

uint32_t COUNTER;
pthread_mutex_t LOCK;
pthread_mutex_t START;
pthread_cond_t CONDITION;

void * threads (void * unused) {
    pthread_mutex_lock(&START);
    pthread_mutex_unlock(&START);
    pthread_mutex_lock(&LOCK);
    if (COUNTER > 0) {
        pthread_cond_signal(&CONDITION);
    }
    for (;;) {
        COUNTER++;
        pthread_cond_wait(&CONDITION, &LOCK);
        pthread_cond_signal(&CONDITION);
    }
    pthread_mutex_unlock(&LOCK);
}

int tTimer() {
    struct timeval tv;

}

int main() {
    pthread_mutex_lock(&START);
    COUNTER = 0;
    pthread_create(&t1, NULL, threads, NULL);
    pthread_create(&t2, NULL, threads, NULL);
    pthread_detach(t1);
    pthread_detach(t2);
    time_t myTime = tTimer();
    pthread_mutex_unlock(&START);
    sleep(1);
    // Lock both simulaneous threads
    pthread_mutex_lock(&LOCK);
    //Normalize the result in second precision
    myTime = tTimer() - myTime / 1000;
    printf("Time: %d", myTime);
}
