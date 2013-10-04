/*
 * TrianglePane.java
 * Sep 24, 2013 - 10:00:24 PM
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
package com.googlecode.sort.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 */
public class TrianglePane
        extends SortPane {

    public TrianglePane(int[] arr, int width, int height, BlockingQueue notifier) {
        super(arr, width, height, notifier);
    }

    @Override
    protected void initScreen() {
        Graphics g = getOffGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < arr.length; i++) {
            drawIndex(i);
        }
    }

    @Override
    protected void drawIndex(int i) {
        Graphics g = getOffGraphics();
        int y = 2 * i;
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(0, y, this.width, y);

        g.setColor(Color.BLACK);
        g.drawLine(0, y, arr[i], y);
    }

}
