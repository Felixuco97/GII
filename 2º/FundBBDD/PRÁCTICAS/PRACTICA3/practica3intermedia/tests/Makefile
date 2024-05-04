CC = gcc -g
CFLAGS = -Wall -Wextra -pedantic -ansi

# recompile if this header changes
HEADERS = library.h

EXE = library
OBJ = $(EXE).o

all : $(EXE)

# compile all files ending in *.c
%.o: %.c $(HEADERS)
	@echo Compiling $<...
	$(CC) $(CFLAGS) -c -o $@ $<

# link binary
$(EXE): $(OBJ)
	$(CC) -o $(EXE) $(OBJ)

clean:
	rm -f *.o core $(EXE)

run:
	./$(EXE)

valgrind:
	valgrind --leak-check=full ./$(EXE)