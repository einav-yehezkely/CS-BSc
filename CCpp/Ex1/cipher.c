#include "cipher.h"
#include "stdio.h"

/// IN THIS FILE, IMPLEMENT EVERY FUNCTION THAT'S DECLARED IN cipher.h.


// See full documentation in header file
void cipher (char s[], int k)
{
  for (int i = 0; s[i] != '\0'; i++) {
    unsigned char c = s[i];
    if (s[i] >= 'a' && s[i] <= 'z') {
      s[i] = (char)((((c - 'a' + k) % 26) + 26) % 26 + 'a');
    }
    else if (s[i] >= 'A' && s[i] <= 'Z') {
      s[i] = (char)((((c - 'A' + k) % 26) + 26) % 26 + 'A');
    }
  }
}

// See full documentation in header file
void decipher (char s[], int k)
{
  cipher(s, (-k));
}