//
// Created by dvirp on 21/04/2024.
//
#include "RecommendationSystemLoader.h"
#include "UsersLoader.h"
#include "User.h"
#include "SOURCE_PATH.h"
#define EXIT_SUCCESS_TEST 0
#define EXIT_FAIL_TEST 2

int main()
{
    auto rs = RecommendationSystemLoader::create_rs_from_movies(SOURCE_PATH + "TestComparison_19.in_m");
    std::vector<User> users = UsersLoader::create_users(SOURCE_PATH + "TestComparison_19.in_u", std::move(rs));
    auto ranks = users[0].get_rank();

    //BrokebackMountain-1966 5 3 7 4 7
    std::string movie_name_a = "BrokebackMountain";
    int movie_year_a = 1966;
    std::vector<double> features_a = {5, 3, 7, 4, 7};
    //Muriel-1980 7 6 8 8 10
    std::string movie_name_b = "Muriel";
    int movie_year_b = 1980;
    std::vector<double> features_b = {7, 6, 8, 8, 10};
    //TheMagnificentAmbersons-2001 8 6 9 1 1
    std::string movie_name_c = "TheMagnificentAmbersons";
    int movie_year_c = 2001;
    std::vector<double> features_c = {8, 6, 9, 1, 1};
    //Harakiri-1967 2 6 6 10 5
    std::string movie_name_d = "Harakiri";
    int movie_year_d = 1967;
    std::vector<double> features_d = {2, 6, 6, 10, 5};

    sp_movie movie_a = std::make_shared<Movie>(movie_name_a, movie_year_a);
    sp_movie movie_b = std::make_shared<Movie>(movie_name_b, movie_year_b);
    sp_movie movie_c = std::make_shared<Movie>(movie_name_c, movie_year_c);
    sp_movie movie_d = std::make_shared<Movie>(movie_name_d, movie_year_d);

    if(ranks[movie_b] != 10 || ranks[movie_c] != 1){
        return EXIT_FAIL_TEST;
    }
    try{
        users[0].add_movie_to_user(movie_name_a, movie_year_a, features_a, 5);
        users[0].add_movie_to_user(movie_name_b, movie_year_b, features_b, 6);
        users[0].add_movie_to_user(movie_name_d, movie_year_d, features_d, 7);
    }
    catch (const std::exception& e){
        return EXIT_FAIL_TEST;
    }

    ranks = users[0].get_rank();

    if(ranks[movie_a] != 5 || ranks[movie_b] != 6 || ranks[movie_c] != 1 || ranks[movie_d] != 7){
        return EXIT_FAIL_TEST;

    }
    return EXIT_SUCCESS_TEST;
}
