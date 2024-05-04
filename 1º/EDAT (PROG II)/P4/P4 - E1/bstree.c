#include "bstree.h"

#include <stdio.h>
#include <stdlib.h>

/* START [_BSTNode] */
typedef struct _BSTNode {
  void *info;
  struct _BSTNode *left;
  struct _BSTNode *right;
} BSTNode;
/* END [_BSTNode] */

/* START [_BSTree] */
struct _BSTree {
  BSTNode *root;
  P_ele_print print_ele;
  P_ele_cmp cmp_ele;
};
/* END [_BSTree] */

/*Funciones privadas*/
BSTNode *_bst_find_min_rec(BSTNode *pn);
BSTNode *_bst_find_max_rec(BSTNode *pn);
Bool tree_contains_rec(BSTNode *pn, const void *ele, P_ele_cmp cmp);
BSTNode *tree_insert_rec(BSTNode *pn, const void *ele, P_ele_cmp cmp);
BSTNode *_bst_remove_rec(BSTNode *pn, const void *ele, P_ele_cmp cmp);
void _bst_node_free(BSTNode *pn);
void _bst_node_free_rec(BSTNode *pn);
int _bst_depth_rec(BSTNode *pn);
int _bst_size_rec(BSTNode *pn);
int _bst_preOrder_rec(BSTNode *pn, FILE *pf, P_ele_print print_ele);
int _bst_inOrder_rec(BSTNode *pn, FILE *pf, P_ele_print print_ele);
int _bst_postOrder_rec(BSTNode *pn, FILE *pf, P_ele_print print_ele);
/********************/

/*** BSTNode TAD private functions ***/
BSTNode *_bst_node_new() {
  BSTNode *pn = NULL;

  pn = malloc(sizeof(BSTNode));
  if (!pn) {
    return NULL;
  }

  pn->left = NULL;
  pn->right = NULL;
  pn->info = NULL;

  return pn;
}
/*Inicio primitivas nuevas*/
void *tree_find_min(BSTree *tree){

  BSTNode *pn = NULL;

  if(!tree) return NULL;

  pn = _bst_find_min_rec(tree->root);

  return pn->info;
}

BSTNode *_bst_find_min_rec(BSTNode *pn){

  if(!pn->left) return pn;

  return _bst_find_min_rec(pn->left);
}

void *tree_find_max(BSTree *tree){

  BSTNode *pn = NULL;

  if(!tree) return NULL;

  pn = _bst_find_max_rec(tree->root);

  return pn->info;
}

BSTNode *_bst_find_max_rec(BSTNode *pn){

  if(!pn->right) return pn;

  return _bst_find_max_rec(pn->right);
}

Bool tree_contains(BSTree *tree, const void *elem){

  Bool contains = TRUE;

  if(!tree || !elem) return FALSE;

  elem = (void*) elem;

  if(tree_isEmpty(tree) == TRUE){
    return FALSE;
  }

  contains = tree_contains_rec(tree->root, elem, tree->cmp_ele);

  return contains;
}

Bool tree_contains_rec(BSTNode *pn, const void *ele, P_ele_cmp cmp){

    if(!ele) return FALSE;

    if(!pn) return FALSE;

    if(cmp(ele, pn->info) > 0){
      return tree_contains_rec(pn->right, ele, cmp);
    } else if(cmp(ele, pn->info) < 0){
      return tree_contains_rec(pn->left, ele, cmp);
    } else{
      return TRUE;
    }
}

Status tree_insert(BSTree *tree, const void *elem){

  BSTNode *pn = NULL;

  if(!tree || !elem) return ERROR;

  elem = (void*) elem;

  pn = tree_insert_rec(tree->root, elem, tree->cmp_ele);

  if(!pn) return ERROR;

  tree->root = pn;

  return OK;
}

BSTNode *tree_insert_rec(BSTNode *pn, const void *ele, P_ele_cmp cmp){

  if(!pn){

    pn = _bst_node_new();

    if(!pn) return NULL;

    pn->info = (void*) ele;

    return pn;
  }

  if(cmp(ele, pn->info) < 0){
    pn->left = tree_insert_rec(pn->left, ele, cmp);
  } else if(cmp(ele, pn->info) > 0){
    pn->right = tree_insert_rec(pn->right, ele, cmp);
  } else {
    return NULL;
  }
  return pn;
}

Status tree_remove(BSTree *tree, const void *elem){

  BSTNode *pn = NULL;

  if(!tree || !elem) return ERROR;

  pn = _bst_remove_rec(tree->root, elem, tree->cmp_ele);

  if(!pn) return ERROR;

  return OK;
}

