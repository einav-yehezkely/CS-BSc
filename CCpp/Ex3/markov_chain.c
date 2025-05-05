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
    if ((from_database) != NULL) {
        return from_database;
    }
    Node *new_node = malloc(sizeof(Node));
    if (new_node == NULL) {
        return NULL;
    }
    markov_chain->database->last->next = new_node;
    markov_chain->database->last = new_node;
    return new_node;

}