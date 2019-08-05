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
package com.hudhaifa.sortframework.parallel;

import com.hudhaifa.sortframework.core.AbstractSort;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * This class implements a parallel version of the Merge sort algorithm which is
 * a fork each task to work in different process. This algorithm offers O(n
 * log(n)) complexity in a single processor machine but it is typically faster
 * than traditional Merge sort implementations in the big data sets and multi
 * processor machines.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 13, 2013 - 10:45:54 PM
 * @since sort-framework v1.0
 */
public class ParallelMergeSort
        extends AbstractSort {

    @Override
    public String getName() {
        return "Parallel MergeSort";
    }

    @Override
    public void sort() {
        if (arr.length == 1) {
            return;
        }

        helper = new int[arr.length];
        System.arraycopy(arr, 0, helper, 0, arr.length);
        addUnits(arr.length);

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ParallelDivide(0, arr.length - 1));

        finish();
    }

    /**
     *
     */
    private class ParallelDivide
            extends RecursiveAction {

        public ParallelDivide(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            divide(left, right);
        }

        /**
         * Divides the array into two equaled sub arrays.
         *
         * @param left the first index of the array or sub array
         * @param right the last index of the array or sub array
         */
        private void divide(int left, int right) {
            int length = right - left;

            if (length < 1) {
                return;
            }

            updateCursors(left, right);

            // Calculates the middle of the array.
            int middle = left + length / 2;

            if (length <= SINGLETHREAD_THRESHOLD) {
                divide(left, middle);
                divide(middle + 1, right);
            } else {
                invokeAll(new ParallelDivide(left, middle), new ParallelDivide(middle + 1, right));
            }

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

        private final int left;
        private final int right;
    }

    private int[] helper;
    /**
     * If the length of an array or sub array is less than the threshold,
     * Insertion sort is used rather than Merge sort.
     */
    private static final int SINGLETHREAD_THRESHOLD = 45;
}
