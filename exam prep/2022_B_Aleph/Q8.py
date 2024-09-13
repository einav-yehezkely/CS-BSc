class Link:
    empty = ()

    def __init__(self, data, next=empty):
        self.data = data
        self.next = next

    def __repr__(self):
        if self.next is Link.empty:
            return "Link({})".format(self.data)
        else:
            return "Link({}, {})".format(self.data, self.next)


def de_comp(n, base_lst):
    if n == 0:
        return Link.empty
    elif base_lst == Link.empty:
        raise ArithmeticError
    elif base_lst.data > n:
        return de_comp(n, base_lst.next)
    else:
        return Link(base_lst.data, de_comp(n - base_lst.data,base_lst.next))


twos = Link(32, Link(16, Link(8, Link(5, Link(4, Link(1))))))
fibos = Link(34, (Link(21, Link(13, Link(8, Link(5, Link(3, Link(2, Link(1)))))))))

print(de_comp(9, twos), "---Link(8, Link(1))---")
print(de_comp(45, twos), "---Link(32, Link(8, Link(4, Link(1))))---")
try:
    print(de_comp(71, twos), "---ArithmeticError: ...---")
except:
    print("no")
print(de_comp(9, fibos), "---Link(8, Link(1))---")
print(de_comp(30, fibos), "---Link(21, Link(8, Link(1)))---")
print(de_comp(41, fibos), "---Link(34, Link(5, Link(2)))---")
print(de_comp(0, fibos), "---()---")
