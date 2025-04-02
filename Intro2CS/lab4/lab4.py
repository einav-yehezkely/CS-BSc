#################################################################
# FILE : lab4.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs lab4 2024
# DESCRIPTION: Let's play Nim!
# STUDENTS I DISCUSSED THE EXERCISE WITH: Students and instructor of the lab
# WEB PAGES I USED: none
# NOTES: Made it on lab4
#################################################################

"""Defining global variables"""

MATCH = '*'
ROW_LENGTH = 4
NUMBER_OF_ROW = 5
ERROR_NO_SUCH_ROW = "Illegal row number"
ERROR_ILLEGAL_AMOUNT = "Illegal amount"
MESSAGE_AMOUNT_REQUEST = "Please enter the amount to take from row number {}:"
MESSAGE_ROW_NUMBER_REQUEST = "Please enter row number:"
PLAYER_ONE = 1
PLAYER_TWO = 2
WINNER_MESSAGE = "The winner is .... PLAYER {}!!!!"
TURN_MESSAGE = "Player {}, it's your turn"


def init_board():
    return [ROW_LENGTH] * NUMBER_OF_ROW


def print_board(board):
    for row in board:
        print(MATCH * row)


def is_board_empty(board):
    # return not board
    for row in board:
        if row > 0:
            return False
    return True


def update_board(board, row, match_number):
    board[row] -= match_number
    return board


def check_row_number_validity(row):
    # return row in range(NUMBER_OF_ROW)
    return 0 <= row < NUMBER_OF_ROW


def check_amount_taken(board, row, amount_taken):
    return 0 < amount_taken <= board[row]


def get_next_player(current_player):
    return PLAYER_ONE if current_player == PLAYER_TWO else PLAYER_TWO


def get_input(board):
    chosen_row = int(input(MESSAGE_ROW_NUMBER_REQUEST))
    while not check_row_number_validity(chosen_row) or not board[chosen_row]:
        print(ERROR_NO_SUCH_ROW)
        chosen_row = int(input(MESSAGE_ROW_NUMBER_REQUEST))

    amount = int(input(MESSAGE_AMOUNT_REQUEST.format(chosen_row)))
    while not check_amount_taken(board, chosen_row, amount):
        print(ERROR_ILLEGAL_AMOUNT)
        amount = int(input(MESSAGE_AMOUNT_REQUEST))

    return chosen_row, amount


def run_game():
    board = init_board()
    current_player = PLAYER_TWO
    while not is_board_empty(board):
        current_player = get_next_player(current_player)
        print_board(board)
        print(TURN_MESSAGE.format(current_player))
        chosen_row, amount = get_input(board)
        update_board(board, chosen_row, amount)

    print(WINNER_MESSAGE.format(current_player))
