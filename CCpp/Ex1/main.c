#include "cipher.h"
#include "tests.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define ARGC_ERROR "The program receives 1 or 4 arguments only.\n"
#define TEST_ERROR "Usage: cipher test\n"
#define COMMAND_ERROR "The given command is invalid.\n"
#define SHIFT_ERROR "The given shift value is invalid.\n"
#define FILE_ERROR "The given file is invalid.\n"

#define MAX_LINE_LENGTH 1025

/**
 * Runs tests for cipher and decipher if given 1 argument.
 * @return passed/didn't pass all tests
 */
int run_tests();

/**
 * Validates the input arguments provided by the user.
 * @param argc number of arguments
 * @param argv all the arguments received
 * @return valid/invalid input
 */
int test_input(int argc, char *argv[]);

/**
 * Applying the cipher or decipher operation
 * @param command cipher/decipher
 * @param k shift parameter
 * @param input_file 
 * @param output_file 
 */
void do_cipher(char *command, int k, FILE *input_file, FILE *output_file);

/**
 * Opens the output file for writing if exists and creates a new one if don't
 * @param output_file_path 
 * @return output file
 */
FILE *open_output_file(const char *output_file_path);

int main (int argc, char *argv[])
{
  if ((test_input(argc, argv)) == EXIT_FAILURE) {
    return EXIT_FAILURE;
    }
  if (argc == 2 && strcmp(argv[1], "test") == 0) {
    return run_tests();
  }

  // Extract arguments
  char command[MAX_LINE_LENGTH];
  strcpy(command, argv[1]);

  const char *k_str = argv[2];
  char *endptr;
  long int temp_k = strtol(k_str, &endptr, 10);
  int k = (int) temp_k;

  char *input_file_path = argv[3];
  char *output_file_path = argv[4];

  FILE *input_file = fopen(input_file_path, "r");

  FILE *output_file = open_output_file(output_file_path);
  if (!output_file) {
    fprintf(stderr, FILE_ERROR);
    fclose(input_file);
    return EXIT_FAILURE;
  }

  do_cipher(command, k, input_file, output_file);

  fclose(input_file);
  fclose(output_file);
  return EXIT_SUCCESS;
}

int run_tests() {
  if (test_cipher_non_cyclic_lower_case_positive_k() != 0){
    return EXIT_FAILURE;
  }
  if (test_cipher_cyclic_lower_case_special_char_positive_k() != 0){
    return EXIT_FAILURE;
  }
  if (test_cipher_non_cyclic_lower_case_special_char_negative_k() != 0){
    return EXIT_FAILURE;
  }
  if (test_cipher_cyclic_lower_case_negative_k() != 0){
    return EXIT_FAILURE;
  }
  if (test_cipher_cyclic_upper_case_positive_k() != 0){
    return EXIT_FAILURE;
  }
  if (test_decipher_non_cyclic_lower_case_positive_k() != 0){
    return EXIT_FAILURE;
  }
  if (test_decipher_cyclic_lower_case_special_char_positive_k() != 0){
    return EXIT_FAILURE;
  }
  if (test_decipher_non_cyclic_lower_case_special_char_negative_k() != 0){
    return EXIT_FAILURE;
  }
  if (test_decipher_cyclic_lower_case_negative_k() != 0){
    return EXIT_FAILURE;
  }
  if (test_decipher_cyclic_upper_case_positive_k() != 0){
    return EXIT_FAILURE;
  }
  return EXIT_SUCCESS;
}

int test_input(int argc, char *argv[]) {
  // check if number of arguments is valid
  if ((argc != 5) && (argc != 2)) {
    fprintf (stderr, ARGC_ERROR);
    return EXIT_FAILURE;
  }
  // check if the only arg is "test"
  if ((argc == 2) && strcmp(argv[1], "test") != 0) {
    fprintf (stderr, TEST_ERROR);
    return EXIT_FAILURE;
  }
  if (argc == 5) {
    // check if command is valid - cipher or decipher
    if ((strcmp(argv[1], "cipher") != 0) && (strcmp(argv[1], "decipher") != 0)) {
      fprintf (stderr, COMMAND_ERROR);
      return EXIT_FAILURE;
      }
    // check if shift value is valid - integer
    const char *str = argv[2];
    char *endptr;
    strtol(str, &endptr, 10);
    if ((endptr == str) || (*endptr != '\0')) {
      fprintf (stderr, SHIFT_ERROR);
      return EXIT_FAILURE;
    }
    // check input file: exists and readable
    FILE *input_file = fopen(argv[3], "r");
    if (!input_file) {
      fprintf(stderr, FILE_ERROR);
      return EXIT_FAILURE;
    }
    fclose(input_file);
  }
  return EXIT_SUCCESS;
}

void do_cipher(char *command, int k, FILE *input_file, FILE *output_file) {
  char line[MAX_LINE_LENGTH];
  while (fgets(line, MAX_LINE_LENGTH, input_file)) {

    if (strcmp(command, "cipher") == 0) {
      cipher(line, k);
    }
    else if (strcmp(command, "decipher") == 0) {
      decipher(line, k);
    }
    fprintf(output_file, "%s", line);
  }
}

FILE *open_output_file(const char *output_file_path) {
  FILE *output_file = fopen(output_file_path, "r");
  if (output_file) {
    fclose(output_file); // Close to check permissions later
    output_file = fopen(output_file_path, "r+"); // Try opening with "r+" (read & write)
  }
  else {
    output_file = fopen(output_file_path, "w"); // Create new file if it doesn't exist
  }

  return output_file;
}


