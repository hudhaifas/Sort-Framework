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
 * <p/>
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.1, Oct 8, 2013
 * @since sort-framework v1.0
 */
public abstract class SortApplet
        extends JApplet
        implements Runnable, MouseListener, Sort.ArrayChangeListener {

    @Override
    public void cursorsChanged(int i, int j) {
        masterCursor = i;
        slaveCursor = j;
        repaint();
        await(40);
    }

    @Override
    public void cursorsChanged(int i) {
        cursorsChanged(i, slaveCursor);
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
        this.sorter.reset(arr);
        this.sorter.sort();
    }

    @Override
    public void sortFinished() {
        masterCursor = -1;
        slaveCursor = -1;
        repaint();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
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

    protected void startSort() {
        if (executer != null && executer.isAlive()) {
            return;
        }
        fill();
        repaint();
        this.executer = new Thread(this);
        this.executer.start();
    }

    protected abstract void drawIndex(Graphics g, int i);

    protected abstract void drawCursor(Graphics g, int master, int slave);

    protected abstract void drawSwaps(Graphics g, int swaps);

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
