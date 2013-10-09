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

    public void setListener(ArrayChangeListener listener) {
        this.listener = listener;
    }

    public abstract String getName();

    public void reset(int[] arr) {
        this.arr = arr;
    }

    public abstract void sort();

    protected final void swap(int i, int j) {
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
        if (listener != null) {
            listener.cursorsChanged(i, -1);
        }
    }

    protected final void notifyPause() {
        if (listener != null) {
            listener.requestPause();
        }
    }

    protected final void finish() {
        if (listener != null) {
            listener.sortFinished();
        }
        System.out.println(getName() + " Finished.");
    }

    protected final boolean isLess(int a, int b) {
        comparisons++;
        return a < b;
    }

    protected final boolean isGreater(int a, int b) {
        comparisons++;
        return a > b;
    }

    protected final boolean isEqual(int a, int b) {
        comparisons++;
        return a == b;
    }

    protected final boolean isLessEqual(int a, int b) {
        comparisons++;
        return a <= b;
    }

    protected final boolean isGreaterEqual(int a, int b) {
        comparisons++;
        return a >= b;
    }

    public int getComparisons() {
        return comparisons;
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
    protected int[] arr;
    protected long waitFor = 40L;
    protected ArrayChangeListener listener;
    protected int comparisons;
}