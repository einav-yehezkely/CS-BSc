import helper5


def symmetric(mtx):
    t_mtx = helper5.transpose_matrix(mtx)
    if mtx == t_mtx:
        with open("sym.txt", "r+") as f:
            f.write(helper5.str_matrix(mtx))
        return True
    return False


def transpose_matrix_2(mtx):
    return [[mtx[j][i] for j in range(len(mtx))] for i in range(len(mtx[0]))]


def magic_square(mtx):
    # rows
    for row in range(len(mtx) - 1):
        if sum(mtx[row]) != sum(mtx[row+1]):
            # print(False)
            return False
    sum_of_rows = sum(mtx[0])
    print(sum_of_rows)

    # cols
    t_mtx = helper5.transpose_matrix(mtx)
    for col in range(len(t_mtx) - 1):
        if sum(t_mtx[col]) != sum(t_mtx[col+1]):
            # print(False)
            return False
    sum_of_cols = sum(t_mtx[0])
    print(sum_of_cols)

    # left slant
    if sum(mtx[i][i] for i in range(len(mtx))) != sum_of_rows:
        # print(False)
        return False

    # right slant
    if sum(mtx[-i-1][-i-1] for i in range(len(mtx))) != sum_of_rows:
        # print(False)
        return False

    print(mtx)
    print(True)
    return True


# print(symmetric(helper5.mtx3))
# print(helper5.str_matrix(helper5.mtx1))
# print(helper5.str_matrix(helper5.transpose_matrix(helper5.mtx1)))
# print(helper5.str_matrix(transpose_matrix_2(helper5.mtx1)))
magic_square(helper5.not_magic)
