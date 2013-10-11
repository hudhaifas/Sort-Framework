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
 * @version 1.0, Sep 24, 2013 - 5:44:14 PM
 * @since sort-framework v1.0
 */
public class ShuffleSort
        extends Sort {

    public ShuffleSort() {
        waitFor = 1l;
    }

    @Override
    public void sort() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                if (isLess(arr[j], arr[j - 1])) {
                    swap(j, j - 1);
                    notifyCursor(i, j);
                }
            }
            notifyCursor(i);
        }
        finish();
    }

    @Override
    public String getName() {
        return "ShuffleSort";
    }
}
