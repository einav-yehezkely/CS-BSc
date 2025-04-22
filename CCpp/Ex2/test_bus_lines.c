#include "test_bus_lines.h"

int is_sorted_by_distance (const BusLine *start, const BusLine *end) {
    for (BusLine *i = start; i < (end-1); i++) {
        if ((*i).distance > ((*(i+1)).distance)) {
            return 0;
        }
    }
    return 1;
}

int is_sorted_by_duration (const BusLine *start, const BusLine *end) {
    for (BusLine *i = start; i < (end-1); i++) {
        if ((*i).duration > ((*(i+1)).duration)) {
            return 0;
        }
    }
    return 1;
}

int is_sorted_by_name (const BusLine *start, const BusLine *end) {
    for (BusLine *i = start; i < (end-1); i++) {
        if (strcmp((*i).name, (*(i + 1)).name) > 0) {
            return 0;
        }
    }
    return 1;
}

int is_equal (const BusLine *start_sorted,
              const BusLine *end_sorted,
              const BusLine *start_original,
              const BusLine *end_original) {

    const size_t len_sorted = end_sorted - start_sorted;
    const size_t len_original = end_original - start_original;

    if (len_sorted != len_original) {
        return 0;
    }
    for (BusLine *i = start_original; i < (end_original); i++) {
        int found =  0;
        for (BusLine *j = start_sorted; j < (end_sorted); j++) {
            if (strcmp((*i).name, (*(j)).name) != 0) {
                found = 1;
                break;
            }
        }
        if (found == 0) {
            return 0;
        }
    }
    return 1;
}
