#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "stack.h"
#include "file_utils.h"

typedef int (*P_stack_ele_cmp)(const void*, const void*);

Status mergeStacks(Stack *sin1, Stack *sin2, Stack *sout, P_stack_ele_cmp f){

    Status st = OK;
    Stack *ps = NULL;
    float *e = NULL;

    while((stack_isEmpty(sin1) == FALSE) && (stack_isEmpty(sin2) == FALSE)){
        if((f(stack_top(sin1),stack_top(sin2)) > 0)){
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

int main(int argc, char **argv){

    FILE *f1 = NULL, *f2 = NULL;
    Stack *s1 = NULL, *s2 = NULL, *sout = NULL;
    float *elem = NULL;
    float ele;
    int num = 0;
    int total = 0;
    int i;

    f1 = fopen(argv[1],"r");
    f2 = fopen(argv[2],"r");

    if(f1 == NULL || f2 == NULL)
        return -1;

    printf("Ranking 0: \n");

    s1 = stack_init();
    s2 = stack_init();
    sout = stack_init();

    if(s1 == NULL || s2 == NULL || sout == NULL)
        return -1;

    fscanf(f1,"%d", &num);
    total+=num;
    printf("SIZE: %d\n", num);

    for(i=0;i<num;i++){
        fscanf(f1, "%f", &ele);
        elem = float_init(ele);
        stack_push(s1, elem);
    }

    stack_print(stdout, s1, float_print);

    printf("Ranking 1: \n");

    fscanf(f2,"%d", &num);
    total+=num;
    printf("SIZE: %d\n", num);
    
    for(i=0;i<num;i++){
        fscanf(f2, "%f", &ele);
        elem = float_init(ele);
        stack_push(s2, elem);
    }

    stack_print(stdout, s2, float_print);

    printf("Joint Ranking: \n");

    if(mergeStacks(s1, s2, sout, float_cmp) == OK){
        printf("SIZE: %d\n", total);
        stack_print(stdout, sout, float_print);
    }

    while(stack_isEmpty(sout) == FALSE){
        elem = stack_pop(sout);
        float_free(elem);
    }

    stack_free(s1);
    stack_free(s2);
    stack_free(sout);
    fclose(f1);
    fclose(f2);

    return 0;
}