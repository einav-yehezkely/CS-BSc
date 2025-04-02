#################################################################
# FILE : ex8.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex8 2024
# DESCRIPTION: A simple program to practice backtracking - solving a puzzle.
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: https://www.youtube.com/watch?v=gBC_Fd8EE8A&t=1s
# https://www.youtube.com/watch?v=lK4N8E6uNr4
# NOTES: none
#################################################################
from typing import List, Tuple, Set, Optional

# We define the types of a partial picture and a constraint (for type checking).
Picture = List[List[int]]
Constraint = Tuple[int, int, int]


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
        if constraint[0] == row or constraint[1] == col:
            if check_constraints(picture, {constraint}) == 0:
                return False
    return True


def solve_puzzle_helper(constraints_set: Set[Constraint], n: int, m: int, picture: Picture, row, col, find_all=False, counter=0):
    """
    :param find_all: if True then count all possible solutions
    :param constraints_set:
    :param n: row length
    :param m: column length
    :param picture: unknown picture
    :param row: current row
    :param col: current column
    :return: one possible solution / number of possible solutions if find_all is True
    """
    if row == n:
        # only if all constraints are exactly satisfied - return True
        if check_constraints(picture, constraints_set) == 1:
            if find_all:
                return counter + 1
            return True
        if find_all:
            return counter
        return False
    # move horizontally
    if col + 1 < m:
        next_col = col + 1
        next_row = row
    # move vertically
    else:
        next_col = 0
        next_row = row + 1

    for color in (0, 1):
        picture[row][col] = color
        if valid_color(picture, row, col, constraints_set):

            if find_all:
                # reached the end and all constraints are satisfied, so it's a possible solution
                counter = solve_puzzle_helper(constraints_set, n, m, picture, next_row, next_col, True, counter)

            else:
                # recursive call. after row == n and constraints are satisfied, fold.
                if solve_puzzle_helper(constraints_set, n, m, picture, next_row, next_col, False):
                    return True

        if find_all:
            picture[row][col] = -1

    # if not valid for 0 and 1 don't change counter and backtrack
    if find_all:
        return counter

    # if not valid for 0 and 1 return False and backtrack
    return False


def solve_puzzle(constraints_set: Set[Constraint], n: int, m: int) -> Optional[Picture]:
    """call solve_puzzle_helper to return one possible solution or None if no solution exists"""
    picture = [[-1 for _ in range(m)] for _ in range(n)]
    if solve_puzzle_helper(constraints_set, n, m, picture, 0, 0):
        return picture
    return None


def how_many_solutions(constraints_set: Set[Constraint], n: int, m: int) -> int:
    """call solve_puzzle_helper to count all possible solutions"""
    picture = [[-1 for _ in range(m)] for _ in range(n)]
    # # more efficient to skip recursive call if there are no constraints
    if not constraints_set:
        return n * m * 2 * 2
    return solve_puzzle_helper(constraints_set, n, m, picture, 0, 0, find_all=True)


def create_list_of_constraints(picture) -> List[Constraint]:
    constraints_set = []
    for row in range(len(picture)):
        for col in range(len(picture[0])):
            constraints_set.append((row, col, max_seen_cells(picture, row, col)))
    return constraints_set

def generate_puzzle(picture: Picture) -> Set[Constraint]:
    """generate constraints set for a given picture"""
    if len(picture) == 1 and len(picture[0]) == 1:
        if picture[0][0] == 0:
            return {(0, 0, 0)}
        return {(0, 0, 1)}

    constraints_set = create_list_of_constraints(picture)
    minimal_set = create_list_of_constraints(picture)
    for i in constraints_set:
        minimal_set.remove(i)

        if how_many_solutions(set(minimal_set), len(picture), len(picture[0])) != 1:
            minimal_set.append(i)

    return set(minimal_set)
