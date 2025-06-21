cmake_minimum_required(VERSION 3.28)
project(Ex5)

set(CMAKE_CXX_STANDARD 20)

add_executable(Ex5
        User.h
        Movie.h
        RecommendationSystem.h
        User.cpp
        Movie.cpp
        RecommendationSystem.cpp
        RecommendationSystemLoader.cpp
        UsersLoader.cpp
        Tests/Basic_tests/presubmit.cpp)

