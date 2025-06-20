//
// Created by einavye on 20/06/2025.
//
#include "RecommendationSystemLoader.h"
#include <fstream>
#include <sstream>

std::unique_ptr<RecommendationSystem> RecommendationSystemLoader::create_rs_from_movies(const std::string &movies_file_path) {
    auto recommendation_system = std::make_unique<RecommendationSystem>();
    using std::ifstream;
    ifstream ifs(movies_file_path);
    std::string line;
    while (std::getline(ifs, line)) {
        // Assuming the line format is "MovieName(Year) feature1 feature2 ... featureN"
        // We will parse the line to extract the movie name, year, and features.
        std::istringstream line_stream(line);
        std::string movie_with_year;
        line_stream >> movie_with_year;
        // Extract movie name and year
        std::string movie_name;
        std::stringstream movie_split_stream(movie_with_year);
        std::getline(movie_split_stream, movie_name, '-');
        std::string year_str;
        std::getline(movie_split_stream, year_str);
        int movie_year = std::stoi(year_str);
        // Extract features
        std::vector<double> features;
        double feature;
        while (line_stream >> feature) {
            if (feature < 1.0 || feature > 10.0) {
                throw std::invalid_argument("Feature value out of range");
            }
            features.push_back(feature);
        }
        recommendation_system->add_movie_to_rs(movie_name, movie_year, features);
    }
    return recommendation_system;
}
