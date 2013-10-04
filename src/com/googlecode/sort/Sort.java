/*
 * Sort.java
 * Sep 24, 2013 - 5:45:40 PM
 *
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
package com.googlecode.sort;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 */
public abstract class Sort
        implements Runnable {

    public Sort(int[] arr, BlockingQueue<Integer> notifier) {
        this.arr = arr;
        this.notifier = notifier;
    }

    public abstract String getName();

    public void reset(int[] arr) {
        this.arr = arr;
    }

    protected final void await(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sort.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected final void exchange(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        try {
            notifier.put(i);
            notifier.put(j);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sort.class.getName()).log(Level.SEVERE, null, ex);
        }
        await(waitFor);
    }

    protected final void notify(int i) {
        try {
            notifier.put(i);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sort.class.getName()).log(Level.SEVERE, null, ex);
        }
        await(waitFor);
    }

    protected final void finish() {
        try {
            notifier.put(-1);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sort.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println(getName() + " Finished.");
        }
    }

    protected int[] arr;
    protected BlockingQueue<Integer> notifier;
    protected long waitFor = 40L;
}
