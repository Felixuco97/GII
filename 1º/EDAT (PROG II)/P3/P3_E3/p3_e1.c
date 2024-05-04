#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "delivery.h"
#include "vertex.h"
#define MAX_STR_LENGTH 50

int main(int argc, char **argv){

    Delivery *d = NULL;
    FILE *f = NULL, *s = NULL;
    Vertex *v = NULL;
    char name[MAX_STR_LENGTH];
    char product_name[MAX_STR_LENGTH];
    char desc[MAX_STR_LENGTH];
    int size = 0;
    int i = 0;

    f = fopen(argv[1],"r");
    s = fopen(argv[2], "w");

    if(f == NULL || s == NULL){
        return -1;
    }

    d = delivery_init(name, product_name);

    if(d == NULL){
        return -1;
    }

    fscanf(f, "%s %s\n", name, product_name);
    fscanf(f, "%d\n", &size);

    for(i=0;i<size;i++){
        fgets(desc, MAX_STR_LENGTH, f);
        v = vertex_initFromString(desc);
        fprintf(s, "Adding: ");
        if(delivery_add(s, d, v, vertex_print) == ERROR){
            return -1;
        }
        fprintf(s, " to delivery: %s\n", name);
    }

    fprintf(s, "Running delivery plan for queue: \n");
    if(delivery_run_plan(s, d, vertex_print, vertex_free) == ERROR){
        return -1;
    }

    delivery_free(d);

    fclose(f);
    fclose(s);

    return 0;
}