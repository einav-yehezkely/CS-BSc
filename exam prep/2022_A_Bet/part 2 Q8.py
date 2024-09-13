class Node:
    def __init__(self, data, children):
        self.data = data
        self.children = children

def check_tree(root):
    if not root.children:
        return 0
    l = check_tree(root.children[0])
    if l == None:
        return None
    for i in range(1, len(root.children)):
        r = check_tree(root.children[i])
        if r == None or l != r:
            return None
    return 1 - l

tree = Node('*',[Node('+',[Node(5, []),Node(6, [])]), Node('+',[Node(3, []),Node(4, [])])])
print(check_tree(tree))
tree2 = Node(77, [Node('*', []), Node(11, [Node('+', []), Node(5, []), Node(6, [])]), Node(7, [Node('+', []), Node(3, []), Node(4, [])])])
print(check_tree(tree2))