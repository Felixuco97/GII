#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "stack.h"
#include "file_utils.h"
#include "vertex.h"

#define MAX_LINE_LENGTH 80

typedef int (*P_stack_ele_cmp)(const void*, const void*);

Status mergeStacks(Stack *sin1, Stack *sin2, Stack *sout, P_stack_ele_cmp f){

    Status st = OK;
    Stack *ps = NULL;
    Vertex *e = NULL;
    while((stack_isEmpty(sin1) == FALSE) && (stack_isEmpty(sin2) == FALSE)){
        if(f(stack_top(sin1),stack_top(sin2)) > 0){
            e = stack_pop(sin1);
        }else{
            e = stack_pop(sin2);
        }
        stack_push(sout, e);
    }

    if(stack_isEmpty(sin1) == TRUE){
        ps = sin2;
    }else{
        ps = sin1;
    }

    while(stack_isEmpty(ps) == FALSE){
        e = stack_pop(ps);
        stack_push(sout,e);
    }

    return st;
}

int main(int argc, char *argv[])
{
    FILE *f1 = NULL, *f2 = NULL;
    Stack *s1= NULL, *s2 = NULL, *sout = NULL;
    Vertex *v1 = NULL, *v2 = NULL;
    char descr[MAX_LINE_LENGTH];
    int num = 0;
    int total = 0;
    int i;

    f1 = fopen(argv[1],"r");

    if(f1 == NULL)
        return -1;

    printf("Ranking 0: \n");

    s1 = stack_init();

    if(s1 == NULL)
        return -1;

    fscanf(f1,"%d\n", &num);
    printf("SIZE: %d\n", num);
    total+=num;
    for(i=0;i<num;i++){
        fgets(descr,MAX_LINE_LENGTH,f1);
        v1 = vertex_initFromString(descr);
        stack_push(s1, v1);
    }

    stack_print(stdout, s1, vertex_print);

    f2 = fopen(argv[2],"r");

    if(f2 == NULL)
        return -1;

    printf("Ranking 1: \n");

    s2 = stack_init();

    if(s2 == NULL)
        return -1;

    fscanf(f2,"%d\n", &num);
    printf("SIZE: %d\n", num);
    total+=num;
    for(i=0;i<num;i++){
        fgets(descr,MAX_LINE_LENGTH,f2);
        v2 = vertex_initFromString(descr);
        stack_push(s2, v2);
    }

    stack_print(stdout, s2, vertex_print);

    sout = stack_init();

    if(sout == NULL)
        return -1;

    printf("Joint Ranking:\n");
    printf("SIZE: %d", total);
    if(mergeStacks(s1, s2, sout, vertex_cmp) == OK){
        stack_print(stdout, sout, vertex_print);
    }

    while(stack_isEmpty(sout) == FALSE){
        vertex_free(stack_pop(sout));
    }

    stack_free(s1);
    stack_free(s2);
    stack_free(sout);

    fclose(f1);
    fclose(f2);

    return 0;
}