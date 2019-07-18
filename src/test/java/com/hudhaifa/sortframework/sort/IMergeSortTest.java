/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudhaifa.sortframework.sort;

import com.hudhaifa.sortframework.sort.IMergeSort;
import com.hudhaifa.sortframework.util.ArrayUtil;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 13, 2013 - 10:40:11 PM
 */
public class IMergeSortTest {

    public IMergeSortTest() {
    }

    @Before
    public void setUp() {
        instance = new IMergeSort();
        a = ArrayUtil.random(1_000_000);
        b = ArrayUtil.cloneArray(a);
        Arrays.sort(b);
    }

    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of sort method, of class InsertionSort.
     */
    @Test
    public void testSort() {
        System.out.println("sort");

        instance.reset(a);
        instance.sort();

        System.out.println("Array size: " + a.length);
        System.out.println("# Comparisons: " + instance.getComparisons());
        System.out.println("# Swaps: " + instance.getSwaps());
        System.out.println("# Compexity: " + instance.getTimeCompleixty());

        assertArrayEquals(a, b);
    }
    private IMergeSort instance;
    private int[] a;
    private int[] b;
}
