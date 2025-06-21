//
// Created by einavye on 21/06/2025.
//
#include "UsersLoader.h"

#include <cassert>
#include <chrono>
#include <fstream>
#include <sstream>

struct MovieInfo {
    std::string name;
    int year;
};

std::vector<User> UsersLoader::create_users(const std::string &users_file_path, const std::shared_ptr<RecommendationSystem>& rs) {
    using std::ifstream;
    ifstream file(users_file_path);
    if (!file.is_open()) {
        throw std::runtime_error("Couldn't open file");
    }
    // Read the first line to get the movie names and years
    std::vector<std::string> movies_names_years;
    std::string line;
    std::getline(file, line);
    std::istringstream header_stream(line);

    // Assuming the first line contains movie names and years in the format "MovieName-Year MovieName-Year ..."
    std::vector<MovieInfo> movie_infos;
    std::string info;
    // Extract movie names and years
    while (header_stream >> info) {
        std::stringstream info_split_stream(info);
        std::string name, year_str;
        std::getline(info_split_stream, name, '-');
        std::getline(info_split_stream, year_str);
        int year = std::stoi(year_str);
        movie_infos.push_back({name, year});
    }
    // Create users
    std::vector<User> users;
    while (std::getline(file, line)) {
        // Assuming the line format is "UserName movie1_rating movie2_rating ..."
        // Extract username
        std::istringstream user_stream(line);
        std::string user_name;
        user_stream >> user_name;
        // Extract ratings
        rank_map ranks;
        for (const MovieInfo& m_info : movie_infos) {
            std::string rating_str;
            user_stream >> rating_str;

            if (rating_str != "NA") {
                double rating = std::stod(rating_str);
                if (rating < 1.0 || rating > 10.0) {
                    throw std::invalid_argument("Rating out of range");
                }
                sp_movie movie = rs->get_movie(m_info.name, m_info.year);
                if (movie == nullptr) {
                    std::cerr << "Error: movie " << m_info.name << " (" << m_info.year << ") not found in RS" << std::endl;
                    throw std::runtime_error("Movie not found in RS");
                }
                ranks[movie] = rating;
            }
        }
        users.emplace_back(user_name, ranks, rs);
    }
    return users;
}




