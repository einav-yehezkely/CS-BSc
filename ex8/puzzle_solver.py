#################################################################
# FILE : ex8.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex8 2024
# DESCRIPTION: A simple program to practice backtracking
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: none
# NOTES: none
#################################################################

from typing import List, Tuple, Set, Optional
UNKNOWN_CELL = -1


# We define the types of a partial picture and a constraint (for type checking).
Picture = List[List[int]]
Constraint = Tuple[int, int, int]

# TODO: write entire code prettier

def seen_cells_helper(picture: Picture, row: int, col: int):
    pass
def max_seen_cells(picture: Picture, row: int, col: int) -> int:
    UNKNOWN_CELL = 1    # treat unknown cells as white
    seen_cells = 0
    if picture[row][col] == 0:
        return seen_cells
    row_index = row
    col_index = col
    # check left
    while col_index >= 0:
        if picture[row_index][col_index] in (1, -1):
            seen_cells += 1
        else:
            break
        col_index -= 1
    col_index = col     # reset col index
    row_index -= 1  # go one up
    # check up
    while row_index >= 0:
        if picture[row_index][col_index] in (1, -1):
            seen_cells += 1
        else:
            break
        row_index -= 1
    row_index = row
    col_index += 1  # go one right
    # check right
    while col_index < len(picture[0]):
        if picture[row_index][col_index] in (1, -1):
            seen_cells += 1
        else:
            break
        col_index += 1
    col_index = col
    row_index += 1 # go one down
    # check down
    while row_index < len(picture):
        if picture[row_index][col_index] in (1, -1):
            seen_cells += 1
        else:
            break
        row_index += 1
    # return number of seen cells
    return seen_cells


def min_seen_cells(picture: Picture, row: int, col: int) -> int:
    ...


def check_constraints(picture: Picture, constraints_set: Set[Constraint]) -> int:
    ...


def solve_puzzle(constraints_set: Set[Constraint], n: int, m: int) -> Optional[Picture]:
    ...


def how_many_solutions(constraints_set: Set[Constraint], n: int, m: int) -> int:
    ...


def generate_puzzle(picture: Picture) -> Set[Constraint]:
    ...

picture = [[-1, 0, 1, -1], [0, 1, -1, 1], [1, 0, 1, 0]]
print(max_seen_cells(picture, 0, 0))
print(max_seen_cells(picture, 1, 0))
print(max_seen_cells(picture, 1, 2))
print(max_seen_cells(picture, 1, 1))