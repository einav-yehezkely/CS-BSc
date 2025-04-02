from math import gcd
def lcm(a, b): return abs(a // gcd(a, b) * b)


class Frac:
    def __init__(self, mone, mahane):
        self.mone = mone
        if mahane == 0:
            raise ZeroDivisionError
        self.mahane = mahane
        self._reduce()

    def _reduce(self):
        divider = gcd(self.mone, self.mahane)
        self.mone = self.mone // divider
        self.mahane = self.mahane // divider

    def __add__(self, other):
        m = lcm(self.mahane, other.mahane)
        pass

    def __sub__(self, other):
        pass
