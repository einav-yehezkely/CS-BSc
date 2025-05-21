cmake_minimum_required(VERSION 3.28)
project(Ex3B C)

set(CMAKE_C_STANDARD 99)

add_executable(Ex3B
        markov_chain.h
        markov_chain.c
        tweets_generator.c
        linked_list.c)
