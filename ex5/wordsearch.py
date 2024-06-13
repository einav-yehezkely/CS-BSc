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


def flip_matrix(mtx):
    return [row[::-1] for row in mtx]


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


def convert_slats_to_rows(matrix):
    """return a matrix in which the rows were the slants - left to right, up to down"""
    slants_matrix = []
    rows = len(matrix)
    cols = len(matrix[0])
    # half down
    for start_row in range(rows):
        slant = []
        r, c = start_row, 0
        while r < rows and c < cols:
            slant.append(matrix[r][c])
            r += 1
            c += 1
        slants_matrix.append(slant)
    # half up
    for start_col in range(1, cols):
        slant = []
        r, c = 0, start_col
        while r < rows and c < cols:
            slant.append(matrix[r][c])
            r += 1
            c += 1
        slants_matrix.append(slant)

    return slants_matrix


def remove_all_occurrences(lst, item):
    """for removing direction"""
    return [x for x in lst if x != item]

def find_words(wordlist, matrix, directions):
    if len(matrix) == 0 or len(matrix[0]) == 0:
        return []

    wordlist_set = set(wordlist)    # set is more efficient
    wordlist_reversed = []  # for the second direction
    for word in range(len(wordlist)):
        wordlist_reversed.append(wordlist[word][::-1])
    wordlist_reversed = set(wordlist_reversed)
    # print(wordlist_set)
    # print(wordlist_reversed)
    dict = {}
    print(directions)
    print(list(directions))
    directions = list(directions)
    all_directions = ['r', 'l', 'u', 'd', 'w', 'x', 'y', 'z']
    # while directions:
    #     directions = remove_all_occurrences(directions, 'w')
    #     print(directions)
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
        matrix = flip_matrix(matrix) if directions in ('z', 'w') else matrix
        diagonal_matrix = convert_slats_to_rows(matrix)
        used_word_list = wordlist_set if directions in ('y', 'z') else wordlist_reversed
        for i in range(len(diagonal_matrix)):
            substrings = make_rows_substrings(diagonal_matrix[i])
            for j in substrings:
                if j in used_word_list:
                    word = j[::-1] if directions in ('x', 'w') else j
                    if j in dict:
                        dict[word] += 1
                    else:
                        dict[word] = 1

    print(dict)

if __name__ == '__main__':
    word = '1234567890'
    print('35' in word)
    print(read_wordlist("word_list.txt"))
    # print(read_wordlist("word_list.txt")[0])
    print(read_matrix("mat.txt"))
    # transpose_matrix(read_matrix("mat.txt"))
    # print(read_matrix("mat.txt")[0][0])  # ??????????????????

    print(convert_slats_to_rows(read_matrix("mat.txt")))
    find_words(read_wordlist("word_list.txt"), read_matrix("mat.txt"), "wrwd")