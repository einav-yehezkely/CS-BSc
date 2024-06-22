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
import sys

##############################################################################
#                                   Imports                                  #
##############################################################################
from ex6_helper import *
from typing import Optional, List

##############################################################################
#                                  Functions                                 #
##############################################################################

purpose = "blur"
edge_constant = 0


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
    combined = []
    for col in range(len(channels[0])):
        new_row = []
        for chan in range(len(channels[0][0])):
            new_col = []
            for row in range(len(channels)):
                new_col.append(channels[row][col][chan])
            new_row.append(new_col)
        combined.append(new_row)
    return combined


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
    # edge_image = []
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

            if purpose == "edges":
                avr = avr / (len(kernel) ** 2)
                threshold = avr - edge_constant
                row_blurred.append(0) if cur_cell < threshold else row_blurred.append(255)

            else:
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
    """
    return only the edges of an image
    :param image: OG
    :param blur_size: for kernel
    :param block_size:
    :param c:
    :return: image edges
    """
    blurred_image = apply_kernel(image, blur_kernel(blur_size))
    # image_edges = []
    global purpose
    global edge_constant
    purpose = "edges"
    edge_constant = c
    block = []
    for i in range(block_size):
        row = []
        for j in range(block_size):
            row.append(1)
        block.append(row)

    image_edges = apply_kernel(blurred_image, block)
    purpose = "blur"
    return image_edges


def quantize(image: SingleChannelImage, N: int) -> SingleChannelImage:
    """
    quantize B&W images
    :param image: OG 2D
    :param N: number of shades
    :return: quantized image
    """
    quantized_image = []
    for row in range(len(image)):
        quantized_row = []
        for col in range(len(image[0])):
            quantized_row.append(round(math.floor(image[row][col] * (N / 256)) * (255 / (N - 1))))
        quantized_image.append(quantized_row)
    return quantized_image


def quantize_colored_image(image: ColoredImage, N: int) -> ColoredImage:
    """
    :param image: OG 3D
    :param N: number of shades
    :return: quantized image
    """
    quantized_image = []
    for channel in separate_channels(image):
        quantized_image.append(quantize(channel, N))
    return combine_channels(quantized_image)


if __name__ == '__main__':
    if len(sys.argv) != 2:
        print("Invalid Input, program ends.")
    else:
        image = load_image(sys.argv[1])
        edited_image = image
        exit_program = False
        while not exit_program:
            action = input("These are the available actions you can take:\n"
                           "1. convert colored image to grayscale image\n"
                           "2. blur image\n"
                           "3. resize image\n"
                           "4. rotate image by 90 degrees\n"
                           "5. create image edges\n"
                           "6. quantize \n"
                           "7. display image\n"
                           "8. exit program\n")
            if action in range(1, 9):
                # grayscale #
                if action == 1:
                    if type(image[0][0]) is not list:
                        print("image is already grayscale")
                    else:
                        edited_image = RGB2grayscale(image)
                # blur #
                if action == 2:
                    kernel_size = int(input("please enter size for kernel (int):"))
                    if (type(kernel_size) is not int) or (kernel_size % 2 == 0) or (kernel_size <= 0):
                        print("Invalid kernel size")
                    else:
                        if type(image[0][0]) is not list:   # grayscale
                            edited_image = apply_kernel(image, blur_kernel(kernel_size))
                        else:   # colored
                            blurred_image = []
                            for channel in separate_channels(image):
                                blurred_image.append(apply_kernel(image, blur_kernel(kernel_size)))
                            edited_image = combine_channels(blurred_image)
                # resize #
                if action == 3:
                    new_scale = list(input("please enter new height, new width, seperated by a comma"))
                    print(new_scale)
                    exit_program = True

            else:
                print("Invalid Input.")

