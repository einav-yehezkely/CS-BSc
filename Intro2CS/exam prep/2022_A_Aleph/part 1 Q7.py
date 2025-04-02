def unify(intervals):
    intervals.sort()
    updated = []
    left, right = intervals[0]
    for n_left, n_right in intervals[1:]:
        if n_left <= right:
            right = max(right, n_right)
        else:
            updated.append((left, right))
            left, right = n_left, n_right
    updated.append((left, right))
    return updated


intervals = [(2.2, 5), (7.1, 9), (1, 4.2)]
print(unify(intervals))
