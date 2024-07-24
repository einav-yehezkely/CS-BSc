def mb_fib2(n):
    if n == 1 or n == 0:
        return 1
    if n == 2:
        return 2
    if n == 3:
        return 3
    else:
        return mb_fib2(n-1) * mb_fib2(n-2) * mb_fib2(n-3)

lst = []
for i in range(10):
    lst.append(mb_fib2(i))
print(lst)
# print(mb_fib2(9))


# school solution
def mb_fib(m: int) -> int:
    return m if m<4 else fib_helper(m)[2]
def fib_helper(m):
    if m==3:
        return 1,2,3
    xm3, xm2, xm1 = fib_helper(m-1)
    return xm2, xm1, xm1 * xm2 * xm3

lst2 = []
for i in range(10):
    lst2.append(mb_fib(i))
print(lst2)