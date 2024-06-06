mtx1 = [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12]]
mtx3 = [[1, 2, 3], [2, 2, 3], [3, 3, 3]]
magic = [[6, 1, 8], [7, 5, 3], [2, 9, 4]]
not_magic = [[4, 5, 6], [4, 5, 6], [4, 5, 6]]


def str_matrix(mtx):
    str_mtx = ""
    for row in mtx:
        for col in row:
            str_mtx += str(col) + ' '
        str_mtx += '\n'
    return str_mtx

def transpose_matrix(mtx):
    trn_mtx = []
    len_row = len(mtx[0])
    for i in range(len_row):
        row = []
        for j in range(len(mtx)):
            row.append(mtx[j][i])
        trn_mtx.append(row)
    return trn_mtx
