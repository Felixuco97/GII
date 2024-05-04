#include "vertex.h"
#include "types.h"
#include "graph.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

#define TAG_LENGTH 64

int main(){

    Graph *g = NULL;
    FILE *f = NULL;

    g = graph_init();

    if(g == NULL)
        return -1;

    f = fopen("MyGraph.log","r");

    if(graph_readFromFile(f, g) == ERROR)
        return -1;

    graph_free(g);

    fclose(f);

    return 0;
}
