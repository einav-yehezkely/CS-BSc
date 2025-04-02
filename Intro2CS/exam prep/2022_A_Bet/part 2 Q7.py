from typing import *


def intersect(intervals: List[Tuple[float, float]], points: List[float]) -> bool:
    for point in points:
        for interval in intervals:
            if interval[0] <= point <= interval[1]:
                return True
    return False


print(intersect([(3.5, 4.5), (-1, 2), (3, 4)], [2.5, 5.5]))
print(intersect([(1, 2), (3, 4)], [1]))
print(intersect([(1, 2), (3, 4)], [7, 3.5]))
print(intersect([], []))
print(intersect([(1, 2), (3, 4)], []))