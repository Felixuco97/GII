/**
 * @file index.c
 * @authors Ignacio Martin, Felix Lopez
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "index.h"

/**
 * @brief Reserva memoria para el array de indices y lo inicializa 
 * 
 * @param a, puntero del array a inicializar
 * @param size, tamaño con el que se crea el array
 */
void init_index_array(index_array *a, size_t size)
{
    a->array = malloc(size * sizeof(indexbook));
    a->used = 0;
    a->size = size;
}

/**
 * @brief Libera la memoria de un index_array
 * 
 * @param a, puntero al array
 */
void free_index_array(index_array *a)
{
    if (a->array != NULL)
    {
        free(a->array);
        a->array = NULL;
    }
}

/**
 * @brief Inserta un indexbook al final del array
 * 
 * @param a, puntero al array
 * @param ib, indexbook a insertar
 */
void insert_indexbook_end(index_array *a, indexbook ib)
{
    if (!a)
    {
        return;
    }

    /*Si no hay espacio reserva mas memoria*/
    if (a->used == a->size)
    {
        a->size *= 2;
        a->array = realloc(a->array, a->size * sizeof(indexbook));
    }

    a->array[a->used++] = ib;
}

/**
 * @brief Inserta un indexbook de forma ordenana en un array
 * 
 * @param a, puntero del array
 * @param ib, indexbook a insertar
 */
void insert_indexbook(index_array *a, indexbook ib)
{
    size_t pos;

    if (!a)
    {
        return;
    }

    /*Si no hay espacio reserva mas memoria*/
    if (a->used == a->size)
    {
        a->size *= 2;
        a->array = realloc(a->array, a->size * sizeof(indexbook));
    }

    /*Si estra vacio inserta al inicio*/
    if (a->used == 0)
    {
        a->array[0] = ib;
        a->used++;
        printf("Record with BookID=%d has been added to the database\n", ib.key);
        return ;
    }

    /*Busca la posicion donde guardalo*/
    pos = binary_search(a->array, 0, a->used, ib.key);

    /*Recoloca el array e introduce el indexbook*/
    memmove(&a->array[pos + 1], &a->array[pos], (a->used - pos) * sizeof(indexbook));
    a->array[pos] = ib;
    a->used++;
    printf("Record with BookID=%d has been added to the database\n", ib.key);
    return;
}

/**
 * @brief Elimina un indexbook del array
 * 
 * @param a, puntero del array
 * @param pos, posicion del indexbook a eliminar 
 */
void delete_indexbook(index_array *a, size_t pos)
{
    if(!a)
    {
        return;
    }

    memmove(&(a->array[pos]), &(a->array[pos + 1]), (a->used - pos - 1) * sizeof(indexbook));

    --a->used;
}

/**
 * @brief Realiza una busqueda binaria de un elemento en el array
 * 
 * @param a, puntero al array 
 * @param low, valor minimo donde buscar 
 * @param high, valor maximo donde buscar
 * @param key, bookID que se busca
 * @return size_t, posición del indexbook
 */
size_t binary_search(indexbook *a, size_t low, size_t high, int key)
{
    if(!a)
    {
        return -1;
    }

    /*Hace una busqueda binaria*/
    while (low < high)
    {
        size_t mid = low + (high - low) / 2;

        if (a[mid].key == key)
        {
            return mid;
        }
        else if (a[mid].key < key)
        {
            low = mid + 1;
        }
        else
        {
            high = mid;
        }
    }

    return low;
}

/**
 * @brief Imprime por pantalla los elementos de un index_array 
 * 
 * @param a, puntero del array
 */
void print_index(index_array *a)
{
    size_t i = 0;
    indexbook *index_b;

    if(!a)
    {
        return;
    }

    for (i = 0; i < a->used; i++)
    {
        index_b = &a->array[i];
        printf("Entry #%ld\n", i);
        printf("    key: #%d\n", index_b->key);
        printf("    offset: #%ld\n", index_b->offset);
        printf("    size: #%ld\n", index_b->size);
    }
}

/**
 * @brief Guarda un index_array en un fichero
 * 
 * @param a, puntero al array
 * @param index_file, archivo donde se guarda
 */
void save_index_file(index_array *a, FILE *index_file)
{
    size_t i;
    indexbook ib;

    if (!a || !index_file)
    {
        return;
    }

    /*Se posiciona al final del archivo*/
    fseek(index_file, 0, SEEK_END);

    /*Escribe todos los indexbook*/
    for (i = 0; i < a->used; i++)
    {
        ib = a->array[i];
        fwrite(&ib.key, sizeof(int), 1, index_file);
        fwrite(&ib.offset, sizeof(long int), 1, index_file);
        fwrite(&ib.size, sizeof(size_t), 1, index_file);
    }
}

/**
 * @brief Carga un index_array a partir de un archivo
 * 
 * @param a, puntero al array
 * @param index_file, archivo con los datos
 */
void load_index_file(index_array *a, FILE *index_file)
{
    indexbook ib;

    if (!index_file)
    {
        return;
    }

    /*Se posiciona al inicio del archivo*/
    fseek(index_file, 0, SEEK_SET);

    /*Lee los indexbook y los inserta en memoria*/
    while(fread(&ib.key, sizeof(int), 1, index_file))
    {
        fread(&ib.offset, sizeof(long int), 1, index_file);
        fread(&ib.size, sizeof(size_t), 1, index_file);

        insert_indexbook_end(a, ib);
    }
}