class Node:
    def __init__(self, data=None, next=None):
        self.data = data
        self.next = next
    def __repr__(self):
        stri = ""
        cur = self
        while cur:
            stri += str(cur.data)
            stri += " -> "
            cur = cur.next
        stri += "None"
        return stri

def linked_filter(f, lnk):
    if lnk is None:
        return lnk
    filtered_next = linked_filter(f, lnk.next)

    if f(lnk.data):
        return Node(lnk.data, filtered_next)

    else:
        return filtered_next


print(linked_filter(lambda x: (x+1)%2,Node(2,Node(3,Node(4,Node(5, Node(6)))))), "  ---Node(2, Node(4, Node(6)))---")
print(Node(2,Node(3,Node(4,Node(5, Node(6))))))