cmake_minimum_required(VERSION 3.28)
project(Ex1 C)

set(CMAKE_C_STANDARD 99)

add_executable(Ex1 main.c
        tests.c tests.h cipher.c cipher.h)
