/**
 * @file  delivery.c
 * @author Profesores EDAT
 * @date 10 March 2023
 * @version 1.0
 * @brief Library to manage ADT Delivery
 *
 * @details
 *
 * @see
 */

#include <string.h>
#include <stdlib.h>
#include "delivery.h"

struct _Delivery {
  char *name;
  char *product_name;
  Queue *plan;
};

Delivery *delivery_init (char *name, char *productName){
	Delivery *d;

  if ((name==NULL) || (productName==NULL)){
    return NULL;
  }

	d = (Delivery*) malloc (sizeof(Delivery));
	if (!d){
    return NULL;
  }

  d->name = (char*) calloc(strlen(name)+1, sizeof(char));
  if (d->name == NULL){
    free(d);
    return NULL;
  }
  strcpy(d->name, name);
  
  d->product_name = (char*) calloc(strlen(productName)+1, sizeof(char));
  if (d->product_name == NULL){
    free(d->name);
    free(d);
    return NULL;
  }
  strcpy(d->product_name, productName);

  d->plan = queue_new();
  if (d->plan == NULL){
    free(d->name);
    free(d->product_name);
    free(d);
    return NULL;
  }

	return d;
}

void delivery_free (void *p){
  Delivery *d;

  if (p == NULL){
    return;
  }
  
  d = (Delivery*) p;

  free(d->name);
  free(d->product_name);
  queue_free(d->plan);
  free(d);
}

void delivery_free_products (Delivery *d, p_element_free ffree) {
  if (!(d)) {
    return;
  }

  while (queue_isEmpty(d->plan) == FALSE) {
    ffree(queue_pop(d->plan));
  }

  return;
}

const char *delivery_getName (const Delivery *d){
  if (d == NULL){
    return NULL;
  }

  return d->name;
}

const char *delivery_getProductName (const Delivery *d){
  if (d == NULL){
    return NULL;
  }

  return d->product_name;
}

Queue *delivery_getPlan (Delivery *d){
  if (d == NULL){
    return NULL;
  }

  return d->plan;
}

Status delivery_add(FILE *pf, Delivery *d, void *p, p_element_print f){
  /* TO DO */
  
  if(!pf || !d || !p || !f){
    return ERROR;
  }

  if(queue_push(d->plan, p) == ERROR){
    return ERROR;
  }

  f(pf, p);

  return OK;
}

Status delivery_run_plan(FILE *pf, Delivery *d, p_element_print fprint, p_element_free ffree){
  /* TO DO */

  void *e = NULL;
  Queue *qaux = NULL;

  
  if(!pf || !d || !fprint || !ffree){
    return ERROR;
  }

  qaux = queue_new();

  if(!qaux){
    return ERROR;
  }

  while(queue_isEmpty(d->plan) == FALSE){
    e = queue_pop(d->plan);
    fprint(pf, e);
    queue_push(qaux,e);
  }
  fprintf(pf, "\n");

  while(queue_isEmpty(qaux) == FALSE){
    fprintf(pf, "Delivering vacuna_pfizer requested by %s to ", d->name);
    e = queue_pop(qaux);
    fprint(pf, e);
    ffree(e);
    fprintf(pf, "\n");
  }

  queue_free(qaux);

  return OK;
}

int delivery_cmp (const void *p1, const void *p2){
  int c = 0;
  Delivery *d1, *d2;

  if ((p1 == NULL) || (p2 == NULL)){
    return c;
  }
  
  d1 = (Delivery*) p1;
  d2 = (Delivery*) p2;
  
  c = strcmp(d1->name, d2->name);
  if (c == 0){
    c = strcmp(d1->product_name, d2->product_name);
    if (c == 0){
      c = queue_size(d1->plan) - queue_size(d2->plan);
    }
  }
  
  return c;
}

void *delivery_copy (const void *src){
  Delivery *d, *trg;
  int plan_size, i;
  void *e;
  
  if (src == NULL){
    return NULL;
  }
  
  d = (Delivery*) src;
  trg = delivery_init(d->name, d->product_name);
  
  plan_size = queue_size(d->plan);
  for (i = 0; i<plan_size; i++){
    e = queue_pop(d->plan);
    queue_push(trg->plan, e);
    queue_push(d->plan, e);
  }
  
  return trg;
}

int delivery_print (FILE *pf, const void *p, p_queue_ele_print f){
  Delivery * d;
  int n=0;

  if ((pf == NULL) || (p == NULL)){
    return -1;
  }

  d = (Delivery*) p;
  n += fprintf(pf, "%s delivers %s\n", d->name, d->product_name);
  n += queue_print(pf, d->plan, f);

  return n;
}
