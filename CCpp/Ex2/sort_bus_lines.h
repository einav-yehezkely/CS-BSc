#ifndef EX2_REPO_SORTBUSLINES_H
#define EX2_REPO_SORTBUSLINES_H
// write only between #define EX2_REPO_SORTBUSLINES_H and #endif //EX2_REPO_SORTBUSLINES_H
#include <string.h>
#define NAME_LEN 21
/**
 * TODO add documentation
 */
typedef struct BusLine
{
    char name[NAME_LEN];
    int distance, duration;
} BusLine;

typedef enum SortType
{
    DISTANCE,
    DURATION
} SortType;

/**
 * sort bus lines by name with bubble sort
 */
void bubble_sort (BusLine *start, BusLine *end);

/**
 * sort bus lines by distance or duration with quick sort
 */
void quick_sort (BusLine *start, BusLine *end, SortType sort_type);

/**
 * partition for quick sort implementation
 */
BusLine *partition (BusLine *start, BusLine *end, SortType sort_type);
// write only between #define EX2_REPO_SORTBUSLINES_H and #endif //EX2_REPO_SORTBUSLINES_H
#endif //EX2_REPO_SORTBUSLINES_H
