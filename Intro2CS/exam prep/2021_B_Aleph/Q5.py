def rotate_90_clock(mat):
    mat = mat[::-1]
    new_mat = []
    for col in range(len(mat[0])):
        lst = []
        for row in range(len(mat)):
            lst.append(mat[row][col])
        new_mat.append(lst)
    return new_mat



mat = [[1, 2], [3, 4], [5, 6]]
print(rotate_90_clock(mat), "   ---[[5, 3, 1], [6, 4, 2]]---")
print(list(map(lambda x: list(x)[::-1], zip(*mat))))
print(*mat)