class Node:
    def __init__(self, data, left=None, right=None):
        self.data, self.left, self.right = data, left, right


def is_legal_BST(root):
    if root.left:
        if root.data > root.left.data:
            return is_legal_BST(root.left)
        return False
    if root.right:
        if root.data < root.right.data:
            return is_legal_BST(root.right)
        return False
    return True


illegal_BST = Node(3, Node(8), Node(7))
legal_BST = Node(3, Node(2), Node(7))
illegal_BST_2 = Node(8, Node(3, Node(1), Node(6, Node(4), Node(7))), Node(10, None, Node(14, Node(15))))
legal_BST_2 = Node(8, Node(3, Node(1), Node(6, Node(4), Node(7))), Node(10, None, Node(14, Node(13))))

root = Node(5)
root.left = Node(3)
root.right = Node(8)
root.left.left = Node(1)
root.left.right = Node(4)
root.right.right = Node(10)
print(is_legal_BST(root))
