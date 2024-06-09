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


def make_rows_substrings(row):
    """return a list of all substrings of a row"""
    list_of_substrings = []
    for letter in range(len(row)):
        for i in range(letter, len(row)):
            substring = ""
            for j in range(letter, i + 1):
                substring += str(row[j])
            list_of_substrings.append(substring)
    return list_of_substrings



def find_words(wordlist, matrix, directions):
    wordlist = set(wordlist)
    dict = {}
    for i in range(len(matrix)):
        substrings = make_rows_substrings(matrix[i])
        for j in substrings:
            if j in wordlist:
                if j in dict:
                    dict[j] += 1
                else:
                    dict[j] = 1
    print(dict)

if __name__ == '__main__':
    print(read_wordlist("word_list.txt"))
    # print(read_wordlist("word_list.txt")[0])
    print(read_matrix("mat.txt"))
    # print(read_matrix("mat.txt")[0][0])  # ??????????????????
    find_words(read_wordlist("word_list.txt"), read_matrix("mat.txt"), ["r"])
    # print(make_rows_substrings(read_matrix("mat.txt")[0]))