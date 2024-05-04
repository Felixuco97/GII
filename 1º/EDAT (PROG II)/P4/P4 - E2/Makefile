CC=gcc
CFLAGS=-Wall -ggdb -pedantic
EJS = p4_e1 p4_e2
IFLAGS=-I./
LDFLAGS=-L./

OBJECTSP2E1 = p4_e1.o vertex.o bstree.o
OBJECTSP2E2 = p4_e2.o bstree.o search_queue.o file_utils.o

all: $(EJS) clean

p4_e1: $(OBJECTSP2E1)
	$(CC) -o $@ $^ $(LDFLAGS) $(LDLIBS)

p4_e2: $(OBJECTSP2E2)
	$(CC) -o $@ $^ $(LDFLAGS) $(LDLIBS)

vertex.o: vertex.c vertex.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

bstree.o: bstree.c bstree.h file_utils.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

search_queue.o: search_queue.c search_queue.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

file_utils.o: file_utils.c file_utils.h
	$(CC) -c $(CFLAGS) -o $@ $<

p4_e1.o: p4_e1.c vertex.h bstree.h types.h
	$(CC) -c -o $@ $< $(CFLAGS)

p4_e2.o: p4_e2.c bstree.h search_queue.h file_utils.h types.h
	$(CC) -c -o $@ $< $(CFLAGS)

clean:
	rm  -f *.o