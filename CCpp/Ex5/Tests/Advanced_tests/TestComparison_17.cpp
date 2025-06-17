#include "RecommendationSystemLoader.h"
#include "UsersLoader.h"
#include "SOURCE_PATH.h"
#define EXIT_SUCCESS_TEST 0
#define EXIT_FAIL_TEST 2

int main()
{
	auto rs1nu = RecommendationSystemLoader::create_rs_from_movies(SOURCE_PATH + "TestComparison_11.in_m1");
    auto rs1 = RecommendationSystemLoader::create_rs_from_movies(SOURCE_PATH + "TestComparison_11.in_m1");
    std::vector<User> users1 = UsersLoader::create_users(SOURCE_PATH + "TestComparison_11.in_u1", std::move(rs1));
    auto rs2nu = RecommendationSystemLoader::create_rs_from_movies(SOURCE_PATH + "TestComparison_11.in_m2");
    auto rs2 = RecommendationSystemLoader::create_rs_from_movies(SOURCE_PATH + "TestComparison_11.in_m2");
    std::vector<User> users2 = UsersLoader::create_users(SOURCE_PATH + "TestComparison_11.in_u2", std::move(rs2));
    auto rs3nu = RecommendationSystemLoader::create_rs_from_movies(SOURCE_PATH + "TestComparison_11.in_m3");
    auto rs3 = RecommendationSystemLoader::create_rs_from_movies(SOURCE_PATH + "TestComparison_11.in_m3");
    std::vector<User> users3 = UsersLoader::create_users(SOURCE_PATH + "TestComparison_11.in_u3", std::move(rs3));

	sp_movie recommendation1 = rs1nu->recommend_by_content(users1[79]);
	sp_movie recommendation2 = rs2nu->recommend_by_content(users2[299]);
	sp_movie recommendation3 = rs3nu->recommend_by_content(users3[399]);
	if (recommendation1->get_name() != "TroubleinParadise" ||
		recommendation2->get_name() != "DeepRed" ||
		recommendation3->get_name() != "BonnieandClyde"){
			return EXIT_FAIL_TEST;
		}



	std::vector<double> feature1;
	for(int i=1;i<11;i++){
			feature1.push_back(i%11);
		}
	users1[79].add_movie_to_user("AAAMovieTestingAddition", 1800,  feature1,5);
	rs1nu->add_movie_to_rs("AAAMovieTestingAddition", 1800,  feature1);
	recommendation1 = rs1nu->recommend_by_content(users1[79]);
	std::vector<double> feature2;
	for(int i=1;i<61;i++){
			if (i % 11 == 0){
					feature2.push_back (1);
				}
			else {
					feature2.push_back (i % 11);
				}
		}
	users2[299].add_movie_to_user("AAMovieTestingAddition", 1820, feature2, 10);
	rs2nu->add_movie_to_rs ("AAMovieTestingAddition", 1820, feature2);
	recommendation2 =  rs2nu->recommend_by_content(users2[299]);
	std::vector<double> feature3;
	for(int i=1;i<121;i++) {
			if (i % 11 == 0) {
					feature3.push_back (1);
				}
			else {
					feature3.push_back (i % 11);
				}
		}
	users3[399].add_movie_to_user("AMovieTestingAddition", 1840, feature3, 1);
	rs3nu->add_movie_to_rs ("AMovieTestingAddition", 1840, feature3);
	recommendation3 = rs3nu->recommend_by_content(users3[399]);

	if (recommendation1->get_name() != "MasculinFeminin" ||
		recommendation2->get_name() != "DeepRed" ||
		recommendation3->get_name() != "BonnieandClyde"){
			return EXIT_FAIL_TEST;
		}

	return EXIT_SUCCESS_TEST;
}
