#include "sort_bus_lines.h"
#include <stdio.h>
#include <stdlib.h>

void bubble_sort (BusLine *start, BusLine *end){
    BusLine *i, *j;
    BusLine temp;
    for (i = start; i < end; i++){
        for (j = start; j < end - 1; j++){
            if (strcmp((*j).name, (*(j + 1)).name) > 0){
                temp = *j;
                *j = *(j + 1);
                *(j + 1) = temp;
            }
        }
    }

}

void quick_sort (BusLine *start, BusLine *end, SortType sort_type) {
    if (start < (end - 1)) {
        BusLine *p = partition(start, end, sort_type);
        quick_sort(start, p, sort_type);
        quick_sort(p + 1, end, sort_type);
    }
}


BusLine *partition (BusLine *start, BusLine *end, SortType sort_type) {
    BusLine pivot = *(end - 1);     // pivot is the last element
    BusLine *i = start;
    for (BusLine *j = start; j < end - 1; j++) {
        int j_val = (sort_type == DISTANCE) ? (*j).distance : (*j).duration;
        int pivot_val = (sort_type == DISTANCE) ? pivot.distance : pivot.duration;
        if (j_val <= pivot_val) {

            // swap
            BusLine temp = *i;
            *i = *j;
            *j = temp;
            i++;
        }
    }
    BusLine temp = *i;
    *i = *(end - 1);
    *(end - 1) = temp;
    return i;

}

