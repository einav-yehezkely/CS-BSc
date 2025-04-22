#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "sort_bus_lines.h"
#include "test_bus_lines.h"
#define MAX_LINE_LENGTH 61
#define MAX_FIELD_LENGTH 21
#define VALID_NAME_CHARS "abcdefghijklmnopqrstuvwxyz0123456789"

int test_command_input(int argc, char *argv[]);
int run_tests();
int read_input(char *buffer);
int get_number_of_lines(char *buffer);
int get_line_info(char *buffer, int num_of_lines, BusLine *lines);
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
    char buffer[MAX_LINE_LENGTH];
    const int num_of_lines = get_number_of_lines(buffer);

    // get line info
    BusLine *lines = calloc(num_of_lines, sizeof(BusLine));
    if (get_line_info(buffer, num_of_lines, lines) == EXIT_FAILURE) {
        return EXIT_FAILURE;
    }
    // for control. TODO: delete later
    for (int i = 0; i < num_of_lines; i++) {
        printf("%s,%d,%d\n", lines[i].name, lines[i].distance, lines[i].duration);
    }
    free(lines);
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

int run_tests() {
    return EXIT_SUCCESS;
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