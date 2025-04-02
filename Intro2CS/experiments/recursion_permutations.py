def gen_str(n):
    return gen_str_helper("", n)

def gen_str_helper(str, n):
    """print all combinations for binary string. 1 and 1 aren't allowed together"""
    """second version - all combinations"""
    if len(str) == n:
        print(str)
        return
    gen_str_helper(str+"0", n)
    # if str == "" or str[-1] == "0":
    gen_str_helper(str+"1", n)

########################
#   list form
########################
# from 2021_B_Aleph, Q3
# return list of all sub lists with length n. their values are x and y.
# y can't appear twice in a row.
def f3(n, x, y):
    return helper(n, x, y, [], [])

def helper(n, x, y, lst, big_lst):
    if len(lst) == n:
        big_lst.append(lst[::])
        return
    lst.append(x)
    helper(n, x, y, lst, big_lst)
    lst.pop()
    if lst == [] or lst[-1] != y:
        lst.append(y)
        helper(n, x, y, lst, big_lst)
        lst.pop()
    return big_lst

print("generate strings - all permutations of 0, 1 in length n:")
gen_str(3)
print("return list of all sub lists with length n. their values are x and y. y can't appear twice in a row:")
print(f3(3, "x", "y"))