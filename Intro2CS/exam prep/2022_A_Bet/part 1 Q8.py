class Node:
    def __init__(self, data, next=None):
        self.data = data
        self.next = next

    # def __str__(self):
    #     if self.data is None:
    #         return
    #     str = ""
    #     while self.data:
    #         str += f"{self.data} ->" if self.next else ""
    #         print(self.data, end=" -> " if self.next else "")
    #         self.data = self.next
    #     print()

        # print(self.data, self.next)

def find_max_sum_pair(head: Node) -> float:
    lst = []
    start = head
    cur = head
    last = None
    alert = 0
    while start != last:

        while cur.next is not last:
            if cur is last:
                alert = 1
                break
            cur = cur.next
        if alert == 1:
            lst.append(start.data*2)
            break

        last = cur
        lst.append(start.data + last.data)
        start = start.next
        cur = start.next

    return max(lst)



linked_list = Node(7, Node(1, (Node(2, Node(4, Node(-3, Node(-2)))))))
# print(linked_list)
print(find_max_sum_pair(linked_list))
