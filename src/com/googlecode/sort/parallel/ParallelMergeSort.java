/*
 * ParallelMergeSort.java
 * Sep 24, 2013 - 7:54:48 PM
 *
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
package com.googlecode.sort.parallel;

import com.googlecode.sort.Sort;
import com.googlecode.sort.util.ForkJoin;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 */
public class ParallelMergeSort
        extends Sort {

    public ParallelMergeSort(int[] arr, BlockingQueue<Integer> notifier) {
        this(arr, notifier, 0, arr.length - 1);
    }

    private ParallelMergeSort(int[] arr, BlockingQueue<Integer> notifier, int low, int high) {
        super(arr, notifier);
        this.helper = new int[arr.length];
        this.low = low;
        this.high = high;
    }

    @Override
    public void run() {
        // Check if low is smaller then high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;

            ForkJoin fj = new ForkJoin();
            // Sort the left side of the array
            fj.add(new ParallelMergeSort(arr, notifier, low, middle));
            // Sort the right side of the array
            fj.add(new ParallelMergeSort(arr, notifier, middle + 1, high));
            try {
                fj.fork();
            } catch (InterruptedException ex) {
                Logger.getLogger(ParallelQuickSort.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Combine them both
            merge(low, middle, high);
        }
    }

    @Override
    public String getName() {
        return "Parallel MergeSort";
    }

    private void merge(int low, int middle, int high) {

        // Copy both parts into the helper array
        for (int i = low; i <= high; i++) {
            helper[i] = arr[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                arr[k] = helper[i];
                i++;
            } else {
                arr[k] = helper[j];
                j++;
            }
            notify(k);
            k++;
        }
        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
            arr[k] = helper[i];
            notify(k);
            k++;
            i++;
        }

    }

    private int[] helper;
    private int low;
    private int high;
}
