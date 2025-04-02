class Lnk:
    empty = ()
    def __init__(self, data, next = empty):
        self.data = data
        self.next = next
    def __repr__(self):
        if self.next is Lnk.empty:
            return "Lnk({})".format(self.data)
        else:
            return "Lnk({}, {})".format(self.data, self.next)

def place_between(inter, lnk_lst):
    if lnk_lst is Lnk.empty:
        return Lnk.empty

    head = Lnk(lnk_lst.data)
    cur = head
    lnk_lst = lnk_lst.next

    while lnk_lst is not Lnk.empty:
        if cur.data == lnk_lst.data:
            cur.next = Lnk(inter)
            cur = cur.next
        cur.next = Lnk(lnk_lst.data)
        cur = cur.next
        lnk_lst = lnk_lst.next

    return head

def place_between_recursive(inter, lnk_lst):
    if lnk_lst is Lnk.empty:
        return Lnk.empty
    elif lnk_lst.next is Lnk.empty:
        return Lnk(lnk_lst.data)
    elif lnk_lst.data == lnk_lst.next.data:
        return Lnk(lnk_lst.data, Lnk(inter, place_between_recursive(inter, lnk_lst.next)))
    return Lnk(lnk_lst.data, place_between(inter, lnk_lst.next))

print("---my solution---")
print(place_between(-7, Lnk(3, Lnk(3))), "  ---Lnk(3, Lnk(-7, Lnk(3)))---")
print(place_between(3, Lnk(3, Lnk(3))), "   ---Lnk(3, Lnk(3, Lnk(3)))---")
print(place_between(-7, Lnk(-7, Lnk(3, Lnk(3)))), "     ---Lnk(-7, Lnk(3, Lnk(-7, Lnk(3))))---")
print(place_between(11, Lnk(7, Lnk(7,Lnk(6,Lnk(6,Lnk(11)))))), "    ---Lnk(7, Lnk(11, Lnk(7, Lnk(6, Lnk(11, Lnk(6, Lnk(11)))))))---")
print(place_between(10, Lnk(9, Lnk(9, Lnk(9)))), "   ---Lnk(9, Lnk(10, Lnk(9, Lnk(10, Lnk(9)))))---")
ling = Lnk(11, Lnk(21))
print(place_between(5, ling), "---Lnk(11, Lnk(21))---")
print("---recursive solution---")
print(place_between_recursive(-7, Lnk(3, Lnk(3))), "  ---Lnk(3, Lnk(-7, Lnk(3)))---")
print(place_between_recursive(3, Lnk(3, Lnk(3))), "   ---Lnk(3, Lnk(3, Lnk(3)))---")
print(place_between_recursive(-7, Lnk(-7, Lnk(3, Lnk(3)))), "     ---Lnk(-7, Lnk(3, Lnk(-7, Lnk(3))))---")
print(place_between_recursive(11, Lnk(7, Lnk(7,Lnk(6,Lnk(6,Lnk(11)))))), "    ---Lnk(7, Lnk(11, Lnk(7, Lnk(6, Lnk(11, Lnk(6, Lnk(11)))))))---")
print(place_between_recursive(10, Lnk(9, Lnk(9, Lnk(9)))), "   ---Lnk(9, Lnk(10, Lnk(9, Lnk(10, Lnk(9)))))---")
ling = Lnk(11, Lnk(21))
print(place_between_recursive(5, ling), "---Lnk(11, Lnk(21))---")