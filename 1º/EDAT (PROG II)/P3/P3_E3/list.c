#include <stdio.h>
#include <stdlib.h>
#include "list.h"

typedef struct _NodeList{
    void *data;
    struct _NodeList *next;
} NodeList;

struct _List{
    NodeList *last;
};

/*PRIMITIVAS DE UNA LISTA*/

List *list_new(){

    List *pl = NULL;

    pl = (List*) malloc(sizeof(List));

    if(!pl) return NULL;

    pl->last = NULL;

    return pl;
}
Bool list_isEmpty(const List *pl){

    if(!pl) return TRUE;

    if(pl->last == NULL) return TRUE;

    return FALSE;
}
Status list_pushFront(List *pl, void *e){

    NodeList *pn = NULL;

    if(!pl || !e) return ERROR;

    pn = node_new();

    if(pn == NULL) return ERROR;

    pn->data = e;

    if(list_isEmpty(pl) == TRUE){
        pn->next = pn;
        pl->last = pn;
    } else {
        pn->next = pl->last->next;
        pl->last->next = pn;
    }

    return OK;
}
Status list_pushBack(List *pl, void *e){

    NodeList *pn = NULL;

    if(!pl || !e) return ERROR;

    pn = node_new();

    if(pn == NULL) return ERROR;

    pn->data = e;

    if(list_isEmpty(pl) == TRUE){
        pn->next = pn;
        pl->last = pn;
    } else {
        pn->next = pl->last->next;
        pl->last->next = pn;
        pl->last = pn;
    }

    return OK;
}
Status list_pushInOrder (List *pl, void *e, P_ele_cmp f, int order){

    NodeList *pn = NULL;
    NodeList *qn = NULL;
    NodeList *prev = NULL;

    if(!pl || !e || !f || (order!=1 && order!=-1)) return ERROR;

    pn = node_new();

    if(!pn) return ERROR;

    pn->data = e;

    if(list_isEmpty(pl) == TRUE){
        pl->last = pn;
        pn->next = pl->last;

        return OK;
    }

    qn = pl->last->next;
    prev = pl->last;

        if(f(pn->data, pl->last->data)*order > 0){
            list_pushBack(pl, e);
        }
        if(f(pn->data, pl->last->data)*order < 0){
            while(qn != pl->last){
                if(f(pn->data, qn->data)*order < 0){
                    prev->next = pn;
                    pn->next = qn;
                    break;
                }
                prev = qn;
                qn = qn->next;
            }
            if(f(pn->data, qn->data)*order < 0){
                prev->next = pn;
                pn->next = qn;
            }
        }

    return OK;
}
void *list_popFront(List *pl){

    NodeList *pn = NULL;
    void *pe = NULL;

    if(!pl || list_isEmpty(pl) == TRUE) return NULL;

    pn = pl->last->next;
    pe = pn->data;

    if(pl->last->next == pl->last){
        pl->last = NULL;
    } else {
        pl->last->next = pn->next;
    }

    free(pn);

    return pe;
}
void *list_popBack(List *pl){

    NodeList *pn = NULL;
    void *pe = NULL;

    if(!pl || list_isEmpty(pl) == TRUE) return NULL;

    if(pl->last->next == pl->last){
        pe = pl->last->data;
        free(pl->last);
        pl->last = NULL;

        return pe;
    }

    pn = pl->last;

    while(pn->next != pl->last){
        pn = pn->next;
    }

    pe = pl->last->data;
    pn->next = pl->last->next;
    free(pl->last);
    pl->last = pn;

    return pe;
}
void list_free(List *pl){

    if(!pl) return;

    while(list_isEmpty(pl) == FALSE){
        list_popBack(pl);
    }

    free(pl);
}
size_t list_size(const List *pl){

    int n = 0;

    NodeList *pn = NULL;

    pn = pl->last;

    while(pn->next != pl->last){
        pn = pn->next;
        n++;
    }

    return n;
}
int list_print(FILE *fp, const List *pl, P_ele_print f){

    int n = 0;

    NodeList *pn = NULL;

    if(!fp || !pl || !f) return -1;

    pl = (List*) pl;

    pn = pl->last;

    while(pn->next != pl->last){
        pn = pn->next;
        f(fp, pn->data);
        n++;
    }

    f(fp, pl->last->data);

    return n;
}

/*PRIMITIVA DE UN NODO*/

NodeList *node_new(){

    NodeList *pn = NULL;

    pn = (NodeList*) malloc(sizeof(NodeList));

    if(!pn) return NULL;

    pn->data = NULL;
    pn->next = NULL;

    return pn;
}