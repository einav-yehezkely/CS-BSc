#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define NUM_OF_STUDENTS 4
#define NUM_OF_CLASSES 3

/** insert macro for  1. **/

/**************************/

void setup_some_stuff()
{
  setvbuf(stdout, NULL, _IONBF, 0); // don't mind this
}


void print_grades(int** grades, int n_rows, int n_cols)
{
    for(int i = 0; i < n_rows; i++)
    {
        for(int j = 0; j < n_cols; j++)
            printf("%d, ", grades[i][j]);
        printf("\n");
    }
}


/**
 * 1. Implement the function fully_dynamic_grades().
 * This time neither the number of students nor the number of courses is known. Both are given by the user.
 * 1.b. What should we do if each student has different number of courses?
 * 1.c. Can we return the created array? How? When we can’t?
 */
int fully_dynamic_grades()
{
    int num_of_courses = 0, num_of_students = 0;
    char buff[10];
    fgets(buff, 10, stdin);
    sscanf(buff, "%d %d", &num_of_students, &num_of_courses);
    // 2.a Allocate fully dynamic 2D array "grades" with num_of_students pointers

    if(grades == NULL)
    {
        return -1;
    }
    for (int i = 0; i < num_of_students; i++)
    {
        // 2.a Allocate memory for the grades in courses of each student

        if(grades[i] == NULL)
        {
            //free...
            for (int j = 0; j < i; ++j)
            {
                free(grades[j]);
                grades[j] = NULL;
            }
            free(grades);
            return -1;
        }
    }
    // Do things
    for (int i = 0; i < num_of_students; i++)
    {
        free(grades[i]);
        grades[i] = NULL;
    }
    free(grades);
    grades = NULL;
    return 0;
}



int static_grades()
{
    int grades[NUM_OF_STUDENTS][NUM_OF_CLASSES];
    printf("sizeof grades: %lu\n", sizeof(grades));
    printf("sizeof grades[0]: %lu\n", sizeof(grades[0]));
    for(int i = 0; i < NUM_OF_STUDENTS; i++)
    {
        for(int j = 0; j < NUM_OF_CLASSES; j++)
            grades[i][j] = 0;
    }
    return 0;
}

/**
* 2. Implement the function “efficient fully dynamic()” that receives number
*     of rows, columns and pointer to result array. It returns 0 if function
*     succeed:
* 2a. Create an efficient dynamic array and initialize each cell to 10
* 2b. Assign to each cell the value of  (row index) X (column index).
* 2c. Use the MACRO INDEX that computes the index easily.
* 2d. Return the array with the result pointer. Note the use of pointer to
* pointer?
* 2e. Call this function from main.
* 2f. Free the array.
 */
int efficient_fully_dynamic(int rows, int cols, int **res)
{
    // a. Create an efficient dynamic array and initialize each cell to 10
    int *arr;

    if(arr == NULL)
        return EXIT_FAILURE;

    // initialize each cell to 10
    for(int i = 0; i < /*insert value*/ ; i++)
    {

    }
    print_grades(arr, rows, cols);

    // b. Assign to each cell the value of  (row index) X (column index).
    for(int i = 0; i < rows; ++i)
    {
        for(int j = 0; j < cols; ++j)
        {
            // c.implement without macro:

            // c.Use the MACRO INDEX that computes the index easily:

        }
    }
    // d. Return the array with the result pointer.


    return EXIT_SUCCESS;
}

int main()
{
  	setup_some_stuff(); 		// don't mind this
    fully_dynamic_grades(); 	// 1

    // e. Call this function from main.

    //efficient_fully_dynamic(...);  //2
}
