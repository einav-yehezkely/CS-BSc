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
    if x == 1:    # b^0 = 1
        return True
    if b == 0 and x == 0:   # 0^n = 0
        return True
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
    """play Hanoi game"""
    if n <= 0:
        return
    if n == 1:
        Hanoi.move(src, dest)
        return
    play_hanoi(Hanoi, n - 1, src, temp, dest)
    play_hanoi(Hanoi, 1, src, dest, temp)
    play_hanoi(Hanoi, n - 1, temp, dest, src)


def number_of_ones_digit_helper(n: int, counter=0):
    """return how many times is the digit 1 appears in a number"""
    if n == 0:
        return 0
    counter += 1 if n % 10 == 1 else 0
    return counter + number_of_ones_digit_helper(n // 10)


def number_of_ones_helper(n: int, counter=0):
    """go over all numbers from 1 to n and counts how many times the digit 1 appears"""
    if n == 0:
        return 0
    counter += number_of_ones_digit_helper(n)
    return counter + number_of_ones_helper(n - 1)


def number_of_ones(n: int) -> int:
    return number_of_ones_helper(n)


def check_internal_lists_length(l1, l2, i=0):
    """compare lengths of internal lists"""
    if len(l1[i]) != len(l2[i]):
        return False
    if i != len(l1) - 1:
        return check_internal_lists_length(l1, l2, i + 1)
    return True


def check_digits(l1, l2, i=0, j=0):
    """compare digits in lists"""
    if len(l1[i]) != 0:
        if l1[i][j] != l2[i][j]:
            return False
        if j != len(l1[i]) - 1:
            return check_digits(l1, l2, i, j+1)
    if i != len(l1) - 1:
        return check_digits(l1, l2, i+1, 0)
    return True


def compare_2d_lists(l1: List[List[int]], l2: List[List[int]]) -> bool:
    """compare between lists"""
    if len(l1) != len(l2):
        return False
    if check_internal_lists_length(l1, l2):
        return check_digits(l1, l2)


def magic_list(n: int) -> List[Any]:
    """create list of nested lists"""
    if n == 0:
        return []
    new_list = magic_list(n-1)
    new_list.append(magic_list(n-1))
    return new_list

