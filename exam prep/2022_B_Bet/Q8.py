class BinT:
    empty = ()
    def __init__(self, data, left=empty, right=empty):
        self.data = data
        self.left = left
        self.right = right

def print_tree(node, level=0, prefix="Root: "):
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


root = BinT(1)
root.left = BinT(2)
root.right = BinT(3)
root.left.left = BinT(4)
root.left.right = BinT(5)

# Print the tree
# print_tree(root)

def pardes(lst, n=0):
    if n >= len(lst) or lst[n] is None:
        return BinT.empty
    left = pardes(lst, 2*n + 1)
    right = pardes(lst, 2*n + 2)
    return BinT(lst[n], left, right)

print_tree(pardes([1,2,3],n=0))