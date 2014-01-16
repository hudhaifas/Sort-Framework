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
package edu.mum.cs.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the relation between threads that need to wait each other.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0,
 */
public class ForkJoin {

    public ForkJoin() {
        threads = new ArrayList<>();
    }

    /**
     * Adds a thread to the list.
     *
     * @param runnable runnable thread to be forked and join with other threads.
     */
    public void add(Runnable runnable) {
        threads.add(new Thread(runnable));
    }

    /**
     * Adds a threads to the list.
     *
     * @param runnables runnable threads to be forked and join with other threads.
     */
    public void add(Runnable... runnables) {
        for (Runnable runnable : runnables) {
            threads.add(new Thread(runnable));
        }
    }

    /**
     * Starts all threads and joins them to wait each other.
     *
     * @throws InterruptedException if any thread has interrupted the current thread. The
     * <i>interrupted status</i> of the current thread is cleared when this exception is thrown.
     */
    public void fork()
            throws InterruptedException {
        if (threads.isEmpty()) {
            return;
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    /**
     * Adds a list of threads then starts and joins them to wait each other.
     *
     * @param runnables list of threads to be forked.
     * @throws InterruptedException if any thread has interrupted the current thread. The
     * <i>interrupted status</i> of the current thread is cleared when this exception is thrown.
     * @see #fork()
     */
    public void fork(List<Runnable> runnables)
            throws InterruptedException {
        for (Runnable runnable : runnables) {
            threads.add(new Thread(runnable));
        }
        fork();
    }

    /**
     * Adds a list of threads then starts and joins them to wait each other.
     *
     * @param runnables
     * @throws InterruptedException if any thread has interrupted the current thread. The
     * <i>interrupted status</i> of the current thread is cleared when this exception is thrown.
     * @see #add(java.lang.Runnable...)
     * @see #fork()
     */
    public void fork(Runnable... runnables)
            throws InterruptedException {
        add(runnables);
        fork();
    }

    /**
     * Starts all threads.
     *
     */
    public void start() {
        for (Thread t : threads) {
            t.start();
        }
    }

    private final List<Thread> threads;
}
