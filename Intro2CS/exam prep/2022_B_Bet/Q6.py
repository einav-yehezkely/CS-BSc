def no_twins(n, x, y):
    return no_twins_helper(n, x, y, [], [])


def no_twins_helper(n, x, y, lst, big_lst):
    if len(lst) == n:
        big_lst.append(lst[::])
        return
    lst.append(x)
    no_twins_helper(n, x, y, lst, big_lst)
    lst.pop()
    if lst == [] or lst[-1] != y:
        lst.append(y)
        no_twins_helper(n, x, y, lst, big_lst)
        lst.pop()
    return big_lst


print(no_twins(2, 5, 3))
print(sorted(no_twins(3, 5, 3)))
print(sorted(no_twins(4, 5, 3))[:3])
print(sorted(no_twins(4, 5, 3))[3:])