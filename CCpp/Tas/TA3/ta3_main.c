#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define BUFFER_SIZE 10

void print_numbers(const int *arr, int n)
{
    for (int i = 0; i < n; ++i)
    {
        printf("%d ", arr[i]);
    }
    puts("\n");
}

//-----------------------------------//
//         2. ALLOCATION             //
//-----------------------------------//
int store_digits(int capacity)
{
    // 2.a Allocate an array of integers in the size of the given capacity.
    int *numbers = malloc(sizeof(int) * capacity);
    if(numbers == NULL)
    {
        puts("Oh no");
        return EXIT_FAILURE;
    }
    // 2.b Get char from user until '0' is inserted. Convert the given char
    // to int and insert to array
    int temp_digit=-1, i = 0;
    while((temp_digit = getchar()) != '0')
    {
        numbers[i] = temp_digit - '0';
        i++;
        // 2.c If the array capacity exceeded, increase the size of the array.
        if (i >= capacity)
        {
            capacity *= 2;
            int *tmp = realloc(numbers, sizeof(int) * capacity);
            if(tmp == NULL)
            {
                free(numbers);
                return EXIT_FAILURE;
            }
            numbers = tmp;
        }
        // Clear buffer from \n
        getchar();
    }
    // 2.d after the user finished, fit the array size to the actual number
    // of elements.
    int* tmp = realloc(numbers, sizeof(int) * (i));
    if(tmp == NULL)
    {
        free(numbers);
        return EXIT_FAILURE;
    }
    numbers = tmp;
    // Call print_numbers() to print the array.
    print_numbers(numbers, i);
    // 2.e free allocated memory
    free(numbers);
    return i;
}

//-----------------------------------//
//         1. STRINGS                //
//-----------------------------------//
// 1. implement reverse_str which receives a string (pointer) and prints it
// in reversed order.
void reverse_str(const char* str)
{
    int counter = strlen(str);
    str += counter -1;
    while (counter > 0)
    {
        putchar(*(str--));
        counter--;
    }
    putchar('\n');
}




int main()
{
    char str[] = "unicorn";
    reverse_str(str); // 1

    store_digits (5); // 2
}
