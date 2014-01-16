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

import java.awt.Color;
import java.awt.Graphics;

/**
 * This is an implementation of simulating the sorting algorithms in a triangular lines.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 5, 2013 - 9:29:54 PM
 * @since sort-framework v1.0
 */
public class LineApplet
        extends SortApplet {

    @Override
    protected void drawIndex(Graphics g, int i) {
        int y = 2 * i;
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine((int) (ratio * arr[i]), y, this.width, y);

        g.setColor(Color.BLACK);
        g.drawLine(0, y, (int) (ratio * arr[i]), y);
    }

    @Override
    protected void drawCursor(Graphics g, int master, int slave) {
        if (master != -1) {
            g.setColor(Color.RED);
            int y = master * 2;
            g.drawLine(0, y, width, y);
        }

        if (slave != -1) {
            g.setColor(Color.BLUE);
            int y = slave * 2;
            g.drawLine(0, y, width, y);
        }
    }

    @Override
    protected void drawSwaps(Graphics g, int swaps) {
    }

    @Override
    protected void drawComparisons(Graphics g, int comparisons) {
    }
}
