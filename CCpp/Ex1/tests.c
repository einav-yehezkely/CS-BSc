#include "tests.h"

#include <string.h>

#define K_1 3
#define K_2 2
#define K_3 (-1)
#define K_4 (-3)
#define K_5 29


// See full documentation in header file
int test_cipher_non_cyclic_lower_case_positive_k ()
{
  char in[] = "abc";
  char out[] = "def";
  cipher (in, K_1);
  return strcmp (in, out) != 0;
}

// See full documentation in header file
int test_cipher_cyclic_lower_case_special_char_positive_k ()
{
  char in[] = "lr ,z";
  char out[] = "nt ,b";
  cipher (in, K_2);
  return strcmp (in, out) != 0;
}

// See full documentation in header file
int test_cipher_non_cyclic_lower_case_special_char_negative_k ()
{
  char in[] = "l!r ,d";
  char out[] = "k!q ,c";
  cipher (in, K_3);
  return strcmp (in, out) != 0;
}

// See full documentation in header file
int test_cipher_cyclic_lower_case_negative_k ()
{
  char in[] = "lr ,a";
  char out[] = "io ,x";
  cipher (in, K_4);
  return strcmp (in, out) != 0;
}

// See full documentation in header file
int test_cipher_cyclic_upper_case_positive_k ()
{
  char in[] = "LRZ";
  char out[] = "OUC";
  cipher (in, K_5);
  return strcmp (in, out) != 0;
}

// See full documentation in header file
int test_decipher_non_cyclic_lower_case_positive_k ()
{
  char in[] = "def";
  char out[] = "abc";
  decipher (in, K_1);
  return strcmp (in, out) != 0;
}

// See full documentation in header file
int test_decipher_cyclic_lower_case_special_char_positive_k ()
{
  char in[] = "nt ,b";
  char out[] = "lr ,z";
  decipher (in, K_2);
  return strcmp (in, out) != 0;
}

// See full documentation in header file
int test_decipher_non_cyclic_lower_case_special_char_negative_k ()
{
  char in[] = "k!q ,c";
  char out[] = "l!r ,d";
  decipher (in, K_3);
  return strcmp (in, out) != 0;
}

// See full documentation in header file
int test_decipher_cyclic_lower_case_negative_k ()
{
  char in[] = "io ,x";
  char out[] = "lr ,a";
  decipher (in, K_4);
  return strcmp (in, out) != 0;
}

// See full documentation in header file
int test_decipher_cyclic_upper_case_positive_k ()
{
  char in[] = "OUC";
  char out[] = "LRZ";
  decipher (in, K_5);
  return strcmp (in, out) != 0;
}

