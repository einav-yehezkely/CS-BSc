class Node:
    def __init__(self, data, rep, next):
        self.data = data
        self.rep = rep
        self.next = next


def pr_lnk(node):
    if not node:
        print("None")
        return
    current = node
    while current:
        print(current.data, current.rep, end=" -> " if current.next else "")
        current = current.next
    print()


def compress(head: Node):
    while head.next:
        if head.data == head.next.data:
            head.rep += head.next.rep
            head.next = head.next.next
        else:
            head = head.next


linked_list = Node("c", 2, Node("c", 5, Node("a", 3, (Node("a", 1, Node("a", 15, None))))))
pr_lnk(linked_list)
compress(linked_list)
pr_lnk(linked_list)
