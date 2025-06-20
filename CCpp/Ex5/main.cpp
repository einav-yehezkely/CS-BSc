#include "Movie.h"
#include "RecommendationSystem.h"
#include "User.h"
#include <iostream>
#include <memory>

int main()
{
    std::shared_ptr<RecommendationSystem> rs = std::make_shared<RecommendationSystem>();

    // 1. יצירת סרטים והוספה למערכת
    rs->add_movie_to_rs("Matrix", 1999, {8, 9, 7, 6});
    rs->add_movie_to_rs("Inception", 2010, {9, 8, 6, 7});
    rs->add_movie_to_rs("Interstellar", 2014, {7, 9, 8, 6});
    rs->add_movie_to_rs("Dunkirk", 2017, {6, 7, 7, 6});
    rs->add_movie_to_rs("Tenet", 2020, {7, 7, 6, 6});

    // 2. קבלת סרט ובדיקת הדפסה
    sp_movie m = rs->get_movie("Matrix", 1999);
    std::cout << "Movie test: " << *m << std::endl;

    // 3. יצירת דירוגים והמשתמש
    rank_map r(10, sp_movie_hash, sp_movie_equal);
    r[rs->get_movie("Matrix", 1999)] = 9.0;
    r[rs->get_movie("Inception", 2010)] = 8.0;
    r[rs->get_movie("Interstellar", 2014)] = 7.0;

    User u("Alice", r, rs);

    // 4. הוספת סרט חדש דרך היוזר + דריסה
    u.add_movie_to_user("Dunkirk", 2017, {6, 7, 7, 6}, 6.0);  // חדש
    u.add_movie_to_user("Matrix", 1999, {8, 9, 7, 6}, 9.5);   // דריסה

    // 5. הדפסת היוזר
    std::cout << "=== User ===\n";
    std::cout << u;

    // 6. המלצה לפי תוכן
    try {
        sp_movie rec = u.get_rs_recommendation_by_content();
        std::cout << "Recommendation by content: " << *rec << std::endl;
    } catch (const std::exception& e) {
        std::cerr << "Content-based recommendation failed: " << e.what() << std::endl;
    }

    // 7. חיזוי ניקוד לסרט שלא דורג
    try {
        double score = u.get_rs_prediction_score_for_movie("Tenet", 2020, 2);
        std::cout << "Predicted score for Tenet: " << score << std::endl;
    } catch (const std::exception& e) {
        std::cerr << "Prediction failed: " << e.what() << std::endl;
    }

    // 8. המלצה לפי סינון שיתופי
    try {
        sp_movie rec_cf = u.get_rs_recommendation_by_cf(2);
        std::cout << "Recommendation by collaborative filtering: " << *rec_cf << std::endl;
    } catch (const std::exception& e) {
        std::cerr << "Collaborative filtering failed: " << e.what() << std::endl;
    }

    return 0;
}
