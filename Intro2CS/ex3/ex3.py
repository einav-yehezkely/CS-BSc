#################################################################
# FILE : ex3.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex3 2024
# DESCRIPTION: A program for practicing the usage of loops
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: https://www.w3schools.com/python/ref_keyword_not.asp
# NOTES: none
#################################################################

def input_list():
    """returns a list with all user-inputs and summation of them"""
    list_returned = []
    user_input = input()
    input_sum = 0
    while user_input:  # while input isn't empty
        list_returned.append(float(user_input))
        input_sum += float(user_input)
        user_input = input()
    list_returned.append(input_sum)
    return list_returned


def inner_product(vec_1, vec_2):
    """returns the inner product of two vectors"""
    if len(vec_1) == len(vec_2):
        if len(vec_1) == 0:
            return 0
        product = 0
        for i in range(len(vec_1)):
            product += vec_1[i] * vec_2[i]
        return product
    return None


def sequence_monotonicity(sequence):
    """checks sequence for monotonicity - strictly or regular. returns boolean value for each option"""
    increasing, strictly_increasing, decreasing, strictly_decreasing = True, True, True, True  # default values

    if len(sequence) > 1:  # else: default values
        # check if increasing
        for i in range(len(sequence) - 1):
            if sequence[i] > sequence[i + 1] or sequence[i] == sequence[i + 1]:
                if strictly_increasing is not False:  # once False always False
                    strictly_increasing = False
                if sequence[i] != sequence[i + 1]:  # > and != means False
                    increasing = False
        # check if decreasing
        for i in range(len(sequence) - 1):
            if sequence[i] < sequence[i + 1] or sequence[i] == sequence[i + 1]:
                if strictly_decreasing is not False:  # once False always False
                    strictly_decreasing = False
                if sequence[i] != sequence[i + 1]:  # < and != means False
                    decreasing = False

    sequence_monotonicity_answer = [increasing, strictly_increasing, decreasing, strictly_decreasing]
    return sequence_monotonicity_answer


def monotonicity_inverse(def_bool):
    """gets a list of boolean values for sequence monotonicity and returns a list that answers these requirements"""
    # strictly increasing -> increasing and not decreasing or strictly decreasing
    if def_bool[1]:
        if (not def_bool[0]) or def_bool[2] or def_bool[3]:
            return None
        return [1, 2, 3, 4]
    # increasing -> not strictly decreasing
    if def_bool[0]:
        if def_bool[3]:
            return None
        # if also decreasing - same values
        if def_bool[2]:
            return [1, 1, 1, 1]
        return [1, 2, 2, 3]
    # strictly decreasing -> decreasing and not increasing or strictly increasing
    if def_bool[3]:
        if (not def_bool[2]) or def_bool[1] or def_bool[0]:
            return None
        return [4, 3, 2, 1]
    # decreasing -> not strictly increasing
    if def_bool[2]:
        if def_bool[1]:
            return None
        return [4, 3, 3, 2]
    # if not decreasing and not increasing:
    return [1, 4, 2, 3]


def sum_three(mat, row, col, start_col, start_row):
    """a pre-function for convolve function: summation of first 3 numbers on each row
    from a given column and row to a given column and row"""
    sum_of_three = 0
    for i in range(start_row, row):
        for j in range(start_col, col):
            sum_of_three += mat[i][j]
    return sum_of_three


def convolve(mat):
    """gets a matrix and returns the outcome of convolution
    by making a range of 3X3 matrix and move it each time"""
    row, col = 3, 3
    start_col, start_row = 0, 0
    current_l, all_l = [], []
    # if [] not in mat and mat != []:
    if mat:  # False if empty
        while row <= len(mat):
            while col <= len(mat[0]):
                current_l.append(sum_three(mat, row, col, start_col, start_row))
                col += 1    # move 1 col right
                start_col += 1  # move starting col 1 right
            all_l.append(current_l)
            current_l = []
            row += 1    # move 1 row down
            start_row += 1  # move starting row 1 down
            col = 3  # restart end col
            start_col = 0   # restart start col
        return all_l
    return None


def sum_of_vectors(vec_lst):
    """summation of vectors - same coordinate on each list"""
    if vec_lst:  # False if empty
        sum_of_vecs = 0
        list_returned = []
        for i in range(len(vec_lst[0])):    # coordinate
            for j in range(len(vec_lst)):   # list  -   sum every coordinate from ease list
                sum_of_vecs += vec_lst[j][i]
            list_returned.append(sum_of_vecs)
            sum_of_vecs = 0
        return list_returned
    return None


def num_of_orthogonal(vectors):
    """gets a list of vectors and returns the number of orthogonal pairs"""
    orthogonal_counter = 0
    for i in range(len(vectors) - 1):
        for j in range(i+1, len(vectors)):
            if inner_product(vectors[i], vectors[j]) == 0:
                orthogonal_counter += 1
    return orthogonal_counter
