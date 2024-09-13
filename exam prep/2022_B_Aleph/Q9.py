def max_sum(liz1, liz2, liz3, lim):
    liz1.sort()
    liz2.sort()
    liz3.sort()
    cur_max = 0
    for i in liz1:
        if i > lim:
            break
        for j in liz2:
            if j + i > lim:
                break
            for k in liz3:
                if k + j + i > lim:
                    break
                s = k + j + i
                if s >= cur_max:
                    cur_max = s
    return cur_max


liz1 = [9, 8, 7]
liz2 = [2, 5, 1]
liz3 = [6]
liz4 = [6, 2]
liz5 = [0]
print(max_sum(liz1, liz2, liz3, 30), "  ---20---")
print(max_sum(liz1, liz2, liz3, 20), "  ---20---")
print(max_sum(liz3, liz4, liz5, 13), "  ---12---")
print(max_sum(liz3, liz4, liz5, 7), "   ---0---")