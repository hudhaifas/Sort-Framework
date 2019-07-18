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

import com.hudhaifa.sortframework.core.SortCanvas;
import java.awt.Color;

/**
 * Colored visualization of the sort process.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Jul 17, 2019
 * @since sort-framework v1.1
 */
public abstract class ColorCanvas
        extends SortCanvas {

    public ColorCanvas() {
        this(Color.GREEN);
    }

    public ColorCanvas(Color baseColor) {
        super();
        this.baseColor = baseColor;
    }

    @Override
    protected long tickDelay() {
        return 500L;
    }

    protected Color lighten(Color inColor, double inAmount) {
        inAmount = 1.0d - inAmount;
        return new Color((int) Math.min(255, inColor.getRed() + 255 * inAmount),
                (int) Math.min(255, inColor.getGreen() + 255 * inAmount),
                (int) Math.min(255, inColor.getBlue() + 255 * inAmount), inColor.getAlpha());
    }

    protected Color baseColor;

}
