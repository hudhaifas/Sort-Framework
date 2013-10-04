/*
 * ColorPane.java
 * Sep 24, 2013 - 10:02:24 PM
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
public class ColorPane
        extends SortPane {

    public ColorPane(int[] arr, int width, int height, BlockingQueue notifier) {
        this(arr, width, height, notifier, Color.GREEN);
    }

    public ColorPane(int[] arr, int width, int height, BlockingQueue notifier, Color color) {
        super(arr, width, height, notifier);
        setBaseColor(color);
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
        if (baseColor == null) {
            return;
        }
        Graphics g = getOffGraphics();
        int x = i * 2;
        g.setColor(Color.BLACK);
        g.fillRect(x, getGap(), 2, height - getGap() * 2);

        g.setColor(baseColor.getColor(arr[i]));
        g.fillRect(x, getGap(), 2, height - getGap() * 2);
        drawTitle();
    }

    private float getIndexColor(int i) {
//        return 1.0F - ((float) i / width);
        return ((float) i / width);
    }

    public final void setBaseColor(Color color) {
        this.baseColor = new BaseColor(color);
        initScreen();
    }

    void drawTitle() {
        if (getTitle() == null) {
            return;
        }
        Graphics g = getOffGraphics();
        g.setColor(Color.WHITE);
        g.drawString(getTitle(), getWidth() >> 1, getHeight() >> 1);
    }

    private class BaseColor {

        public BaseColor(Color c) {
            this.r = c.getRed() / 255F;
            this.g = c.getGreen() / 255F;
            this.b = c.getBlue() / 255F;
            this.a = 0.0f;
        }

        Color getColor() {
            return new Color(r, g, b, a);
        }

        Color getColor(int i) {
            return new Color(r, g, b, 1.0F - ((float) i / (width * 1.7F)));
        }

        float r, g, b, a;
    }
    private BaseColor baseColor;
}
