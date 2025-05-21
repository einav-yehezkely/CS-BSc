//Don't change the macros!
#define FILE_PATH_ERROR "Error: incorrect file path"
#define NUM_ARGS_ERROR "Usage: invalid number of arguments"
#define MAX_LINE_LENGTH 1001

#define DELIMITERS " \n\t\r"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "markov_chain.h"


int fill_database(FILE *fp, int words_to_read, MarkovChain *markov_chain);
void print_string(void *data);
int compare_strings(void *a, void *b);
void *copy_string(void *data);
void free_string(void *data_ptr);
bool is_last_string(void *data_ptr);


int main(int argc, char *argv[]) {
    if (argc != 4 && argc != 5) {
        printf(NUM_ARGS_ERROR);
        return EXIT_FAILURE;
    }

    // get the arguments
    unsigned int seed = (unsigned int) strtol(argv[1], NULL, 10);
    char *endptr;
    int num_of_tweets = (int) strtol(argv[2], &endptr, 10);
    char *path_to_corpus = argv[3];
    int max_words = -1;
    if (argc == 5) {
        max_words = (int) strtol(argv[4], &endptr, 10);;
    }


    // open the file
    FILE *fp = fopen(path_to_corpus, "r");
    if (!fp) {
        printf(FILE_PATH_ERROR);
        return EXIT_FAILURE;
    }

    // allocate memory for the MarkovChain
    MarkovChain *markov_chain = malloc(sizeof(MarkovChain));
    if (markov_chain == NULL) {
        printf(ALLOCATION_ERROR_MESSAGE);
        fclose(fp);
        return EXIT_FAILURE;
    }

    // initialize the database
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
    markov_chain->print_func = print_string;
    markov_chain->comp_func = compare_strings;
    markov_chain->copy_func = copy_string;
    markov_chain->free_data = free_string;
    markov_chain->is_last = is_last_string;

    srand(seed);

    // fill the database with words from the file
    if (fill_database(fp, max_words, markov_chain) != EXIT_SUCCESS) {
        free_database(&markov_chain);
        fclose(fp);
        return EXIT_FAILURE;
    }

    // generate tweets
    for (int i = 0; i < num_of_tweets; i++) {
        MarkovNode *first_node = get_first_random_node(markov_chain);
        printf("Tweet %d: ", i+1);
        generate_random_sequence(markov_chain, first_node, 20);
    }

    // free the database
    free_database(&markov_chain);
    fclose(fp);

    return EXIT_SUCCESS;
}


int fill_database(FILE *fp, int words_to_read, MarkovChain *markov_chain) {
    char line[MAX_LINE_LENGTH];
    int read = 0;
    Node *prev_node = NULL;

    // read the file line by line and tokenize it
    while (fgets(line, MAX_LINE_LENGTH, fp) != NULL) {
        char *word = strtok(line, DELIMITERS);
        while (word != NULL) {
            if (read >= words_to_read && words_to_read != -1) {
                return EXIT_SUCCESS;
            }

            // copy the word to a new location
            char *copied = malloc(strlen(word) + 1);
            if (copied == NULL) {
                printf(ALLOCATION_ERROR_MESSAGE);
                return EXIT_FAILURE;
            }

            strcpy(copied, word);

            Node *node_data = add_to_database(markov_chain, copied);
            // if memory allocation failed
            if (node_data == NULL) {
                free(copied);
                return EXIT_FAILURE;
            }
            // check if the data was already in the database - a pointer to the same word but different location
            if (node_data->data->data != copied) {
                free(copied);
                copied = NULL;
            }
            // if not, we need to save the pointer and not free it
            // now we need to add the current word to the frequency list of the previous word
            if (prev_node != NULL) {
                add_node_to_frequency_list(prev_node->data, node_data->data);
            }

            prev_node = node_data;
            read += 1;
            word = strtok(NULL, DELIMITERS);

        }
    }

    return EXIT_SUCCESS;
}

void print_string(void *data) {
    printf("%s", (char *) data);
}
int compare_strings(void *a, void *b) {
    return strcmp((char *) a, (char *) b);
}
void *copy_string(void *data) {
    char *copied = malloc(strlen((char *) data) + 1);
    if (copied == NULL) {
        printf(ALLOCATION_ERROR_MESSAGE);
        return NULL;
    }
    strcpy(copied, data);
    return copied;
}
void free_string(void *data_ptr) {
    free(data_ptr);
}
bool is_last_string(void *data_ptr) {
    size_t len = strlen((char *) data_ptr);
    return ((char *) data_ptr)[len-1] == '.';
}