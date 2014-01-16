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
 * This class implements the Insertion sort algorithm by inserting each element in its convenient place in the
 * array. This algorithm offers O(n^2) complexity.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 7, 2013 - 2:58:50 PM
 * @since sort-framework v1.0
 */
public class InsertionSort
        extends Sort {

    @Override
    public String getName() {
        return "InsertionSort";
    }

    /**
     * The Iteration implement of the Insertion sort algorithm.
     */
    @Override
    public void sort() {
        int temp;

        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            int j;
            for (j = i; j > 0 && isLess(temp, arr[j - 1]); j--) {
                swap(j, j - 1);
                notifyCursor(i, j);
            }
            arr[j] = temp;
            notifyCursor(i);
        }
        finish();
    }

    /**
     * The Recursive implement of the Insertion sort algorithm.
     *
     * @param size the size of the sub array.
     */
    void sort(int size) {
        if (size > 1) {
            sort(size - 1);
            insert(arr[size - 1], size - 1);
        }
    }

    /**
     * Finds the convenient position for the element in the array.
     *
     * @param m the element's index to be inserted in the convenient position.
     * @param size
     */
    private void insert(int m, int size) {
        int j = 0;
        for (; j < size; j++) {
            if (isGreater(arr[j], m)) {
                break;
            }
        }

        for (int k = size - 1; k >= j; k--) {
            swap(k + 1, k);
        }
        arr[j] = m;
    }
}
