/*
 * Copyright (C) 2013 Hudhaifa Shatnawi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.mum.cs.algo.sort;

import java.util.Arrays;

/**
 * This class implements the IQuick sort algorithm which is a combination of quick sort algorithm and
 * insertion sort algorithm based on the array size. This algorithm offers O(n log(n)) complexity but it is
 * typically faster than traditional Quicksort implementations in the big data sets.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 8, 2013 - 9:12:36 AM
 * @since sort-framework v1.0
 */
public class IQuickSort
        extends Sort {

    @Override
    public String getName() {
        return "IQuickSort";
    }

    @Override
    public void sort() {
        chooseAlgorithm(0, arr.length - 1);
        finish();
    }

    /**
     * Sort the array or sub array by quick sort.
     *
     * @param left the start index
     * @param right the end index
     */
    private void quickSort(int left, int right) {
        int i = left;
        int j = right;
        notifyCursor(i, j);

        // Calculate the povit in the middle of the array
        int pivot = arr[left + (right - left) / 2];

        // Numbers which are grater then the pivot moved to the right and numbers which are less than 
        // pivot moved to the right
        while (i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list
            while (arr[i] < pivot) {
                i++;
            }

            // If the current value from the right list is larger then the pivot
            // element then get the next element from the right list
            while (arr[j] > pivot) {
                j--;
            }

            // If we have found a values in the left list which is larger then
            // the pivot element and if we have found a value in the right list
            // which is smaller then the pivot element then we swap the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
                notifyPause();
            }
        }

        // Recursion
        if (left < j) {
            chooseAlgorithm(left, j);
        }
        if (i < right) {
            chooseAlgorithm(i, right);
        }
    }

    /**
     * Sort the array or sub array by insertion sort.
     *
     * @param left the start index
     * @param right the end index
     */
    private void insertionSort(int left, int right) {
        int temp;

        for (int i = left + 1; i <= right; i++) {
            temp = arr[i];
            int j;
            for (j = i; j > 0 && isLess(temp, arr[j - 1]); j--) {
                swap(j, j - 1);
                notifyCursor(i, j);
            }
            arr[j] = temp;
            notifyCursor(i);
        }
    }

    /**
     * Choose the convenient algorithm based on the array or sub array length.
     *
     * @param left the start index
     * @param right the end index
     */
    private void chooseAlgorithm(int left, int right) {
        // Use the Insertion sort
        if ((right - left) <= INSERTION_THRESHOLD) {
            insertionSort(left, right);
        } else {
            quickSort(left, right);
        }
    }

    /**
     * If the length of an array or sub array is less than the threshold, Insertion sort is used rather than
     * Quick sort.
     */
    private static final int INSERTION_THRESHOLD = 45;
}
