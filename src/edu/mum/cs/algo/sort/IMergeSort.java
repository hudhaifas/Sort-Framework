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
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 13, 2013 - 10:34:20 PM
 */
public class IMergeSort
        extends Sort {

    @Override
    public String getName() {
        return "IMergeSort";
    }

    @Override
    public void sort() {
        chooseAlgorithm(arr);
    }

    /**
     *
     * @param src
     */
    private void divide(int[] src) {
        if (src.length == 1) {
            return;
        }

        int middle = src.length / 2;

        int[] left = new int[middle];
        System.arraycopy(src, 0, left, 0, left.length);

        int[] right = new int[src.length - middle];
        System.arraycopy(src, middle, right, 0, right.length);

        chooseAlgorithm(left);
        chooseAlgorithm(right);

        merge(src, left, right);
    }

    /**
     *
     * @param target
     * @param left
     * @param right
     */
    private void merge(int[] target, int[] left, int[] right) {
        int targetCount = 0, leftCount = 0, rightCount = 0;

        while (leftCount < left.length && rightCount < right.length) {
            if (isLess(left[leftCount], right[rightCount])) {
                target[targetCount++] = left[leftCount++];
            } else {
                target[targetCount++] = right[rightCount++];
            }
        }

        while (leftCount < left.length) {
            target[targetCount++] = left[leftCount++];
        }

        while (rightCount < right.length) {
            target[targetCount++] = right[rightCount++];
        }
    }

    private void chooseAlgorithm(int[] src) {
        if (src.length <= 45) {
            insertionSort(src);
        } else {
            divide(src);
        }
    }

    private void insertionSort(int[] src) {
        int temp;

        for (int i = 1; i < src.length; i++) {
            temp = src[i];
            int j;
            for (j = i; j > 0 && isLess(temp, src[j - 1]); j--) {
                swap(src, j, j - 1);
                notifyCursor(i, j);
            }
            src[j] = temp;
            notifyCursor(i);
        }
    }
}
