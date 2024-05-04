#include "vertex.h"
#include "types.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

#define TAG_LENGTH 64

int main(){

    Vertex *v1 = NULL, *v2 = NULL;
    void *v3 = NULL;

    v1 = vertex_init();

    if(v1 == NULL)
        return -1;

    v2 = vertex_init();

    if(v2 == NULL)
        return -1;

    vertex_setId(v1,10);
    vertex_setTag(v1,"one");
    vertex_setState(v1,WHITE);

    vertex_setId(v2,20);
    vertex_setTag(v2,"two");
    vertex_setState(v2,BLACK);
    printf("[");
    vertex_print(stdout,v1);
    printf("][");
    vertex_print(stdout,v2);
    printf("]");
    printf("\nEquals? ");

    if(vertex_cmp(v1,v2)==0){
        printf("Yes\n");
    } else {
        printf("No\n");
    }

    printf("Vertex 2 tag: %s\n",vertex_getTag(v2));
    v3 = vertex_copy(v1);
    printf("Vertex 3 id: %ld\n", vertex_getId(v3));
    printf("[");
    vertex_print(stdout,v1);
    printf("][");
    vertex_print(stdout,v3);
    printf("]");
    printf("\nEquals? ");

    if(vertex_cmp(v1,v3)==0){
        printf("Yes\n");
    } else {
        printf("No\n");
    }  

    vertex_free(v1);
    vertex_free(v2);
    vertex_free(v3);

    return 0;
}
