//
// Created on 2/20/2022.
//

#ifndef RECOMMENDATIONSYSTEM_H
#define RECOMMENDATIONSYSTEM_H
#include "User.h"

class User;

class RecommendationSystem
{
public:
    RecommendationSystem();
    sp_movie get_movie(const std::string& name, int year) const;
    sp_movie add_movie_to_rs(const std::string& name,int year, const std::vector<double>& features);
    friend std::ostream& operator<<(std::ostream& os, const RecommendationSystem& recommendation);
    sp_movie recommend_by_content(const User& user_rankings);
    double predict_movie_score(const User& user_rankings, const sp_movie &movie, int k);
    sp_movie recommend_by_cf(const User& user_rankings, int k);

};

#endif // RECOMMENDATIONSYSTEM_H

