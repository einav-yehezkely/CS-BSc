def exponent(x, n):
    if n == 0:
        return 1
    org_x = x
    for i in range(n - 1):
        x = x * org_x
    return x

print(exponent(2, 3))