class TreeNode:
    def __init__(self, value, left=None, right=None):
        self.value = value
        self.left = left
        self.right = right

def kth_smallest(root, k):
    lst = helper(root, [])
    lst = sorted(lst)
    for i in range(len(lst)):
        if i+1 == k:
            print(lst[i])

def helper(root, lst):
    if root:
        lst.append(root.value)
        helper(root.left, lst)
        helper(root.right, lst)
    return lst


def tree_iter(root):
    if root:
        yield from tree_iter(root.left)
        yield root.value
        yield from tree_iter(root.right)

def another_method(root, k):
    for i, val in enumerate(tree_iter(root), 1):
        if i == k:
            return val


root = TreeNode(8)
root.left = TreeNode(5)
root.right = TreeNode(14)
root.left.left = TreeNode(0)
root.left.right = TreeNode(6)
root.right.right = TreeNode(27)
root.right.right.right = TreeNode(300)

kth_smallest(root, 7)
print(another_method(root, 3))