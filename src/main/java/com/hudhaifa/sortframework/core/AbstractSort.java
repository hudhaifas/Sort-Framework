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
package com.hudhaifa.sortframework.core;

import java.text.DecimalFormat;

/**
 * This is the base class of all sort algorithms implementation, can handle the
 * sorting process and help to calculate the complexity of the sort algorithm,
 * and also it can be used with the applet sorting simulator to compare with
 * other algorithms.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Sep 24, 2013 - 5:45:40 PM
 * @version 1.1, Oct 7, 2013
 * @version 1.2, Jul 16, 2019
 * @since sort-framework v1.0
 */
public abstract class AbstractSort
        extends Algorithm {

    public AbstractSort() {
    }

    /**
     * Returns the name of the sort algorithm.
     *
     * @return the name of the sort algorithm
     */
    public abstract String getName();

    /**
     * Starts the sorting operation.
     */
    public abstract void sort();

    /**
     * Sets the listener instance to get notified when the sort process is done.
     *
     * @param listener listener instance to get notified when the sort process
     * is done.
     */
    public final void setListener(SortListener listener) {
        this.listener = listener;
    }

    /**
     * Initializes the array.
     *
     * @param arr the array the will be sorted.
     */
    public final void reset(int[] arr) {
        this.arr = arr;
    }

    /**
     * Starts the sorting process
     */
    @Override
    public final void run() {
        beginTime = System.nanoTime();
        this.sort();
    }

    /**
     * Initializes the sorting thread, so the sort run out of the rendering
     * thread scope.
     */
    public final void takeOff() {
        Thread t = new Thread(this);
        t.start();
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
    }

    /**
     * Generates the notification that either master or slave cursor was
     * changed.
     *
     * @param master Master loop index
     * @param slave Slave loop index
     */
    protected final void updateCursors(int master, int slave) {
        this.masterCursor = master;
        this.slaveCursor = slave;
    }

    /**
     * Generates the notification that master cursor was changed.
     *
     * @param master Master loop index
     */
    protected final void updateCursor(int master) {
        updateCursors(master, -1);
    }

    /**
     * The algorithm finished sorting.
     */
    protected final void finish() {
        endTime = System.nanoTime();

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
        return a == b;
    }

    /**
     * Compares two values and notify about a comparison occurred.
     *
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     *
     * @return <code>true</code> if a is <b>not</b> equal to b,
     * <code>false</code>
     */
    protected final boolean isNotEqual(int a, int b) {
        comparisons++;
        return a != b;
    }

    /**
     * Compares two values and notify about a comparison occurred.
     *
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     *
     * @return <code>true</code> if a is less than or equal to b,
     * <code>false</code>
     */
    protected final boolean isLessEqual(int a, int b) {
        comparisons++;
        return a <= b;
    }

    /**
     * Compares two values and notify about a comparison occurred.
     *
     * @param a value to be used in the comparison operation
     * @param b value to be used in the comparison operation
     *
     * @return <code>true</code> if a is greater than or equal to b,
     * <code>false</code>
     */
    protected final boolean isGreaterEqual(int a, int b) {
        comparisons++;
        return a >= b;
    }

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
    public final long getComparisons() {
        return comparisons;
    }

    /**
     * Returns the number of swaps used by the algorithm to sort the array
     *
     * @return @since 1.1
     * @see #swap(int, int)
     */
    public final long getSwaps() {
        return swaps;
    }

    /**
     * Returns the elapsed time in nanoseconds.
     *
     * @return the elapsed time in nanoseconds.
     */
    public long getElapsedTime() {
        if (beginTime == -1) {
            return 0;
        }

        // if the task is not finished read the system current time.
        long end = endTime != -1 ? endTime : System.nanoTime();
        
        return end - beginTime;
    }

    /**
     * Returns formatted elapsed time in seconds.
     *
     * @return formatted elapsed time in seconds.
     */
    public String getElapsedSeconds() {
        return df2.format((double) getElapsedTime() / 1_000_000_000.0) + "s";
    }

    /**
     * Returns the master cursor position.
     *
     * @return the master cursor position
     */
    public final int getMasterCursor() {
        return masterCursor;
    }

    /**
     * Returns the slave cursor position.
     *
     * @return the slave cursor position
     */
    public final int getSlaveCursor() {
        return slaveCursor;
    }

    /**
     * This is an observer callback listener to notify about any changes during
     * the sorting process.
     *
     * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
     * @version 1.0, Sep 24, 2013 - 5:45:40 PM
     * @since sort-framework v1.0
     */
    public interface SortListener {

        /**
         * Notifies that the sorting process is finished.
         */
        public void sortFinished();

    }

    /**
     * The array to be sorted.
     */
    protected int[] arr;
    /**
     * Callback reference.
     */
    protected SortListener listener;
    /**
     * Number of comparisons used by the algorithm to sort the array, used to
     * calculate the complexity.
     */
    protected long comparisons;
    /**
     * Number of swaps used by the algorithm to sort the array, used to
     * calculate the complexity.
     */
    protected long swaps;
    /**
     * Master loop index
     */
    protected int masterCursor;
    /**
     * Slave loop index
     */
    protected int slaveCursor;
    /**
     * the time at start executing the sort task
     */
    protected long beginTime = -1;
    /**
     * the time at end executing the sort task
     */
    protected long endTime = -1;
    /**
     * Formats the elapsed time
     */
    private static final DecimalFormat df2 = new DecimalFormat("#.##");
}
