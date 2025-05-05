#include "markov_chain.h"

#include <string.h>

/**
 * Get random number between 0 and max_number [0, max_number).
 * @param max_number
 * @return Random number
 */
int get_random_number(int max_number)
{
    return rand() % max_number;
}

Node* get_node_from_database(MarkovChain *markov_chain, char *data_ptr) {
    Node *first = markov_chain->database->first;
    for (;first != NULL; first = first->next) {
        if (strcmp(first->data->data, data_ptr) == 0) {
            return first;
        }
    }
    return NULL;
}


Node* add_to_database(MarkovChain *markov_chain, char *data_ptr) {
    Node *from_database = get_node_from_database(markov_chain, data_ptr);
    if (from_database != NULL) {
        return from_database;
    }

    MarkovNode *new_markov_node = malloc(sizeof(MarkovNode));
    if (new_markov_node == NULL) {
        return NULL;
    }

    new_markov_node->data = data_ptr;
    new_markov_node->frequency_list = NULL;

    if (add(markov_chain->database, new_markov_node) == 1) {
        free(new_markov_node);
        return NULL;
    }

    return markov_chain->database->last;
}

int add_node_to_frequency_list(MarkovNode *first_node, MarkovNode *second_node) {
    int i = 0;
    for (; i < first_node->frequency_list_size; i++) {
        if (first_node->frequency_list[i].markov_node == second_node) {
            first_node->frequency_list[i].frequency++;
            return 0;
        }
    }


    return 0;   // success
}