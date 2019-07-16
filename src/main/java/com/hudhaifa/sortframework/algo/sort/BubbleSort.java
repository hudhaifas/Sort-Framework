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
package com.hudhaifa.sortframework.algo.sort;

/**
 * This class implements the Bubble sort algorithm which starts finding the largest value and moving it to the
 * end of the array. This algorithm offers O(n^2) complexity.
 *
 * The Bubble sort is the opposite of the Shuffle sort, it starts sorting the largest value.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Sep 24, 2013 - 5:44:14 PM
 * @since sort-framework v1.0
 */
public class BubbleSort
        extends Sort {

    public BubbleSort() {
        waitFor = 1l;
    }

    @Override
    public void sort() {
        // Iterates all elements and swap the greatest element to the end of the array.
        for (int i = arr.length; --i >= 0;) {
            for (int j = 0; j < i; j++) {
                if (isGreater(arr[j], arr[j + 1])) {
                    swap(j, j + 1);
                    notifyCursor(i, j);
                }
            }
            notifyCursor(i);
        }
        finish();
    }

    @Override
    public String getName() {
        return "BubbleSort";
    }
}
