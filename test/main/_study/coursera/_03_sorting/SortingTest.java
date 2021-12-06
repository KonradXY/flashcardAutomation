package main._study.coursera._03_sorting;

import main._study.coursera._03_sorting_and_searching.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SortingTest {

    private static final Integer[] UNSORTED_ARRAY = new Integer[]{1, 3, 2, 4, 6, 5, 9, 8, 7};
    private static final Integer[] REVERSED_ARRAY = new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
    private static final Integer[] EXPECTED_ARRAY = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    private static final int[] UNSORTED_ARRAY_INT = new int[]{1, 3, 2, 4, 6, 5, 9, 8, 7};
    private static final int[] REVERSED_ARRAY_INT = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
    private static final int[] EXPECTED_ARRAY_INT = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void selectionSort() {
        SelectionSort<Integer> array = new SelectionSort<>(UNSORTED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);
    }

    @Test
    public void selectionSortReversedArray() {
        SelectionSort<Integer> array = new SelectionSort<>(REVERSED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);

    }

    @Test
    public void insertionSort() {
        InsertionSort<Integer> array = new InsertionSort<>(UNSORTED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);

    }

    @Test
    public void insertionSortReversedArray() {
        InsertionSort<Integer> array = new InsertionSort<>(REVERSED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);
    }

    @Test
    public void mergeSort() {
        MergeSort<Integer> array = new MergeSort<>(UNSORTED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);

    }

    @Test
    public void mergeSortReversedArray() {
        MergeSort<Integer> array = new MergeSort<>(REVERSED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);
    }

    @Test
    public void bottomUpMergeSort() {
        BottomUpMergeSort<Integer> array = new BottomUpMergeSort<>(UNSORTED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);

    }

    @Test
    public void bottomUpMergeSortReversedArray() {
        BottomUpMergeSort<Integer> array = new BottomUpMergeSort<>(REVERSED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);
    }

    @Test
    public void quickSort() {
        QuickSort<Integer> array = new QuickSort<>(UNSORTED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);

    }

    @Test
    public void quickSortReversedArray() {
        QuickSort<Integer> array = new QuickSort<>(REVERSED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);
    }
    @Test
    public void threeWayQuickSort() {
        ThreeWayQuickSort<Integer> array = new ThreeWayQuickSort<>(UNSORTED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);

    }
    @Test
    public void threeWayQuickSort2() {
        ThreeWayQuickSort<Integer> array = new ThreeWayQuickSort<>(new Integer[]{0,1,2,0,1,2,0,1,2});
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(new Integer[]{0,0,0,1,1,1,2,2,2});

    }

    @Test
    public void threeWayQuickSortReversedArray() {
        ThreeWayQuickSort<Integer> array = new ThreeWayQuickSort<>(REVERSED_ARRAY);
        assertThat(array.sort()).usingRecursiveFieldByFieldElementComparator().isEqualTo(EXPECTED_ARRAY);
    }

    @Test
    public void quickSelectTest() {
        QuickSelect<Integer> quickSelect = new QuickSelect<>(UNSORTED_ARRAY);
        assertEquals(quickSelect.quickSelect(3), 4); // assert that the fourth smallest element is 4
    }

    @Test
    public void testBinarySearch() {
        BinarySearch search = new BinarySearch();
        int result = search.search(EXPECTED_ARRAY_INT, 5);
        assertEquals(4, result);

    }
    @Test
    public void testBinarySearchNotFound() {
        BinarySearch search = new BinarySearch();
        int result = search.search(EXPECTED_ARRAY_INT, 10);
        assertEquals(-1, result);

    }


}