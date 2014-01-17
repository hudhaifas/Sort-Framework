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

import edu.mum.cs.util.ArrayUtil;
import java.util.Arrays;
import org.junit.After;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 13, 2013 - 10:55:20 PM
 */
public class ParallelMergeSortTest {

    public ParallelMergeSortTest() {
    }

    @Before
    public void setUp() {
        instance = new ParallelMergeSort();
        a = ArrayUtil.random(1_000_000);
        b = ArrayUtil.cloneArray(a);
        Arrays.sort(b);
    }

    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of sort method, of class ParallelMergeSort.
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

    private ParallelMergeSort instance;
    private int[] a = {2, 1, 5, 0, 5, 4};
    private int[] b = {0, 1, 2, 4, 5, 5};

}
