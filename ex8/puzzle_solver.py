#################################################################
# FILE : ex8.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex8 2024
# DESCRIPTION: A simple program to practice backtracking
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: none
# NOTES: none
#################################################################
import copy
from typing import List, Tuple, Set, Optional

# We define the types of a partial picture and a constraint (for type checking).
Picture = List[List[int]]
Constraint = Tuple[int, int, int]


# TODO: write entire code prettier


def seen_cells_helper(picture: Picture, row: int, col: int, unknown_cell):
    """
    counts all seen cells
    :param picture: 2D list
    :param row: specific cell row
    :param col: specific cell col
    :param unknown_cell: -1 or 1. if -1 count. elif 1 treat like black
    :return: number of seen cells
    """
    seen_cells = 0
    row_index = row
    col_index = col
    # check left
    while col_index >= 0:
        if picture[row_index][col_index] in (1, unknown_cell):
            seen_cells += 1
        else:
            break
        col_index -= 1
    col_index = col  # reset col index
    row_index -= 1  # go one up
    # check up
    while row_index >= 0:
        if picture[row_index][col_index] in (1, unknown_cell):
            seen_cells += 1
        else:
            break
        row_index -= 1
    row_index = row
    col_index += 1  # go one right
    # check right
    while col_index < len(picture[0]):
        if picture[row_index][col_index] in (1, unknown_cell):
            seen_cells += 1
        else:
            break
        col_index += 1
    col_index = col
    row_index += 1  # go one down
    # check down
    while row_index < len(picture):
        if picture[row_index][col_index] in (1, unknown_cell):
            seen_cells += 1
        else:
            break
        row_index += 1
    # return number of seen cells
    return seen_cells


def max_seen_cells(picture: Picture, row: int, col: int) -> int:
    """count all seen cells - including unknown cells"""
    unknown_cell = -1  # unknown cells count, count also -1' cells
    if picture[row][col] == 0:
        return 0
    return seen_cells_helper(picture, row, col, unknown_cell)


def min_seen_cells(picture: Picture, row: int, col: int) -> int:
    """count all seen cells - excluding unknown cells"""
    unknown_cell = 1  # unknown cells don't count. ignore, count only 1' cells
    if picture[row][col] == 0 or picture[row][col] == -1:
        return 0
    return seen_cells_helper(picture, row, col, unknown_cell)


def check_constraints(picture: Picture, constraints_set: Set[Constraint]) -> int:
    """
    check if constraints are exactly satisfied
    :param picture: partial picture
    :param constraints_set:
    :return: 0 - at least one of the constraints is violated,
    1 - all the constraints are satisfied
    2 - else
    """
    possible = 0
    for constraint in constraints_set:
        if max_seen_cells(picture, constraint[0], constraint[1]) != constraint[2] and min_seen_cells(picture, constraint[0], constraint[1]) != constraint[2]:
            return 0
        if max_seen_cells(picture, constraint[0], constraint[1]) != min_seen_cells(picture, constraint[0], constraint[1]):
            if min_seen_cells(picture, constraint[0], constraint[1]) <= constraint[2] <= max_seen_cells(picture, constraint[0], constraint[1]):
                possible = 2
    if possible == 2:
        return 2
    return 1


def solve_puzzle_helper(constraints_set: Set[Constraint], n: int, m: int, picture: Picture, row, col) -> Picture:
    print(check_constraints(picture, constraints_set))
    if check_constraints(picture, constraints_set) != 0:
        picture[row][col] = 1
        print(picture)

        if col < m - 1:
            solve_puzzle_helper(constraints_set, n, m, picture, row, col + 1)
        if row < n - 1:
            solve_puzzle_helper(constraints_set, n, m, picture, row + 1, 0)
    else:
        picture[row][col] = 0
        print(picture)
        if col < m - 1:
            solve_puzzle_helper(constraints_set, n, m, picture, row, col + 1)
        if row < n - 1:
            solve_puzzle_helper(constraints_set, n, m, picture, row + 1, 0)

    # if check_constraints(picture, constraints_set) == 0:
    #     picture[row][col] = -1  # ?
    #     return None  # no solution
    #
    # if check_constraints(picture, constraints_set) == 1:    # finish
    #     return picture
    #
    # # while check_constraints(picture, constraints_set) == 2:
    # picture[row][col] = 1  # white
    # print(picture)
    # if col < m - 1:
    #     solve_puzzle_helper(constraints_set, n, m, picture, row, col + 1)
    #
    #     # solve_puzzle_helper(constraints_set, n, m, picture, row, col)
    # if row < n - 1:
    #     solve_puzzle_helper(constraints_set, n, m, picture, row + 1, 0)
    #
    # return None


def solve_puzzle(constraints_set: Set[Constraint], n: int, m: int) -> Optional[Picture]:
    picture = []
    for i in range(n):
        row = []
        for j in range(m):
            row.append(-1)
        picture.append(row)

    return solve_puzzle_helper(constraints_set, n, m, picture, 0, 0)


def how_many_solutions(constraints_set: Set[Constraint], n: int, m: int) -> int:
    ...


def generate_puzzle(picture: Picture) -> Set[Constraint]:
    ...

#
# picture1 = [[-1, 0, 1, -1], [0, 1, -1, 1], [1, 0, 1, 0]]
# picture2 = [[0, 0, 1, 1], [0, 1, 1, 1], [1, 0, 1, 0]]
# # print(check_constraints(picture1, {(0, 3, 5), (1, 2, 5), (2, 0, 1)}))
# print(check_constraints(picture2, {(0, 3, 3), (1, 2, 5), (2, 0, 1)}))
# print(check_constraints(picture1, {(0, 3, 3), (1, 2, 5), (2, 0, 1)}))
# print(solve_puzzle({(0, 3, 3), (1, 2, 5), (2, 0, 1), (0, 0, 0)}, 3, 4))
print(solve_puzzle({(0, 0, 2), (1, 0, 2)}, 2, 2))
