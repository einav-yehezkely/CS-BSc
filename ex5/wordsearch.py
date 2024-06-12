#################################################################
# FILE : wordsearch.py
# WRITER : Einav Yehezkely , einavye , 213835796
# EXERCISE : intro2cs ex5 2024
# DESCRIPTION: A program that finds words in a matrix of letters
# STUDENTS I DISCUSSED THE EXERCISE WITH: none
# WEB PAGES I USED: none
# NOTES: none
#################################################################

def read_wordlist(filename):
    """return list of words from a file"""
    with open(filename, 'r') as f:
        return [line.strip() for line in f]


def read_matrix(filename):
    """return list of lists (rows) of letters from a file"""
    with open(filename, 'r') as f:
        return [list(line.strip().replace(",", "")) for line in f]


def transpose_matrix(mtx):
    """return a transposed matrix"""
    return [[mtx[j][i] for j in range(len(mtx))] for i in range(len(mtx[0]))]


def make_rows_substrings(row):
    """return a list of all substrings of a row"""
    list_of_substrings = []
    for letter in range(len(row)):
        for i in range(letter, len(row)):
            substring = ""
            for j in range(letter, i + 1):
                substring += str(row[j])
            list_of_substrings.append(substring)
    # print(list_of_substrings)
    return list_of_substrings



def make_slant_substrings(slant):
    pass

def make_slant_wz_substrings(slant_wz):
    pass


def find_words(wordlist, matrix, directions):
    wordlist_set = set(wordlist)    # set is more efficient
    wordlist_reversed = []  # for the second direction
    for word in range(len(wordlist)):
        wordlist_reversed.append(wordlist[word][::-1])
    wordlist_reversed = set(wordlist_reversed)
    # print(wordlist_set)
    # print(wordlist_reversed)
    dict = {}
    # horizontal search
    if directions in ('r', 'l', 'u', 'd'):
        used_word_list = wordlist_set if directions in ('r', 'd') else wordlist_reversed
        matrix = matrix if directions in ('r', 'l') else transpose_matrix(matrix)
        for i in range(len(matrix)):
            substrings = make_rows_substrings(matrix[i])
            for j in substrings:
                if j in used_word_list:
                    word = j[::-1] if directions in ('l', 'u') else j
                    if j in dict:
                        dict[word] += 1
                    else:
                        dict[word] = 1
    # diagonal search
    if directions in ('w', 'x', 'y', 'z'):
        used_word_list = wordlist_set if directions in ('r', 'd') else wordlist_reversed
        matrix = matrix if directions in ('r', 'l') else transpose_matrix(matrix)
        for i in range(len(matrix)):
            substrings = make_rows_substrings(matrix[i])

    print(dict)

if __name__ == '__main__':
    print(read_wordlist("word_list.txt"))
    # print(read_wordlist("word_list.txt")[0])
    print(read_matrix("mat.txt"))
    # transpose_matrix(read_matrix("mat.txt"))
    # print(read_matrix("mat.txt")[0][0])  # ??????????????????
    find_words(read_wordlist("word_list.txt"), read_matrix("mat.txt"), "l")
