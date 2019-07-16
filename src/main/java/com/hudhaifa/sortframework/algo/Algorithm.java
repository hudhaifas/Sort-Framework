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
package com.hudhaifa.sortframework.algo;

/**
 * Algorithm class is the base class of all other algorithm implementation to calculate their complexity.
 *
 * @author Hudhaifa Shatnawi <hudhaifa.shatnawi@gmail.com>
 * @version 1.0, Oct 12, 2013 - 5:45:00 PM
 */
public abstract class Algorithm {

    /**
     * Adds a unit to the time complexity.
     */
    public void addUnit() {
        complexity++;
    }

    /**
     * Adds a units to the time complexity.
     *
     * @param units the value of the complexity units.
     */
    public void addUnits(int units) {
        complexity += units;
    }

    /**
     * Returns the time complexity of the algorithm.
     *
     * @return the time complexity of the algorithm.
     */
    public int getTimeCompleixty() {
        return complexity;
    }
    /**
     * Time complexity.
     */
    private int complexity;
}
