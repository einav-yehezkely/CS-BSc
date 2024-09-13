
def balanced(str1):
    str2 = list(str1)
    return helper(str2, 0)

def helper(str, i):
    if i >= len(str) - 1:
        for j in range(len(str)-1):
            if str[j] in ("}", "]", ")"):
                return False
        return True
    found = False
    if str[i] == "{":
        for j in range(i+1, len(str)):
            if str[j] == "}":
                del str[j]
                del str[i]
                found = True
                break
        if not found:
            return False
    elif str[i] == "(":
        for j in range(i + 1, len(str)):
            if str[j] == ")":
                del str[j]
                del str[i]
                found = True
                break
        if not found:
            return False
    elif str[i] == "[":
        for j in range(i+1, len(str)):
            if str[j] == "]":
                del str[j]
                del str[i]
                found = True
                break
        if not found:
            return False

    if found is True:
        return helper(str, i)
    else:
        return helper(str,i+1)
    return True

balanced_str = 'lu{v yf;[oh(uc)bk]bd[qn(kf);cb]j9}ry'
not_balanced = 'lu{v yf;[oh(uc[bk]bd[qn(kf);cb]j9}ry'
print(balanced(balanced_str))
print(balanced(not_balanced))
print(balanced("{[]()[(){}]}"))
print(balanced('(({}((}))))'))