class Item:
    all_items = dict()
    def __init__(self, name, price, sale_price=None):
        self.name = name
        self.price = price
        self.sale_price = sale_price

        self.all_items[name] = price

class ShoppingCart():
    def __init__(self):
        self.cart = []

    def add_item(self, name:str, quantity:int):
        if name not in Item.all_items.keys():
            raise ValueError("doesn't exist")
        if name in self.cart:
            raise ValueError("already in cart")
        else:
            self.cart.append((name, quantity))


it = Item("Apple", 4)
it2 = Item("banana", 2)
print(it.all_items)
cart = ShoppingCart()
cart.add_item("banana", 2)
print(cart.cart)

