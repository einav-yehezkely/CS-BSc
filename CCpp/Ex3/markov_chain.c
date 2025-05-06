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
        printf(ALLOCATION_ERROR_MESSAGE);
        return NULL;
    }

    new_markov_node->data = data_ptr;
    new_markov_node->frequency_list = NULL;
    new_markov_node->frequency_list_size = 0;
    new_markov_node->frequency_list_capacity = 0;

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
            free(markov_node->data);
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

// #define WORDS_COUNT 5

// int main() {
//     // מילים לדוגמה
//     const char *words[WORDS_COUNT] = {"hello", "world", "hello", "again", "world"};
//
//     // יצירת שרשרת מרקוב
//     MarkovChain *chain = malloc(sizeof(MarkovChain));
//     chain->database = malloc(sizeof(LinkedList));
//     chain->database->first = NULL;
//     chain->database->last = NULL;
//     chain->database->size = 0;
//
//     Node *prev_node = NULL;
//
//     for (int i = 0; i < WORDS_COUNT; i++) {
//         // העתקת המילה כדי שיהיה זיכרון עצמאי
//         char *copy = malloc(strlen(words[i]) + 1);
//         strcpy(copy, words[i]);
//
//         Node *curr_node = add_to_database(chain, copy);
//         if (!curr_node) {
//             fprintf(stderr, "Error adding word to database\n");
//             free(copy);
//             free_database(&chain);
//             return EXIT_FAILURE;
//         }
//         if (curr_node->data->data != copy) {
//             free(copy); // נמנע דליפת זיכרון למילה שלא נכנסה
//         }
//         if (prev_node) {
//             add_node_to_frequency_list(prev_node->data, curr_node->data);
//         }
//
//         prev_node = curr_node;
//     }
//
//     // הדפסה
//     Node *n = chain->database->first;
//     while (n) {
//         MarkovNode *m = n->data;
//         printf("%s:", m->data);
//         for (int i = 0; i < m->frequency_list_size; i++) {
//             printf(" [%s x%d]", m->frequency_list[i].markov_node->data,
//                                  m->frequency_list[i].frequency);
//         }
//         printf("\n");
//         n = n->next;
//     }
//
//     free_database(&chain);
//
//     return EXIT_SUCCESS;
// }