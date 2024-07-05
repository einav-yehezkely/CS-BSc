#################################################################
# FILE : ex8.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex8 2024
# DESCRIPTION: A simple program to practice backtracking
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: https://www.youtube.com/watch?v=gBC_Fd8EE8A&t=1s
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

        if (max_seen_cells(picture, constraint[0], constraint[1]) != min_seen_cells(picture, constraint[0],
                                                                                    constraint[1])
                and min_seen_cells(picture, constraint[0], constraint[1]) <= constraint[2] <= max_seen_cells(picture,
                                                                                                             constraint[
                                                                                                                 0],
                                                                                                             constraint[
                                                                                                                 1])):
            possible = 2
        elif (max_seen_cells(picture, constraint[0], constraint[1]) != constraint[2]
              and min_seen_cells(picture, constraint[0], constraint[1]) != constraint[2]):
            return 0
    if possible == 2:
        return 2
    return 1


def valid_color(picture: Picture, row: int, col: int, constraints_set) -> bool:
    """check only if affects cell's column or row"""
    for constraint in constraints_set:
        if (constraint[0] == row or constraint[1] == col) and check_constraints(picture, {constraint}) == 0:
            return False
    return True


def solve_puzzle_helper(constraints_set: Set[Constraint], n: int, m: int, picture: Picture, row, col):
    if row == n and col == m:
        if check_constraints(picture, constraints_set) == 0:
            return False
        else:
            return True

    last_color = -1
    for color in (0, 1):
        picture[row][col] = color
        if valid_color(picture, row, col, constraints_set):

            if col + 1 < m:
                solve_puzzle_helper(constraints_set, n, m, picture, row, col + 1)

            elif row + 1 < n:
                solve_puzzle_helper(constraints_set, n, m, picture, row + 1, 0)
        else:
            picture[row][col] = last_color

        last_color = color

    return True




def solve_puzzle(constraints_set: Set[Constraint], n: int, m: int) -> Optional[Picture]:
    picture = []
    for i in range(n):
        row = []
        for j in range(m):
            row.append(-1)
        picture.append(row)

    if solve_puzzle_helper(constraints_set, n, m, picture, 0, 0):
        return picture
    else:
        return None


def how_many_solutions(constraints_set: Set[Constraint], n: int, m: int) -> int:
    ...


def generate_puzzle(picture: Picture) -> Set[Constraint]:
    ...


# picture1 = [[-1, 0, 1, -1], [0, 1, -1, 1], [1, 0, 1, 0]]
# picture2 = [[0, 0, 1, 1], [0, 1, 1, 1], [1, 0, 1, 0]]
# print(check_constraints(picture1, {(0, 3, 5), (1, 2, 5), (2, 0, 1)}))
# print(check_constraints(picture2, {(0, 3, 3), (1, 2, 5), (2, 0, 1)}))
# print(check_constraints(picture1, {(0, 3, 3), (1, 2, 5), (2, 0, 1)}))
print(solve_puzzle({(0, 0, 2), (1, 0, 2)}, 2, 2), "   supposed: [[1,0],[1,0]]")
print(solve_puzzle({(0, 3, 3), (1, 2, 5), (2, 0, 1), (0, 0, 0)}, 3, 4),
      "   supposed: [[0, 0, 1, 1], [0, 1, 1, 1], [1, 0, 1, 0]]")
print(solve_puzzle({(0, 3, 3), (1, 2, 5), (2, 0, 1), (2, 3, 5)}, 3, 4), "   supposed: None")
print(solve_puzzle({(0, 2, 3), (1, 1, 4), (2, 2, 5)}, 3, 3),
      "   supposed: [[[0, 0, 1], [1, 1, 1], [1, 1, 1]] or [[1, 0, 1], [1, 1, 1], [1, 1, 1]]")
