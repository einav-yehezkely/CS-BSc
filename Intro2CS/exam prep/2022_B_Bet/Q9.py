class Tree:
    def __init__(self, data, branches=[]):
        self.data = data
        self.branches = list(branches)
    def is_a_leaf(self):
        return not self.branches

def how_close(tr):
    return helper(tr, [])

def helper(tr, lst):
    # list of all children's data
    lst2 = []
    for branch in tr.branches:
        lst2.append(branch.data)
    # current distance between father and sum of children
    near = abs(tr.data - sum(lst2))
    # go to each branch's children
    for sub in tr.branches:
        lst.append(helper(sub, lst))
    return min([near] + lst)


t1 = Tree(1, [Tree(2), Tree(3)])
print(how_close(t1))
t2 = Tree(7, [Tree(6), Tree(5)])
print(how_close(t2))
t3 = Tree(13, [Tree(8), Tree(2)])
print(how_close(t3))
t4 = Tree(1, [t3])
print(how_close(t4))