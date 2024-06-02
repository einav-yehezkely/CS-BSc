#################################################################
# FILE : battleship.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex4 2024
# DESCRIPTION: A program that runs the game Battleship
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: https://www.commfront.com/pages/ascii-chart
# https://www.youtube.com/watch?v=WSBAh_OStA4
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
    return tup  # return row_index, column_index


def create_list_of_locations(num_rows, num_cols):
    """creates a list of all board indexes"""
    list_of_locations = []
    # tup = ()
    for i in range(num_rows):
        for j in range(num_cols):
            list_of_locations.append((i, j))
    return list_of_locations


def valid_ship(board, size, loc):
    """check if location to place ship is valid"""
    if size > (helper.NUM_ROWS - loc[0] + 1):
        # print("size of ship is too big for this location")
        return False
    row_index = loc[0]  # we need to move 1 row down every time to reach ship size
    cell_count = 0
    while cell_count < size:
        # check if current location is in board range
        if (0 <= row_index <= helper.NUM_ROWS) and (0 <= loc[1] <= helper.NUM_COLUMNS):
            if board[row_index - 1][loc[1]] == 1:  # check if ship was placed on top of other ship
                # print("you cant place a ship on top of another ship")
                return False
            # print(f"here you can {row_index - 1}")
            cell_count += 1
            row_index += 1
        else:
            # print("Illegal value - out of board's range")
            return False

    # print("everything is good")
    return True


def uppercase(user_input):
    """make letter uppercase"""
    upper_input = user_input.replace(user_input[0], user_input[0].upper())
    return upper_input


def create_player_board(rows, columns, ship_sizes):
    """create board from user inputs for ships"""
    board = (init_board(rows, columns))
    row_index = 0
    i = 0
    while i < len(ship_sizes):
        input_from_user_uppercase = uppercase(
            helper.get_input("Please insert column value (letter) and row value ("
                             "number): "))
        location_from_user = cell_loc(input_from_user_uppercase)
        row_input = location_from_user[row_index]
        col_input = location_from_user[1]
        if valid_ship(board, ship_sizes[i], location_from_user):
            for j in range(ship_sizes[i]):
                board[row_input][col_input] = helper.SHIP
                row_input += 1
            helper.print_board(board)
            i += 1
        else:
            print("Illegal value")

    return board


def create_computer_board(rows, columns, ship_sizes):
    """create hidden board from random locations for ships"""
    board = (init_board(rows, columns))
    # helper.print_board(board)
    locations = create_list_of_locations(helper.NUM_ROWS, helper.NUM_COLUMNS)
    # helper.print_board(board)
    i = 0
    while i < len(ship_sizes):
        chosen_location = helper.choose_ship_location(board, ship_sizes, locations)
        chosen_row, chose_col = chosen_location
        # print(chosen_row+1,chose_col+1)
        # print(ship_sizes[i])
        if valid_ship(board, ship_sizes[i], (chosen_row + 1, chose_col)):
            for j in range(ship_sizes[i]):
                board[chosen_row][chose_col] = helper.SHIP
                chosen_row += 1
            # helper.print_board(board)
            i += 1
        # else:
        #     print("illegal_val")
    return board


def fire_torpedo(board, loc):
    """fire torpedo on board based on given location"""
    if (0 <= loc[0] <= helper.NUM_ROWS) or (0 <= loc[1] <= helper.NUM_COLUMNS - 1):
        if board[loc[0]][loc[1]] == helper.WATER:
            board[loc[0]][loc[1]] = helper.HIT_WATER
        elif board[loc[0]][loc[1]] == helper.SHIP:
            board[loc[0]][loc[1]] = helper.HIT_SHIP
    return board


def is_torpedo_location_valid(board, location):
    if (0 <= location[0] <= helper.NUM_ROWS - 1) and (0 <= location[1] <= helper.NUM_COLUMNS - 1):
        if (board[location[0]][location[1]] == helper.WATER) or (board[location[0]][location[1]] == helper.SHIP):
            return True
    return False


def main():
    hidden_board = init_board(helper.NUM_ROWS, helper.NUM_COLUMNS)
    helper.print_board(hidden_board)
    player_board = create_player_board(helper.NUM_ROWS, helper.NUM_COLUMNS, helper.SHIP_SIZES)
    computer_board = create_computer_board(helper.NUM_ROWS, helper.NUM_COLUMNS, helper.SHIP_SIZES)
    helper.print_board(player_board, hidden_board)
    num_of_ship_cells_of_comp = sum(helper.SHIP_SIZES)
    num_of_ship_cells_of_player = sum(helper.SHIP_SIZES)
    list_of_locations = create_list_of_locations(helper.NUM_ROWS, helper.NUM_COLUMNS)

    while num_of_ship_cells_of_comp and num_of_ship_cells_of_player:

        is_user_played = 0
        while is_user_played == 0:
            user_input = helper.get_input("Please insert location for torpedo: ")
            upper_input = user_input.replace(user_input[0], user_input[0].upper())
            chosen_location = cell_loc(upper_input)
            if is_torpedo_location_valid(computer_board, chosen_location):
                computer_board = fire_torpedo(computer_board, chosen_location)
                if computer_board[chosen_location[0]][chosen_location[1]] == helper.HIT_WATER:
                    hidden_board[chosen_location[0]][chosen_location[1]] = helper.HIT_WATER
                    # num_of_ship_cells_of_comp -= 1
                    is_user_played = 1
                if computer_board[chosen_location[0]][chosen_location[1]] == helper.HIT_SHIP:
                    hidden_board[chosen_location[0]][chosen_location[1]] = helper.HIT_SHIP
                    num_of_ship_cells_of_comp -= 1
                    is_user_played = 1

        is_comp_played = 0
        while is_comp_played == 0:
            comp_chosen_torpedo_location = helper.choose_torpedo_target(player_board, list_of_locations)
            if is_torpedo_location_valid(player_board, comp_chosen_torpedo_location):
                player_board = fire_torpedo(player_board, comp_chosen_torpedo_location)
                if player_board[comp_chosen_torpedo_location[0]][comp_chosen_torpedo_location[1]] == helper.HIT_WATER:
                    # hidden_board[chosen_location[0]][chosen_location[1]] = helper.HIT_WATER
                    # num_of_ship_cells_of_player -= 1
                    is_comp_played = 1
                if player_board[comp_chosen_torpedo_location[0]][comp_chosen_torpedo_location[1]] == helper.HIT_SHIP:
                    # hidden_board[chosen_location[0]][chosen_location[1]] = helper.HIT_SHIP
                    num_of_ship_cells_of_player -= 1
                    is_comp_played = 1
        helper.print_board(player_board, hidden_board)
    helper.print_board(player_board, computer_board)


if __name__ == "__main__":
    while True:
        main()
        while True:
            answer = helper.get_input("play again? (Y/N): ")
            if answer in ("Y", "N"):
                break
            print("invalid value.", end=' ')
        if answer == "Y":
            continue
        else:
            print("Game Over")
            break
    # main()
