##############################
#   sort dictionary by values
##############################
dict1 = {'a': 5, "b": 6, "h": -5, "c": 4, "d": 12, "f": 1, "g": -1, "e": -2, "j": 700}
sorted_dict1 = sorted(dict1.items(), key=lambda item: int(item[1]), reverse=True)

print("by values - descending (reverse=True):")
print("     list form:")
print("        ", sorted_dict1)
print("     convert to dictionary:")
print("        ", dict(sorted_dict1))

##############################
#   sort dictionary by keys
##############################
dict2 = {'a': 5, "b": 6, "h": -5, "c": 4, "d": 12, "f": 1, "g": -1, "e": -2, "j": 700}
sorted_dict2 = sorted(dict2.items(), key=lambda item: item[0], reverse=False)
print()
print("by keys - ascending (reverse=False):")
print("     list form:")
print("        ", sorted_dict2)
print("     convert to dictionary:")
print("        ", dict(sorted_dict2))