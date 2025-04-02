#################################################################
# FILE : math_print.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex1 2024
# DESCRIPTION: A simple program that prints a few mathematical calculations
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: https://he.wikipedia.org/wiki/%D7%99%D7%97%D7%A1_%D7%94%D7%96%D7%94%D7%91 ,
# https://docs.python.org/3/library/math.html ,
# NOTES: none
#################################################################
import math


def golden_ratio():
    """prints the golden ratio"""
    print((1+math.sqrt(5))/2)


def six_squared():
    """prints 6 to the power of 2"""
    print(6**2)


def hypotenuse():
    """prints the length of the hypotenuse of a right triangle"""
    ab = (12**2)+(5**2)
    print(math.sqrt(ab))


def pi():
    """prints the value of pi"""
    # circumference = 15.0011049209
    # diameter = 4.775
    # print(circumference/diameter) # another option
    print(math.pi)


def e():
    """prints the value of e"""
    print(math.e)


def squares_area():
    """prints the area of squares 1X1 to 10X10"""
    for i in range(1, 10):
        print(i*i, end=" ")
    print(10*10)


if __name__ == "__main__":
    golden_ratio()
    six_squared()
    hypotenuse()
    pi()
    e()
    squares_area()
