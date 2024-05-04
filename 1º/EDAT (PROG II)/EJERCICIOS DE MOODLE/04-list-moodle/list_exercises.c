#include "list_exercises.h"
#include "elements.h"
#include "list.h"
#include "types.h"

void list_delete(List *pl, const void *elem, elem_cmp_fn compare) {

    Node *pn = NULL;
    Node *prev = NULL;

    if(!pl || !pl->first || !elem || !compare){
        return;
    }

    elem = (void*) elem;

    pn = pl->first;

    if(compare(pl->first->info, elem) == 0){
        pl->first = pn->next;
        free(pn);
        return;
    }

    while(pn->next != NULL){
        prev = pn;
        pn = pn->next;
        if(compare(pn->info, elem) == 0){
            prev->next = pn->next;
            free(pn);
            break;
        }
    }
    
    return;
}

List *list_copy(List *pl) { 

    List *pl_copy = NULL;
    Node *pn = NULL;
    Node *node_copy = NULL;
    Node *prev = NULL;
    void *ele = NULL;

    if(!pl) return NULL;

    pl_copy = list_new();

    if(!pl_copy) return NULL;

    if(!pl->first){
        pl_copy->first = pl->first;
    }
    else {
        pn = pl->first;

        while(pn->next != NULL){
            ele = pn->info;
            node_copy = node_new();
            if(!node_copy) return NULL;
            if(pl_copy->first == NULL){
                pl_copy->first = node_copy;
                node_copy->info = ele;
            } else {
                prev->next = node_copy;
                node_copy->info = ele;
            }
            prev = node_copy;
            pn = pn->next;
        }

        ele = pn->info;
        node_copy = node_new();
        if(!node_copy) return NULL;
        prev->next = node_copy;
        node_copy->info = ele;
    }

    return pl_copy; 
}