def all_sums(num, bound):
    yield from helper([], num, bound)

def helper(lst, num, bound):
    if sum(lst) == num:
        stri = ""
        for i in range(len(lst)):
            stri += str(lst[i])
            if i < len(lst) - 1:
                stri += " + "
        yield stri

    if sum(lst) > num:
        return

    for i in range(bound, 0, -1):
        lst.append(i)
        yield from helper(lst, num, i)
        lst.pop()



print("------ ['3 + 1', '2 + 2', '2 + 1 + 1', '1 + 1 + 1 + 1'] ------")
print(list(all_sums(4, 3)))
print()
print("------ ['2 + 2 + 1', '2 + 1 + 1 + 1', '1 + 1 + 1 + 1 + 1'] ------")
print(list(all_sums(5, 2)))
print()
print("------ ['2', '1 + 1'] ------")
print(list(all_sums(2, 2)))
print()
print("------ ['1'] ------")
print(list(all_sums(1, 1)))