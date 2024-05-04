#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#define LEN_REG 50
#define LEN_FILE 10
#define INITIAL_SIZE 15

typedef struct{
    int id;
    long int offset;
    size_t size;
} indexbook;

typedef struct{
    indexbook *array;
    size_t used;
    size_t size;
} Array;

typedef struct{
    size_t register_size;
    size_t offset;
} indexdeletedbook;

int binary_search(indexbook *array, int F, int L, int key, int pos){

    int middle, start, end, result;

    assert(array!=NULL);
    assert(F >= 0);
    assert(L >= 0);
    assert(F <= L);

    start = F;
    end = L;

    while(start <= end){

        middle = (start + end)/2;
        result = array[middle].id - key;

        if(result == 0){
            pos = middle;
            return pos;
        } else if (result > 0){
            end = middle -1;
        } else {
            start = middle -1;
        }
    }

    return pos;
}

void initArray(Array *a, size_t initialSize){

    a->array = malloc(initialSize * sizeof(int));
    a->used = 0;
    a->size = initialSize;
}

void insertArray(Array *a, indexbook *i){

    int pos = -1;
    int found;

    found = binary_search(a->array, 0, a->used, i->id, pos);

    if(found != -1){
        printf("Record with BookID=%d exists", i->id);
        return;
    }

    if(a->used == a->size){
        a->size *= 2;
        a->array = realloc(a->array, a->size * sizeof(int));
    }

    a->array[a->used++] = *i;
}

void freeArray(Array *a){

    free(a->array);
    a->array = NULL;
    a->used = a->size = 0;
}

void printInd(Array a){

    size_t i;

    for(i = 0; i < a.used; i++){

        printf("Entry #%ld:\n", i);
        printf("    Id: %d", a.array[i].id);
        printf("    Offset: %ld", a.array[i].offset);
        printf("    Size: %ld", a.array[i].size);
    }
}

void saveInd(Array a, FILE *f){

    size_t i;

    fseek(f, 0L, SEEK_SET);

    for(i = 0; i < a.used; i++){

        fwrite(&a.array[i].id, sizeof(int), 1, f);
        fwrite(&a.array[i].offset, sizeof(long int), 1, f);
        fwrite(&a.array[i].size, sizeof(size_t), 1, f);
    }
}

void loadInd(Array *a, FILE *f){

    size_t i;

    fseek(f, 0L, SEEK_SET);

    for(i = 0; i < a->used; i++){

        fread(&a->array[i].id, sizeof(int), 1, f);
        fread(&a->array[i].offset, sizeof(long int), 1, f);
        fread(&a->array[i].size, sizeof(size_t), 1, f);
    }
}

void add(FILE *f, char *reg, Array *a, indexbook *i){

    size_t size;
    int id;
    char *token;
    char *isbn = NULL;
    char *titulo = NULL;
    char *editorial = NULL;

    if(!f || !reg)
        return;

    token = strtok(reg,"|");
    isbn = strtok(NULL,"|");
    titulo = strtok(NULL,"|");
    editorial = strtok(NULL,"\r");

    id = atoi(token);

    size = sizeof(int)+strlen(isbn)+strlen(titulo)+1+strlen(editorial);

    i->id = id;
    i->offset = ftell(f);
    i->size = size;

    insertArray(a, i);

    fseek(f, 0L, SEEK_SET);
    fwrite(&size, sizeof(size_t), 1, f);
    fwrite(&id, sizeof(int), 1, f);
    fwrite(isbn, sizeof(char), strlen(isbn), f);
    fwrite(titulo, sizeof(char), strlen(titulo), f);
    fwrite("|", sizeof(char), 1, f);
    fwrite(editorial, sizeof(char), strlen(editorial), f);

    printf("Record with BookID=%d has been added to the database\n", id);
    printf("exit\n");
}

int main(int argc, char **argv){

    char cmd[LEN_REG];
    char input[LEN_REG];
    char reg[LEN_REG];
    int flag = 0;
    FILE *f = NULL;
    int loop = 1;
    indexbook i;
    Array a;

    if(argc < 2){

        printf("Missing argument\n");
        flag = 1;

    } else {

        if(strcmp(argv[1],"best_fit") && strcmp(argv[1],"first_fit")){

            printf("Unknown search strategy unknown_search_strategy\n");
            flag = 1;
        }
    }

    if(flag == 1)
        return 0;

    strcat(argv[2],".db");

    f = fopen(argv[2],"wb");

    if(!f){
        printf("ERROR: no se pudo abrir el archivo\n");
        return -1;
    }

    initArray(&a, INITIAL_SIZE);

    do{

        printf("Type command and argument/s.\n");
        printf("exit\n");
        fgets(input,sizeof(input),stdin);

        if(sscanf(input, "%s %[^\n]", cmd, reg) == 2){

            if(!strcmp(cmd,"add")){

                add(f, reg, &a, &i);
            
            }
        }
        else{
            if(!strcmp(cmd,"find")){
                printf("FIND");
            }
            else if(!strcmp(cmd,"del")){
                printf("DEL");
            }
            else if(!strcmp(cmd,"exit")){
                fclose(f);
                break;
            }
            else if(!strcmp(cmd,"printInd")){
                printf("PRINTIND");
            }
            else if(!strcmp(cmd,"printLst")){
                printf("PRINTLST");
            }
            else if(!strcmp(cmd,"printRec")){
                printf("PRINTREC");
            }
            else{
                printf("Unknown command\n");
            }  
        }
    }while(loop == 1);

    return 0;
}