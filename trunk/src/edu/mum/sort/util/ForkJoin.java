/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
package edu.mum.sort.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 */
public class ForkJoin {

    public ForkJoin() {
        threads = new ArrayList<>();
    }

    public void add(Runnable runnable) {
        threads.add(new Thread(runnable));
    }

    public void add(Runnable... runnables) {
        for (Runnable runnable : runnables) {
            threads.add(new Thread(runnable));
        }
    }

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

    public void start()
            throws InterruptedException {
        for (Thread t : threads) {
            t.start();
        }
    }

    public void fork(List<Runnable> runnables)
            throws InterruptedException {
        for (Runnable runnable : runnables) {
            threads.add(new Thread(runnable));
        }
        fork();
    }

    public void fork(Runnable... runnables)
            throws InterruptedException {
        for (Runnable runnable : runnables) {
            threads.add(new Thread(runnable));
        }
        fork();
    }

    private List<Thread> threads;
}
