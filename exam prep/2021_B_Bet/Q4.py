def guess_pass(pwd, text):
    return helper(pwd, text, [], 0)

def helper(pwd, text, lst, ind):
    if pwd == "":
        return lst
    if text == "":
        return False
    if pwd[0] == text[0]:
        return helper(pwd[1:], text[1:], lst + [ind], ind + 1)
    return helper(pwd, text[1:], lst, ind + 1)

pwd="abc-123"
text="abc-123abc-123"
print(guess_pass(pwd, text))