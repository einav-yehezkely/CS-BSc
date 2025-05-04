#include <stdlib.h>
#include <stdio.h>

int example1() 
{
    char *x = malloc(100);
    return 0;
}

int example2(char *str_index) 
{
    int index = atoi(str_index);
    char *x = malloc(10);
    x[index] = 'f';
    free(x);
    return 0;
}

int main(int argc, const char *argv[]) 
{
    example2(argv[1]);
}


int example3() 
{
    int x;
    if (x == 0) {
            printf("x is zero.");
    }
    
    return 0;
}