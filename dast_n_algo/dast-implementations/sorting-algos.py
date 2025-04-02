import unittest
import random


class SortingAlgorithms:
    """
    Collection of sorting algorithms.

    Complexity Analysis:
    - Bubble Sort: O(n^2) worst-case, O(n) best-case.
    - Merge Sort: O(n log n) for all cases.
    - Insertion Sort: O(n^2) worst-case, O(n) best-case.
    - Quick Sort: O(n log n) average-case, O(n^2) worst-case.
    - Counting Sort: O(n + k) where k is the range of numbers.
    - Radix Sort: O(nk) where k is the number of digits.
    - Bucket Sort: O(n^2) worst-case, O(n) best-case.
    - Heap Sort: O(n log n) for all cases.
    """

    def bubble_sort(arr):
        """
        Bubble Sort Algorithm
        ---------------------
        Repeatedly swaps adjacent elements if they are in the wrong order.

        Hint 1: Use nested loops.
        Hint 2: Optimize by stopping early if no swaps occur in a pass.
        """
        swap = False
        for i in range(len(arr) - 1):
            for j in range(len(arr) - 1):
                if arr[j] > arr[j + 1]:
                    arr[j], arr[j + 1] = arr[j + 1], arr[j]
                    swap = True
            if not swap:
                break
        return arr

    def merge_sort(arr):
        """
        Merge Sort Algorithm
        ---------------------
        Divides array into halves, sorts each half, and merges the sorted halves.

        Hint 1: Use recursion.
        Hint 2: Implement a helper function to merge two sorted lists.
        """
        if len(arr) <= 1:
            return arr
        left = []
        right = []
        for i in range(len(arr)):
            if i < len(arr) / 2:
                left.append(arr[i])
            else:
                right.append(arr[i])
        left = SortingAlgorithms.merge_sort(left)
        right = SortingAlgorithms.merge_sort(right)
        return SortingAlgorithms.merge(left, right)

    def merge(left, right):
        result = []
        while left and right:
            if left[0] <= right[0]:
                result.append(left[0])
                left = left[1:]
            else:
                result.append(right[0])
                right = right[1:]
        while left:
            result.append(left[0])
            left = left[1:]
        while right:
            result.append(right[0])
            right = right[1:]
        return result

    def insertion_sort(arr):
        """
        Insertion Sort Algorithm
        -------------------------
        Builds the sorted array one element at a time by shifting elements.

        Hint 1: Iterate through the array and insert elements in the correct position.
        Hint 2: Use a while loop to shift elements in-place.
        """
        for i in range(1, len(arr)):
            key = arr[i]
            j = i - 1
            while j >= 0 and arr[j] > key:
                arr[j + 1] = arr[j]
                j -= 1
            arr[j + 1] = key
        return arr

    def quick_sort(arr):
        """
        Quick Sort Algorithm
        ---------------------
        Selects a pivot and partitions the array into elements smaller and larger than the pivot.

        Hint 1: Choose a pivot (e.g., middle element).
        Hint 2: Use recursion to sort left and right partitions.

        https://youtu.be/WprjBK0p6rw?si=0f3cwtoSjtuCnNYN
        """
        left = 0
        right = len(arr) - 1
        return SortingAlgorithms.randomized_quick_sort(arr, left, right)

    def randomized_quick_sort(arr, left, right):
        if left < right:
            pivot = SortingAlgorithms.randomized_partition(arr, left, right)
            SortingAlgorithms.randomized_quick_sort(arr, left, pivot - 1)
            SortingAlgorithms.randomized_quick_sort(arr, pivot + 1, right)
        return arr

    def randomized_partition(arr, left, right):
        pivot = random.randint(left, right)
        arr[pivot], arr[right] = arr[right], arr[pivot]
        i = left - 1
        for j in range(left, right):
            if arr[j] <= arr[right]:
                i += 1
                arr[i], arr[j] = arr[j], arr[i]
        arr[i + 1], arr[right] = arr[right], arr[i + 1]
        return i + 1

    def counting_sort(arr):
        """
        Counting Sort Algorithm
        ------------------------
        Uses counting array to determine the positions of elements in the sorted array.

        Hint 1: Works best when range of numbers is small.
        Hint 2: Use an auxiliary count array to store frequency of elements.
        """
        k = max(arr)
        B = [0] * len(arr)
        C = [0] * (k + 1)
        for j in range(len(arr)):
            C[arr[j]] += 1
        for i in range(1, k + 1):
            C[i] += C[i - 1]
        for j in range(len(arr) - 1, -1, -1):
            B[C[arr[j]] - 1] = arr[j]
            C[arr[j]] -= 1
        return B

    def radix_sort(arr):
        """
        Radix Sort Algorithm
        ---------------------
        Sorts numbers digit by digit from least significant to most significant.

        Hint 1: Use Counting Sort as a subroutine.
        Hint 2: Iterate through digits from least to most significant.
        """
        pass

    def bucket_sort(arr):
        """
        Bucket Sort Algorithm
        ----------------------
        Divides elements into buckets, sorts each bucket, and merges them.

        Hint 1: Distribute elements into buckets based on range.
        Hint 2: Sort each bucket individually (e.g., using Insertion Sort).
        """
        pass

    def heap_sort(arr):
        """
        Heap Sort Algorithm
        --------------------
        Builds a max heap, repeatedly extracts max element, and re-heapifies.

        Hint 1: Convert the array into a max heap first.
        Hint 2: Use heapify operations to maintain heap property.
        """
        pass


class TestSortingAlgorithms(unittest.TestCase):
    """Unit tests for sorting algorithms."""

    def test_bubble_sort(self):
        self.assertEqual(SortingAlgorithms.bubble_sort([3, 1, 2]), [1, 2, 3])

    # print(SortingAlgorithms.bubble_sort([1, 2, 3]))

    def test_merge_sort(self):
        self.assertEqual(SortingAlgorithms.merge_sort([5, 3, 8, 6, 2]), [2, 3, 5, 6, 8])

    def test_insertion_sort(self):
        self.assertEqual(SortingAlgorithms.insertion_sort([4, 1, 7, 3]), [1, 3, 4, 7])

    def test_quick_sort(self):
        self.assertEqual(SortingAlgorithms.quick_sort([9, 7, 5, 11]), [5, 7, 9, 11])

    def test_counting_sort(self):
        self.assertEqual(
            SortingAlgorithms.counting_sort([4, 2, 2, 8, 3, 3, 1]),
            [1, 2, 2, 3, 3, 4, 8],
        )

    # def test_radix_sort(self):
    #     self.assertEqual(
    #         SortingAlgorithms.radix_sort([170, 45, 75, 90, 802, 24, 2, 66]),
    #         [2, 24, 45, 66, 75, 90, 170, 802],
    #     )

    # def test_bucket_sort(self):
    #     self.assertEqual(
    #         SortingAlgorithms.bucket_sort(
    #             [0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12]
    #         ),
    #         [0.12, 0.17, 0.21, 0.26, 0.39, 0.72, 0.78, 0.94],
    #     )

    # def test_heap_sort(self):
    #     self.assertEqual(
    #         SortingAlgorithms.heap_sort([12, 11, 13, 5, 6, 7]), [5, 6, 7, 11, 12, 13]
    #     )


if __name__ == "__main__":
    unittest.main()
