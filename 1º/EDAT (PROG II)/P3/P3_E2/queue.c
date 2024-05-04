#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "queue.h"
#define MAX_QUEUE 8

struct _Queue{
    void *data[MAX_QUEUE];
    void **front;
    void **rear;
};

Queue *queue_new(){
    Queue *pq = NULL;
    int i;

    pq = (Queue*)malloc(sizeof(Queue));

    if(!pq){
        return NULL;
    }

    for(i=0;i<MAX_QUEUE;i++){
        pq->data[i] = NULL;
    }

    pq->front = &(pq->data[0]);
    pq->rear = &(pq->data[0]);

    return pq;
}

void queue_free(Queue *pq){
    free(pq);
}

Bool queue_isEmpty(const Queue *pq){

    pq = (Queue*) pq;

    if(pq == NULL){
        return TRUE;
    }

    if(pq->front == pq->rear){
        return TRUE;
    }

    return FALSE;
}
Bool _queue_isFull(Queue *pq){
    if(!pq)
        return FALSE;
    
    if((pq->rear + 1 - pq->front) % MAX_QUEUE == 0)
        return TRUE;
    
    return FALSE;
}
Status queue_push(Queue *pq, void *ele){
    if(!pq || ele == NULL || _queue_isFull(pq) == TRUE){
        return ERROR;
    }

    *(pq->rear) = (void*) ele;
    pq->rear = pq->data + (pq->rear + 1 - pq->data) % MAX_QUEUE;

    return OK;
}

void *queue_pop(Queue *pq){
    void *ele = NULL;

    if(!pq || queue_isEmpty(pq) == TRUE)
        return NULL;

    ele = *(pq->front);
    *(pq->front) = NULL;
    pq->front = pq->data + (pq->front + 1 - pq->data) % MAX_QUEUE;

    return ele;
}

void *queue_getFront(const Queue *pq){

    pq = (Queue*) pq;

    if(!pq || queue_isEmpty(pq) == TRUE)
        return NULL;

    return *(pq->front);
}

void *queue_getBack(const Queue *pq){
    void **last_elem;

    if(!pq || queue_isEmpty(pq) == TRUE)
        return NULL;

    if(pq->rear == pq->data){
        last_elem = ((Queue*)pq)->data + MAX_QUEUE -1;
    } else {
        last_elem = pq->rear - 1;
    }

    return *last_elem;
}

size_t queue_size(const Queue *pq){

    pq = (Queue*) pq;

    if(!pq)
        return 0;

    if(queue_isEmpty(pq) == TRUE){
        return 0;
    } else if(pq->rear == pq->front){
        return MAX_QUEUE;
    } else {
        return (MAX_QUEUE + pq->rear - pq->front) % MAX_QUEUE;
    }
}

int queue_print(FILE *fp, const Queue *pq, p_queue_ele_print f){

    void **i = NULL;
    int n = 0;

    if(!fp || !pq || !f)
        return -1;

    for(i=pq->front;i<pq->rear;i++){
        n+=f(stdout, i);
    }

    return n;
}