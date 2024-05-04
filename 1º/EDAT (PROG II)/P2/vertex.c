#include "vertex.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

/* Estructura de datos */
/* START [_Vertex] */
struct _Vertex{
  long id;
  char tag[TAG_LENGTH];
  Label state;
  int index;
};
/* END [_Vertex] */
/* Private functions: */
Status vertex_setField (Vertex *v, char *key, char *value);

Vertex * vertex_init (){

  Vertex *v;

  v = (Vertex*) malloc(sizeof(Vertex));

  if(v == NULL)
    return NULL;

  return v;
}

void vertex_free(void *v){

  if(v == NULL)
    return;

  free(v);
}

long vertex_getId(const Vertex *v){

  if(v == NULL)
    return -1;

  v = (Vertex*) v;

  return v->id;
}

const char* vertex_getTag(const Vertex * v){

  if(v == NULL)
    return NULL;

  v = (Vertex*) v;

  return v->tag;
}

Label vertex_getState(const Vertex * v){

  if(v == NULL)
    return ERROR_VERTEX;

  v = (Vertex*) v;

  return v->state;
}

int vertex_getIndex(const Vertex *v){

  if(v == NULL)
    return -1;

  v = (Vertex*) v;

  return v->index;
}

Status vertex_setId (Vertex * v, const long id){

  long ID;

  if(v == NULL || id < 0)
    return ERROR;
  
  ID = (long) id;

  v->id = ID;

  return OK;

}

Status vertex_setTag (Vertex * v, const char * tag){
  
  if(v == NULL || tag == NULL)
    return ERROR;

  tag = (char*) tag;

  strcpy(v->tag,tag);

  return OK;
}

Status vertex_setState (Vertex * v, const Label state){

  Label s;

  if(v == NULL || state == ERROR_VERTEX)
      return ERROR;

  s = (Label) state;

  v->state = s;

  return OK;
}

Status vertex_setIndex(Vertex *v, const int index){

  int i;

  if(v == NULL)
    return ERROR;

  i = (int) index;

  v->index = i;

  return OK;
}

int vertex_cmp (const void * v1, const void * v2){

  if(v1 == NULL || v2 == NULL)
    return 0;

  v1 = (Vertex*) v1;
  v2 = (Vertex*) v2;

  if(vertex_getId(v1) == vertex_getId(v2)){
    return strcmp(vertex_getTag(v1),vertex_getTag(v2));
  }
  else{
    return vertex_getId(v1) - vertex_getId(v2);
  }
}

void * vertex_copy (const void * src){

  Vertex *trg = NULL;
  Vertex *v;

  if(src == NULL)
    return NULL;

  v = (Vertex*) src;

  trg = vertex_init();

  if(trg == NULL)
    return NULL;

  if(vertex_setId(trg,vertex_getId(v)) == ERROR)
    return NULL;
  if(vertex_setTag(trg,vertex_getTag(v)) == ERROR)
    return NULL;
  if(vertex_setState(trg,vertex_getState(v)) == ERROR)
    return NULL;

  return trg;
}

int vertex_print (FILE * pf, const void * v){

  Vertex *v1;

  if(pf == NULL || v == NULL)
    return -1;

  v1 = (Vertex*) v;

  fprintf(stdout,"[%ld, %s, %d, %d]", v1->id, v1->tag, v1->state, v1->index);

  return -1;
}

Status vertex_setField (Vertex *v, char *key, char *value) {
  if (!key || !value) return ERROR;

  if (strcmp(key, "id") == 0) {
    return vertex_setId(v, atol(value));
  } else if (strcmp(key, "tag") == 0) {
    return vertex_setTag(v, value);
  } else if (strcmp(key, "state") == 0) {
    return vertex_setState(v, (Label)atoi(value));
  }

  return ERROR;
}

/*----------------------------------------------------------------------------------------*/
Vertex *vertex_initFromString(char *descr){
  char buffer[1024];
  char *token;
  char *key;
  char *value;
  char *p;
  Vertex *v;

  /* Check args: */
  if (!descr) return NULL;

  /* Allocate memory for vertex: */
  v = vertex_init();
  if (!v) return NULL;

  /* Read and tokenize description: */
  sprintf(buffer, "%s", descr);
  token = strtok(buffer, " \t\n");
  while (token) {
    p = strchr(token, ':');
    if (!p) continue;

    *p = '\0';
    key = token;
    value = p+1;

    vertex_setField(v, key, value);

    token = strtok(NULL, " \t\n");
  }

  return v;
}

/**  rest of the functions in vertex.h **/
