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
package edu.mum.cs.parallel;

import edu.mum.cs.algo.sort.Sort;
import edu.mum.cs.util.concurrent.ForkJoin;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements a parallel version of the Merge sort algorithm which is a fork each task to work in
 * different process. This algorithm offers O(n log(n)) complexity in a single processor machine but it is
 * typically faster than traditional Merge sort implementations in the big data sets and multi processor
 * machines.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 13, 2013 - 10:45:54 PM
 */
public class ParallelMergeSort
        extends Sort {

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

        locker = new ReentrantLock();

        Thread t = new Thread(new ParallelDivide(0, arr.length - 1));
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ParallelMergeSort.class.getName()).log(Level.SEVERE, null, ex);
        }

        finish();
    }

    /**
     *
     */
    private class ParallelDivide
            implements Runnable {

        public ParallelDivide(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
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

            notifyCursor(left, right);

            // Calculates the middle of the array.
            int middle = left + length / 2;

            if (length <= SINGLETHREAD_THRESHOLD) {
                divide(left, middle);
                divide(middle + 1, right);
            } else {
                // Add both
                fj = new ForkJoin();
                fj.add(new ParallelDivide(left, middle));
                fj.add(new ParallelDivide(middle + 1, right));
                try {
                    fj.fork();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ParallelMergeSort.class.getName()).log(Level.SEVERE, null, ex);
                }
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

            notifyCursor(left, right);

            // Adding the smaller value from each array in each loop.
            while (leftCount <= middle && rightCount <= right) {
                if (isLess(helper[leftCount], helper[rightCount])) {
                    locker.lock();
                    try {
                        arr[targetCount++] = helper[leftCount++];
                    } finally {
                        locker.unlock();
                    }
                    notifyCursor(targetCount, leftCount);
                } else {
                    locker.lock();
                    try {
                        arr[targetCount++] = helper[rightCount++];
                    } finally {
                        locker.unlock();
                    }
                    notifyCursor(targetCount, rightCount);
                }
            }

            // Adding the rest values of the left array
            while (leftCount <= middle) {
                locker.lock();
                try {
                    arr[targetCount++] = helper[leftCount++];
                } finally {
                    locker.unlock();
                }
                notifyCursor(targetCount, leftCount);
            }

            // Adding the rest values of the right array
            while (rightCount <= right) {
                locker.lock();
                try {
                    arr[targetCount++] = helper[rightCount++];
                } finally {
                    locker.unlock();
                }
                notifyCursor(targetCount, rightCount);
            }

            System.out.println("Merge: " + Arrays.toString(arr));
            locker.lock();
            try {
                for (int i = left; i <= right; i++) {
                    helper[i] = arr[i];
                }
            } finally {
                locker.unlock();
            }
        }

        private final int left;
        private final int right;
        private ForkJoin fj;
    }

    private int[] helper;
    private Lock locker;
    /**
     * If the length of an array or sub array is less than the threshold, Insertion sort is used rather than
     * Merge sort.
     */
    private static final int SINGLETHREAD_THRESHOLD = 10;
}
