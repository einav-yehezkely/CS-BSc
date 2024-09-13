from functools import reduce
def f4(st, dict, k):
    sorted_dict = sorted(dict.items(), key=lambda item: int(item[1]), reverse=True)
    print(sorted_dict)
    new_st = ""
    for i in range(len(sorted_dict)):
        for key in sorted_dict[i][0]:
            if key in st:
                if new_st != "" and st != "":
                        new_st += key
                else:
                    new_st += key
            if len(new_st) == k:
                if new_st in st:
                    new_st = new_st[:-1]
                    continue
                return new_st


st = "abcdzzm"
dict1 = {'a': 1, "b": 1, "t": -5, "c": 1, "d": 1, "m": 1, "n": -1, "e": -1, "z": -1}
print(f4(st, dict1, 4))
