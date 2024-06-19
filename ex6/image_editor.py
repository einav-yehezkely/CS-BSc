#################################################################
# FILE : image_editor.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex6 2024
# DESCRIPTION: A simple program that...
# STUDENTS I DISCUSSED THE EXERCISE WITH: Bugs Bunny, b_bunny.
#								 	      Daffy Duck, duck_daffy.
# WEB PAGES I USED: https://www.w3schools.com/python/ref_func_round.asp
# NOTES: none
#################################################################

##############################################################################
#                                   Imports                                  #
##############################################################################
from ex6_helper import *
from typing import Optional, List


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
            avr = round(col[0]*0.299 + col[1]*0.587 + col[2]*0.114)
            rows.append(avr)
        grayscale_image.append(rows)
    return grayscale_image


def blur_kernel(size: int) -> Kernel:
    ...


def apply_kernel(image: SingleChannelImage, kernel: Kernel) -> SingleChannelImage:
    ...


def bilinear_interpolation(image: SingleChannelImage, y: float, x: float) -> int:
    ...


def resize(image: SingleChannelImage, new_height: int, new_width: int) -> SingleChannelImage:
    ...


def rotate_90(image: Image, direction: str) -> Image:
    ...


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
    print(combine_channels(separate_channels(a)))
    b = RGB2grayscale(a)
    show_image(b)
    # b = separate_channels(a)
    # show_image(b[0])

