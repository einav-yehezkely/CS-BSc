class Tree:
    def __init__(self, data, branches=[]):
        self.data = data
        self.branches = list(branches)
    def is_a_leaf(self):
        return not self.branches

def plus_it(t):
    final_res = 0
    return plus_it_helper(t, final_res)


def plus_it_helper(t, final_res):
    prev_val = 0
    if t.is_a_leaf():
        return t.data
    for b in t.branches:
        x = plus_it_helper(b, 0)
        if t.data == "+":
            final_res += x
        if t.data == "*":
            final_res = 0
            for i in range(prev_val):
                final_res += x
        if prev_val == 0:
            prev_val = x
        else:
            prev_val = final_res
    return final_res

t1 = Tree('*', [Tree(2), Tree(3)])
print(plus_it(t1), "---6---")
t2 = Tree('+',[Tree(8), Tree(7)])
print(plus_it(t2), "---15---")
t3 = Tree('*',[Tree(5), Tree(7)])
print(plus_it(t3), "---35---")
t4 = Tree('+',[t1, t2, t3])
print(plus_it(t4), "---56---")
t5 = Tree('*', [Tree(11), Tree(12), t4, Tree(13)])
print(plus_it(t5), "---96096---")




# def plus_it_helper(t, curr_res):
#     prev_val = 0
#     if t.is_a_leaf():
#         return t.data
#     for child in t.branches:
#         x = plus_it_helper(child, 0)
#         if t.data == "+":
#             curr_res += x
#         if t.data == "*":
#             curr_res = 0
#             for i in range(prev_val):
#                 curr_res += x
#         if prev_val == 0:
#             prev_val = x
#         else:
#             prev_val = curr_res
#     return curr_res




