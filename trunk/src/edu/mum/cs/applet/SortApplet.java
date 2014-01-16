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
package edu.mum.cs.applet;

import edu.mum.cs.util.ArrayUtil;
import edu.mum.cs.algo.sort.Sort;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;

/**
 * This is the base class for visualization sorting steps in different ways.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.1, Oct 8, 2013
 * @since sort-framework v1.0
 */
public abstract class SortApplet
        extends JApplet
        implements Runnable, MouseListener, Sort.ArrayChangeListener {

    @Override
    public void cursorsChanged(int master, int slave) {
        masterCursor = master;
        slaveCursor = slave;
        repaint();
        await(40);
    }

    @Override
    public void cursorsChanged(int master) {
        cursorsChanged(master, slaveCursor);
    }

    @Override
    public void requestPause() {
        cursorsChanged(masterCursor, slaveCursor);
    }

    @Override
    public void elementsSwaped(int swaps) {
        this.swaps = swaps;
    }

    @Override
    public void elementsCompared(int comparisons) {
        this.comparisons = comparisons;

    }

    /**
     * Initializes the applet SortApplets
     */
    @Override
    public void init() {
        this.strategy = getParameter("strategy");
        this.fill = Integer.parseInt(getParameter("fill"));
        try {
            sorter = (Sort) Class.forName(strategy).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(LineApplet.class.getName()).log(Level.SEVERE, null, ex);
        }
        sorter.setListener(this);
        this.addMouseListener(this);
        this.width = getSize().width;
        this.height = getSize().height;
        fill();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        startSort();
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < arr.length; i++) {
            drawIndex(g, i);
        }

        drawCursor(g, masterCursor, slaveCursor);
        drawSwaps(g, swaps);
        drawComparisons(g, comparisons);
    }

    @Override
    public final void run() {
        // Reinitialzes the array.
        this.sorter.reset(arr);

        // Starts sorting
        this.sorter.sort();
    }

    @Override
    public void sortFinished() {
        // Resets the cursors to the default value.
        masterCursor = -1;
        slaveCursor = -1;

        repaint();
    }

    /**
     * Wait awhile
     * <p/>
     * @param time
     */
    protected final void await(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sort.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fills the array in on of the following orders:
     * <ul>
     * <li>Reverse order (non-repeated elements)</li>
     * <li>Nearly sorted (non-repeated elements)</li>
     * <li>Randomized (non-repeated elements)</li>
     * <li>Randomized (repeated elements)</li>
     * </ul>
     *
     * @see ArrayUtil
     */
    protected void fill() {
        switch (fill) {
            case ArrayUtil.FILL_RANDOM:
                this.arr = ArrayUtil.random(height / 2);
                break;
            case ArrayUtil.FILL_REVERSE:
                this.arr = ArrayUtil.reversed(height / 2);
                break;
            case ArrayUtil.FILL_NEARLY_SORTED:
                this.arr = ArrayUtil.nearlySorted(height / 2, 3);
                break;
            case ArrayUtil.FILL_REPEATED:
                this.arr = ArrayUtil.repeated(height / 2, 15);
                break;
        }

        this.ratio = (double) this.width / this.arr.length;
    }

    /**
     * Starts the sorting process.
     */
    protected void startSort() {
        // Check if the sorting thread is already running.
        if (executer != null && executer.isAlive()) {
            return;
        }

        fill();
        repaint();

        this.executer = new Thread(this);
        this.executer.start();
    }

    /**
     * Draws a single element.
     *
     * @param g Graphics objects
     * @param i the index of an element to be drawing
     */
    protected abstract void drawIndex(Graphics g, int i);

    /**
     * Draws both master and slave cursors.
     *
     * @param g Graphics objects
     * @param master Master loop index
     * @param slave Slave loop index
     */
    protected abstract void drawCursor(Graphics g, int master, int slave);

    /**
     * Draws the swaps statistics.
     *
     * @param g Graphics object
     * @param swaps current number of swaps occurred.
     */
    protected abstract void drawSwaps(Graphics g, int swaps);

    /**
     * Draws the comparisons statistics.
     *
     * @param g Graphics object
     * @param comparisons current number of comparisons occurred.
     */
    protected abstract void drawComparisons(Graphics g, int comparisons);

    protected int[] arr;
    protected int fill;
    protected int height;
    protected double ratio;
    protected int width;
    private Thread executer;
    private Sort sorter;
    private String strategy;
    private int masterCursor;
    private int slaveCursor;
    private int swaps;
    private int comparisons;
}
