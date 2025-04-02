class Node:
    def __init__(self, data, left=None, right=None):
        self.data, self.left, self.right = data, left, right


def tree_iter(root, depth):
    for i in range(depth):
        yield from tree_iter_helper(root, i)


def tree_iter_helper(root, i):
    if i == 0:
        yield root.data
    if root.left:
        yield from tree_iter_helper(root.left, i - 1)
    if root.right:
        yield from tree_iter_helper(root.right, i - 1)


root = Node(1, Node("A", Node(2)), Node("B", Node(3, Node("C")), Node(4)))
print(list(tree_iter(root, 3)))