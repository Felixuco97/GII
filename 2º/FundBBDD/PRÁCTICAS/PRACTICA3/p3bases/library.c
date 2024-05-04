/**
 * @file library.c
 * @authors Ignacio Martin, Felix Lopez
 */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "library.h"

/**
 * @brief Funcion main del programa 
 */
int main(int argc, char const *argv[])
{
    char data_file_name[64], index_file_name[64], deleted_records_file_name[64];
    char command[16], options[512], input[528];
    FILE *data_file, *index_file, *deleted_records_file;
    index_array ind_array;
    deleted_array del_array;
    int fit;

    /*Comprueba que no faltan argumentos*/
    if (argc != 3 || !argv)
    {
        printf("Missing argument");
        return 0;
    }

    /*Comprueba si es una estrategia valida y la guarda*/
    if (strcmp(argv[1], "best_fit") == 0)
    {
        fit = BESTFIT;
    }
    else if (strcmp(argv[1], "first_fit") == 0)
    {
        fit = FIRSTFIT;
    }
    else if (strcmp(argv[1], "worst_fit") == 0)
    {
        fit = WORSTFIT;
    }
    else
    {
        printf("Unknown search strategy unknown_search_strategy");
        return 0;
    }

    /*Abre los archivos binariao*/
    sprintf(data_file_name, "%s.db", argv[2]);
    sprintf(index_file_name, "%s.ind", argv[2]);
    sprintf(deleted_records_file_name, "%s.lst", argv[2]);

    data_file = fopen(data_file_name, "rb+");
    if (data_file == NULL)
    {
        data_file = fopen(data_file_name, "wb+");
        if (data_file == NULL)
        {
            printf("Error apertura data_file");
            return 0;
        }
    }

    index_file = fopen(index_file_name, "ab+");
    if (index_file == NULL)
    {
        fclose(data_file);
        printf("Error apertura index_file");
        return 0;
    }

    deleted_records_file = fopen(deleted_records_file_name, "ab+");
    if (deleted_records_file == NULL)
    {
        fclose(data_file);
        fclose(index_file);
        printf("Error apertura deleted_records_file");
        return 0;
    }

    /*Inicializa el array de indice y el array de libros borrados*/
    init_index_array(&ind_array, 16);
    load_index_file(&ind_array, index_file);
    fclose(index_file);

    init_deleted_array(&del_array, 8, fit);
    load_deleted_file(&del_array, deleted_records_file);
    fclose(deleted_records_file);

    printf("Type command and argument/s.\n");

    /*Bucle que espera los comandos y los ejecuta*/
    while (1)
    {
        printf("exit\n");
        fgets(input, sizeof(input), stdin);
        if (sscanf(input, "%s %[^\n]", command, options) == 2)
        {
            if (strcmp(command, "add") == 0)
            {
                /*Ejecuta el comando add*/
                add_function(options, data_file, &ind_array, &del_array);
            }
            else if (strcmp(command, "find") == 0)
            {
                /*Ejecuta el comando find*/
                find_function(&ind_array, atoi(options), data_file);
            }
            else if (strcmp(command, "del") == 0)
            {   
                /*Ejecuta el comando del*/
                del_function(options, &ind_array, &del_array);
            }
            else
            {
                printf("Unknown command\n");
            }
        }
        else if (sscanf(input, "%s %[^\n]", command, options) == 1)
        {
            if (strcmp(command, "exit") == 0)
            {   
                /*Ejecuta el comando exit*/
                index_file = fopen(index_file_name, "wb+");
                deleted_records_file = fopen(deleted_records_file_name, "wb+");
                exit_function(data_file, index_file, deleted_records_file, ind_array, del_array);
                return 0;
            }
            else if (strcmp(command, "printRec") == 0)
            {
                /*Ejecuta el comando printRec*/
                print_records(&ind_array, data_file);
            }
            else if (strcmp(command, "printInd") == 0)
            {
                /*Ejecuta el comando printInd*/
                print_index(&ind_array);
            }
            else if (strcmp(command, "printLst") == 0)
            {
                /*Ejecuta el comando printLst*/
                print_deleted_books(&del_array);
            }
            else
            {
                printf("Unknown command\n");
            }
        }
        else
        {
            printf("Unknown command\n");
        }
    }

    exit_function(data_file, index_file, deleted_records_file, ind_array, del_array);
    return 0;
}

/**
 * @brief Añade un libro a la base y actualiza el indice y los registros borrados
 * 
 * @param options, string con la informacion del libro 
 * @param data_file, fichero binario de datos
 * @param ia, array de indices 
 * @param da, array de libros borrados
 */
