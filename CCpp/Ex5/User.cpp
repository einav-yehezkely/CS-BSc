//
// Created by einavye on 18/06/2025.
//
#include "User.h"

#include <algorithm>

#include "RecommendationSystem.h"

void User::add_movie_to_user(const std::string &name, int year,
                         const std::vector<double> &features,
                         double rate) {
    for (double feature : features) {
        if (feature < 1.0 || feature > 10.0) {
            throw std::invalid_argument("Feature value out of range");
        }
    }
    sp_movie movie = recommendationSystem_->add_movie_to_rs(name, year, features);
    rank_[movie] = rate;
}

sp_movie User::get_rs_recommendation_by_content(){
    return recommendationSystem_->recommend_by_content(*this);
}

double User::get_rs_prediction_score_for_movie(const std::string &name, int year, int k) {
    sp_movie movie = recommendationSystem_->get_movie(name, year);
    return recommendationSystem_->predict_movie_score(*this, movie, k);
}

sp_movie User::get_rs_recommendation_by_cf(int k) {
    return recommendationSystem_->recommend_by_cf(*this, k);
}

std::ostream& operator<<(std::ostream& os, const User& user) {
    os << user.get_name() << ":\n";

    std::vector<sp_movie> movies;
    for (const auto& pair : user.rank_) {
        movies.push_back(pair.first);
    }
    std::sort(movies.begin(), movies.end(), [](const sp_movie& a, const sp_movie& b) { return *a < *b; });

    for (const auto& movie : movies) {
        os << *movie << std::endl;
    }

    return os;

}