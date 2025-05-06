//Don't change the macros!
#define FILE_PATH_ERROR "Error: incorrect file path"
#define NUM_ARGS_ERROR "Usage: invalid number of arguments"

#define DELIMITERS " \n\t\r"
#include <stdio.h>
#include <stdlib.h>

#include "markov_chain.h"


int fill_database(FILE *fp, int words_to_read, MarkovChain *markov_chain);

int main(int argc, char *argv[]) {
    if (argc != 4 && argc != 5) {
        printf(NUM_ARGS_ERROR);
        return EXIT_FAILURE;
    }

    unsigned int seed = (unsigned int) *argv[1];
    int num_of_tweets = (int) *argv[2];
    char *path_to_corpus = argv[3];
    int max_words;
    if (argc == 5) {
        max_words = (int) *argv[4];
    }
    else {
        max_words = -1;
    }

    srand(seed);

    FILE *fp = fopen(path_to_corpus, "r");
    if (!fp) {
        printf(FILE_PATH_ERROR);
        return EXIT_FAILURE;
    }

    MarkovChain *markov_chain = malloc(sizeof(MarkovChain));
    if (markov_chain == NULL) {
        printf(ALLOCATION_ERROR_MESSAGE);
        fclose(fp);
        return EXIT_FAILURE;
    }

    markov_chain->database = malloc(sizeof(LinkedList));
    if (markov_chain->database == NULL) {
        printf(ALLOCATION_ERROR_MESSAGE);
        fclose(fp);
        free(markov_chain);
        return EXIT_FAILURE;
    }

    markov_chain->database->first = NULL;
    markov_chain->database->last = NULL;
    markov_chain->database->size = 0;

    if (fill_database(fp, max_words, markov_chain) != EXIT_SUCCESS) {
        free_database(&markov_chain);
        fclose(fp);
        return EXIT_FAILURE;
    }

    free_database(&markov_chain);
    fclose(fp);

    return EXIT_SUCCESS;
}


int fill_database(FILE *fp, int words_to_read, MarkovChain *markov_chain) {

    return EXIT_SUCCESS;
}