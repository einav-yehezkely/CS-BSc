#################################################################
# FILE : calculate_mathematical_expression.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex2 2024
# DESCRIPTION: A program that calculates mathematical expressions for Toni Spark and Bruce Banner
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: https://www.w3schools.com/python/ref_string_split.asp
# NOTES: none
#################################################################

def calculate_mathematical_expression(n, f, s):
    """returns expression after calculation"""
    if s == '+':
        return n+f
    elif s == '*':
        return n*f
    elif s == ':':
        if f == 0:
            return None
        else:
            return n/f
    elif s == '-':
        return n-f
    elif s not in ['+', '*', '-', ':']:
        return None


def calculate_from_string(s):
    x = s.split()
    answer = calculate_mathematical_expression(int(x[0]), float(x[2]), x[1])
    return answer