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
    number = int(name[1:3])  #- 1  # we can assume there are 26 columns max
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
    row_index = loc[0] - 1  # we need to move 1 row down every time to reach ship size
    cell_count = 0
    while cell_count < size:
        # check if current location is in board range
        if (0 <= row_index <= helper.NUM_ROWS) and (0 <= loc[1] <= helper.NUM_COLUMNS - 1):
            if board[row_index][loc[1]] == 1:  # check if ship was placed on top of other ship
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


def create_player_board(rows, columns, ship_sizes):
    """create board from user inputs for ships"""
    board = (init_board(rows, columns))
    # helper.print_board(board)
    row_index = 0
    i = 0
    while i < len(ship_sizes):
        location_from_user = cell_loc(helper.get_input("Please insert column value (CAPITAL letter) and row value ("
                                                       "NUMBER):"))
        row_input = location_from_user[row_index] - 1
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


def create_hidden_board(rows, columns, ship_sizes):
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
        if board[loc[0]][loc[1]] == 0:
            board[loc[0]][loc[0]] = 2
        elif board[loc[0]][loc[1]] == 1:
            board[loc[0]][loc[1]] = 3
    return board


def main():
    helper.print_board((create_player_board(helper.NUM_ROWS, helper.NUM_COLUMNS, helper.SHIP_SIZES)),
                       create_hidden_board(helper.NUM_ROWS, helper.NUM_COLUMNS, helper.SHIP_SIZES))



if __name__ == "__main__":
    # helper.print_board(init_board(helper.NUM_ROWS, helper.NUM_COLUMNS))
    # init_board(helper.NUM_ROWS, helper.NUM_COLUMNS)
    # user_input = helper.get_input("Please insert column value (CAPITAL letter) and row value (NUMBER)")
    # loca = cell_loc(user_input)
    # valid_ship(init_board(helper.NUM_ROWS, helper.NUM_COLUMNS), 3, loca)
    # create_player_board(helper.NUM_ROWS, helper.NUM_COLUMNS, helper.SHIP_SIZES)
    # create_hidden_board(helper.NUM_ROWS, helper.NUM_COLUMNS, helper.SHIP_SIZES)
    # create_list_of_locations(helper.NUM_ROWS, helper.NUM_COLUMNS)
    main()
