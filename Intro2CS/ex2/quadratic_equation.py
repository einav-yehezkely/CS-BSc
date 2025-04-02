#################################################################
# FILE : quadratic_equation.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex2 2024
# DESCRIPTION: A program that calculates a quadratic equation
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: none
# NOTES: none
#################################################################
import math


def quadratic_equation(a, b, c):
    """solve quadratic equation of given numbers"""
    delta = b ** 2 - 4 * a * c
    if delta < 0:  # no solution
        return None, None
    equation_plus = (-b + math.sqrt(delta)) / (2 * a)
    equation_minus = (-b - math.sqrt(delta)) / (2 * a)
    if delta == 0:  # one solution
        return equation_plus, None
    else:  # two solutions
        return equation_plus, equation_minus


def quadratic_equation_user_input():
    """gets input of 3 numbers from user and solve quadratic equation"""
    s = input("Insert coefficients a, b, and c: ").split()
    if s[0] == "0":
        print("The parameter 'a' may not equal 0")
        return
    x, y = quadratic_equation(float(s[0]), float(s[1]), float(s[2]))
    if x is not None and y is not None:
        print("The equation has 2 solutions:", x, "and", y)
    if ((x is None) or (y is None)) and not ((x is None) and (y is None)):
        print("The equation has 1 solution:", x)
    if (x is None) and (y is None):
        print("The equation has no solutions")
        