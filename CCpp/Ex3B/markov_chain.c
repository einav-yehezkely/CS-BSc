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

Node* get_node_from_database(MarkovChain *markov_chain, void *data_ptr) {
    Node *first = markov_chain->database->first;
    for (;first != NULL; first = first->next) {
        if (markov_chain->comp_func(first->data->data, data_ptr) == 0) {
            return first;
        }
    }
    return NULL;
}

Node* add_to_database(MarkovChain *markov_chain, void *data_ptr) {

    Node *from_database = get_node_from_database(markov_chain, data_ptr);
    if (from_database != NULL) {
        return from_database;
    }

    MarkovNode *new_markov_node = malloc(sizeof(MarkovNode));
    if (new_markov_node == NULL) {
        printf(ALLOCATION_ERROR_MESSAGE);
        return NULL;
    }

    void *copied_data = markov_chain->copy_func(data_ptr);
    if (copied_data == NULL) {
        printf(ALLOCATION_ERROR_MESSAGE);
        free(new_markov_node);
        return NULL;
    }

    new_markov_node->data = copied_data;
    new_markov_node->frequency_list = NULL;
    new_markov_node->frequency_list_size = 0;
    new_markov_node->frequency_list_capacity = 0;

    if (add(markov_chain->database, new_markov_node) == 1) {
        markov_chain->free_data(copied_data);
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

    if (first_node->frequency_list_size == first_node->frequency_list_capacity) {
        int new_capacity = (first_node->frequency_list_capacity == 0) ? 1 : first_node->frequency_list_capacity * 2;
        MarkovNodeFrequency *tmp = realloc(first_node->frequency_list, new_capacity*sizeof(MarkovNodeFrequency));
        if (tmp == NULL) {
            printf(ALLOCATION_ERROR_MESSAGE);
            return 1;
        }
        first_node->frequency_list = tmp;
        first_node->frequency_list_capacity = new_capacity;
    }

    int j = first_node->frequency_list_size;
    first_node->frequency_list[j].markov_node = second_node;
    first_node->frequency_list[j].frequency = 1;
    first_node->frequency_list_size++;
    return 0;

}

void free_database(MarkovChain ** ptr_chain) {

    LinkedList *database = (*ptr_chain)->database;
    Node *cur = database->first;

    while (cur != NULL) {
        Node *next = cur->next;
        MarkovNode *markov_node = cur->data;
        if (markov_node) {
            (*ptr_chain)->free_data(markov_node->data);
            free(markov_node->frequency_list);
            free(markov_node);
        }
        free(cur);
        cur = next;
    }
    free(database);
    free(*ptr_chain);
    *ptr_chain = NULL;
}

MarkovNode* get_first_random_node(MarkovChain *markov_chain) {
    Node *current;
    do {
        current = markov_chain->database->first;
        int ran = get_random_number(markov_chain->database->size);
        for (int i = 0; i < ran; i++) {
            current = current->next;
    }
    }while (markov_chain->is_last(current->data->data));

    return current->data;
}

MarkovNode* get_next_random_node(MarkovNode *cur_markov_node) {
    int total_freq = 0;
    for (int i = 0; i < cur_markov_node->frequency_list_size; i++) {
        total_freq += cur_markov_node->frequency_list[i].frequency;
    }
    int ran = get_random_number(total_freq);

    int cumulative = 0;
    for (int i = 0; i < cur_markov_node->frequency_list_size; i++) {
        cumulative += cur_markov_node->frequency_list[i].frequency;
        if (ran < cumulative) {
            return cur_markov_node->frequency_list[i].markov_node;
        }
    }
    return NULL;
}


void generate_random_sequence(MarkovChain *markov_chain, MarkovNode *first_node, int max_length) {
    int i = 1;
    markov_chain->print_func(first_node->data);

    while (i < max_length && !markov_chain->is_last(first_node->data)) {
        MarkovNode *next_node = get_next_random_node(first_node);
        if (next_node == NULL) {
            break;
        }
        printf(" ");
        markov_chain->print_func(next_node->data);
        first_node = next_node;
        i++;
    }
    printf("\n");
}
