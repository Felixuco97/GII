#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "delivery.h"
#include "vertex.h"
#include "graph.h"
#include "stack.h"
#define MAX_ARRAYS 10

Vertex *_graph_findVertexByTag(const Graph *g, char *tag);
Status _graph_insertEdgeFromIndices(Graph *g, const long origIx, const long destIx);
Vertex *_graph_findVertexById(const Graph *g, long id);
int _graph_getNumConnections(const Graph *g, int ix);
long *_graph_getConnections(const Graph *g, int ix);
void _graph_setVertexState(Graph *g, Label l);

Status graph_depthSearch(Graph *g, long from_id, long to_id){
    
    Status st = OK;
    Stack *s = NULL;
    Vertex *vo = NULL, *vi = NULL, *vf = NULL;
    long *array[MAX_ARRAYS];
    int i, n = 0;

    if(!g)
        st = ERROR;

    _graph_setVertexState(g, WHITE);

    s = stack_init();

    if(s == NULL)
        return ERROR;

    vi = _graph_findVertexById(g, from_id);
    vf = _graph_findVertexById(g, to_id);
    
    stack_push(s,vi);

    printf("From Vertex id: %ld\nTo Vertex id: %ld\nOutput:\n", vertex_getId(vi), vertex_getId(vf));

    while(stack_isEmpty(s) == FALSE && st == OK){
        vo = stack_pop(s);
        if(vertex_cmp(vo,vf) == 0){
            st = END;
        }
        if(vertex_getState(vo) == WHITE){
            vertex_setState(vo, BLACK);
        }
        if(_graph_getNumConnections(g, vertex_getIndex(vo))!=0){
            array[n] = _graph_getConnections(g, vertex_getIndex(vo));
            for(i=0;array[n][i]!='\0';i++){
                if(vertex_getState(_graph_findVertexById(g, array[n][i])) == WHITE){
                    st = stack_push(s, _graph_findVertexById(g, array[n][i]));
                }
            }
            n++;
        }
        vertex_print(stdout, vo);
        printf("\n");
        if(st == END){
            break;
        }
    }

    for(i=0;i<n;i++){
        free(array[i]);
    }

    stack_free(s);
    return st;
}

int main(int argc, char **argv){

    Graph *g = NULL;
    FILE *f = NULL;
    int numVertices = 0;
    char line[MAX_LINE_LENGTH];
    int i;
    long orig, dest;
    Vertex *vo = NULL, *vd = NULL;
    Vertex* vi = NULL, *vf = NULL;

    f = fopen(argv[1],"r");

    if(f == NULL)
        return -1;

    g = graph_init();

    if(g == NULL)
        return -1;

    fscanf(f, "%d\n", &numVertices);

    for(i=0;i<numVertices;i++){
        fgets(line,MAX_LINE_LENGTH,f);
        graph_newVertex(g, line);
    }

    for(i=0;i<numVertices;i++){
        fscanf(f,"%ld %ld\n", &orig, &dest);
        if(orig == 100){
            vi = _graph_findVertexById(g, orig);
        }
        if(dest == 700){
            vf = _graph_findVertexById(g, dest); 
        }
        vo = _graph_findVertexById(g, orig);
        vd = _graph_findVertexById(g, dest);
        if(_graph_insertEdgeFromIndices(g, vertex_getIndex(vo), vertex_getIndex(vd)) == ERROR){
            return -1;
        }
    }

    if(graph_print(stdout, g) == -1){
        return -1;
    }
    printf("---------DFS---------\n");
    if(graph_depthSearch(g, vertex_getId(vi), vertex_getId(vf)) == ERROR){
        return -1;
    }
    printf("\n---------BFS---------\n");
    if(graph_breathSearch(g, vertex_getId(vi), vertex_getId(vf)) == ERROR){
        return -1;
    }
    graph_free(g);
    fclose(f);

    return 0;
}