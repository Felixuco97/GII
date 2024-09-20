#Structures
list = [0,1,2,3]
tuple = (0,1,2,3)
dict = {'a':1, 'b':2, 'c':3, 4:'FOUR'}
set = {1,2,3}

#Lists
L = [1,'two',3.14,[0,3,5]]
list.append(4)
list += [5,6,7,8,9]
print(list, ", length: ",len(list))
print(L)
print(L[0])
print(L[-1])
print(list[:7])
print(list[2:8])
print(list[-3:])
print(list[::2])
print(list[0:9:3])
print(list[::-2])
L[0] = 55
print(L)
print("\n")
#Tuples (cannot be changed)
print(tuple)
print(tuple[2])
print("\n")
#Dictionaries
print(dict['a'])
print(dict[4])
dict['a'] = "Visual"
print(dict)
print("\n")
#Sets
primes = {2,3,5,7}
odds = {1,3,5,7,9}
union = primes | odds
intersection = primes & odds
difference = primes - odds
print(union)
print(intersection)
print(difference)

