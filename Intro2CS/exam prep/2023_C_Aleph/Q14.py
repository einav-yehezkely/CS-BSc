class TreeNode:
    def __init__(self, val, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

def p_tr(root):
    if root:
        print("     val:", root.val)
        if root.left:
            print("left:")
            p_tr(root.left)
        if root.right:
            print("             right:")
            p_tr(root.right)

def unbalance_tree(root):
    if root.left is None and root.right is None:
        return root
    tail = root
    if root.left:
        tail = unbalance_tree(root.left)
    tail.left = root.right
    if root.right:
        tail = unbalance_tree(root.right)
    root.right = None

root = TreeNode(1)
root.left = TreeNode(2)
root.right = TreeNode(3)
root.right.right = TreeNode(4)
unbalance_tree(root)
print(root.val, "--1--")
# p_tr(root)