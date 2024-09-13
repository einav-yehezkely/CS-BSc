def f3(n, x, y):
    return helper(n, x, y, [], [])

def helper(n, x, y, lst, big_lst):
    if len(lst) == n:
        big_lst.append(lst[::])
        return
    lst.append(x)
    helper(n, x, y, lst, big_lst)
    lst.pop()
    if lst == [] or lst[-1] != y:
        lst.append(y)
        helper(n, x, y, lst, big_lst)
        lst.pop()
    return big_lst

print(f3(3, 'a', (2,)))