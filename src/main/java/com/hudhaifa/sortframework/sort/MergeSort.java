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
package com.hudhaifa.sortframework.sort;

import com.hudhaifa.sortframework.core.AbstractSort;

/**
 * This class implements the traditional Merge sort algorithm by dividing the
 * array into two array in each loop iteration. This algorithm offers O(n
 * log(n)) complexity.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 13, 2013 - 10:09:56 PM
 */
public class MergeSort
        extends AbstractSort {

    @Override
    public String getName() {
        return "MergeSort";
    }

    @Override
    public void sort() {
        if (arr.length == 1) {
            return;
        }

        helper = new int[arr.length];
        System.arraycopy(arr, 0, helper, 0, arr.length);
        addUnits(arr.length);

        divide(0, arr.length - 1);
        finish();
    }

    /**
     * Divides the array into two equaled sub arrays.
     *
     * @param left the first index of the array or sub array
     * @param right the last index of the array or sub array
     */
    private void divide(int left, int right) {
        addUnit();
        int length = right - left;

        if (length < 1) {
            return;
        }

        updateCursors(left, right);

        // Calculates the middle of the array.
        int middle = left + length / 2;

        divide(left, middle);
        divide(middle + 1, right);
        merge(left, middle, right);

    }

    /**
     * Merges two unsorted array parts into one sorted array part.
     *
     * @param left the first index of the array or sub array
     * @param middle the middle index of the array or sub array
     * @param right the last index of the array or sub array
     */
    private void merge(int left, int middle, int right) {
        int targetCount = left, leftCount = left, rightCount = middle + 1;

        updateCursors(left, right);

        // Adding the smaller value from each array in each loop.
        while (leftCount <= middle && rightCount <= right) {
            addUnit();
            if (isLess(helper[leftCount], helper[rightCount])) {
                arr[targetCount++] = helper[leftCount++];
                updateCursors(targetCount, leftCount);
            } else {
                arr[targetCount++] = helper[rightCount++];
                updateCursors(targetCount, rightCount);
            }
        }

        // Adding the rest values of the left array
        while (leftCount <= middle) {
            addUnit();
            arr[targetCount++] = helper[leftCount++];
            updateCursors(targetCount, leftCount);
        }

        // Adding the rest values of the right array
        while (rightCount <= right) {
            addUnit();
            arr[targetCount++] = helper[rightCount++];
            updateCursors(targetCount, rightCount);
        }

        for (int i = left; i <= right; i++) {
            addUnit();
            helper[i] = arr[i];
        }
    }

    private int[] helper;
}
