//
// Created by einavye on 18/06/2025.
//
#include "RecommendationSystem.h"

#include <complex>
#include <set>


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
        find->second = features;  // override features
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

struct comparator_similarity {
    bool operator()(const std::pair<sp_movie, double>& a, const std::pair<sp_movie, double>& b) const {
        return a.second > b.second;
    }
};

double RecommendationSystem::predict_movie_score(const User& user_rankings, const sp_movie &movie, int k) {
    const rank_map& user_ranks = user_rankings.get_rank();
    const std::vector<double>& features = movie_db_.at(movie);
    std::multiset<std::pair<sp_movie, double>, comparator_similarity> similarities_sorted;  // movie, similarity
    for (const auto& pair : user_ranks) {
        const sp_movie& movie_to_compare = pair.first;
        const std::vector<double>& features_of_movie = movie_db_.at(movie_to_compare);
        double similarity = cs(features_of_movie, features);
        similarities_sorted.insert({movie_to_compare, similarity});
    }
    double numerator = 0.0;
    double denominator = 0.0;
    int count = 0;

    for (const auto& pair : similarities_sorted) {
        if (count >= k) {
            break;
        }

        const sp_movie& next_movie = pair.first;
        double similarity = pair.second;
        double user_rating = user_ranks.at(next_movie);

        numerator += similarity * user_rating;
        denominator += similarity;
        count++;
    }
    if (denominator == 0.0) {
        return 0.0;
    }
    return numerator / denominator;
}
sp_movie RecommendationSystem::recommend_by_cf(const User& user_rankings, int k) {
    sp_movie best_movie = nullptr;
    double best_rate = -1.0;
    const rank_map& user_ranks = user_rankings.get_rank();
    for (const auto& pair : movie_db_) {
        const sp_movie& possible_movie = pair.first;
        if (user_ranks.count(possible_movie)) {
            continue;
        }
        double predicted_score = predict_movie_score(user_rankings, possible_movie, k);
        if (predicted_score > best_rate) {
            best_movie = possible_movie;
            best_rate = predicted_score;
        }
    }
    return best_movie;

}