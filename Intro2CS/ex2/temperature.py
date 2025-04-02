#################################################################
# FILE : temperature.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex2 2024
# DESCRIPTION: A program that helps Thanos decide what is the right day to come to Vormir based on temperature
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: none
# NOTES: none
#################################################################

def is_vormir_safe(threshold, day1, day2, day3):
    if day1 > threshold and day2 > threshold:
        return True
    if day3 > threshold and day2 > threshold:
        return True
    if day1 > threshold and day3 > threshold:
        return True
    else:
        return False
