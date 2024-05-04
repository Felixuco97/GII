#include "bstree.h"
#include "search_queue.h"
#include <stdlib.h>
#include <string.h>

struct _SearchQueue{
    BSTree *data;
};

SearchQueue *search_queue_new(P_ele_print print_ele, P_ele_cmp cmp_ele){

    SearchQueue *sq = NULL;

    sq = (SearchQueue*) malloc(sizeof(SearchQueue));

    if(!sq) return NULL;

    sq->data = tree_init(print_ele, cmp_ele);

    if(!sq->data){
        search_queue_free(sq);
        return NULL;
    }

    return sq;
}

void search_queue_free(SearchQueue *q){

    if(!q) return;

    if(q->data!=NULL)
        tree_destroy(q->data);

    free(q);
}

Bool search_queue_isEmpty(const SearchQueue *q){

    if(!q || !q->data || tree_isEmpty(q->data)) return TRUE;

    return FALSE;
}

Status search_queue_push(SearchQueue *q, void *ele){

    if(!q || !ele){
        return ERROR;
    }

    if(tree_insert(q->data, ele) == ERROR){
        return ERROR;
    }

    return OK;
}

void *search_queue_pop(SearchQueue *q){

    void *ele = NULL;

    if(!q) return NULL;

    ele = tree_find_min(q->data);

    if(tree_remove(q->data, ele) == ERROR)
        return NULL;
    
    return ele;
}

void *search_queue_getFront(const SearchQueue *q){

    if(!q) return NULL;

    q = (SearchQueue*) q;

    return tree_find_min(q->data);
}

void *search_queue_getBack(const SearchQueue *q){

    if(!q) return NULL;

    q = (SearchQueue*) q;

    return tree_find_max(q->data);
}

size_t search_queue_size(const SearchQueue *q){

    if(!q) return -1;

    q = (SearchQueue*) q;

    return tree_size(q->data);
}

int search_queue_print(FILE *fp, const SearchQueue *q){

    if(!fp || !q) return -1;

    return tree_inOrder(fp, q->data);
}