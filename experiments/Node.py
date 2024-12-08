class Node:
    def __init__(self, data, next=None):
        self.data = data
        self.next = next

    def __repr__(self):
        cur = self
        stri = ""
        while cur:
            stri += cur.data
            stri += " -> "
            cur = cur.next
        stri += "end_of_linked_list"
        return stri
        # nodes = []
        # current = self
        # while current is not None:
        #     nodes.append(str(current.num))
        #     current = current.next
        # return "->".join(nodes) + "-> None"


print(Node("1", Node("2", Node("3"))))