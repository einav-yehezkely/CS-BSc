#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sort_bus_lines.h"
#include "test_bus_lines.h"
#define MAX_LINE_LENGTH 61
#define MAX_FIELD_LENGTH 21
#define VALID_NAME_CHARS "abcdefghijklmnopqrstuvwxyz0123456789"

/**
 * test if the 1st input is valid
 * @param argc number of arguments
 * @param argv arguments
 * @return EXIT_SUCCESS if the command is valid, EXIT_FAILURE otherwise
 */
int test_command_input(int argc, char *argv[]);
/**
 * read input from stdin
 * @param buffer stores the input
 * @return EXIT_SUCCESS if the input is valid, EXIT_FAILURE otherwise
 */
int read_input(char *buffer);
/**
 * get the number of lines from the user, check if valid integer
 * @param buffer stores the input
 * @return number of lines
 */
int get_number_of_lines(char *buffer);
/**
 * get the line info from the user, check if valid <line_name>,<distance>,<duration>
 * @param buffer stores the input
 * @param num_of_lines number of lines to get
 * @param lines array of bus lines
 * @return EXIT_SUCCESS if the input is valid, EXIT_FAILURE otherwise
 */
int get_line_info(char *buffer, int num_of_lines, BusLine *lines);
/**
 * sort the bus lines by distance, duration or name, with quick sort for distance and duration or bubble sort for name
 * @param start_sorted start of the array to sort
 * @param end_sorted end of the array to sort
 * @param command command to sort by
 */
void sort(BusLine *start_sorted, BusLine *end_sorted, char *command);
/**
 * run tests on the bus lines - check sorting
 * @param start_original start of the original array
 * @param end_original end of the original array
 * @param start_sorted start of the sorted array
 * @param end_sorted end of the sorted array
 * @return EXIT_SUCCESS if all tests passed, EXIT_FAILURE otherwise
 */
int run_tests(BusLine *start_original, BusLine *end_original, BusLine *start_sorted, BusLine *end_sorted);
/**
 * print if the test passed or failed
 * @param result result of the test
 * @param i test number
 * @param name_of_sort name of the sort
 */
void sort_passed_failed(int result, int i, const char *name_of_sort);
/**
 * print if the test passed or failed
 * @param result result of the test
 * @param i test number
 */
void equal_passed_failed(int result, int i);
/**
 * print lines after sorting
 * @param start
 * @param end
 */
void print_sorted_lines(const BusLine *start, const BusLine *end);

/**
 * main function
 * @param argc number of arguments
 * @param argv arguments
 * @return EXIT_SUCCESS if the program ran successfully, EXIT_FAILURE otherwise
 */

int main (int argc, char *argv[])
{
    // check if the command is valid
    if (test_command_input(argc, argv) == EXIT_FAILURE) {
        return EXIT_FAILURE;
    }

    char *command = argv[1];
    char buffer[MAX_LINE_LENGTH];
    const int num_of_lines = get_number_of_lines(buffer);

    // get line info
    BusLine *lines_original = malloc(num_of_lines * sizeof(BusLine));
    BusLine *lines_sorted = malloc(num_of_lines * sizeof(BusLine));
    if (lines_original == NULL || lines_sorted == NULL) {
        return EXIT_FAILURE;
    }
    if (get_line_info(buffer, num_of_lines, lines_original) == EXIT_FAILURE) {
        return EXIT_FAILURE;
    }

    // copy to line_sorted
    for (int i = 0; i < num_of_lines; i++) {
        lines_sorted[i] = lines_original[i];
    }

    BusLine *start_original = lines_original;
    BusLine *end_original = lines_original + num_of_lines;
    BusLine *start_sorted = lines_sorted;
    BusLine *end_sorted = lines_sorted + num_of_lines;

    // sort
    sort(start_sorted, end_sorted, command);
    print_sorted_lines(start_sorted, end_sorted);

    // run tests if asked
    if (strcmp(argv[1], "test") == 0) {
        const int result = run_tests(start_original, end_original, start_sorted, end_sorted);
        free(lines_original);
        free(lines_sorted);
        return result;
    }


    free(lines_original);
    free(lines_sorted);
    lines_original = NULL;
    lines_sorted = NULL;
    return EXIT_SUCCESS;
}

int test_command_input(int argc, char *argv[]) {
    if (argc != 2) {
        return EXIT_FAILURE;
    }
    char *legit_inputs[4] = {"by_duration", "by_distance", "by_name", "test"};
    for (int i = 0; i < 4; i++) {
        if (strcmp(argv[1], legit_inputs[i]) == 0) {
            return EXIT_SUCCESS;
        }
    }
    printf("Usage: invalid command. valid input: ./sort_lines <by_duration|by_distance|by_name|test>\n");
    return EXIT_FAILURE;
}

