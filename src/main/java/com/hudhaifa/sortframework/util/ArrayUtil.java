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
package com.hudhaifa.sortframework.util;

/**
 * This is a utility class helps in generating arrays in certain forms, to be
 * used in comparing sorting algorithms:
 * <ul>
 * <li>Reverse order (non-repeated elements)</li>
 * <li>Nearly sorted (non-repeated elements)</li>
 * <li>Randomized (non-repeated elements)</li>
 * <li>Randomized (repeated elements)</li>
 * </ul>
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 3, 2013 - 10:49:01 PM
 * @since sort-framework v1.0
 */
public class ArrayUtil {

    /**
     * Creates an array of size n and filled with random numbers from 0...n - 1.
     *
     * @param n Size of the array
     *
     * @return An array of size n.
     * <p/>
     * @since sort-framework v1.0
     */
    public static int[] random(int n) {
        int[] arr = new int[n];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        random(arr, 0, arr.length);
        return arr;
    }

    /**
     * Creates an array of size n and filled with sorted numbers from 0...n - 1.
     * <p/>
     * @param n Size of the array
     * <p/>
     * @return An array of size n.
     * <p/>
     * @since sort-framework v1.0
     */
    public static int[] sorted(int n) {
        int[] arr = new int[n];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        return arr;
    }

    /**
     * Creates an array of size n and filled with nearly sorted random numbers
     * from 0..(n - 1).
     *
     * @param n Size of the array
     * @param g Group size
     *
     * @return An array of size n.
     * <p/>
     * @since sort-framework v1.0
     */
    public static int[] nearlySorted(int n, int g) {
        int[] arr = new int[n];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        for (int i = 0; i < arr.length - g; i += g) {
            reverse(arr, i, g);
        }
        return arr;
    }

    /**
     * Creates an array of size n and filled with nearly sorted random numbers
     * from 0..(n - 1).
     *
     * @param n Size of the array
     * @param g Group size
     *
     * @return An array of size n.
     * <p/>
     * @since sort-framework v1.0
     */
    public static int[] repeated(int n, int g) {
        int[] arr = new int[n];

        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i % g == 0) {
                j = j + g >= arr.length ? arr.length - 1 : j + g;
            }
            arr[i] = j;
        }
        random(arr, 0, arr.length);

        return arr;
    }

    /**
     * Creates an array of size n and filled reversed numbers from (n - 1)..0
     *
     * @param n Size of the array
     *
     * @return An array of size n.
     * <p/>
     * @since sort-framework v1.0
     */
    public static int[] reversed(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        reverse(arr, 0, arr.length);
        return arr;
    }

    /**
     * Creates an array of size n and filled with random numbers from 0...n - 1.
     *
     * @param src the array to be cloned
     * @return An array of size n.
     * @since sort-framework v1.0
     */
    public static int[] cloneArray(int[] src) {
        int[] ret = new int[src.length];
        System.arraycopy(src, 0, ret, 0, src.length);
        return ret;
    }

    /**
     * Mixes range of existing array's elements randomly
     *
     * @param arr Array to be mixed
     * @param start First index in the range
     * @param end Last index in the range
     * <p/>
     * @since sort-framework v1.0
     */
    private static void random(int[] arr, int start, int end) {
        for (int i = start; i < end; i++) {
            int j = (int) (i * Math.random());
            swap(arr, i, j);
        }
    }

    /**
     * Reverses ordering of range of existing array's elements randomly
     *
     * @param arr Array to be reversed
     * @param start First index in the range
     * @param end Last index in the range
     * <p/>
     * @since sort-framework v1.0
     */
    private static void reverse(int[] arr, int start, int length) {
        int range = length - 1;
        for (int i = start; i < start + length / 2; i++) {
            int k = start + range--;
            swap(arr, i, k);
        }
    }

    /**
     * Exchanges to elements positions in an array.
     *
     * @param arr Array that has elements to be exchange
     * @param i Index of element which will be swapped.
     * @param j Index of element which will be swapped.
     * <p/>
     * @since sort-framework v1.0
     */
    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    /**
     * Randomized (non-repeated elements)
     */
    public static final int FILL_RANDOM = 0;
    /**
     * Reverse order (non-repeated elements)
     */
    public static final int FILL_REVERSE = 1;
    /**
     * Randomized (repeated elements)
     */
    public static final int FILL_REPEATED = 2;
    /**
     * Nearly sorted (non-repeated elements)
     */
    public static final int FILL_NEARLY_SORTED = 3;
}
