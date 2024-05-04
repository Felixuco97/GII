/**
 * @file index.h
 * @authors Ignacio Martin, Felix Lopez
 */
#ifndef INDEX_H
#define INDEX_H

#include <stdlib.h>

#define BESTFIT 0
#define WORSTFIT 1
#define FIRSTFIT 2

typedef struct
{
    int key;         /* book id */
    long int offset; /* book is stored in disk at this position */
    size_t size;     /* book record size . This is a redundant
                    field that helps in the implementation */
} indexbook;

typedef struct
{
    indexbook *array;
    size_t used;
    size_t size;
} index_array;

void init_index_array(index_array *a, size_t size);

void free_index_array(index_array *a);

void insert_indexbook_end(index_array *a, indexbook ib);

void insert_indexbook(index_array *a, indexbook ib);

void delete_indexbook(index_array *a, size_t pos);

size_t binary_search(indexbook *a, size_t low, size_t high, int key);

void print_index(index_array *array);

void save_index_file(index_array *a, FILE *index_file);

void load_index_file(index_array *a, FILE *index_file);

#endif