//
// Created by einavye on 18/06/2025.
//
#include "RecommendationSystem.h"

#include <complex>


sp_movie RecommendationSystem::get_movie(const std::string& name, int year) const {
    auto to_search = std::make_shared<Movie>(name, year);
    auto find = movie_db_.find(to_search);
    if (find == movie_db_.end()) {
        return nullptr;
    }
    return find->first; // return the movie itself - the key, not the features - the value
}


sp_movie RecommendationSystem::add_movie_to_rs(const std::string& name,int year, const std::vector<double>& features) {
    for (double feature : features) {
        if (feature < 1.0 || feature > 10.0) {
            throw std::invalid_argument("Feature value out of range");
        }
    }
    auto to_search = std::make_shared<Movie>(name,year);
    auto find = movie_db_.find(to_search);
    if (find != movie_db_.end()) {
        return find->first;
    }
    movie_db_[to_search] = features;
    return to_search;
}
std::ostream& operator<<(std::ostream& os, const RecommendationSystem& recommendation) {
    for (const auto& pair : recommendation.movie_db_) {
        os << *(pair.first);
    }
    return os;
}

double cs(const std::vector<double> &u, const std::vector<double> &v) {
    double dot = 0.0;
    double norm1 = 0.0;
    double norm2 = 0.0;
    for (int i = 0; i < u.size(); i++) {
        dot += u[i] * v[i];
        norm1 += u[i] * u[i];
        norm2 += v[i] * v[i];
    }
    return dot/(std::sqrt(norm1)*std::sqrt(norm2));
}

sp_movie RecommendationSystem::recommend_by_content(const User& user_rankings) {
    const rank_map& user_ranks = user_rankings.get_rank();
    double sum = 0.0;
    for (const auto& pair : user_ranks) {
        sum += pair.second;
    }
    double mean = sum / user_ranks.size();
    int num_of_features = movie_db_.begin()->second.size();
    std::vector<double> preference_vector(num_of_features, 0.0);
    for (const auto& pair : user_ranks) {
        const sp_movie& movie = pair.first;
        double value = pair.second - mean;
        const std::vector<double>& features = movie_db_.at(pair.first);
        for (int i = 0; i < num_of_features; i++) {
            preference_vector[i] += value * features[i];
        }
    }
    sp_movie recommendation = nullptr;
    double best_similarity = 0.0;
    for (const auto& pair : movie_db_) {
        const sp_movie& movie = pair.first;
        if (user_ranks.count(movie)) {
            continue;
        }
        const std::vector<double>& features = movie_db_.at(pair.first);
        double similarity = cs(preference_vector, features);
        if (similarity > best_similarity) {
            best_similarity = similarity;
            recommendation = movie;
        }
    }
    return recommendation;
}

double predict_movie_score(const User& user_rankings, const sp_movie &movie, int k) {

}
// sp_movie recommend_by_cf(const User& user_rankings, int k);