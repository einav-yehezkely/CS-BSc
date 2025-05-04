cmake_minimum_required(VERSION 3.28)
project(Ex3_A C)

set(CMAKE_C_STANDARD 99)

add_executable(Ex3_A main.c linked_list.c markov_chain.c tweets_generator.c)
