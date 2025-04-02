def get_k_tuples(it, k):
    tup = []
    try:
        for i in range(k):
            tup.append(next(it))
        yield tuple(tup)
        while True:
            del tup[0]
            tup.append(next(it))
            yield tuple(tup)
    except StopIteration:
        return


lst = [1, 2, 3, 4, 5]
print(*get_k_tuples(iter(lst), 3), end='')
