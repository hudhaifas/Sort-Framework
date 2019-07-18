/*
 * Copyright (C) 2019 Hudhaifa Shatnawi
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * This class is responsible for drawing the current state of the sorting
 * process.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Jul 16, 2019
 * @since sort-framework v1.1
 */
public abstract class SortCanvas {

    protected SortCanvas() {
    }

    /**
     * Called when Sorter is activating this canvas Safe to load and prepare
     * canvas data
     *
     * @since sort-framework v1.1
     */
    protected void activate() {
    }

    /**
     * <pre>
     * Time in milliseconds to call this canvas job i.e. tick()
     * <b>Important</b> Since 2.3 the -1 means no ticking
     * </pre>
     *
     * @return time to call tick() or -1 if canvas going to control Sorter
     * manually
     * @since sort-framework v1.1
     */
    protected abstract long tickDelay();

    /**
     * Called by Sorter to provide this canvas to preform its job
     *
     * @param masterCursor the position of the master cursor
     * @param slaveCursor the position of the slave cursor
     * @since sort-framework v1.1
     */
    public void tick(int masterCursor, int slaveCursor) {
        this.masterCursor = masterCursor;
        this.slaveCursor = slaveCursor;
    }

    /**
     * Paints this SortCanvas.
     *
     * @param g the Graphics object with which to render the screen.
     * @throws NullPointerException if g is null
     * @see Component#paint
     * @since sort-framework v1.1
     */
    protected final void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        for (int i = 0; i < arr.length; i++) {
            drawIndex(g2d, arr, i);
        }

        drawCursor(g2d, masterCursor, slaveCursor);
    }

    /**
     * Called when Sorter is deactivating this canvas Safe to deallocate
     * resources
     *
     * @since sort-framework v1.1
     */
    protected void deactivate() {
    }

    /**
     * Draws a single element at a specific index.
     *
     * @param g Graphics objects
     * @param arr the array being sorted
     * @param i the index of an element to be drawing
     */
    protected abstract void drawIndex(Graphics2D g, int[] arr, int i);

    /**
     * Draws both master and slave cursors.
     *
     * @param g Graphics objects
     * @param master Master loop index
     * @param slave Slave loop index
     */
    protected abstract void drawCursor(Graphics2D g, int master, int slave);

    /**
     * Sets the array being sorted.
     *
     * @param arr the array will be sorted.
     */
    public final void reset(int[] arr) {
        this.arr = arr;
    }

    /**
     * Sets the size of the drawing area
     *
     * @param width the width of the drawing area
     * @param height the height of the drawing area
     */
    public final void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of the drawing area
     *
     * @return the width of the drawing area
     */
    public final int getWidth() {
        return width;
    }

    /**
     * Returns the height of the drawing area
     *
     * @return the height of the drawing area
     */
    public final int getHeight() {
        return height;
    }

    /**
     * Calculates the width of an elements
     *
     * @return the width of an elements
     */
    public final float getElementWidth() {
        return (float) width / arr.length;
    }

    /**
     * Draws a single element at a specific coordinates and dimensions.
     *
     * @param g Graphics objects
     * @param x the X coordinate of the upper-left corner of the element
     * @param y the Y coordinate of the upper-left corner of the element
     * @param w the width of the element
     * @param h the height of the element
     */
    protected final void drawElement(Graphics2D g, float x, float y, float w, float h) {
        Rectangle2D rect = new Rectangle2D.Float(x, y, w, h);
        g.draw(rect);
    }

    /**
     * Fills the interior of a single element at a specific coordinates and
     * dimensions.
     *
     * @param g Graphics objects
     * @param x the X coordinate of the upper-left corner of the element
     * @param y the Y coordinate of the upper-left corner of the element
     * @param w the width of the element
     * @param h the height of the element
     */
    protected final void fillElement(Graphics2D g, float x, float y, float w, float h) {
        Rectangle2D rect = new Rectangle2D.Float(x, y, w, h);
        if (w < 1.0f) {
            g.draw(rect);
        } else {
            g.fill(rect);
        }
    }

    /**
     * The array being sorted.
     */
    private int[] arr;
    /**
     * The master cursor on some sort algorithms
     */
    private int masterCursor;
    /**
     * The slave cursor on some sort algorithms
     */
    private int slaveCursor;
    /**
     * the width of the drawing area
     */
    private int width;
    /**
     * the height of the drawing area
     */
    private int height;
}
