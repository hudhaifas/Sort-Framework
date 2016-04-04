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
package com.wajatto.code.sortframework.algo.sort;

import com.wajatto.code.sortframework.algo.Algorithm;

/**
 * This is the base class of all sort algorithms implementation, can handle the sorting process and help to
 * calculate the complexity of the sort algorithm, and also it can be used with the applet sorting simulator
 * to compare with other algorithms.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Sep 24, 2013 - 5:45:40 PM
 * @version 1.1, Oct 7, 2013
 * @since sort-framework v1.0
 */
public abstract class Sort
        extends Algorithm {

    public Sort() {
    }

    protected Sort(int[] arr) {
        this.arr = arr;
    }

    /**
     *
     * @param listener
     */
    public void setListener(ArrayChangeListener listener) {
        this.listener = listener;
    }

    /**
     * Returns the name of the sort algorithm.
     *
     * @return the name of the sort algorithm
     */
    public abstract String getName();

    /**
     * Initializes the array.
     *
     * @param arr the array the will be sorted.
     */
    public void reset(int[] arr) {
        this.arr = arr;
    }

    /**
     * Starts the sorting operation.
     */
    public abstract void sort();

    /**
     * Returns the number of comparisons used by the algorithm to sort the array
     *
     * @return Number of comparisons used by the algorithm to sort the array
     * @since 1.1
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
     * Returns the number of swaps used by the algorithm to sort the array
     *
     * @return @since 1.1
     * @see #swap(int, int)
     */
    public int getSwaps() {
        return swaps;
    }

    /**
     * Generates the notification that a comparison occurred.
     */
    private void fireCompareEvent() {
        if (listener != null) {
            listener.elementsCompared(comparisons);
        }
    }

    /**
     * Generates the notification that a swap occurred.
     */
    private void fireSwapEvent() {
        if (listener != null) {
            listener.elementsSwaped(swaps);
        }
    }

    /**
     * Exchanges position of two integer values.
     *
     * @param i index of the value to be exchanged
     * @param j index of the value to be exchanged
     */
    protected final void swap(int i, int j) {
        swaps++;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        fireSwapEvent();
    }

    /**
     * Exchanges position of two integer values in a specific array.
     *
     * @param a the array that need to swap its elements.
     * @param i index of the value to be exchanged
     * @param j index of the value to be exchanged
     */
    protected final void swap(int[] a, int i, int j) {
        swaps++;
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;

        fireSwapEvent();
    }

    /**
     * Generates the notification that either master or slave cursor was changed.
     *
     * @param master Master loop index
     * @param slave Slave loop index
     */
    protected final void notifyCursor(int master, int slave) {
        if (listener != null) {
            listener.cursorsChanged(master, slave);
        }
    }

    /**
     * Generates the notification that master cursor was changed.
     *
     * @param master Master loop index
     */
    protected final void notifyCursor(int master) {
        notifyCursor(master, -1);
    }

    /**
     * Generates the notification that request a delay before the next loop.
     */
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
     * Compares two values and notify about a comparison occurred.
     *
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     *
     * @return <code>true</code> if a is less than b, <code>false</code>
     */
    protected final boolean isLess(int a, int b) {
        comparisons++;
        fireCompareEvent();
        return a < b;
    }

    /**
     * Compares two values and notify about a comparison occurred.
     *
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     *
     * @return <code>true</code> if a is greater than b, <code>false</code>
     */
    protected final boolean isGreater(int a, int b) {
        comparisons++;
        fireCompareEvent();
        return a > b;
    }

    /**
     * Compares two values and notify about a comparison occurred.
     *
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     *
     * @return <code>true</code> if a is equal to b, <code>false</code>
     */
    protected final boolean isEqual(int a, int b) {
        comparisons++;
        fireCompareEvent();
        return a == b;
    }

    /**
     * Compares two values and notify about a comparison occurred.
     *
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     *
     * @return <code>true</code> if a is <b>not</b> equal to b, <code>false</code>
     */
    protected final boolean isNotEqual(int a, int b) {
        comparisons++;
        fireCompareEvent();
        return a != b;
    }

    /**
     * Compares two values and notify about a comparison occurred.
     *
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     *
     * @return <code>true</code> if a is less than or equal to b, <code>false</code>
     */
    protected final boolean isLessEqual(int a, int b) {
        comparisons++;
        fireCompareEvent();
        return a <= b;
    }

    /**
     * Compares two values and notify about a comparison occurred.
     *
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     *
     * @return <code>true</code> if a is greater than or equal to b, <code>false</code>
     */
    protected final boolean isGreaterEqual(int a, int b) {
        comparisons++;
        fireCompareEvent();
        return a >= b;
    }

    /**
     * This is an observer callback listener to notify about any changes during the sorting process.
     *
     * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
     * @version 1.0, Sep 24, 2013 - 5:45:40 PM
     * @since sort-framework v1.0
     */
    public interface ArrayChangeListener {

        /**
         * Called to request delay to render.
         */
        public void requestPause();

        /**
         * Notifies that either master or slave cursor changed.
         *
         * @param master Master loop index
         * @param slave Slave loop index
         */
        public void cursorsChanged(int master, int slave);

        /**
         * Notifies that the master cursor was changed.
         *
         * @param master Master loop index
         */
        public void cursorsChanged(int master);

        /**
         * Notifies that the sorting process is finished.
         */
        public void sortFinished();

        /**
         * Notifies with number of swaps that occurred right now, to calculate the complexity.
         *
         * @param swaps the number of swaps that occurred.
         */
        public void elementsSwaped(int swaps);

        /**
         * Notifies with number of comparisons that occurred right now, to calculate the complexity.
         *
         * @param comparisons the number of comparisons that occurred.
         */
        public void elementsCompared(int comparisons);
    }
    /**
     * The array to be sorted.
     */
    protected int[] arr;
    /**
     * The delay between each loop.
     */
    protected long waitFor = 40L;
    /**
     * Callback reference.
     */
    protected ArrayChangeListener listener;
    /**
     * Number of comparisons used by the algorithm to sort the array, used to calculate the complexity.
     */
    protected int comparisons;
    /**
     * Number of swaps used by the algorithm to sort the array, used to calculate the complexity.
     */
    protected int swaps;
}
