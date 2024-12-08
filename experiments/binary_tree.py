class BinaryTree:
    def __init__(self, data, left=None, right=None):
        self.data = data
        self.left = left
        self.right = right


def print_inorder(root):
    if root:
        print_inorder(root.left)
        print(root.data, end=" ")
        print_inorder(root.right)


def print_preorder(root):
    if root:
        print(root.data, end=" ")
        print_preorder(root.left)
        print_preorder(root.right)


def print_postorder(root):
    if root:
        print_postorder(root.left)
        print_postorder(root.right)
        print(root.data, end=" ")


def print_levelorder_helper(root, i):
    if i == 0:
        print(root.data, end=" ")
    if root.left:
        print_levelorder_helper(root.left, i - 1)
    if root.right:
        print_levelorder_helper(root.right, i - 1)


def print_levelorder(root, depth):
    for i in range(depth):
        print_levelorder_helper(root, i)

def print_tree(node, level=0, prefix="Root: "):
    """fancy way from chatGPT"""
    if node is not None:
        print(" " * (level * 4) + prefix + str(node.data))
        if node.left is not None or node.right is not None:
            if node.left:
                print_tree(node.left, level + 1, "L--- ")
            else:
                print(" " * ((level + 1) * 4) + "L--- None")
            if node.right:
                print_tree(node.right, level + 1, "R--- ")
            else:
                print(" " * ((level + 1) * 4) + "R--- None")


root = BinaryTree(8, BinaryTree(3, BinaryTree(1), BinaryTree(6, BinaryTree(4), BinaryTree(7))),
                  BinaryTree(10, None, BinaryTree(14, BinaryTree(13))))
print("In-order traversal:", end=" ")
print_inorder(root)# tree_iter(root, 3)
print()
print("Pre-order traversal:", end=" ")
print_preorder(root)
print()
print("Post-order traversal:", end=" ")
print_postorder(root)
print()
print("Level-order traversal:", end=" ")
print_levelorder(root, 3)
print()
print("______________")
print("Tree:")
print_tree(root)