#include <stdio.h>
#include <stdlib.h>
#include "sort_bus_lines.h"
#include "test_bus_lines.h"
#define MAX_LINE_LENGTH 61

int test_command_input(int argc, char *argv[]);
int run_tests();

/**
 * TODO add documentation
 */
int main (int argc, char *argv[])
{
    // check if the command is valid
    if (test_command_input(argc, argv) == EXIT_FAILURE) {
        return EXIT_FAILURE;
    }
    // run tests if asked
    if (strcmp(argv[1], "test") == 0) {
        return run_tests(argc, argv);
    }
    char command = *argv[1];
    // get the number of lines
    int buffer[MAX_LINE_LENGTH];
    printf("Enter number of lines. Then enter\n");
    if (!fgets(buffer, MAX_LINE_LENGTH, stdin)) {
        printf("Error: failed to read input\n");
        return EXIT_FAILURE;
    }
    int num_of_lines = 0;
    if (sscanf(buffer, "%d", &num_of_lines) != 1 || num_of_lines <= 0) {
        printf("Error: Invalid number of lines\n");
        return EXIT_FAILURE;
    }
    // get line info
    printf("Enter line info. Then enter\n");

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
    printf("Usage: invalid command\n valid input:\n./sort_lines by_duration\n./sort_lines by_distance\n./sort_lines by_name\n./sort_lines test\n");
    return EXIT_FAILURE;
}

int run_tests() {
    return EXIT_SUCCESS;
}

int reaf_input(int *buffer) {

}