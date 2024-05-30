#################################################################
# FILE : battleship.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex4 2024
# DESCRIPTION: A program that runs the game Battleship
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: https://www.commfront.com/pages/ascii-chart
# NOTES: none
#################################################################
import helper


def init_board(rows, columns):
    """creates a board from given rows and columns number"""
    board = []
    for i in range(rows):
        row = []
        for j in range(columns):
            row.append(helper.WATER)  # empty
        board.append(row)
    return board


def cell_loc(name):
    """returns cell coordinates from user input"""
    letter = ord(name[0]) - 65
    number = int(name[1:3]) - 1  # we can assume there are 26 columns max
    tup = (number, letter)
    return tup   # return row_index, column_index


def valid_ship(board, size, loc):
    pass


def create_player_board(rows, columns, ship_sizes):
    pass


def fire_torpedo(board, loc):
    pass


def main():
    pass


if __name__ == "__main__":
    helper.print_board(init_board(helper.NUM_ROWS, helper.NUM_COLUMNS))
    # init_board(helper.NUM_ROWS, helper.NUM_COLUMNS)
    user_input = helper.get_input("Please insert column value (CAPITAL letter) and row value (NUMBER)")
    cell_loc(user_input)
    main()
