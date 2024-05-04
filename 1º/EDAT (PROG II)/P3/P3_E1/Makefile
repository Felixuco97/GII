CC=gcc
CFLAGS=-Wall -ggdb -pedantic
IFLAGS=-I./
LDFLAGS=-L./
LDLIBS = -lqueue
EJS = p3_e1 #p3_e2 p3_e3

OBJECTSP2E1A = p3_e1.o file_utils.o delivery.o vertex.o graph.o
#OBJECTSP2E1B = p3_e2.o vertex.o stack.o file_utils.o
#OBJECTSP2E2 = p3_e3.o graph.o vertex.o stack.o file_utils.o

all: $(EJS) clean

p3_e1: $(OBJECTSP2E1A)
	$(CC) -o $@ $^ $(LDFLAGS) $(LDLIBS)

#p3_e2: $(OBJECTSP2E2)
#	$(CC) -o $@ $^ $(LDFLAGS) $(LDLIBS)

#p3_e3: $(OBJECTSP2E3)
#	$(CC) -o $@ $^ $(LDFLAGS) $(LDLIBS)

graph.o: graph.c graph.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

vertex.o: vertex.c vertex.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

file_utils.o: file_utils.c file_utils.h types.h
	$(CC) -c $(CFLAGS) -o $@ $<

#stack.o: stack.c stack.h types.h
#	$(CC) -c $(CFLAGS) -o $@ $<

p3_e1.o: p3_e1.c delivery.h vertex.h file_utils.h graph.h types.h
	$(CC) -c -o $@ $< $(CFLAGS)

#p3_e2.o: p3_e2.c vertex.h file_utils.h stack.h types.h
#	$(CC) -c -o $@ $< $(CFLAGS)

#p3_e3.o: p3_e3.c graph.h vertex.h stack.h file_utils.h types.h
#	$(CC) -c -o $@ $< $(CFLAGS)

clean:
	rm  -f *.o