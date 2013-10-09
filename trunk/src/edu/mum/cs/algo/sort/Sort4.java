/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs.algo.sort;

import java.util.Arrays;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 8, 2013 - 9:32:46 PM
 */
public class Sort4
        extends Sort {

    public static void main(String[] args) {
        int[] a = {2, 1, 5, 0};
        System.out.println(Arrays.toString(a));

        Sort4 s = new Sort4();
        s.reset(a);
        s.sort();

        System.out.println(Arrays.toString(a));
        System.out.println("Comparisons: " + s.getComparisons());
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
