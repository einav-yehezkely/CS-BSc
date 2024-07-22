class Node:
    def __init__(self, data, next=None, prev=None):
        self.data = data
        self.next = next
        self.prev = prev


class Cycle:
    def __init__(self, data):
        self.head = Node(data)
        self.cur = self.head
        self.cur.next = self.cur
        self.cur.prev = self.cur

    def insert_next(self, data):
        self.cur.next = Node(data, self.cur.next, self.cur)
        self.cur.next.next.prev = self.cur.next

    def rotate(self):
        self.cur = self.cur.next
        return self.cur.data

    def rotate_back(self):
        self.cur = self.cur.prev
        return self.cur.data

    def delete(self):
        if self.cur.next == self.cur:
            raise LookupError
        self.cur.next.prev = self.cur.prev
        self.cur.prev.next = self.cur.next
        self.cur = self.cur.next


c1 = Cycle(3)
c1.insert_next(2)
c1.insert_next(1)
print(c1.rotate())
print(c1.rotate())
print(c1.rotate())
print(c1.rotate())
c1.delete()
print(c1.rotate_back())
