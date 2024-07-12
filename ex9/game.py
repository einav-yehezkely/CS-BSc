from board import Board
from car import Car
import sys
import json
from helper import *


LEGAL_NAMES = ("R", "G", "W", "O", "B", "Y")
LEGAL_ORIENTATIONS = (0, 1)
LEGAL_DIRECTIONS = ("l", "r", "u", "d")
LEGAL_LENGTH = (2, 3, 4)

class Game:
    """
    A class that runs the game of Rush Hour.
    """

    def __init__(self, board: Board) -> None:
        """
        Initialize a new Game object.
        :param board: An object of type board
        """
        # You may assume board follows the API
        self.__board = board

    def __single_turn(self):
        """
        Note - this function is here to guide you, and it is *not mandatory*
        to implement it. 

        The function runs one round of the game :
            1. Get user's input of: what color car to move, and what 
                direction to move it.
            2. Check if the input is valid.
            3. Try moving car according to user's input.

        Before and after every stage of a turn, you may print additional 
        information for the user, e.g., printing the board. In particular,
        you may support additional features, (e.g., hints) as long as they
        don't interfere with the API.
        """
        # TODO: tried all possible moves
        while True:
            user_input = input(f"Enter car's color ({LEGAL_NAMES}) and movement direction ({LEGAL_DIRECTIONS}) divided by a comma (,): \n")
            if user_input == "!":
                "BYE!"
                return False
            if len(user_input) != 3:
                print("Invalid input. Please try again.")
            elif user_input[0] not in LEGAL_NAMES or user_input[1] != "," or user_input[2] not in LEGAL_DIRECTIONS:
                print("Invalid input. Please try again.")
            elif not self.__board.move_car(user_input[0], user_input[2]):
                # if user_input[2] == "u":
                #     opposite = "d"
                # elif user_input[2] == "d":
                #     opposite = "u"
                # elif user_input[2] == "l":
                #     opposite = "r"
                # elif user_input[2] == "r":
                #     opposite = "l"
                #
                # if not self.__board.move_car(user_input[0], opposite):
                #     print("Invalid move. All possible moves are invalid. GAME OVER!")
                #     return False
                # else:
                #     self.__board.move_car(user_input[0], user_input[2])

                    # sys.exit()
                print("Invalid move. Please try again.")

            else:
                break

    def play(self) -> None:
        """
        The main driver of the Game. Manages the game until completion.
        :return: None
        """
        while True:
            print(self.__board)
            if self.__single_turn() is False:
                break
            if self.__board.cell_content(self.__board.target_location()) is not None:
                print(board)
                print("congratulations! You've won! \n---------GAME OVER---------")
                break


if __name__ == "__main__":
    # Your code here
    # All access to files, non API constructors, and such must be in this
    # section, or in functions called from this section.
    board = Board()
    args = sys.argv
    with open(args[1]) as file:
        cars = json.load(file)

    if not cars:
        print("No cars to place.")
    else:
        for car in cars.items():
            if car[0] in LEGAL_NAMES and car[1][0] in LEGAL_LENGTH and car[1][2] in LEGAL_ORIENTATIONS:
                board.add_car(Car(car[0], car[1][0], tuple(car[1][1]), car[1][2]))  # name, length, location, orientation

        game = Game(board)
        game.play()

