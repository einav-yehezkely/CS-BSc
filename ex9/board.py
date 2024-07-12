from typing import Tuple, List, Optional
from car import Car
import json
import sys

Coordinates = Tuple[int, int]
TARGET_CELL = (3, 7)

args = sys.argv


class Board:
    """
    Add a class description here.
    Write briefly about the purpose of the class.
    """

    def __init__(self) -> None:
        """
        A constructor for a Board object.
        """
        self.__board = [['_' for _ in range(7)] for _ in range(7)]
        self.__cars = []
        # with open('car_config.json') as file:
        #     self.__cars = json.load(file)
        # print(self.__cars)

    def __str__(self) -> str:
        """
        This function is called when a board object is to be printed.
        :return: A string representing the current status of the board.
        """
        # The game may assume this function returns a reasonable representation
        # of the board for printing, but may not assume details about it.
        brd = ''
        row_num = 0
        for row in self.__board:
            brd += (' '.join(row)) + " "
            if row_num == 3:
                brd += ("E \n")
            else:
                brd += ("* \n")
            row_num += 1
        return brd

    def cell_list(self) -> List[Coordinates]:
        """
        This function returns the coordinates of cells in this board.
        :return: list of coordinates.
        """
        # In this board, returns a list containing the cells in the square
        # from (0,0) to (6,6) and the target cell (3,7)
        list_of_coordinates = [(row, col) for row in range(len(self.__board)) for col in range(len(self.__board[0]))]
        list_of_coordinates.append(TARGET_CELL)
        return list_of_coordinates

    def possible_moves(self) -> List[Tuple[str, str, str]]:
        """ 
        This function returns the legal moves of all cars in this board.
        :return: list of tuples of the form (name, move_key, description)
                 representing legal moves. The description should briefly
                 explain what is the movement represented by move_key.
        """
        # From the provided example car_config.json file, the return value could be
        # [('O','d',"description"), ('R','r',"description"), ('O','u',"description")]
        legal_moves = []
        for direction in ["u", "d", "l", "r"]:
            ################################
            if direction == "u":
                description = "move up"
            elif direction == "d":
                description = "move down"
            elif direction == "l":
                description = "move left"
            elif direction == "r":
                description = "move right"
            ##############################
            for car in self.__cars:
                requirements = car.movement_requirements(direction)
                if requirements[0][0] < 0 or requirements[0][0] >= 7 or requirements[0][1] < 0 or requirements[0][1] >= 7:
                    continue
                if direction in car.possible_moves().keys():
                    if self.__board[requirements[0][0]][requirements[0][1]] == '_':
                        legal_moves.append((car.get_name(), direction, description))
        return legal_moves


    def target_location(self) -> Coordinates:
        """
        This function returns the coordinates of the location that should be 
        filled for victory.
        :return: (row, col) of the goal location.
        """
        # In this board, returns (3,7)
        return TARGET_CELL

    def cell_content(self, coordinates: Coordinates) -> Optional[str]:
        """
        Checks if the given coordinates are empty.
        :param coordinates: tuple of (row, col) of the coordinates to check.
        :return: The name of the car in "coordinates", None if it's empty.
        """
        if self.__board[coordinates[0]][coordinates[1]] == '_':
            return None
        return self.__board[coordinates[0]][coordinates[1]]

    def __valid_location(self, coord) -> bool:
        # check for index errors
        if coord[0] < 0 or coord[0] >= 7 or coord[1] < 0 or coord[1] >= 7:
            return False
        # check for collisions
        if self.__board[coord[0]][coord[1]] != '_':
            return False
        return True

    def add_car(self, car: Car) -> bool:
        """
        Adds a car to the game.
        :param car: car object to add.
        :return: True upon success, False if failed.
        """
        # Remember to consider all the reasons adding a car can fail.
        # You may assume the car is a legal car object following the API.
        car_coordinates = car.car_coordinates()
        for coord in car_coordinates:
            if not self.__valid_location(coord):
                return False
        for coord in car_coordinates:
            self.__board[coord[0]][coord[1]] = car.get_name()
        self.__cars.append(car)
        return True

    def move_car(self, name: str, move_key: str) -> bool:
        """
        Moves car one step in a given direction.
        :param name: name of the car to move.
        :param move_key: the key of the required move.
        :return: True upon success, False otherwise.
        """
        if name not in self.__cars:
            return False



board = Board()
print(board)
print(board.cell_list())
print(board.target_location())
print(board.cell_content((3, 5)))
with open('car_config.json') as file:
    cars = json.load(file)
list_of_cars = list(cars.items())
list_of_tuples = []
# print(list_of_cars)
for car in list_of_cars:
    print(board.add_car(Car(car[0], car[1][0], tuple(car[1][1]), car[1][2]))) # name, length, location, orientation
    # print(board.add_car(Car(car[0], car[1][0], tuple(car[1][1]), car[1][2])))
# print(board.add_car(Car("Y", 2, (0,1), 1)))

#     list_of_tuples.append(tuple(car[1][1]))
# print(list_of_tuples)
# car1 = Car("Y", 2, (2, 5), 1)
# car2 = Car("R", 3, (0, 4), 0)
# print(board.add_car(car1))
# # print(board.add_car(car2))
print(board)
print(board.possible_moves())
print(list_of_cars)
dic = {1: (4,5,6) , 2: 2, 3: 3, 4: 4}
print(dic.get(1))