#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "stack.h"
#include "file_utils.h"

#define INIT_CAPACITY 2
#define FCT_CAPACITY 2

struct _Stack
{
    void **item;
    int top;
    int capacity;
};

Stack *stack_init(){

    Stack *s = NULL;
    int i;

    s = (Stack *) malloc(sizeof(Stack));
    if (s == NULL)
        return NULL;

    s->item = malloc(INIT_CAPACITY*sizeof(void*));
    if (!s->item){
        stack_free(s);
        return NULL;
    }

    for(i=0;i<INIT_CAPACITY;i++){
        s->item[i] = NULL;
    }

    s->capacity = INIT_CAPACITY;
    s->top = -1;

    return s;
}
void stack_free(Stack *s){
    if(s){
        free(s->item);
        free(s);
    }
}
Status stack_grow(Stack *s){

    int new_size = 0;
    int i;
    void **aux = NULL;
    if(!s || !s->item){
        return ERROR;
    }

    new_size = s->capacity * FCT_CAPACITY;
    aux = realloc(s->item, sizeof(void*)*new_size);

    if(!aux)
        return ERROR;

    s->capacity = new_size;
    s->item = aux;

    for(i=s->capacity;i<new_size;i++){
        s->item[i] = NULL;
    }

    return OK;
}
Status stack_push(Stack *s, void *ele){

    if (!s || !ele)
        return ERROR;

    if(stack_isFull(s) == TRUE){
        if(stack_grow(s) == ERROR){
            return ERROR;
        }
    }

    s->top++;
    s->item[s->top] = ele;

    return OK;
}

void *stack_pop(Stack *s){

    void *ele = NULL;

    if (!s || stack_isEmpty(s) == TRUE)
    {
        return NULL;
    }

    ele = s->item[s->top];
    s->item[s->top] = NULL;
    s->top--;

    return ele;
}
void *stack_top(Stack *s){
    if (!s || stack_isEmpty(s) == TRUE)
    {
        return NULL;
    }

    return s->item[s->top];
}
Bool stack_isEmpty(Stack *s){
    if (!s)
    {
        return TRUE;
    }
 
    if (s->top == -1)
    {
        return TRUE;
    }

    return FALSE;
}
Bool stack_isFull(Stack *s){

    if (!s){
        return FALSE;
    }
    
    if (s->top == s->capacity){
        return TRUE;
    }

    return FALSE;
}
size_t stack_size(Stack *s){

    return s->top;
}
int stack_print(FILE *file, Stack *s, P_stack_ele_print f){
    int i;
    int n = 0;

    if (!file || !s)
    {
        return -1;
    }

    for (i = s->capacity; i >=0; i--){
        f(file, s->item[i]);
        if(i<s->capacity && f != float_print)
            printf("\n");
        n++;
    }
    return n;
}