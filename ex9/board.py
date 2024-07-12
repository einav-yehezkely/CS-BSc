from typing import Tuple, List, Optional
from car import Car
import json
import sys

Coordinates = Tuple[int, int]
TARGET_CELL = (3, 7)
DIRECTIONS = ["u", "d", "l", "r"]

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
        self.__board[3].append('E')
        self.__cars = {}

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
                brd += ("\n")
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
        for direction in DIRECTIONS:
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
                    if self.__board[requirements[0][0]][requirements[0][1]] == '_' or self.__board[requirements[0][0]][requirements[0][1]] == 'E':
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
        if self.__board[coordinates[0]][coordinates[1]] == '_' or self.__board[coordinates[0]][coordinates[1]] == 'E':
            return None
        return self.__board[coordinates[0]][coordinates[1]]

    def __valid_location(self, coord) -> bool:
        if coord[0] == 3 and coord[1] == 7:
            return True
        # check for index errors
        if coord[0] < 0 or coord[0] >= 7 or coord[1] < 0 or coord[1] >= 7:
            return False
        # check for collisions
        if self.__board[coord[0]][coord[1]] != '_' and self.__board[coord[0]][coord[1]]!= 'E':
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
        self.__cars[car.get_name()] = car
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
        car = self.__cars.get(name)
        if move_key not in car.possible_moves().keys():
            return False
        movement_requirements = car.movement_requirements(move_key)
        for requirement in movement_requirements:
            if not self.__valid_location(requirement):
                return False

        coordinates = car.car_coordinates()
        for coord in coordinates:
            self.__board[coord[0]][coord[1]] = '_'
        car.move(move_key)
        self.add_car(car)
        return True
