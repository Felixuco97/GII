/**
 * @file deleted.c
 * @authors Ignacio Martin, Felix Lopez
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "deleted.h"

/**
 * @brief Inicializa un array de libros borrados 
 * 
 * @param a, puntero del array 
 * @param size, tamaño incial del array 
 */
void init_deleted_array(deleted_array *a, size_t size, int fit)
{
    a->array = malloc(size * sizeof(deleted_book));
    a->used = 0;
    a->size = size;
    a->fit = fit;
}

/**
 * @brief Libera la memoria de un deleted_array
 * 
 * @param a, puntero del array
 */
void free_deleted_array(deleted_array *a)
{
    if (a->array != NULL)
    {
        free(a->array);
        a->array = NULL;
    }
}

/**
 * @brief Inserta un libro borrado a un array
 * 
 * @param a, punteri al array 
 * @param db, deleted_book a insertar
 * @param fit, indica la estrategia de ordenación
 */
void insert_deleted_book(deleted_array *a, deleted_book db)
{
    size_t pos;

    if (!a)
    {
        return;
    }

    /*Comprueba que hay espacio*/
    if (a->used == a->size)
    {
        /*Si no hay espacio reserva mas memoria*/
        a->size *= 2;
        a->array = realloc(a->array, a->size * sizeof(deleted_book));
    }

    /*Si esta vacio lo inserta al inicio*/
    if (a->used == 0)
    {
        a->array[0] = db;
        a->used++;
        return;
    }

    /*Elige la posicion de insercion segun la estrategia*/
    if (a->fit == BESTFIT)
    {
       pos = search_pos_best(a, db.size); 
    }
    else if (a->fit == WORSTFIT)
    {
        pos = search_pos_worst(a, db.size);
    }
    else if (a->fit == FIRSTFIT)
    {
        pos = a->used;
        a->array[pos] = db;
        a->used++;
        return;
    }
    else
    {
        return;
    }

    /*Hace espacio e inserta el elemento*/
    memmove(&a->array[pos + 1], &a->array[pos], (a->used - pos) * sizeof(deleted_book));
    a->array[pos] = db;
    a->used++;
}

/**
 * @brief Elimina un deleted_book del array
 * 
 * @param a, puntero del array
 * @param pos, posicion del deleted_book a eliminar 
 */
void delete_deletedbook(deleted_array *a, size_t pos)
{
    if(!a)
    {
        return;
    }

    memmove(&(a->array[pos]), &(a->array[pos + 1]), (a->used - pos - 1) * sizeof(deleted_book));

    a->used--;
}

/**
 * @brief Hace una busqueda binaria para el caso best_fit
 * 
 * @param a, puntero al array
 * @param size, el valor a encontar 
 * @return size_t, posicion buscada
 */
size_t search_pos_best(deleted_array *a, size_t size)
{
    size_t low = 0;
    size_t high = a->used;

    /*Realiza una busqueda binaria de menor a mayor*/
    while (low < high) {
        size_t mid = low + (high - low) / 2;

        if (a->array[mid].size < size) {
            low = mid + 1;
        } else {
            high = mid;
        }
    }

    return low;
}

/**
 * @brief Hace una busqueda binaria para el caso worst_fit
 * 
 * @param a, puntero al array
 * @param size, el valor a encontar 
 * @return size_t, posicion buscada
 */
size_t search_pos_worst(deleted_array *a, size_t size)
{
    size_t low = 0;
    size_t high = a->used;

    /*Realiza una busqueda binaria de mayor a menor*/
    while (low < high) {
        size_t mid = low + (high - low) / 2;

        if (a->array[mid].size > size) {
            low = mid + 1;
        } else {
            high = mid;
        }
    }

    return low;
}

/**
 * @brief Imprime el contenido del array de libros borrados
 * 
 * @param a, puntero al array
 */
void print_deleted_books(deleted_array *a)
{
    size_t i = 0;
    deleted_book *db;

    if(!a)
    {
        return;
    }

    for (i = 0; i < a->used; i++)
    {
        db = &a->array[i];
        printf("Entry #%ld\n", i);
        printf("    offset: #%ld\n", db->offset);
        printf("    size: #%ld\n", db->size);
    }
}

/**
 * @brief Guarda un deleted_array en un fichero
 * 
 * @param a, puntero al array
 * @param index_file, archivo donde se guarda
 */
void save_deleted_file(deleted_array *a, FILE *deleted_file)
{
    size_t i;
    deleted_book db;

    if (!a || !deleted_file)
    {
        return;
    }

    /*Se posiciona al final del archivo*/
    fseek(deleted_file, 0, SEEK_END);

    /*Escribe la estrategia*/
    fwrite(&a->fit, sizeof(int), 1, deleted_file);

    /*Escribe cada registro borrado*/
    for (i = 0; i < a->used; i++)
    {
        db = a->array[i];
        fwrite(&db.offset, sizeof(long int), 1, deleted_file);
        fwrite(&db.size, sizeof(size_t), 1, deleted_file);
    }
}

/**
 * @brief Carga un deleted_array a partir de un archivo
 * 
 * @param a, puntero al array
 * @param deleted_file, archivo con los datos
 */
void load_deleted_file(deleted_array *a, FILE *deleted_file)
{
    deleted_book db;
    int fit;

    if (!deleted_file)
    {
        return;
    }

    /*Se posiciona al inicio del archivo*/
    fseek(deleted_file, 0, SEEK_SET);

    /*Lee la estrategia*/
    fread(&fit, sizeof(int), 1, deleted_file);

    /*Lee e inserta todos los deleted_books hasta que acaba el archivo*/
    while(fread(&db.offset, sizeof(long int), 1, deleted_file))
    {
        fread(&db.size, sizeof(size_t), 1, deleted_file);

        insert_deleted_book(a, db);
    }
}

/**
 * @brief Obtiene el offset del primer espacio disponible y actualiza el array
 * 
 * @param a, array de libros borrados 
 * @param size, tamaño minimo necesario
 * @return long int, offset donde escribir 
 */
long int get_available_space(deleted_array *a, size_t size)
{
    size_t i;
    deleted_book db;
    long int offset;

    for (i = 0; i < a->used; i++)
    {
        if (a->array[i].size >= size)
        { 
            offset = a->array[i].offset;
            db.size = a->array[i].size - size;
            db.offset = offset + sizeof(size_t) + size;
            delete_deletedbook(a, i);
            if (db.size > 0)
            {
                insert_deleted_book(a, db);
            }
            return offset;
        }
    }

    return -1;
}