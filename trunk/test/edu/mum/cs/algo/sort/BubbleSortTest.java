/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs.algo.sort;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 11, 2013 - 8:45:27 AM
 */
public class BubbleSortTest {

    public BubbleSortTest() {
    }

    @Before
    public void setUp() {
        instance = new BubbleSort();
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
    private BubbleSort instance;
    private int[] a = {2, 1, 5, 0, 5, 4};
    private int[] b = {0, 1, 2, 4, 5, 5};
}