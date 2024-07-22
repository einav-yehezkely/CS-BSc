from typing import *

def min_time_helper(num_workers, tasks, count):
    
    return count


def min_time(num_workers: int, tasks: List[float]) -> float:
    tasks.sort(reverse=True)
    return min_time_helper(num_workers, tasks, tasks[0])


tasks = [4, 2, 2.5, 2, 3, 5]
print(min_time(4, tasks))
