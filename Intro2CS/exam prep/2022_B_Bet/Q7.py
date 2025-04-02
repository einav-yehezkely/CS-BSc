class Tree:
    def __init__(self, data, branches=[]):
        self.data = data
        self.branches = list(branches)

    def is_a_leaf(self):
        return not self.branches

    def __repr__(self):
        if self.branches:
            return 'Tree({0}, {1})'.format(self.data, repr(self.branches))
        else:
            return 'Tree({0})'.format(repr(self.data))


# def ops_on(oper , vals):
#     if oper == '+':
#         return sum(vals)
#     elif oper == '*':
#         return mult(vals)
# def mult(vals):
#     res = 1
#     for x in vals:
#         res *= x
#     return res
# def ops_to_leaves(iTree):
#     if iTree.data in ('*', '+'):
#         operands = []
#         for br in iTree.branches:
#             operands.append(ops_to_leaves(br))
#         iTree.branches = [Tree(iTree.data)] + iTree.branches
#         iTree.data = ops_on(iTree.data, operands)
#     return iTree.data











def ops_on(oper, vals):
    if oper == "+":
        return sum(vals)
    elif oper == "*":
        return mult(vals)

def mult(vals):
    res = 1
    for x in vals:
        res *= x
    return res

def ops_to_leaves(iTree):
    if iTree.data in ("*", "+"):
        operands = []
        for b in iTree.branches:
            operands.append(ops_to_leaves(b))
        iTree.branches = [Tree(iTree.data)] + iTree.branches
        iTree.data = ops_on(iTree.data, operands)
    return iTree.data


tree = Tree('*',[Tree('+',[Tree(5),Tree(6)]), Tree('+',[Tree(3),Tree(4)])])
print(tree)
print(ops_to_leaves(tree))
print(tree)