int read_input(char *buffer) {
    if (!fgets(buffer, MAX_LINE_LENGTH, stdin)) {
        printf("Error: failed to read input\n");
        return EXIT_FAILURE;
    }
    return EXIT_SUCCESS;
}

int get_number_of_lines(char *buffer) {
    int num_of_lines = 0;
    char *endptr;
    long num;
    do {
        printf("Enter number of lines. Then enter\n");

        if (read_input(buffer) == EXIT_FAILURE) {
            return EXIT_FAILURE;
        }
        num = strtol(buffer, &endptr, 10);
        if ((endptr == buffer) || (*endptr != '\n' && *endptr != '\0') || (buffer[0] == ' ')) {
            printf("Error: Invalid number of lines\n");
        }
        else {
            break;
        }
    }while (true);
    num_of_lines = (int) num;

    return num_of_lines;
}

int get_line_info(char *buffer, int num_of_lines, BusLine *lines) {

    if (lines == NULL) {
        return EXIT_FAILURE;
    }

    for (int i = 0 ; i < num_of_lines; i++) {
        char name[MAX_FIELD_LENGTH];
        int distance;
        int duration;
        do {
            printf("Enter line info. Then enter\n");

            if (read_input(buffer) == EXIT_FAILURE) {
                free(lines);
                return EXIT_FAILURE;
            }

            if (sscanf(buffer, "%20[^,],%d,%d", name, &distance, &duration) != 3) {
                printf("Error: invalid format. valid input: <line_name>,<distance>,<duration>\n");
                continue;
            }
            if (strspn(name, VALID_NAME_CHARS) != strlen(name)) {
                printf("Error: bus name should contains only digits and small chars\n");
                continue;
            }
            if (distance < 0 || distance > 1000) {
                printf("Error: distance should be an integer between 0 and 1000 (includes)\n");
                continue;
            }
            if (duration < 10 || duration > 100) {
                printf("Error: duration should be an integer between 10 and 100 (includes)\n");
                continue;
            }
            strcpy(lines[i].name, name);
            lines[i].distance = distance;
            lines[i].duration = duration;
            break;
        }while (true);
    }
    return EXIT_SUCCESS;
}

void sort(BusLine *start_sorted, BusLine *end_sorted, char *command) {
    if (strcmp(command, "by_distance") == 0) {
        quick_sort(start_sorted, end_sorted, DISTANCE);
    }
    if (strcmp(command, "by_duration") == 0) {
        quick_sort(start_sorted, end_sorted, DURATION);
    }
    if (strcmp(command, "by_name") == 0) {
        bubble_sort(start_sorted, end_sorted);
    }
}

int run_tests(BusLine *start_original, BusLine *end_original, BusLine *start_sorted, BusLine *end_sorted) {

    sort(start_sorted, end_sorted, "by_name");
    const int name = is_sorted_by_name(start_sorted, end_sorted);
    sort_passed_failed(name, 1, "name");

    const int equal_1 = is_equal(start_sorted, end_sorted, start_original, end_original);
    equal_passed_failed(equal_1, 2);

    sort(start_sorted, end_sorted, "by_distance");
    const int distance = is_sorted_by_distance(start_sorted, end_sorted);
    sort_passed_failed(distance, 3, "distance");

    const int equal_2 = is_equal(start_sorted, end_sorted, start_original, end_original);
    equal_passed_failed(equal_2, 4);

    sort(start_sorted, end_sorted, "by_duration");
    const int duration = is_sorted_by_duration(start_sorted, end_sorted);
    sort_passed_failed(duration, 5, "duration");

    const int equal_3 = is_equal(start_sorted, end_sorted, start_original, end_original);
    equal_passed_failed(equal_3, 6);

    int const result = (distance + equal_1 + duration + equal_2 + name + equal_3);
    if (result != 6) {
        return EXIT_FAILURE;
    }
    return EXIT_SUCCESS;
}

void sort_passed_failed(const int result, const int i, const char *name_of_sort) {
    if (result == 1) {
        printf("TEST %d PASSED: Lines are sorted by %s\n", i, name_of_sort);
    }
    else if (result == 0) {
        printf("TEST %d FAILED: Lines are not sorted by %s\n", i, name_of_sort);
    }
}

void equal_passed_failed(const int result, const int i) {
    if (result == 1) {
        printf("TEST %d PASSED: Sorted and original are equal\n", i);
    }
    else if (result == 0) {
        printf("TEST %d FAILED: Sorted and original are not equal\n", i);
    }
}

void print_sorted_lines(const BusLine *start, const BusLine *end) {
    for (const BusLine *p = start; p < end; p++) {
        printf("%s,%d,%d\n", p->name, p->distance, p->duration);
    }
}

