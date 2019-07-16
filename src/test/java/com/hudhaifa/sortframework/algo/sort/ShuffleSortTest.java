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

import com.hudhaifa.sortframework.algo.sort.ShuffleSort;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 11, 2013 - 8:45:27 AM
 */
public class ShuffleSortTest {

    public ShuffleSortTest() {
    }

    @Before
    public void setUp() {
        instance = new ShuffleSort();
    }

    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of sort method, of class BubbleSort.
     */
    @Test
    public void testSort() {
        System.out.println("sort");

        instance.reset(a);
        instance.sort();

        System.out.println("Array size: " + a.length);
        System.out.println("# Comparisons: " + instance.getComparisons());
        System.out.println("# Swaps: " + instance.getSwaps());

        assertArrayEquals(a, b);
    }
    private ShuffleSort instance;
    private int[] a = {2, 1, 5, 0, 5, 4};
    private int[] b = {0, 1, 2, 4, 5, 5};
}