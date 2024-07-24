def xyz(num_x, num_y, num_z):
    length = num_x + num_y + num_z
    return helper(num_x, num_y, num_z, length, "", [])


def helper(num_x, num_y, num_z, length, str, lst):
    if len(str) == length:
        lst.append(str)
        return lst
    if num_x > 0:
        helper(num_x - 1, num_y, num_z, length, str + "x", lst)
    if num_y > 0:
        helper(num_x, num_y - 1, num_z, length, str + "y", lst)
    if num_z > 0:
        helper(num_x, num_y, num_z - 1, length, str + "z", lst)
    return lst


print(xyz(2, 0, 1))
