#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*Incluir un fichero para imprimir y comparar los strings*/

#include "bstree.h"
#include "types.h"
#include "search_queue.h"
#include "file_utils.h"

#define MAX_LINE 50001
#define MAX_STRINGS 50001

char *string_cpy(char *line){

    char *data = NULL;
    int i;

    data = (char*) malloc((strlen(line)+1)*sizeof(char));

    if(!data) return NULL;

    strcpy(data, line);

    return data;
}


int main(int argc, char *argv[]){

    char *data[MAX_STRINGS];
    SearchQueue *sq = NULL;
    FILE *in = NULL, *out = NULL;
    char line[MAX_LINE];
    char *ele = NULL;
    int i;

    in = fopen(argv[1],"r");
    out = fopen(argv[2],"w");

    if(!in || !out){
        printf("ERROR al abrir alguno de los ficheros");
        return 1;
    }

    sq = search_queue_new(string_print, string_cmp);

    if(!sq){
        fclose(in);
        fclose(out);
        return 1;
    }

    i = 0;

    while(fgets(line, MAX_LINE, in)){
        data[i] = string_cpy(line);
        if(search_queue_push(sq, &data[i]) == ERROR){
            return 1;
        }
        i++;  
    }

/*
    while(search_queue_isEmpty(sq) == FALSE){
        ele = search_queue_pop(sq);
        fprintf(out, "%s", ele);
    }
*/
    search_queue_free(sq);
    fclose(in);
    fclose(out);

    return 0;
}