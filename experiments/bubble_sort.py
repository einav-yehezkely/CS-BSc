#   https://youtu.be/g_xesqdQqvA?si=6Zr9B7ys8RXFroSs
#   O(n^2)
def bubble(list_a):
    indexing_length = len(list_a) - 1
    sorted = False

    while not sorted:
        sorted = True

        for i in range(indexing_length):
            if list_a[i] > list_a[i + 1]:
                sorted = False
                list_a[i], list_a[i + 1] = list_a[i + 1], list_a[i]

    return list_a


print(bubble([3, 6, 8, 1, 5, 6, 21, 12, 3, 5, 6]))