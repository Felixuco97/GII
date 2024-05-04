#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "list.h"
#include "file_utils.h"

int main(int argc, char **argv){

    FILE *f = NULL, *g = NULL;
    int n=0;
    float nota;
    List *initList = NULL;
    List *finList = NULL;
    int m = 0;
    float *ele = NULL;
    int size;

    initList = list_new();
    finList = list_new();

    if(!initList || !finList) return -1;

    f = fopen(argv[1],"r");
    g = fopen(argv[2], "w");

    if(!f || !g) return -1;

    fscanf(f, "%d", &size);
    fprintf(g,"SIZE: %d\n", size);

    while(n<size){
        fscanf(f,"%f", &nota);
        if(n%2 == 0){
            if(list_pushBack(initList,float_init(nota)) == ERROR){
                return -1;
            }
        } else {
            if(list_pushFront(initList, float_init(nota)) == ERROR){
                return -1;
            }
        }
        n++;
    }

    list_print(g, initList, float_print);
    fprintf(g, "\nFinished inserting. Now we extract from the beginning and insert in order:\n");

    m = n/2;

    while(n > m){
        ele = (float*)list_popFront(initList);
        if(list_pushInOrder(finList, ele, float_cmp, atoi(argv[3])) == ERROR){
            return -1;
        }
        n--;
        fprintf(g, "%.2f ", *ele);
        free(ele);
    }

    fprintf(g,"\n");
    fprintf(g,"Now we extract from the end and insert in order:\n");

    while (n > 0){
        ele = (float*)list_popBack(initList);
        if(list_pushInOrder(finList, ele, float_cmp, atoi(argv[3])) == ERROR){
            return -1;
        }
        n--;
        fprintf(g, "%.2f ", *ele);
        free(ele);
    }

    fprintf(g,"\nSIZE: %d\n", size);

    list_print(g, finList, float_print);

    fprintf(g,"\n");

    while(list_isEmpty(finList) == FALSE){
        ele = list_popFront(finList);
    }

    list_free(initList);
    list_free(finList);
    fclose(f);
    fclose(g);

    return 0;
}