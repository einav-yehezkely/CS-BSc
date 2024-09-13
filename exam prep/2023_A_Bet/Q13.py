def taylor(x: float, n: int) -> float:
    if n == 0:
        return 1
    return (helper_power(x, n) / helper_factorial(n)) + taylor(x, n-1)

def helper_factorial(n):
    if n == 0:
        return 1
    return n*helper_factorial(n-1)

def helper_power(x, n):
    res = 1
    while n > 0:
        res *= x
        n -= 1
    return res

print(helper_factorial(5))
print(helper_power(3, 3))
print(taylor(1, 5))