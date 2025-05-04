#include <stdio.h>

// 3.b create global variable (initialized)
// This variable will be visible everywhere!

//3.c create uninitialized global variable - what is special about this case?


/**
 * 9.a. Show the 'const' keyword:
 * 1. Declare and initialize const int variable
 * 2. Try to change it.
 * 3. Value doesn't have to be known on compilation time (getchar())
 */
void show_const()
{

}

// 6.b. call this function:
void print_sizes()
{
    int x = 10;
    printf("Size of char %lu\n", sizeof(char));
    printf("Size of short %lu\n", sizeof(short));
    printf("Size of int %lu\n", sizeof(int));
    printf("Size of var x %lu\n", sizeof(x));
    printf("Size of long %lu\n", sizeof(long));
    printf("Size of float %lu\n", sizeof(float));
    printf("Size of double %lu\n", sizeof(double));
    printf("Size of long double %lu\n", sizeof(long double));
}

// 5.
void print_n_odd_chars(int n, char c)
{
    for (int i = 0; i < n; i++)
    {
        // 5.a Print c if i is odd. print space otherwise

        // 5.b implement without if-else
    }
}

// 4.a Define function “print_n_chars” that receives int and char and returns int.
// 4.b Implement with for loop
// 4.c Implement with while loop
// 4.d call from main.


int main() {
    printf("First TA!\n");
    // This is how you write comments in C and C++!
    // 2.a - Declare some variables

    // 2.b Declare two variables in single line

    //2.c now with initialization! - why?

    //3.a Open new scope, change existing and create new variables (+shadows)

    // 3.a note - what are the values here?

    // 3.a note - what are the values here?
    // 3.b - create variable in global scope
    // 3.c - create uninitialized variable in global scope

    // 4.d call print_n_chars
    // print_n_chars(5, 'a');

    // 5. Implement print_n_odd_chars
    // print_n_odd_chars(10, 'b');

    // 6.a Use sizeof operator on char, int and x

    // 6.b. call print_sizes()
    // print_sizes();

    // 7.a Show casting on the next variables:
    int i = 10; short s = 5; long l = 20; char c = 'a'; float f = 1;
    // i = s;
    // s = i;
    // i = l;
    // l = s;
    // c = i;
    // i = c;
    // f = c;

    // 9.a. Show the 'const' keyword:
    // show_const();

    return 0;
}
