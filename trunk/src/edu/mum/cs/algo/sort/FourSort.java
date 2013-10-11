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
 * Implements an algorithm that explains how to sort array of size 4 using ONLY 5 comparisons operations.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 8, 2013 - 9:32:46 PM
 */
public class FourSort
        extends Sort {

    @Override
    public String getName() {
        return "FourSort";
    }

    @Override
    public void sort() {
        // Divide
        int[] a1 = {arr[0], arr[1]};
        int[] a2 = {arr[2], arr[3]};

        // Sort
        if (isGreater(a1[0], a1[1])) {
            swap(a1, 0, 1);
        }

        if (isGreater(a2[0], a2[1])) {
            swap(a2, 0, 1);
        }

        // Merge
        int fuzzy1, fuzzy2;
        if (isLess(a1[0], a2[0])) {
            arr[0] = a1[0];
            fuzzy1 = a2[0];
        } else {
            arr[0] = a2[0];
            fuzzy1 = a1[0];
        }

        if (isGreater(a1[1], a2[1])) {
            arr[3] = a1[1];
            fuzzy2 = a2[1];
        } else {
            arr[3] = a2[1];
            fuzzy2 = a1[1];
        }

        if (isLess(fuzzy1, fuzzy2)) {
            arr[1] = fuzzy1;
            arr[2] = fuzzy2;
        } else {
            arr[1] = fuzzy2;
            arr[2] = fuzzy1;
        }
    }

    protected void swap(int[] a, int i, int j) {
        swaps++;
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
