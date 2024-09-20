class MySpecialError(ValueError):
    pass

def safe_divide(a,b):

    try:
        a/b
    except ZeroDivisionError:
        print("b is zero, cannot divide")
        return 1E100
    except TypeError as err:
        print("There is a string in the operation:", err, ",", type(err))
        return "ERROR"
    return a/b
    
try:
    print("Let's try something")
    x = 1/0
except:
    print("Something has hapenned")

print(safe_divide(15,4))
print(safe_divide(15,0))
print(safe_divide(15,'5'))

try:
    print("try something here")
except:
    print("This only happens if it fails")
else:
    print("This only happen when it succeeds")
finally:
    print("This always happens")

try:
    print("This is special")
    raise MySpecialError("here's the message")
except MySpecialError:
    print("This is a special error")