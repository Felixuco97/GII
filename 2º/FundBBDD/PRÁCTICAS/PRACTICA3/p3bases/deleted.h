/**
 * @file deleted.h
 * @authors Ignacio Martin, Felix Lopez
 */
#ifndef DELETED_H
#define DELETED_H

#include <stdlib.h>

#define BESTFIT 0
#define WORSTFIT 1
#define FIRSTFIT 2

typedef struct
{
    long int offset;
    size_t size;
} deleted_book;

typedef struct
{
    deleted_book *array;
    size_t used;
    size_t size;
    int fit;
} deleted_array;

void init_deleted_array(deleted_array *a, size_t size, int fit);

void free_deleted_array(deleted_array *a);

void insert_deleted_book(deleted_array *a, deleted_book db);

void delete_deletedbook(deleted_array *a, size_t pos);

size_t search_pos_best(deleted_array *a, size_t size);

size_t search_pos_worst(deleted_array *a, size_t size);

void print_deleted_books(deleted_array *array);

void save_deleted_file(deleted_array *a, FILE *deleted_file);

void load_deleted_file(deleted_array *a, FILE *deleted_file);

long int get_available_space(deleted_array *a, size_t size);

#endif