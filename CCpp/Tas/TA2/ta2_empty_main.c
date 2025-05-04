#include <stdio.h>
#include <string.h>
// #include "foo.h"

#define BUFFER_SIZE 10

 /**
  * 6 - Files
  * a. Open the file input_file.txt
  * b. Check that the file opened successfully. If not – print to stdio and return.
  * c. Open the file output_file.txt
  * d. Check that the file opened successfully. If not – print to stderr, close input file and return.
  * e. While possible, read 10 chars from input_file and print into output_file.
  * f. close files.
  */
void files()
{
    char input_file_path[] = "../input_file.txt"; // Relative path
    char out_file_path[] = "../output_file.txt";
    // a. Open the file input_file.txt
    // FILE *in_file = ?;

    // b. Check that the file opened successfully. If not – print to stdio and return.
    // if (...)
    // {
    //    printf("error opening file: %s", input_file_path);
    // }

    // c. Open the file output_file.txt
    // FILE *out_file = ?;

    // d. Check that the file opened successfully. If not – print to stderr, close input file and return.
    // if (...)
    // {
        //    ???
    //    fprintf(stderr, "error opening file: %s", out_file_path);
    // }

    // e. While possible, read 10 chars from input_file and print into output_file.
    // char input[BUFFER_SIZE];
    // while (...)
    // {
    //     fprintf(...);
    // }

    // f. close files.
    // fclose(in_file);
    // fclose(out_file);
}

/**
 * 5
 */
void inputs()
{
    // 5.a read a single character from keyboard.

    // 5.b Write code that takes line with day (int), month (string) and
    // year (int),
    // stores into variables and prints them.
    char str[20];

//    if(res != EOF && res == 3)
//        printf("day: %d, month: %s, year: %d \n", day, month, year);
}

// 4 Print the different variables to screen. Use \n for new line.
void outputs(int i, char c, float f, char str[])
{
    puts("bla");
    printf("Just msg without variables\n");
    printf("integer: %d\n", i);
    printf("character: %c\n", c);
    printf("float: %f\n", f);
    printf("string: %s\n", str);
    printf("part of string: %.3s\n", str);
}

// 3.c Implement change_str() – gets string s and char c and replaces s
// last character with c.
void change_str(char s[], char c)
{

}

/**
 * 3
 */
void strings()
{
//  3.a Create “hi” string literals msg_1 with []  Print its size.

//    printf("msg_1 size: %lu \n", sizeof(msg_1));

//  3.b Create an array msg_2 of chars and initialize it with ‘h’ and ‘I’.
//  Print its size.
//    printf("msg_3 size: %lu \n", sizeof(msg_3));

//  3.c Implement change_str() – gets string s and char c and replaces s
//  last character with c.
//    change_str(msg_1, 'b');
}



/**
 * 1.a. Create an array x that contains the numbers 1 – 5.
 * 1.b. Create an array y from size 5. What y contains now?
 * 1.c. Try to assign x to y.
 * 1.d. Fill y with numbers with for loop, WO using the number “5” explicitly.
 */
void arrays()
{
    // 1.a. Create an array x that contains the numbers 1 – 5.
    
    // 1.b. Create an array y from size 5. What y contains now?

    // 1.c. Try to assign x to y.

    printf("array size: %lu \n", sizeof(x));

    // 1.d Fill y with numbers with for loop
    // BAD PRACTICE:




}



int main() {
    arrays(); //1
//    strings(); // 3
//    outputs(5, 'a', 0.5f, "hello"); // 4
//    inputs(); // 5
//    files(); // 6
//    foo(); //7
}
