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
package com.hudhaifa.sortframework.app;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Jul 17, 2019
 * @since sort-framework v1.1
 */
public class HColorCanvas
        extends ColorCanvas {

    public HColorCanvas() {
        super();
    }

    public HColorCanvas(Color baseColor) {
        super(baseColor);
    }

    @Override
    protected void drawIndex(Graphics2D g, int[] arr, int i) {
        if (baseColor == null) {
            return;
        }

        float x = (int) (i * getElementWidth());
        g.setColor(lighten(baseColor, arr[i] / (float) arr.length));
        fillElement(g, x, 0, getElementWidth(), getHeight());
    }

    @Override
    protected void drawCursor(Graphics2D g, int master, int slave) {
        if (master != -1) {
            g.setColor(Color.RED);
            float x = (int) (master * getElementWidth());
            drawElement(g, x, 0, getElementWidth(), getHeight());
        }

        if (slave != -1) {
            g.setColor(Color.BLUE);
            float x = (int) (slave * getElementWidth());
            drawElement(g, x, 0, getElementWidth(), getHeight());
        }
    }

}
