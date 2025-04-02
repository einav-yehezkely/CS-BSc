#################################################################
# FILE : shapes.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex2 2024
# DESCRIPTION: A program that calculates the area of a shape that the user choose
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: none
# NOTES: none
#################################################################
import math


def shape_area():
    """calculates the area of a shape that the user choose"""
    shape = input("Choose shape (1=circle, 2=rectangle, 3=triangle): ")
    if shape not in "123":
        return None
    elif shape == "1":  # circle
        r = float(input())  # circle radius
        circle_area = math.pi * (pow(r, 2))
        return circle_area
    elif shape == "2":  # rectangle
        a = float(input())  # first square's side
        b = float(input())  # second square's side
        rectangle_area = a * b
        return rectangle_area
    elif shape == "3":  # triangle
        a = float(input())  # triangle side
        triangle_area = (((math.sqrt(3))/4) * pow(a, 2))
        return triangle_area
