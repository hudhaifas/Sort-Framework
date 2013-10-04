/*
 * @(#)SortPane.java
 * Sep 24, 2013 - 6:14:34 PM
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

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 */
public abstract class SortPane
        extends JPanel
        implements Runnable {

    public SortPane(int[] arr, int width, int height, BlockingQueue notifier) {
        this.arr = arr;
        this.width = width;
        this.height = height;
        this.notifier = notifier;
        this.setSize(this.width, this.height);
        this.offImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        this.offg = this.offImage.getGraphics();

        this.initScreen();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void run() {
        try {
            Integer rec = notifier.take();
            while (rec != -1) {
                drawIndex(rec);
                repaint();
                rec = notifier.take();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SortPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected final void paintComponent(Graphics g) {
        g.drawImage(offImage, 0, 0, this);
    }

    protected abstract void initScreen();

    protected abstract void drawIndex(int i);

    public final Graphics getOffGraphics() {
        return offg;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public void reset(int[] arr) {
        this.arr = arr;
    }

    private BlockingQueue<Integer> notifier;
    protected int[] arr;
    protected int width;
    protected int height;
    private BufferedImage offImage;
    private Graphics offg;
    private int gap = 2;
    private String title;
}