BSTNode *_bst_remove_rec(BSTNode *pn, const void *ele, P_ele_cmp cmp){

  BSTNode *ret_node = NULL;
  BSTNode *aux_node = NULL;

  if(!ele) return NULL;

  ele = (void*) ele;

  if(!pn) return NULL;

  if(cmp(ele, pn->info) > 0){
    pn->right = _bst_remove_rec(pn->right, ele, cmp);
  } else if (cmp(ele, pn->info) < 0){
    pn->left = _bst_remove_rec(pn->left, ele, cmp);
  } else {
    if(pn->left == NULL && pn->right == NULL){
      _bst_node_free(pn);
      return NULL;
    } else if(pn->left == NULL && pn->right != NULL){
      ret_node = pn->right;
      _bst_node_free(pn);
      return ret_node;
    } else if(pn->left != NULL && pn->right == NULL){
      ret_node = pn->left;
      _bst_node_free(pn);
      return ret_node;
    } else {
      aux_node = _bst_find_min_rec(pn->right);
      pn->info = aux_node->info;
      pn->right = _bst_remove_rec(pn->right, aux_node->info, cmp);
      return pn;
    }
  }
    return pn;
}

/*Fin primitivas nuevas*/
void _bst_node_free(BSTNode *pn) {
  if (!pn) {
    return;
  }

  free(pn);
}

void _bst_node_free_rec(BSTNode *pn) {
  if (!pn) {
    return;
  }

  _bst_node_free_rec(pn->left);
  _bst_node_free_rec(pn->right);
  _bst_node_free(pn);

  return;
}

int _bst_depth_rec(BSTNode *pn) {
  int depth_l, depth_r;

  if (!pn) {
    return 0;
  }

  depth_l = _bst_depth_rec(pn->left);
  depth_r = _bst_depth_rec(pn->right);

  if (depth_r > depth_l) {
    return depth_r + 1;
  } else {
    return depth_l + 1;
  }
}

int _bst_size_rec(BSTNode *pn) {
  int count = 0;

  if (!pn) {
    return count;
  }

  count += _bst_size_rec(pn->left);
  count += _bst_size_rec(pn->right);

  return count + 1;
}

int _bst_preOrder_rec(BSTNode *pn, FILE *pf, P_ele_print print_ele) {
  int count = 0;

  if (!pn) {
    return count;
  }

  count += print_ele(pf, pn->info);
  count += _bst_preOrder_rec(pn->left, pf, print_ele);
  count += _bst_preOrder_rec(pn->right, pf, print_ele);

  return count;
}

int _bst_inOrder_rec(BSTNode *pn, FILE *pf, P_ele_print print_ele) {
  int count = 0;

  if (!pn) {
    return count;
  }

  count += _bst_inOrder_rec(pn->left, pf, print_ele);
  count += print_ele(pf, pn->info);
  count += _bst_inOrder_rec(pn->right, pf, print_ele);

  return count;
}

int _bst_postOrder_rec(BSTNode *pn, FILE *pf, P_ele_print print_ele) {
  int count = 0;

  if (!pn) {
    return count;
  }

  count += _bst_postOrder_rec(pn->left, pf, print_ele);
  count += _bst_postOrder_rec(pn->right, pf, print_ele);
  count += print_ele(pf, pn->info);

  return count;
}

/*** BSTree TAD functions ***/
BSTree *tree_init(P_ele_print print_ele, P_ele_cmp cmp_ele) {
  BSTree *tree;

  if (!print_ele || !cmp_ele) {
    return NULL;
  }

  tree = malloc(sizeof(BSTree));
  if (!tree) {
    return NULL;
  }

  tree->root = NULL;
  tree->print_ele = print_ele;
  tree->cmp_ele = cmp_ele;

  return tree;
}

void tree_destroy(BSTree *tree) {
  if (!tree) {
    return;
  }

  _bst_node_free_rec(tree->root);
  free(tree);

  return;
}

Bool tree_isEmpty(const BSTree *tree) {
  if (!tree || !tree->root) {
    return TRUE;
  }
  return FALSE;
}

int tree_depth(const BSTree *tree) {
  if (!tree) {
    return -1;
  }

  return _bst_depth_rec(tree->root);
}

size_t tree_size(const BSTree *tree) {
  if (!tree) {
    return -1;
  }

  return _bst_size_rec(tree->root);
}

int tree_preOrder(FILE *f, const BSTree *tree) {
  if (!f || !tree) {
    return -1;
  }

  return _bst_preOrder_rec(tree->root, f, tree->print_ele) + fprintf(f, "\n");
}

int tree_inOrder(FILE *f, const BSTree *tree) {
  if (!f || !tree) {
    return -1;
  }

  return _bst_inOrder_rec(tree->root, f, tree->print_ele) + fprintf(f, "\n");
}

int tree_postOrder(FILE *f, const BSTree *tree) {
  if (!f || !tree) {
    return -1;
  }

  return _bst_postOrder_rec(tree->root, f, tree->print_ele) + fprintf(f, "\n");
}

/**** TODO: find_min, find_max, insert, contains, remove ****/

