/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs.applet;

import java.awt.Color;

/**
 *
 * @author Hudhaifa Shatnawi
 */
public abstract class ColorApplet
        extends SortApplet {

    /**
     * Initializes the applet SortApplets
     */
    @Override
    public void init() {
        try {
            this.baseColor = Color.decode(getParameter("color"));
        } catch (NumberFormatException e) {
            this.baseColor = Color.YELLOW;
        }
        super.init();
    }

    protected Color lighten(Color inColor, double inAmount) {
        inAmount = 1.0d - inAmount;
        return new Color((int) Math.min(255, inColor.getRed() + 255 * inAmount),
                (int) Math.min(255, inColor.getGreen() + 255 * inAmount),
                (int) Math.min(255, inColor.getBlue() + 255 * inAmount), inColor.getAlpha());
    }
    protected Color baseColor;
}
