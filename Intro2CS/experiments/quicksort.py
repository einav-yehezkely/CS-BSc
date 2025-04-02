#   https://youtu.be/kFeXwkgnQ9U?si=6C8eHGfc1X77qT_q
#   O(nlog(n))
#   worst -> O(n^2)
def quicksort(lst):
    length = len(lst)
    if length <= 1:
        return lst
    else:
        pivot = lst.pop()

    lower = []
    higher = []
    for item in lst:
        if item > pivot:
            higher.append(item)
        else:
            lower.append(item)

    return quicksort(lower) + [pivot] + quicksort(higher)


print(quicksort([1, 9, 2, 8, 4, 7, 7, 8, 9, 3, 1, 0, 1, 9, 3, 7]))
