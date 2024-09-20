def real_imag_conj(val):

    return val.real, val.imag, val.conjugate()

r, i, c = real_imag_conj(5 + 9j)
print(r,i,c)