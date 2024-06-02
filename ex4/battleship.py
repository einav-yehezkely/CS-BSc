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
    row_index = loc[0] #- 1 # we need to move 1 row down every time to reach ship size
    cell_count = 0
    while cell_count < size:
        # check if current location is in board range
        if (0 <= row_index <= helper.NUM_ROWS - 1) and (0 <= loc[1] <= helper.NUM_COLUMNS):
            if board[row_index - 1][loc[1]] == 1:  # check if ship was placed on top of other ship # -1
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


def is_played(user_or_comp_played, comp_or_user_board, hidden_board, num_of_ships):
    """runs a turn of game"""
    list_of_locations = create_list_of_locations(helper.NUM_ROWS, helper.NUM_COLUMNS)
    is_user_or_comp_played = 0  # when turn ends successfully (after hitting valid target)
    # is_user_played turns 1 in order to move to turn of next player
    while is_user_or_comp_played == 0:
        if user_or_comp_played == 0:    # if 0=user is playing ask for torpedo target input
            user_input = uppercase(helper.get_input("Please insert location for torpedo: "))
            chosen_location = cell_loc(user_input)
        else:  # if 1=comp is playing randomly choose torpedo target
            chosen_location = helper.choose_torpedo_target(comp_or_user_board, list_of_locations)
        if is_torpedo_location_valid(comp_or_user_board, chosen_location):
            comp_or_user_board = fire_torpedo(comp_or_user_board, chosen_location)
            if comp_or_user_board[chosen_location[0]][chosen_location[1]] == helper.HIT_WATER:
                if user_or_comp_played == 0:   # if 0=user is playing, hidden bord needs to show the hit
                    hidden_board[chosen_location[0]][chosen_location[1]] = helper.HIT_WATER
                is_user_or_comp_played = 1  # turn ended
            if comp_or_user_board[chosen_location[0]][chosen_location[1]] == helper.HIT_SHIP:
                if user_or_comp_played == 0:  # if 0=user is playing, hidden bord needs to show the hit
                    hidden_board[chosen_location[0]][chosen_location[1]] = helper.HIT_SHIP
                num_of_ships -= 1   # one ship cell is down
                is_user_or_comp_played = 1  # turn ended
    return num_of_ships  # if num of ships = 0 the game will end


def main():
    """runs the game of battleship"""
    while True:  # restart option loop
        # create hidden board, computer board, player board
        hidden_board = init_board(helper.NUM_ROWS, helper.NUM_COLUMNS)
        helper.print_board(hidden_board)
        player_board = create_player_board(helper.NUM_ROWS, helper.NUM_COLUMNS, helper.SHIP_SIZES)
        computer_board = create_computer_board(helper.NUM_ROWS, helper.NUM_COLUMNS, helper.SHIP_SIZES)
        helper.print_board(player_board, hidden_board)
        # set number of ship cells to hit
        num_of_ship_of_player = sum(helper.SHIP_SIZES)
        num_of_ship_of_comp = sum(helper.SHIP_SIZES)
        # game ends when in one or both boards there are no ship cells to hit anymore
        while num_of_ship_of_player and num_of_ship_of_comp:
            num_of_ship_of_comp = is_played(0, computer_board, hidden_board, num_of_ship_of_comp)  # user play
            num_of_ship_of_player = is_played(1, player_board, hidden_board, num_of_ship_of_player)  # computer play

            helper.print_board(player_board, hidden_board)
        helper.print_board(player_board, computer_board)
        while True:
            if num_of_ship_of_player == 0:
                winner = "computer"
            else:
                winner = "player - YOU!"
            answer = helper.get_input(f"GAME OVER - The winner is: {winner}\nplay again? (Y/N): ")
            if answer == "Y" or answer == "N":
                break
            print("Illegal value.", end=' ')
        if answer == "Y":
            continue
        else:
            print("(:")
            break


if __name__ == "__main__":
    main()
