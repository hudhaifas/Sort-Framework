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
 * @version 1.0, Sep 24, 2013 - 5:45:40 PM
 * @version 1.1, Oct 7, 2013
 * @since sort-framework v1.0
 */
public abstract class Sort {

    /**
     *
     * @param listener
     */
    public void setListener(ArrayChangeListener listener) {
        this.listener = listener;
    }

    /**
     * The name of sort algorithm
     * <p/>
     * @return The name of sort algorithm
     */
    public abstract String getName();

    /**
     * Initialize the array.
     * <p/>
     * @param arr the array the will be sorted.
     */
    public void reset(int[] arr) {
        this.arr = arr;
    }

    /**
     * Start the sorting operation
     */
    public abstract void sort();

    /**
     * Number of comparisons used by the algorithm to sort the array
     * <p/>
     * @return Number of comparisons used by the algorithm to sort the array
     * <p/>
     * @since 1.1
     * <p/>
     * @see #isLess(int, int)
     * @see #isGreater(int, int)
     * @see #isEqual(int, int)
     * @see #isLessEqual(int, int)
     * @see #isGreaterEqual(int, int)
     */
    public int getComparisons() {
        return comparisons;
    }

    /**
     *
     * @return
     * @since 1.1
     * @see #swap(int, int)
     */
    public int getSwaps() {
        return swaps;
    }

    /**
     * Exchange position of two integer values.
     * <p/>
     * @param i index of the value to be exchanged
     * @param j index of the value to be exchanged
     */
    protected final void swap(int i, int j) {
        swaps++;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    protected final void notifyCursor(int i, int j) {
        if (listener != null) {
            listener.cursorsChanged(i, j);
        }
    }

    protected final void notifyCursor(int i) {
        notifyCursor(i, -1);
    }

    protected final void notifyPause() {
        if (listener != null) {
            listener.requestPause();
        }
    }

    /**
     * The algorithm finished sorting.
     */
    protected final void finish() {
        if (listener != null) {
            listener.sortFinished();
        }
        System.out.println(getName() + " Finished.");
    }

    /**
     * Compare two values
     * <p/>
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     * <p/>
     * @return <code>true</code> if a is less than b, <code>false</code>
     */
    protected final boolean isLess(int a, int b) {
        comparisons++;
        return a < b;
    }

    /**
     * Compare two values
     * <p/>
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     * <p/>
     * @return <code>true</code> if a is greater than b, <code>false</code>
     */
    protected final boolean isGreater(int a, int b) {
        comparisons++;
        return a > b;
    }

    /**
     * Compare two values
     * <p/>
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     * <p/>
     * @return <code>true</code> if a is equal to b, <code>false</code>
     */
    protected final boolean isEqual(int a, int b) {
        comparisons++;
        return a == b;
    }

    /**
     * Compare two values
     * <p/>
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     * <p/>
     * @return <code>true</code> if a is less than or equal to b, <code>false</code>
     */
    protected final boolean isLessEqual(int a, int b) {
        comparisons++;
        return a <= b;
    }

    /**
     * Compare two values
     * <p/>
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     * <p/>
     * @return <code>true</code> if a is greater than or equal to b, <code>false</code>
     */
    protected final boolean isGreaterEqual(int a, int b) {
        comparisons++;
        return a >= b;
    }

    /**
     * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
     * @version 1.0, Sep 24, 2013 - 5:45:40 PM
     * @since sort-framework v1.0
     */
    public interface ArrayChangeListener {

        /**
         *
         */
        public void requestPause();

        /**
         *
         * @param i Master loop value
         * @param j Slave loop value
         */
        public void cursorsChanged(int i, int j);

        /**
         *
         * @param i
         */
        public void cursorsChanged(int i);

        /**
         *
         */
        public void sortFinished();
    }
    /**
     *
     */
    protected int[] arr;
    /**
     *
     */
    protected long waitFor = 40L;
    /**
     *
     */
    protected ArrayChangeListener listener;
    /**
     * Number of comparisons used by the algorithm to sort the array
     */
    protected int comparisons;
    protected int swaps;
}