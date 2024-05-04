CC=gcc
CFLAGS=-Wall -ggdb -pedantic
EJS = p2_e1a p2_e1b p2_e2

OBJECTSP2E1A = p2_e1a.o stack.o file_utils.o
OBJECTSP2E1B = p2_e1b.o vertex.o stack.o file_utils.o
OBJECTSP2E2 = p2_e2.o graph.o vertex.o stack.o file_utils.o

all: $(EJS) clean

p2_e1a: $(OBJECTSP2E1A)
	$(CC) -o $@ $^

p2_e1b: $(OBJECTSP2E1B)
	$(CC) -o $@ $^

p2_e2: $(OBJECTSP2E2)
	$(CC) -o $@ $^

graph.o: graph.c graph.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

vertex.o: vertex.c vertex.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

file_utils.o: file_utils.c file_utils.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

stack.o: stack.c stack.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

p2_e1a.o: p2_e1a.c stack.h file_utils.h types.h
	$(CC) -c -o $@ $< $(CFLAGS)

p2_e1b.o: p2_e1b.c vertex.h file_utils.h stack.h types.h
	$(CC) -c -o $@ $< $(CFLAGS)

p2_e2.o: p2_e2.c graph.h vertex.h stack.h file_utils.h types.h
	$(CC) -c -o $@ $< $(CFLAGS)

clean:
	rm  -f *.o
