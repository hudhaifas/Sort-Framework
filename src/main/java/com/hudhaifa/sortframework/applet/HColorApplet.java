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
package com.hudhaifa.sortframework.applet;

import com.hudhaifa.sortframework.util.ArrayUtil;
import java.awt.Color;
import java.awt.Graphics;

/**
 * This is an implementation of simulating the sorting algorithm in a horizontal bar.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 5, 2013 - 9:29:54 PM
 * @since sort-framework v1.0
 */
public class HColorApplet
        extends ColorApplet {

    @Override
    protected void drawIndex(Graphics g, int i) {
        if (baseColor == null) {
            return;
        }
        int x = i * 2;
        g.setColor(lighten(baseColor, arr[i] / (double) arr.length));
        g.fillRect(x, 0, 2, height);
    }

    @Override
    protected void fill() {
        switch (fill) {
            case ArrayUtil.FILL_RANDOM:
                this.arr = ArrayUtil.random(width / 2);
                break;

            case ArrayUtil.FILL_REVERSE:
                this.arr = ArrayUtil.reversed(width / 2);
                break;

            case ArrayUtil.FILL_NEARLY_SORTED:
                this.arr = ArrayUtil.nearlySorted(width / 2, 3);
                break;

            case ArrayUtil.FILL_REPEATED:
                this.arr = ArrayUtil.repeated(width / 2, 15);
                break;
        }
    }

    @Override
    protected void drawCursor(Graphics g, int master, int slave) {
        if (master != -1) {
            g.setColor(Color.RED);
            int x = master * 2;
            g.fillRect(x, 0, 2, height);
        }

        if (slave != -1) {
            g.setColor(Color.BLUE);
            int x = slave * 2;
            g.fillRect(x, 0, 2, height);
        }
    }

    @Override
    protected void drawSwaps(Graphics g, int swaps) {
    }

    @Override
    protected void drawComparisons(Graphics g, int comparisons) {
    }
}
