from typing import Tuple, List, Dict

Coordinates = Tuple[int, int]
VERTICAL = 0
HORIZONTAL = 1

class Car:
    """
    Create a car with a given name, length, location, and orientation. methods: return car coordinates, return car's
    possible moves, return movement requirements, move car, return car's name.
    """

    def __init__(self, name: str, length: int, location: Coordinates, 
                 orientation: int) -> None:
        """
        A constructor for a Car object.
        :param name: A string representing the car's name.
        :param length: A positive int representing the car's length.
        :param location: A tuple representing the car's head location (row,col).
        :param orientation: One of either 0 (VERTICAL) or 1 (HORIZONTAL).
        """
        # TODO: exceptions
        self.__name = name
        self.__length = length
        self.__location = location
        self.__orientation = orientation

    def car_coordinates(self) -> List[Coordinates]:
        """
        :return: A list of coordinates the car is in.
        """
        # TODO: exceptions
        if self.__orientation == VERTICAL:
            col = self.__location[1]
            return [(row, col) for row in range(self.__location[0],
                                              self.__location[0] + self.__length)]
        if self.__orientation == HORIZONTAL:
            row = self.__location[0]
            return [(row, col) for col in range(self.__location[1],
                                              self.__location[1] + self.__length)]


    def possible_moves(self) -> Dict[str, str]:
        """
        :return: A dictionary of strings describing possible movements 
                 permitted by this car.
        """
        # For this car type, keys are from 'udrl'
        # The keys for vertical cars are 'u' and 'd'.
        # The keys for horizontal cars are 'l' and 'r'.
        # You may choose appropriate strings to describe each movement.
        # For example: a car that supports the commands 'f', 'd', 'a' may return
        # the following dictionary:
        # {'f': "cause the car to fly and reach the Moon",
        #  'd': "cause the car to dig and reach the core of Earth",
        #  'a': "another unknown action"}
        if self.__orientation == VERTICAL:
            return {'u': "move up", 'd': "move down"}
        if self.__orientation == HORIZONTAL:
            return {'l': "move left", 'r': "move right"}


    def movement_requirements(self, move_key: str) -> List[Coordinates]:
        """ 
        :param move_key: A string representing the key of the required move.
        :return: A list of cell locations which must be empty in order for 
                 this move to be legal.
        """
        # For example, a car in locations [(1,2),(2,2)] requires [(3,2)] to
        # be empty in order to move down (with a key 'd').
        # TODO: exceptions
        car_coordinates = self.car_coordinates()
        if move_key == 'u':
            return [(car_coordinates[0][0] - 1, car_coordinates[0][1])]
        if move_key == 'd':
            return [(car_coordinates[self.__length - 1][0] + 1, car_coordinates[self.__length - 1][1])]
        if move_key == 'l':
            return [(car_coordinates[0][0], car_coordinates[0][1] - 1)]
        if move_key == 'r':
            return [(car_coordinates[self.__length - 1][0], car_coordinates[self.__length - 1][1] + 1)]

    def move(self, move_key: str) -> bool:
        """ 
        This function moves the car.
        :param move_key: A string representing the key of the required move.
        :return: True upon success, False otherwise
        """
        org_location = self.__location
        if move_key not in self.possible_moves().keys():
            return False
        if move_key == "r":
            self.__location = (org_location[0], org_location[1] + 1)
        if move_key == "l":
            self.__location = (org_location[0], org_location[1] - 1)
        if move_key == "u":
            self.__location = (org_location[0] - 1, org_location[1])
        if move_key == "d":
            self.__location = (org_location[0] + 1, org_location[1])
        self.car_coordinates()
        return True


    def get_name(self) -> str:
        """
        :return: The name of this car.
        """
        return self.__name
