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
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 7, 2013 - 2:58:50 PM
 * @since sort-framework v1.0
 */
public class InsertionSort
        extends Sort {

    public static void main(String[] args) {
        int[] a = {2, 1, 5, 0, 5, 4};
        System.out.println(Arrays.toString(a));

        InsertionSort s = new InsertionSort();
        s.reset(a);
        s.sort2();

        System.out.println(Arrays.toString(a));
        System.out.println("Comparisons: " + s.getComparisons());
    }

    @Override
    public String getName() {
        return "InsertionSort";
    }

    @Override
    public void sort() {
        int temp;

        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            int j;
            for (j = i; j > 0 && temp < arr[j - 1]; j--) {
                arr[j] = arr[j - 1];
                notifyCursor(i, j);
            }
            arr[j] = temp;
            notifyCursor(i);
        }
        finish();
    }

    @Deprecated
    public void sort2() {
        for (int i = 0; i < arr.length - 1; i++) {
            if (isGreater(arr[i], arr[i + 1])) {
                swap(i, i + 1);
                for (int j = i; j > 0; j--) {
                    if (isLess(arr[j], arr[j - 1])) {
                        swap(j, j - 1);
                        notifyCursor(i, j);
                    } else {
                        break;
                    }
                }
            }
            notifyCursor(i, -1);
        }
    }
}