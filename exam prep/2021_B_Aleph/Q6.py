class Campus:
    def __init__(self):
        self.classmates = {}

    def add_classmate(self, st1, st2):
        if st1 not in self.classmates:
            self.classmates[st1] = set()
        if st2 not in self.classmates:
            self.classmates[st2] = set()
        self.classmates[st1].add(st2)
        self.classmates[st2].add(st1)

    def soc_dist(self, st1, st2, n):
        if st1 == st2:
            return True
        if (st1 not in self.classmates.keys() and st1 not in self.classmates.values()) or (st2 not in self.classmates.keys() and st2 not in self.classmates.values()):
            return False
        for key, val in self.classmates.items():
            pass


camp = Campus()
camp.add_classmate("avi", "hey")
print(camp.classmates)
print(camp.soc_dist(2, "avi", 5))