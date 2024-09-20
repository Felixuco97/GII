[print(n**2) for n in range(15)]
[print((i,j)) for i in range(3) for j in range(5)]
l = []
(l.append(val for val in range(20) if val % 3 != 0))
print(l)
L = []
L.append([val if val % 2 else -val
 for val in range(20) if val % 3])
L += [val if val % 2 else -val
 for val in range(20) if val % 3]
print(L)

