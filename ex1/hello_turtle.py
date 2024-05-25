#################################################################
# FILE : hello_turtle.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex1 2024
# DESCRIPTION: A simple program that draws a fleet of ships
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: none
# NOTES: none
#################################################################
import turtle


def draw_triangle():
    """This function draws a triangle"""
    for i in range(3):
        turtle.forward(45)
        turtle.right(120)


def draw_sail():
    """This function draws a sail"""
    turtle.left(90)
    turtle.forward(50)
    turtle.right(150)
    draw_triangle()
    turtle.right(30)
    turtle.up()
    turtle.forward(50)
    turtle.down()
    turtle.left(90)


def draw_ship():
    """This function draws a ship"""
    for i in range(3):  # draw 3 sails
        turtle.forward(50)
        draw_sail()
    turtle.forward(50)
    turtle.right(120)
    turtle.forward(20)
    turtle.right(60)
    turtle.forward(180)
    turtle.right(60)
    turtle.forward(20)
    turtle.right(30)


def draw_fleet():
    """This function draws a fleet of ships"""
    draw_ship()
    # move to next location
    turtle.up()
    turtle.left(90)
    turtle.forward(300)
    turtle.right(180)
    # draw another ship
    turtle.down()
    draw_ship()
    turtle.right(90)


if __name__ == "__main__":
    turtle.down()
    draw_fleet()
    turtle.done()
    
