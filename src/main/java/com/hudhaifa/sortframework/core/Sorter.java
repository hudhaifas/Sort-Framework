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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * Visualize sorting steps in different ways.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Jul 16, 2019
 * @since sort-framework v1.1
 */
public final class Sorter
        extends JPanel
        implements Runnable, AbstractSort.SortListener, ComponentListener {

    /**
     * Initialize sorter instance for a specific sort algorithm and sort canvas.
     *
     * @param model sort algorithm
     * @param view sort canvas
     */
    public Sorter(AbstractSort model, SortCanvas view) {
        this.model = model;
        this.view = view;
        setDoubleBuffered(true);
        initComponents();
    }

    /**
     * The main sort loop.
     */
    @Override
    public void run() {
        isExit = false;
        long beginTime; // the time when the cycle begun
        long timeDiff; // the time it took for the cycle to execute
        int framesSkipped; // number of frames being skipped
        int sleepTime; // ms to sleep (<0 if we're behind)
        int maxFrameSkips = 3;

        // main sorter loop
        while (!isExit) {
            switch (state) {
                case STATE_READY:
                    view.setSize(getWidth(), getHeight());
                    state = STATE_WORKING;
                    break;

                case STATE_DEACTIVATED:
                    if (view != null) {
                        view.deactivate();
                    }
                    break;

                case STATE_ACTIVATED:
                    if (view != null) {
                        view.activate();
                    }
                    model.takeOff();
                    state = STATE_WORKING;
                    break;

                case STATE_WORKING:
                    if (view == null) {
                        break;
                    }

                    beginTime = System.currentTimeMillis();

                    // resetting the frames skipped
                    framesSkipped = 0;

                    // refresh sort view
                    view.tick(model.getMasterCursor(), model.getSlaveCursor()); // refresh without rendering

                    refresh();

                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;

                    // calculate sleep time
                    sleepTime = (int) (view.tickDelay() - timeDiff);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                        }
                    }

                    while (sleepTime < 0 && framesSkipped < maxFrameSkips) {
                        // we need to catch up
                        view.tick(model.getMasterCursor(), model.getSlaveCursor()); // refresh without rendering
                        sleepTime += view.tickDelay(); // add frame period to check
                        // if in next frame
                        framesSkipped++;
                    }
                    break;

                default:
                    break;
            }

        }

        // exiting
        if (view != null && state == STATE_WORKING) {
            view.tick(model.getMasterCursor(), model.getSlaveCursor()); // refresh without rendering
        }
        refresh();
    }

    /**
     * Refreshes drawing canvas and counter labels.
     */
    public void refresh() {
        sortPanel.repaint();
        timeValue.setText(model.getElapsedSeconds());
        complexityValue.setText(Long.toString(model.getTimeCompleixty()));
        comparisonsValue.setText(Long.toString(model.getComparisons()));
        swapsValue.setText(Long.toString(model.getSwaps()));
    }

    /**
     * Initializes the model and view array.
     *
     * @param arr array being sorted
     */
    public void init(int[] arr) {
        model.reset(arr);
        view.reset(arr);

        elementsValue.setText(Integer.toString(arr.length));

        // Check if the sorting thread is already running.
        if (mainThread != null && mainThread.isAlive()) {
            return;
        }

        this.mainThread = new Thread(this);
        this.mainThread.start();
    }

    /**
     * Starts the sorting process.
     */
    public void startSort() {
        state = STATE_ACTIVATED;
    }

    /**
     * Initializes counter labels.
     */
    private void initComponents() {
        this.model.setListener(this);
        setBorder(BorderFactory.createTitledBorder(model.getName()));

        sortPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                if (view != null && (state == STATE_WORKING)) {
                    view.paint(g);
                }
            }
        };
        sortPanel.setDoubleBuffered(true);
        sortPanel.setBackground(new java.awt.Color(255, 255, 255));

        statusBar = new javax.swing.JPanel();
        elementsLabel = new javax.swing.JLabel();
        elementsValue = new javax.swing.JLabel();
        separator1 = new javax.swing.JSeparator();
        timeLabel = new javax.swing.JLabel();
        timeValue = new javax.swing.JLabel();
        separator2 = new javax.swing.JSeparator();
        complexityLabel = new javax.swing.JLabel();
        complexityValue = new javax.swing.JLabel();
        separator3 = new javax.swing.JSeparator();
        comparisonsLabel = new javax.swing.JLabel();
        comparisonsValue = new javax.swing.JLabel();
        separator4 = new javax.swing.JSeparator();
        swapsLabel = new javax.swing.JLabel();
        swapsValue = new javax.swing.JLabel();

        elementsLabel.setText("Elements:");
        elementsValue.setText("0");
        separator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        timeLabel.setText("Time:");
        timeValue.setText("0s");
        separator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        complexityLabel.setText("Complexity:");
        complexityValue.setText("0");
        separator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        comparisonsLabel.setText("Comparisons");
        comparisonsValue.setText("0");
        separator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        swapsLabel.setText("Swaps:");
        swapsValue.setText("0");

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
                statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(statusBarLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(elementsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(elementsValue)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timeValue)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(separator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(complexityLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(complexityValue)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(separator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comparisonsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comparisonsValue)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(separator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(swapsLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(swapsValue)
                                .addContainerGap())
        );
        statusBarLayout.setVerticalGroup(
                statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusBarLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(separator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(separator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(swapsLabel)
                                        .addComponent(swapsValue)
                                        .addComponent(complexityLabel)
                                        .addComponent(complexityValue)
                                        .addComponent(elementsValue)
                                        .addComponent(elementsLabel)
                                        .addComponent(timeLabel)
                                        .addComponent(timeValue)
                                        .addComponent(comparisonsLabel)
                                        .addComponent(comparisonsValue)
                                        .addComponent(separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(sortPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(4, 4, 4))
                        .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(sortPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(4, 4, 4)
                                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }

    /**
     * Limits the sorting view height
     *
     * @return the value of the <code>maximumSize</code> property
     */
    @Override
    public Dimension getMaximumSize() {
        Dimension d = super.getMaximumSize();
        d.height = 100;
        return d;
    }

    /**
     * Terminate the sort loop
     */
    @Override
    public void sortFinished() {
        isExit = true;
    }

    @Override
    public void componentResized(ComponentEvent event) {
        view.setSize(getWidth(), getHeight());
        sortPanel.repaint();
    }

    @Override
    public void componentMoved(ComponentEvent event) {
    }

    @Override
    public void componentShown(ComponentEvent event) {
    }

    @Override
    public void componentHidden(ComponentEvent event) {
    }

    /**
     * The main sort loop thread
     */
    private volatile Thread mainThread = null;
    /**
     * sorting stop case
     */
    private volatile boolean isExit = false;

    /**
     *
     */
    private final SortCanvas view;
    /**
     *
     */
    private final AbstractSort model;

    /**
     * Sort algorithm name
     */
    private JPanel sortPanel;
    private JPanel statusBar;
    private JLabel elementsLabel;
    /**
     * Array length
     */
    private JLabel elementsValue;
    private JSeparator separator1;
    private JLabel timeLabel;
    private JLabel timeValue;
    private JSeparator separator2;
    private JLabel complexityLabel;
    private JLabel complexityValue;
    private JSeparator separator3;
    private JLabel comparisonsLabel;
    private JLabel comparisonsValue;
    private JSeparator separator4;
    private JLabel swapsLabel;
    private JLabel swapsValue;

    /**
     * Tracks the sort process state.
     */
    private volatile byte state;
    /**
     * Ready for sorting
     */
    private static final byte STATE_READY = 0;
    /**
     * Activate current sort and load data.
     */
    private static final byte STATE_ACTIVATED = 1;
    /**
     * Ticking & rendering current sort
     */
    private static final byte STATE_WORKING = 3;
    /**
     * Deactivate current sort and release resources.
     */
    private static final byte STATE_DEACTIVATED = 5;
}
