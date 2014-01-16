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

/**
 * This class implements the traditional Quick sort algorithm by dividing the array into two parts based on
 * calculating single pivot in each loop iteration. This algorithm offers O(n log(n)) complexity.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Sep 24, 2013 - 5:51:57 PM
 * @since sort-framework v1.0
 */
public class QuickSort
        extends Sort {

    @Override
    public void sort() {
        quickSort(0, arr.length - 1);
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
        int length = right - left;

        if (length <= 1) {
            return;
        }

        notifyCursor(i, j);

        // Calculate the povit in the middle of the array
        int pivot = arr[left + length / 2];

        // Numbers which are grater then the pivot moved to the right and numbers which are less than 
        // pivot moved to the right
        while (i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list
            while (isLess(arr[i], pivot)) {
                i++;
            }

            // If the current value from the right list is larger then the pivot
            // element then get the next element from the right list
            while (isGreater(arr[j], pivot)) {
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
            quickSort(left, j);
        }

        if (i < right) {
            quickSort(i, right);
        }
    }

    @Override
    public String getName() {
        return "QuickSort";
    }
}
