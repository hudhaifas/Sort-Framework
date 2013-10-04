/*
 * ParallelRankSort.java
 * Sep 24, 2013 - 7:52:00 PM
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
package com.googlecode.sort.parallel;

import com.googlecode.sort.Sort;
import com.googlecode.sort.util.ForkJoin;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 */
public class ParallelRankSort
        extends Sort {

    public ParallelRankSort(int[] arr, BlockingQueue<Integer> notifier) {
        this(arr, notifier, 0, arr.length);
    }

    private ParallelRankSort(int[] arr, BlockingQueue<Integer> notifier, int low, int high) {
        super(arr, notifier);
        this.low = low;
        this.high = high;
        helper = new int[high - low];

        System.err.println("low = " + low);
        System.err.println("range = " + (high - low));
        System.arraycopy(arr, low, helper, low, high - low);
    }

    @Override
    public void run() {
        int block = helper.length / pnum;
        if (block > MIN_SIZE * 2) {
            ForkJoin fj = new ForkJoin();

            for (int i = 0; i < pnum; i++) {
                int low = i * block;
                fj.add(new ParallelRankSort(arr, notifier, low, low + block));
            }
        } else {
            for (int i = 0; i < helper.length; i++) {
                // put arr[i] in place
                putinPlace(i);
            }
        }
        finish();
    }

    @Override
    public String getName() {
        return "RankSort";
    }

    void putinPlace(int src) {
        int rank = 0;
        // count number less than it
        for (int j = 0; j < helper.length; j++) {
            if (helper[src] > helper[j]) {
                rank++;
            }
        }
        arr[rank] = helper[src];
        notify(rank);
    }

    private int[] helper;
    private int pnum = Runtime.getRuntime().availableProcessors();
    private int low;
    private int high;
    private static final int MIN_SIZE = 20;
}
