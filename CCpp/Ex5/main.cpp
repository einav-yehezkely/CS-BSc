#include <iostream>
#include "RecommendationSystemLoader.h"

#define RS_LIST_PATH "RecommendationSystemLoader_input.txt"

int main()
{
    try {
        auto rs = RecommendationSystemLoader::create_rs_from_movies(RS_LIST_PATH);
        std::cout << *rs;  // מדפיס את כל הסרטים במערכת
    }
    catch (const std::exception& e) {
        std::cerr << "Error: " << e.what() << std::endl;
        return 1;
    }

    return 0;
}
