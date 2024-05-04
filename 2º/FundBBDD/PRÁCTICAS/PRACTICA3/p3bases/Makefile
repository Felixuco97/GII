CC = gcc -g
CFLAGS = -Wall -Wextra -pedantic -ansi
#LDLIBS = -lodbc -lcurses -lpanel -lmenu -lform

# recompile if this header changes
HEADERS = library.h index.h deleted.h

EXE = library
OBJ = $(EXE).o index.o deleted.o

all : $(EXE)

# compile all files ending in *.c
%.o: %.c $(HEADERS)
	@echo Compiling $<...
	$(CC) $(CFLAGS) -c -o $@ $<

# link binary
$(EXE): $(DEPS) $(OBJ)
	$(CC) -o $(EXE) $(OBJ)

clean:
	rm -f *.o core $(EXE) test.db test.ind test.lst

best_fit:
	./$(EXE) best_fit test

worst_fit:
	./$(EXE) worst_fit test

first_fit:
	./$(EXE) first_fit test