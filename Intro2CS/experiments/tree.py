class Node:
    def __init__(self, data, next):
        self.data = data
        self.next = next

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
    """general method for trees - go to every child"""
    for b in tree.branches:
        print(b.data)
        print_tree(b)

tree = Tree('*',[Tree('+',[Tree(5),Tree(0), Tree(6)]), Tree('+',[Tree(3),Tree(4)])])
print(tree)
print_tree(tree)