#################################################################
# FILE : largest_and_smallest.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex2 2024
# DESCRIPTION: A program that returns the strongest and weakest avenger
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: none
# NOTES: I chose 'test 4' inputs because it tests negative numbers which we didn't check so far
# I chose 'test 5' inputs to challenge the program with equal values. All of them are max values and min values.
#################################################################

def largest_and_smallest(a, b, c):
    l = [a, b, c]
    max_v = 0
    # find max value
    for i in l:
        if i > max_v:
            max_v = i
    min_v = max_v
    # find min value
    for i in l:
        if i < min_v:
            min_v = i
    return max_v, min_v


def check_largest_and_smallest():
    """check the return values of largest_and_smallest"""
    max_v, min_v = largest_and_smallest(17, 1, 6)  # test 1
    if max_v == 17 and min_v == 1:
        max_v, min_v = largest_and_smallest(1, 17, 6)  # test 2
        if max_v == 17 and min_v == 1:
            max_v, min_v = largest_and_smallest(1, 1, 2)  # test 3
            if max_v == 2 and min_v == 1:
                max_v, min_v = largest_and_smallest(-5, -8, 0)  # test 4 - my choice
                if max_v == 0 and min_v == -8:
                    max_v, min_v = largest_and_smallest(1, 1, 1)  # test 5 - my choice
                    if max_v == 1 and min_v == 1:
                        return True
    return False
