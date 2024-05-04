#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "vertex.h"
#include "types.h"
#include "graph.h"

#define TAG_LENGTH 64
#define MAX_VTX 4096

int main(){

    Graph *g = NULL;
    long *array = NULL;
    int i;

    g = graph_init();

    if(g == NULL)
        return -1;

    printf("Inserting Madrid... result...: ");

    if(graph_newVertex(g, "id:111\ttag:Madrid\tstate:WHITE") == ERROR)
        return -1;

    printf("%d\n", graph_contains(g, 111));
    printf("Inserting Toledo... result...: ");

    if(graph_newVertex(g, "id:222\ttag:Toledo\tstate:WHITE") == ERROR)
        return -1;

    printf("%d\n", graph_contains(g, 222));
    printf("Inserting edge: 222 --> 111\n");

    graph_newEdge(g, 222, 111);

    printf("111 --> 222? ");

    if(graph_connectionExists(g, 111, 222) == TRUE){
        printf("Yes\n");
    } else {
        printf("No\n");
    }

    printf("222 --> 111? ");

    if(graph_connectionExists(g, 222, 111) == TRUE){
        printf("Yes\n");
    } else {
        printf("No\n");
    }

    printf("Number of connections from 111: %d\n", graph_getNumberOfConnectionsFromId(g, 111));
    printf("Number of connections from Toledo: %d\n", graph_getNumberOfConnectionsFromTag(g, "Toledo"));
    array = graph_getConnectionsFromTag(g, "Toledo");

    for(i=0;i<graph_getNumberOfConnectionsFromTag(g, "Toledo");i++){
        printf("Connections from Toledo: %ld \n", array[i]);
    }

    printf("Graph:\n");

    graph_print(stdout, g);

    free(array);
    graph_free(g);

    return 0;
}