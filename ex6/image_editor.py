#################################################################
# FILE : image_editor.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex6 2024
# DESCRIPTION: A simple program that...
# STUDENTS I DISCUSSED THE EXERCISE WITH: Bugs Bunny, b_bunny.
#								 	      Daffy Duck, duck_daffy.
# WEB PAGES I USED: https://www.w3schools.com/python/ref_func_round.asp
# https://www.geeksforgeeks.org/floor-ceil-function-python/
# NOTES: none
#################################################################
import math

##############################################################################
#                                   Imports                                  #
##############################################################################
from ex6_helper import *
from typing import Optional, List
from copy import deepcopy


##############################################################################
#                                  Functions                                 #
##############################################################################

# TODO additional function
def str_matrix(mtx):
    str_mtx = ""
    for row in mtx:
        for col in row:
            str_mtx += str(col) + ' '
        str_mtx += '\n'
    return str_mtx


# TODO write better
def separate_channels(image: ColoredImage) -> list[list[list[int]]]:
    """return image separated by channels"""
    rows = []
    for channel in range(len(image[0][0])):
        single_channels = []
        for row in range(len(image)):
            chan = []
            for col in range(len(image[0])):
                chan.append(image[row][col][channel])
            single_channels.append(chan)
        rows.append(single_channels)

    return rows


def combine_channels(channels: List[SingleChannelImage]) -> ColoredImage:
    """return original image"""
    rows = []
    for channel in range(len(channels[0][0])):
        single_channels = []
        for col in range(len(channels[0])):
            chan = []
            for row in range(len(channels)):
                chan.append(channels[row][col][channel])
            single_channels.append(chan)
        rows.append(single_channels)

    return rows


def RGB2grayscale(colored_image: ColoredImage) -> SingleChannelImage:
    """return grayscale image using average of RGB channels by the equation RED*0.299 + GREEN*0.587 + BLUE*0.114"""
    grayscale_image = []
    rows = []
    for row in colored_image:
        rows = []
        for col in row:
            avr = round(col[0] * 0.299 + col[1] * 0.587 + col[2] * 0.114)
            rows.append(avr)
        grayscale_image.append(rows)
    return grayscale_image


def blur_kernel(size: int) -> Kernel:
    """return kernel"""
    avr = 1 / (size ** 2)
    return [[avr for col in range(size)] for row in range(size)]


def apply_kernel(image: SingleChannelImage, kernel: Kernel) -> SingleChannelImage:
    """
    apply kernel on original image to blur
    :param image: OG
    :param kernel: size*size kernel with equal values
    :return: blurred image
    """
    center = len(kernel) // 2
    blurred_image = []
    for row in range(len(image)):
        row_blurred = []
        for col in range(len(image[0])):
            avr = 0
            cur_cell = image[row][col]
            cur_row = row - center
            cur_col = col - center
            for i in range(len(kernel)):
                for j in range(len(kernel[i])):
                    if cur_row not in range(len(image)) or cur_col not in range(len(image[0])):
                        avr += cur_cell * kernel[0][0]
                    else:
                        avr += image[cur_row][cur_col] * kernel[0][0]
                    cur_col += 1
                cur_row += 1
                cur_col = col - center
            avr = round(avr)
            if avr < 0:
                avr = 0
            if avr > 255:
                avr = 255
            row_blurred.append(avr)
        blurred_image.append(row_blurred)

    return blurred_image


def bilinear_interpolation(image: SingleChannelImage, y: float, x: float) -> int:
    """
    calc new pixel value based using formula
    𝑎 ⋅ (1 − ∆𝑥) ⋅ (1 − ∆𝑦) + 𝑏 ⋅ ∆𝑦 ⋅ (1 − ∆𝑥) + 𝑐 ⋅ ∆𝑥 ⋅ (1 − ∆𝑦) + 𝑑 ⋅ ∆𝑥 ⋅ ∆y
    :param image: OG
    :param y: y value (row)
    :param x: x value (col)
    :return: new value
    """
    a = image[math.floor(y)][math.floor(x)]
    b = image[math.ceil(y)][math.floor(x)]
    c = image[math.floor(y)][math.ceil(x)]
    d = image[math.ceil(y)][math.ceil(x)]
    delta_x = x - math.floor(x)
    delta_y = y - math.floor(y)
    new_val = a * (1 - delta_x) * (1 - delta_y) + b * delta_y * (1 - delta_x) + c * delta_x * (
            1 - delta_y) + d * delta_x * delta_y
    return round(new_val)


def resize(image: SingleChannelImage, new_height: int, new_width: int) -> SingleChannelImage:
    """
    resize image
    :param image: OG
    :param new_height:
    :param new_width:
    :return: resized image
    """
    resized_image = []
    for row in range(new_height):
        new_row = []
        new_y = (row / (new_height - 1)) * (len(image) - 1)
        for col in range(new_width):
            new_x = (col / (new_width - 1)) * (len(image[0]) - 1)
            new_row.append(bilinear_interpolation(image, new_y, new_x))
        resized_image.append(new_row)
    return resized_image


def rotate_90(image: Image, direction: str) -> Image:
    """
    rotate image by 90 degrees to given direction.
    works on both colorful (3D) and B&W (2D) images.
    :param image: OG
    :param direction: L or R
    :return: rotated image
    """
    rotated_image = []

    if direction == 'L':  # switch cols order within rows
        used_image = [row[::-1] for row in image]
    else:  # direction == 'R', switch rows order
        used_image = image[::-1]
    for col in range(len(used_image[0])):
        new_row = []
        for row in used_image:
            new_row.append(row[col])
        rotated_image.append(new_row)

    return rotated_image


def get_edges(image: SingleChannelImage, blur_size: int, block_size: int, c: float) -> SingleChannelImage:
    ...


def quantize(image: SingleChannelImage, N: int) -> SingleChannelImage:
    ...


def quantize_colored_image(image: ColoredImage, N: int) -> ColoredImage:
    ...


if __name__ == '__main__':
    a = load_image("ziggy.png")
    image = [[[1, 2, 3], [1, 2, 3], [1, 2, 3]],
             [[1, 2, 3], [1, 2, 3], [1, 2, 3]],
             [[1, 2, 3], [1, 2, 3], [1, 2, 3]],
             [[1, 2, 3], [1, 2, 3], [1, 2, 3]]]
    # show_image(rotate_90(a,'L'))
