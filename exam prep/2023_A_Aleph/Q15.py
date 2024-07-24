from typing import *


def assign(n: int, k: int, hate: Set[Tuple[int, int]]) -> Optional[List[List[int]]]:
    lst = []
    print(n)
    for i in range(k):
        lst.append([])

    if n <= k:
        for i in range(n):
            lst[i].append(i + 1)
        return lst

    return assign_helper(0, n, 0, k, hate, lst)


def is_legal(i, hate, lst) -> bool:
    for p in lst:
        if (i, p) in hate or (p, i) in hate:
            return False
    return True


def assign_helper(cur_person: int, num_of_people: int, cur_table: int, num_of_tables: int, hate: Set[Tuple[int, int]],
                  lst):
    if cur_person >= num_of_people:
        return lst

    for table in range(num_of_tables):
        if is_legal(cur_person + 1, hate, lst[table]):
            lst[table].append(cur_person + 1)
            result = assign_helper(cur_person + 1, num_of_people, cur_table, num_of_tables, hate, lst)
            if result:
                return result
            lst[table].pop()
    return None


hate1 = {(1, 2), (1, 3)}
hate2 = {(1, 2), (1, 3), (2, 3)}
# print(is_in_hate(3,2, hate2))
print(assign(4, 2, hate2))
