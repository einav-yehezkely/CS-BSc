class TreeNode:
    def __init__(self, val, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


def count_unival_subtrees(root):
    return helper(root, 0)[0]


def helper(root, count):
    if not root.left and not root.right:
        return 1, root.val

    if root.left:
        c, d = helper(root.left, count)
        if d != root.val:
            count += c

    if root.right:
        c, d = helper(root.right, count)
        if d != root.val:
            count += c

    return count, root.val


root = TreeNode(5)
root.left = TreeNode(1)
root.right = TreeNode(7)
root.left.left = TreeNode(5)
root.left.right = TreeNode(5)
root.right.right = TreeNode(7)
print(count_unival_subtrees(root), "---4---")

root1 = TreeNode(5)
root1.left = TreeNode(1)
root1.right = TreeNode(7)
root1.left.left = TreeNode(5)
root1.left.right = TreeNode(5)
root1.right.right = TreeNode(5)
print(count_unival_subtrees(root1), "---3---")
