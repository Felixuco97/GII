CC=gcc
CFLAGS=-Wall -ggdb -pedantic
EJS = p3_e1 p3_e2 p3_e3
IFLAGS=-I./
LDFLAGS=-L./
LDLIBS = -lstack

OBJECTSP2E1 = p3_e1.o file_utils.o delivery.o vertex.o graph.o queue.o
OBJECTSP2E2 = p3_e2.o graph.o vertex.o file_utils.o queue.o delivery.o
OBJECTSP2E3 = p3_e3.o file_utils.o list.o

all: $(EJS) clean

p3_e1: $(OBJECTSP2E1)
	$(CC) -o $@ $^ $(LDFLAGS) $(LDLIBS)

p3_e2: $(OBJECTSP2E2)
	$(CC) -o $@ $^ $(LDFLAGS) $(LDLIBS)

p3_e3: $(OBJECTSP2E3)
	$(CC) -o $@ $^ $(LDFLAGS) $(LDLIBS)

graph.o: graph.c graph.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

vertex.o: vertex.c vertex.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

file_utils.o: file_utils.c file_utils.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

queue.o: queue.c queue.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

list.o: list.c list.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

p3_e1.o: p3_e1.c delivery.h vertex.h file_utils.h graph.h queue.h types.h
	$(CC) -c -o $@ $< $(CFLAGS)

p3_e2.o: p3_e2.c delivery.h vertex.h file_utils.h graph.h queue.h types.h
	$(CC) -c -o $@ $< $(CFLAGS)

p3_e3.o: p3_e3.c list.h file_utils.h types.h
	$(CC) -c -o $@ $< $(CFLAGS)

clean:
	rm  -f *.o