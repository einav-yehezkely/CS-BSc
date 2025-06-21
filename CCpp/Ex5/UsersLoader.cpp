//
// Created by einavye on 21/06/2025.
//
#include "UsersLoader.h"
#include <fstream>
#include <sstream>

std::vector<User> UsersLoader::create_users(const std::string &users_file_path, std::shared_ptr<RecommendationSystem> &rs) {
    using std::ifstream;
    ifstream file(users_file_path);
    if (!file.is_open()) {
        throw std::runtime_error("Couldn't open file");
    }
}




