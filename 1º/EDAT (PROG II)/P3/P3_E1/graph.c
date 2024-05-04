#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "graph.h"

/*Estructura de datos*/

struct _Graph{
    Vertex *vertices[MAX_VTX];
    Bool connections[MAX_VTX][MAX_VTX];
    int num_vertices;
    int num_edges;
};
/*START [Private_functions]*/
Vertex *_graph_findVertexByTag(const Graph *g, char *tag){


    int i;

    for(i=0;i<g->num_vertices;i++){
        if(strcmp(vertex_getTag(g->vertices[i]), tag) == 0)
            return g->vertices[i];
    }
    return NULL;
}

Vertex *_graph_findVertexById(const Graph *g, long id){

    int i;

    for(i=0;i<g->num_vertices;i++){
        if(vertex_getId(g->vertices[i]) == id)
            return g->vertices[i];
    }
    return NULL;
}

Status _graph_insertEdgeFromIndices(Graph *g, const long origIx, const long destIx){
 
    int i, j;

    if(g == NULL || origIx < 0 || destIx < 0){
        return ERROR;
    }

    i = (int) origIx;
    j = (int) destIx;

    g->connections[i][j] = TRUE;
    g->num_edges++;

    return OK;
}

int _graph_getNumConnections(const Graph *g, int ix){

    int jx;
    int n = 0;

    if(g == NULL || ix < 0)
        return -1;

    if(graph_contains(g,ix) == TRUE){
        for(jx=0;jx<g->num_vertices;jx++){
            if(graph_connectionExists(g, ix, jx) == TRUE){
                n++;
            }
        }
    }

    return n;
}

long *_graph_getConnections(const Graph *g, int ix){

    long *array = NULL;
    int jx;
    int n = 0;

    if(g == NULL || ix < 0)
        return NULL;

    array = (long*) malloc(_graph_getNumConnections(g, ix)*sizeof(long));

    if(array == NULL)
        return NULL;

    for(jx=0;jx<g->num_vertices;jx++){
        if(graph_connectionExists(g, ix, jx)){
            array[n] = vertex_getId(g->vertices[jx]);
            n++;
        }
    }

    return array;
}

void _graph_setVertexState(Graph *g, Label l){
    
    int i;

    for(i=0;i<g->num_vertices;i++){
        vertex_setState(g->vertices[i], l);
    }
}
/*END [Private_functions]*/
Graph * graph_init(){

    Graph *g = NULL;
    int i, j;

    g = (Graph*) malloc(sizeof(Graph));

    if(!g)
        return NULL;
    
    for(i=0;i<MAX_VTX;i++)
        g->vertices[i] = NULL;

    for(i=0;i<MAX_VTX;i++)
        for(j=0;j<MAX_VTX;j++)
            g->connections[i][j] = FALSE;

    g->num_vertices = 0;
    g->num_edges = 0;

    return g;
}

void graph_free(Graph *g){

    int i;

    if(g == NULL)
        return;

    for(i=0;i<g->num_vertices;i++){
        vertex_free(g->vertices[i]);
        }

    free(g);
}

Status graph_newVertex(Graph *g, char *desc){

    if(!g || !desc)
        return ERROR;

    g->vertices[g->num_vertices] = vertex_initFromString(desc);

    if(g->vertices[g->num_vertices] == NULL)
        return ERROR;

    vertex_setIndex(g->vertices[g->num_vertices], g->num_vertices);
    g->num_vertices++;

    return OK;
}

Status graph_newEdge(Graph *g, long orig, long dest){

    int i, j;

    if(g == NULL || orig < 0 || dest < 0){
        return ERROR;
    }

    for(i=0;i<g->num_vertices;i++){
        if(vertex_getId(g->vertices[i]) == orig){
            for(j=0;j<g->num_vertices;j++){
                if(vertex_getId(g->vertices[j]) == dest){
                    g->connections[i][j] = TRUE;
                    g->num_edges++;
                    return OK;
                }
            }
        }
    }

    return ERROR;
}

Bool graph_contains(const Graph *g, int ix){

    int i;

    g = (Graph*) g;

    if(g == NULL || ix < 0)
        return FALSE;

    for(i=0;i<g->num_vertices;i++){
        if(i == ix)
            return TRUE;
    }

    return FALSE;
}

