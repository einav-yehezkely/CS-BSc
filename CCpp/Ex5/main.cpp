#include "RecommendationSystem.h"
#include <iostream>

int main() {
    try {
        RecommendationSystem rs;

        // סרט 1: תקין
        rs.add_movie_to_rs("Inception", 2010, {7.0, 8.0, 9.0, 6.0});

        // סרט 2: תקין
        rs.add_movie_to_rs("The Matrix", 1999, {8.0, 9.0, 10.0, 9.0});

        // נסה להוסיף סרט שכבר קיים (לא צריך להיווצר שוב)
        rs.add_movie_to_rs("Inception", 2010, {7.0, 8.0, 9.0, 6.0});

        // הדפסת כל הסרטים
        std::cout << "=== Movies in Recommendation System ===" << std::endl;
        std::cout << rs;

        // נסה לחפש סרט
        sp_movie m = rs.get_movie("Inception", 2010);
        if (m) {
            std::cout << "Found movie: " << *m << std::endl;
        } else {
            std::cout << "Movie not found." << std::endl;
        }

        // סרט עם פיצ'רים לא תקינים (צריך לזרוק שגיאה)
        rs.add_movie_to_rs("Broken Movie", 2023, {7.0, -3.0, 5.0}); // ❌

    } catch (const std::invalid_argument& e) {
        std::cerr << "Caught exception: " << e.what() << std::endl;
    }

    return 0;
}
