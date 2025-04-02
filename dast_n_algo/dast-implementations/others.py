import numpy as np
import math


################################################
# exam: 2020 A
# input: sorted array B
# output: number of index pairs i,j such that i!=j and B[i]==B[j]
# complexity: O(n)
def num_of_pairs(B):
    count_pairs, block_size = 0, 1
    for i in range(len(B) - 1):
        if B[i] == B[i + 1]:
            block_size += 1
        else:
            if 2 > block_size:
                continue
            elif block_size == 2:
                count_pairs += 1
            else:  # count_pairs += block_size choose 2
                count_pairs += math.factorial(block_size) // (
                    math.factorial(2) * math.factorial(block_size - 2)
                )
                block_size = 1
    return count_pairs


# test cases
print(num_of_pairs([2, 2, 2]))
print(num_of_pairs([1, 2, 2, 3, 3, 3, 4, 4, 4, 4]))  # output: 5
# print(num_of_pairs([1, 2, 3, 4, 5]))  # output: 0
# print(num_of_pairs([1, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5]))  # output: 30

# print(math.factorial(4) // (math.factorial(2) * math.factorial(4 - 2)))
