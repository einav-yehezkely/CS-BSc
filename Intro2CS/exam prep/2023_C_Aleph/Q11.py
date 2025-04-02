def find_path(domlist):
    if not domlist:
        return []

    if check_if_sorted(domlist):
        return domlist

    sorted = False
    while not sorted:
        sorted = True
        for i in range(len(domlist) - 1):
            if domlist[i][0] == domlist[i + 1][1]:
                sorted = False
                domlist[i], domlist[i + 1] = domlist[i + 1], domlist[i]
    i = 0
    while i < len(domlist) - 1:
        try:
            if domlist[i][1] != domlist[i + 1][0]:
                # try:
                if domlist[i - 1][1] == domlist[i][0]:
                    del domlist[i + 1]
                else:
                    del domlist[i]
            else:
                i += 1
        except IndexError:
            return domlist
    return domlist

def check_if_sorted(domlist):
    count = 0
    for domino in range(len(domlist) - 1):
        if domlist[domino][1] == domlist[domino + 1][0]:
            count += 1
        if count == len(domlist) - 1:
            return True


print(find_path([(5, 3), (1, 4), (4, 5), (5, 2), (3, 8)]), "     ---[(1, 4), (4, 5), (5, 2)]---")
print(find_path([(1, 4), (2, 3), (1, 2)]), "     ---[(1, 2), (2, 3)]---")
print(find_path([(100, 200), (200, 100), (100, 200)]), "     ---[(100, 200), (200, 100), (100, 200)]---")
print(find_path([(1, 2)]), "     ---[(1, 2)]---")
print(find_path([]), "  ---[]---")
lst = [1, 2, 3, 4, 5, 6]
print(lst[1:-1])
