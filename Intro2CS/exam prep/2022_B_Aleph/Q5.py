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
def print_tree(tree):
    print(tree.data)
    for b in tree.branches:
        # print("[",end='')
        print_tree(b)
    print("]")

def monotonic(t):
    if t.is_a_leaf():
        return True
    for b in t.branches:
        if t.data > b.data or not monotonic(b):
            return False
    return True


not_monotonic = Tree(2, [Tree(1, [Tree(6), Tree(3, [Tree(4)]), Tree(5), Tree(7, [Tree(7)])])])
do_monotonic = Tree(2, [Tree(3, [Tree(6), Tree(3, [Tree(4)]), Tree(5), Tree(7, [Tree(7)])])])
print(monotonic(not_monotonic))
print(monotonic(do_monotonic))