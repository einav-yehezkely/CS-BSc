#################################################################
# FILE : ex7.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex7 2024
# DESCRIPTION: A simple program to practice recursion
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: https://pythontutor.com/render.html#mode=display
# NOTES: none
#################################################################
from typing import *
from ex7_helper import *


def mult(x: N, y: int) -> N:
    """multiplication function"""
    if y == 0:
        return 0
    return add(x, mult(x, subtract_1(y)))


def is_even(n: int) -> bool:
    """checks if n is even"""
    if n == 0:
        return True
    return not (is_even(subtract_1(n)))


def log_mult(x: N, y: int) -> N:
    """multiplication function - O(log(n))"""
    if y == 0:
        return 0
    temp = log_mult(x, divide_by_2(y))
    if is_odd(y):
        result = add(add(x, temp), temp)
    else:
        result = add(temp, temp)
    return result


def is_power_helper(b, x, power=1):
    """checks if the number x is a power of the number b - helper"""
    if power > x or b == 0:
        return False
    elif power == x:
        return True
    power = log_mult(power, b)
    return is_power_helper(b, x, power)


def is_power(b: int, x: int) -> bool:
    """checks if the number x is a power of the number b"""
    return is_power_helper(b, x)


def reverse_helper(s: str, i=0):
    """reverse string - helper"""
    if i == len(s):
        return ""
    new_str = reverse_helper(s, add(i, 1))
    return append_to_end(new_str, s[i])


def reverse(s: str) -> str:
    """reverse string"""
    return reverse_helper(s)


def play_hanoi(Hanoi: Any, n: int, src: Any, dest: Any, temp: Any):
    pass

