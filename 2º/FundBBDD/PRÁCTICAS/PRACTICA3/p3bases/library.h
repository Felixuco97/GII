/**
 * @file library.h
 * @authors Ignacio Martin, Felix Lopez
 */
#ifndef LIBRARY_H
#define LIBRARY_H

#include "index.h"
#include "deleted.h"

#define MAX_STRING_LENGTH 128

void add_function(char *options, FILE *data_file, index_array *ia, deleted_array *da);

void find_function(index_array *a, int id, FILE *data);

void del_function(char *options, index_array *ia, deleted_array *da);

void print_records(index_array *a, FILE *data);

void exit_function(FILE *data_file, FILE *index_file, FILE *deleted_file, index_array ia, deleted_array da);

#endif