void add_function(char *options, FILE *data_file, index_array *ia, deleted_array *da)
{
    int book_id;
    char isbn[16], title[MAX_STRING_LENGTH], printed_by[MAX_STRING_LENGTH];
    char *tok;
    size_t register_size, pos;
    indexbook index;
    long int offset;

    /*Extrae los campos del string*/
    tok = strtok(options, "|");
    book_id = atoi(tok);

    tok = strtok(NULL, "|");
    strncpy(isbn, tok, sizeof(isbn));

    tok = strtok(NULL, "|");
    strncpy(title, tok, sizeof(title));

    tok = strtok(NULL, "\r");
    strncpy(printed_by, tok, sizeof(printed_by));

    register_size = sizeof(int) + sizeof(isbn) + strlen(title) + sizeof(char) + strlen(printed_by);

    /*Comprueba si ya esta ese bookID*/
    pos = binary_search(ia->array, 0, ia->used, book_id);
    if (ia->array[pos].key == book_id)
    {
        printf("Record with BookID=%d exits.\n", book_id);
        return;
    }

    /*Busca si hay un hueco vacío con tamaño suficiente y si lo hay actualuza los libros borrados*/
    offset = get_available_space(da, register_size);
    if (offset == -1)
    {
        /*Si no hay huecos vacios se posiciona al final del archivo*/
        fseek(data_file, 0, SEEK_END);
    }
    else
    {
        /*Si no se posiciona en el offset obtenido*/
        fseek(data_file, offset, SEEK_SET);
    }

    /*Crea un indexbook con los valores indicados*/
    index.key = book_id;
    index.offset = ftell(data_file);
    index.size = register_size;

    /*Inserta el indexbook en el indice*/
    insert_indexbook(ia, index);

    /*Si se ha actualizado el indice se escribe en el fichero*/
    fwrite(&register_size, sizeof(size_t), 1, data_file);
    fwrite(&book_id, sizeof(int), 1, data_file);
    fwrite(isbn, sizeof(char), sizeof(isbn), data_file);
    fwrite(title, sizeof(char), strlen(title), data_file);
    fwrite("|", sizeof(char), 1, data_file);
    fwrite(printed_by, sizeof(char), strlen(printed_by), data_file);
}

/**
 * @brief Busca un libro a partir de su bookID
 * 
 * @param a, puntero al indice
 * @param id, bookID a buscar
 * @param data, fichero con los datos
 */
void find_function(index_array *a, int id, FILE *data)
{
    indexbook *ib;
    int book_id;
    char isbn[17], title_printed[MAX_STRING_LENGTH * 2];
    size_t register_size, i;

    /*Realiza una busqueda binaria del libro*/
    i = binary_search(a->array, 0, a->used, id);
    ib = &a->array[i];
    if (ib->key != id)
    {
        printf("Record with BookID=%d does not exist\n", id);
        return;
    }

    /*Si esta guardado, lo lee del archivo e imprime por pantalla*/
    fseek(data, ib->offset, SEEK_SET);

    fread(&register_size, sizeof(size_t), 1, data);
    fread(&book_id, sizeof(int), 1, data);
    fread(isbn, sizeof(char), sizeof(isbn) - 1, data);
    isbn[16] = '\0';
    register_size -= (sizeof(int) + sizeof(isbn) - 1);
    fread(title_printed, sizeof(char), register_size, data);
    title_printed[register_size] = '\0';

    printf("%d|%s|%s\n", book_id, isbn, title_printed);
}

/**
 * @brief Elimina un libro y actualiza el indice y los libros borrados
 * 
 * @param options, string con el id
 * @param ia, puntero al array del indice
 * @param da, puntero al array de libros borrados
 * @param fit, estrategia de eliminación
 */
void del_function(char *options, index_array *ia, deleted_array *da)
{
    int book_id;
    size_t i;
    indexbook *ib;
    deleted_book db;

    /*Busca el id en el indice*/
    book_id = atoi(options);
    i = binary_search(ia->array, 0, ia->used, book_id);
    ib = &ia->array[i];
    if (ib->key != book_id)
    {
        printf("Item with key %d does not exist\n", book_id);
        return;
    }

    db.offset = ib->offset;
    db.size = ib->size;

    /*Lo añade a los registros borrados*/
    insert_deleted_book(da, db);

    /*Lo elimina del indice*/
    delete_indexbook(ia, i);

    printf("Record with BookID=%d has been deleted\n", book_id);
}

/**
 * @brief Imprime por pantalla los registros de forma ordenada
 * 
 * @param a, puntero al indice 
 * @param data, fichero binario de datos
 */
void print_records(index_array *a, FILE *data)
{   
    int book_id;
    char isbn[17], title_printed[MAX_STRING_LENGTH * 2];
    size_t register_size, i;

    if (!a || !data)
    {
        return;
    }

    /*Por cada entrada del indice, extrae los datos del fichero y los imprime por pantalla*/
    for (i = 0; i < a->used; i++)
    {
        fseek(data, a->array[i].offset, SEEK_SET);

        fread(&register_size, sizeof(size_t), 1, data);
        fread(&book_id, sizeof(int), 1, data);
        fread(isbn, sizeof(char), sizeof(isbn) - 1, data);
        isbn[16] = '\0';
        register_size -= (sizeof(int) + sizeof(isbn) - 1);
        fread(title_printed, sizeof(char), register_size, data);
        title_printed[register_size] = '\0';

        printf("%d|%s|%s\n", book_id, isbn, title_printed);
    }
}

/**
 * @brief Guarda los indice y los libros borrados, Libera memoria y cierra ficheros de archivos
 * 
 * @param data_file, fichero de datos 
 * @param index_file, ficheros de indices
 * @param deleted_file, fichero de libros borrados
 * @param ia, array de indices
 * @param da, array de libros borrados
 * @param fit, estrategia de eliminación
 */
void exit_function(FILE *data_file, FILE *index_file, FILE *deleted_file, index_array ia, deleted_array da)
{
    /*Guarda el indice en index_file*/
    save_index_file(&ia, index_file);

    /*Guarda los libros borrados en deleted_file*/
    save_deleted_file(&da, deleted_file);
    
    /*Cierra los archivos*/
    fclose(data_file);
    fclose(index_file);
    fclose(deleted_file);

    /*Libera memoria*/
    free_index_array(&ia);
    free_deleted_array(&da);
    
    printf("all done\n");
}