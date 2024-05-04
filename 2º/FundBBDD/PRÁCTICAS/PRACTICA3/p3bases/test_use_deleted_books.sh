#!/usr/bin/expect -f
## check that the new books fill the empty spaces
set timeout -1
set programName "library"
set filename "test"

# delete all files starting with test
# file delete [glob test*]
spawn rm -f $filename.db $filename.ind $filename.lst

# call program
#spawn valgrind ./$programName first_fit test
spawn ./$programName first_fit test
expect "Type command and argument/s."
expect "exit"

# ADD BOOKS
# add first book
send  "add 12346|978-2-12345680-3|El Quijote|Catedra\r"
expect "Record with BookID=12346 has been added to the database"
expect "exit"

# add second book
send  "add 12345|978-2-12345086-3|La busca|Catedra\r"
expect "Record with BookID=12345 has been added to the database"
expect "exit"

# add third book
send  "add 12347|978-2-12345680-4|el quijote|catedra\r"
expect "Record with BookID=12347 has been added to the database"
expect "exit"

# add fourth book
send  "add 12348|978-2-12345086-3|la busca|catedra\r"
expect "Record with BookID=12348 has been added to the database"
expect "exit"

# check index
# print index
puts "------------------------"
send "printInd\n"
expect "Entry #0"
expect "    key: #12345"
expect "    offset: #46"
expect "    size: #36"
expect "Entry #1"
expect "    key: #12346"
expect "    offset: #0"
expect "    size: #38"
expect "Entry #2"
expect "    key: #12347"
expect "    offset: #90"
expect "    size: #38"
expect "Entry #3"
expect "    key: #12348"
expect "    offset: #136"
expect "    size: #36"
expect "exit"

#delete book 12347
send "del 12347\r"
expect "Record with BookID=12347 has been deleted"
expect "exit"
send "printInd\n"
expect "Entry #0"
expect "    key: #12345"
expect "    offset: #46"
expect "Entry #1"
expect "    key: #12346"
expect "    offset: #0"
expect "Entry #2"
expect "    key: #12348"
expect "    offset: #136"
expect "exit"
send "printLst\n"
expect "Entry #0"
expect "    offset: #90"
expect "    size: #38"
expect "exit"

# add book to empty space
send "add 12349|978-2-12345680-8|El Quijote|Catedra\r"
expect "Record with BookID=12349 has been added to the database"
expect "exit"
#check that the empty space has been used
send "printInd\n"
expect "Entry #0"
expect "    key: #12345"
expect "    offset: #46"
expect "Entry #1"
expect "    key: #12346"
expect "    offset: #0"
expect "Entry #2"
expect "    key: #12348"
expect "    offset: #136"
expect "Entry #3"
expect "    key: #12349"
expect "    offset: #90"
expect "exit"
send "printLst\n"
expect "exit"
send "printRec\n"
expect "12345|978-2-12345086-3|La busca|Catedra"
expect "12346|978-2-12345680-3|El Quijote|Catedra"
expect "12348|978-2-12345086-3|la busca|catedra"
expect "12349|978-2-12345680-8|El Quijote|Catedra"
expect "exit"

send "exit\n"
expect "all done"

puts  "1) Delete index records OK, ;-)"

if {[file exists [file join $filename.ind]]} {
    puts "2) file $filename.ind Exists, ;-)"
} else {
    puts "2) file $filename.ind NOT found, :-("
}

set output "differ"
try {
set output [exec diff -s $filename.ind ${filename}_control_use_deleted.ind]
} trap CHILDSTATUS {} {}
if {[regexp -nocase "identical" $output] || [regexp -nocase "idénticos" $output]} {
    puts "3) index files are identical, ;-)"
} else {
    puts "3) index files differ, :-("
}

if {[file exists [file join $filename.lst]]} {
    puts "4) file $filename.lst Exists, ;-)"
} else {
    puts "4) file $filename.lst NOT found, :-("
}

set output "differ"
try {
set output [exec diff -s $filename.lst ${filename}_control_use_deleted.lst]
} trap CHILDSTATUS {} {}
if {[regexp -nocase "identical" $output] || [regexp -nocase "idénticos" $output]} {
    puts "5) deleted books files are identical, ;-)"
} else {
    puts "5) deleted books files differ, :-("
}


if {[file exists [file join $filename.db]]} {
    puts "6) file $filename.db Exists, ;-)"
} else {
    puts "6) file $filename.db NOT found, :-("
}

set output "differ"
try {
set output [exec diff -s $filename.db ${filename}_control_use_deleted.db]
} trap CHILDSTATUS {} {}
if {[regexp -nocase "identical" $output] || [regexp -nocase "idénticos" $output]} {
    puts "7) data files are identical, ;-)"
} else {
    puts "7) data files differ, :-("
}
puts "8) Script end"