class Node:
    def __init__(self, data, next=None):
        self.data = data
        self.next = next
    def __repr__(self):
        return repr(self.data) + "->" + repr(self.next)

def split_sorted(head):
    sorted_list = []
    cur = head
    while head and head.next:
        if head.data <= head.next.data:
            head = head.next
        else:
            sorted_list.append(cur)
            cur = head.next
            head.next = None
            head = cur
    sorted_list.append(cur)
    return sorted_list


my_node = Node(1, Node(2, Node(3, Node(2, Node(1,Node(10))))))
print(split_sorted(my_node), "---[1->2->3->None, 2->None, 1->10->None]---")
my_node2 = Node(1, Node(2, Node(3, Node(4))))
print(split_sorted(my_node2), "---[1->2->3->4->None]---")
my_node3 = Node(10, Node(9, Node(8, Node(3, Node(1)))))
print(split_sorted(my_node3), "---[10->None, 9->None, 8->None, 3->None, 1->None]---")