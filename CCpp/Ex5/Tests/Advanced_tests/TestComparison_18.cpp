#include "UsersLoader.h"
#include "RecommendationSystemLoader.h"
#include "SOURCE_PATH.h"

#define EXIT_FAIL_TEST 2;
#define EXIT_SUCCESS_TEST 0;
#define THRESHOLD 0.01

int main()
{

	auto rs = RecommendationSystemLoader::create_rs_from_movies(SOURCE_PATH + "TestComparison_6.in_m");
	auto rsnu = RecommendationSystemLoader::create_rs_from_movies(SOURCE_PATH + "TestComparison_6.in_m");
	std::vector<User> user = UsersLoader::create_users(SOURCE_PATH + "TestComparison_6.in_u", std::move(rs));

	if (std::abs(rsnu->predict_movie_score(user[0], rsnu->get_movie("TheLastLaugh", 1989), 2) - 4.59) > THRESHOLD ||
		std::abs(rsnu->predict_movie_score(user[5], rsnu->get_movie("TheLastLaugh", 1989), 2) - 6.38) > THRESHOLD ||
		std::abs(rsnu->predict_movie_score(user[10], rsnu->get_movie("TheLastLaugh", 1989), 2) - 4.98) > THRESHOLD ||
		std::abs(rsnu->predict_movie_score(user[15], rsnu->get_movie("TheLastLaugh", 1989), 2) - 4.84) > THRESHOLD ||
		//		std::abs(user[20].get_rs_prediction_score_for_movie("TheLastLaugh", 1989, 2) - 7.60) > THRESHOLD ||
		std::abs(rsnu->predict_movie_score(user[24], rsnu->get_movie("TheLastLaugh", 1989), 2) - 5.5) > THRESHOLD)
		return EXIT_FAIL_TEST;

	return EXIT_SUCCESS_TEST;
}