int graph_getNumberOfVertices(const Graph *g){

    if(g == NULL)
        return -1;

    return g->num_vertices;
}

int graph_getNumberOfEdges(const Graph *g){

    if(g == NULL)
        return -1;

    return g->num_edges;
}

Bool graph_connectionExists(const Graph *g, int origIx, int destIx){

    if(g == NULL || origIx < 0 || destIx < 0)
        return FALSE;

    g = (Graph*) g;

    if(g->connections[origIx][destIx] == TRUE){
        return TRUE;
    }

    return FALSE;
}

int graph_getNumberOfConnectionsFromId(const Graph *g, long id){

    int i, j;
    int n = 0;

        for(i=0;i<g->num_vertices;i++){
            if(vertex_getId(g->vertices[i]) == id){
                for(j=0;j<g->num_vertices;j++){
                    if(graph_connectionExists(g, i, j) == TRUE){
                        n++;
                    }
                }
            }
        }

    return n;
}

long *graph_getConnectionsFromId(const Graph *g, long id){

    long *array = NULL;
    int i, j;
    int n = 0;

    if(g == NULL || id < 0)
        return NULL;

    array = (long*) malloc(graph_getNumberOfConnectionsFromId(g, id)*sizeof(long));

    if(array == NULL)
        return NULL;

    for(i=0;i<g->num_vertices;i++){
        if(vertex_getId(g->vertices[i]) == id)
            for(j=0;j<g->num_vertices;j++){
                if(graph_connectionExists(g, i, j)){
                    array[n] = vertex_getId(g->vertices[j]);
                    n++;
                }
            }
    }
    return array;
}

int graph_getNumberOfConnectionsFromTag(const Graph *g, char *tag){

    int i, j;
    int n = 0;

    if(g == NULL || tag == NULL)
        return -1;

    for(i=0;i<g->num_vertices;i++){
        if(strcmp(vertex_getTag(g->vertices[i]), tag) == 0){
            for(j=0;j<g->num_vertices;j++){
                if(graph_connectionExists(g, i, j) == TRUE){
                    n++;
                }
            }
        }
    }
    return n;
}

long *graph_getConnectionsFromTag(const Graph *g, char *tag){

    long *array = NULL;
    int i, j;
    int n = 0;

    if(g == NULL || tag == NULL)
        return NULL;

    g = (Graph*) g;

    array = (long*) malloc(graph_getNumberOfConnectionsFromTag(g, tag)*sizeof(long));

    if(array == NULL)
        return NULL;

    for(i=0;i<g->num_vertices;i++){
        if(strcmp(vertex_getTag(g->vertices[i]), tag) == 0){
            for(j=0;j<g->num_vertices;j++){
                if(graph_connectionExists(g, i, j) == TRUE){
                    array[n] = vertex_getId(g->vertices[j]);
                    n++;
                }
            }
            break;
        }
    }
    return array;
}

int graph_print (FILE *pf, const Graph *g){

    int i, j;
    int n = 0;

    if(pf == NULL || g == NULL)
        return -1;

    g = (Graph*) g;

    for(i=0;i<g->num_vertices;i++){
        vertex_print(stdout, g->vertices[i]);
        printf(": ");
        for(j=0;j<g->num_vertices;j++){
            if(graph_connectionExists(g, i, j) == TRUE){
                vertex_print(stdout, g->vertices[j]);
                printf("  ");
                n++;
            }
        }
        printf("\n");
    }
    return n;
}

Status graph_readFromFile (FILE *fin, Graph *g){

    int i, j;
    int numVertices = 0;
    char line[MAX_LINE_LENGTH];
    long origen = 0, destino = 0;

    if(!fin || !g)
        return ERROR;

    fscanf(fin,"%d\n", &numVertices);

    for(i=0;i<numVertices;i++){
        fgets(line,MAX_LINE_LENGTH,fin);
        graph_newVertex(g, line);
    }

    for(i=0;i<numVertices;i++){
        for(j=0;j<numVertices;j++){
            fscanf(fin,"%ld %ld\n", &origen, &destino);
            graph_newEdge(g, origen, destino);
        }
    }

    graph_print(stdout, g);

    return OK;
}