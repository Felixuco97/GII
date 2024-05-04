CC=gcc
CFLAGS=-Wall -ggdb -pedantic
EJS = p4_e1
IFLAGS=-I./
LDFLAGS=-L./

OBJECTSP2E1 = p4_e1.o vertex.o bstree.o

all: $(EJS) clean

p4_e1: $(OBJECTSP2E1)
	$(CC) -o $@ $^ $(LDFLAGS) $(LDLIBS)

vertex.o: vertex.c vertex.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

bstree.o: bstree.c bstree.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

p4_e1.o: p4_e1.c vertex.h bstree.h types.h
	$(CC) -c -o $@ $< $(CFLAGS)

clean:
	rm  -f *.o