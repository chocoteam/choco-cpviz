/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:48:05Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.cpviz;

import org.chocosolver.cpviz.visualizers.Writer;
import org.chocosolver.solver.search.strategy.decision.Decision;

import java.io.PrintWriter;

/**
 * An abstract class to define a visulaizer.
 * <code>this</code> defines a constraint and variable visualizer, and its states are logged in the visualization
 * xml file.
 * <p/>
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 9 dec. 2010
 */
public abstract class Visualizer {

    protected Writer writer;

    protected int id;
    protected final String type;
    protected final String display;
    protected int x = -1, y = -1;
    protected final int width, height;
    protected String group;
    protected int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;


    /**
     * Build a visualizer
     *
     * @param type          type of visualizer, must be supported in both sizes
     * @param display       "expanded" or "compact"
     * @param width         width of the visualizer
     * @param height        height of the visualizer
     */
    protected Visualizer(String type, String display, int width, int height) {
        this(type, display, -1, -1, width, height, "", Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Build a visualizer
     *
     * @param type          type of visualizer, must be supported in both sizes
     * @param display       "expanded" or "compact"
     * @param x             coordinate of the visualizer in the x-axis (horizontal)
     * @param y             coordinate of the visualizer in the y-axis (vertical)
     * @param width         width of the visualizer
     * @param height        height of the visualizer
     * @param group         group name (to group multiple constraints)
     * @param min           expected minimal value of any of the domains
     * @param max           expected maximal value of any of the domains
     */
    protected Visualizer(String type, String display, int x, int y, int width, int height, String group, int min, int max) {
        this.type = type;
        this.display = display;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.group = group;
        this.min = min;
        this.max = max;
    }

    /**
     * Set the identifier of <code>this</code>
     *
     * @param id identifier
     */
    protected void setId(int id) {
        this.id = id;
        if (group == null) {
            group = Integer.toString(id);
        }
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Returns the identifier of <code>this</code>
     *
     * @return the identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Set the coordinates of <code>this</code> in the visualizers window (default is [0,0])
     *
     * @param x coordinate on the x-axis (horizontal)
     * @param y coordinate on the y-axis (vertical)
     */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set a group name for <code>this</code>
     *
     * @param group name of the group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Return the group name of <code>this</code>
     *
     * @return group name
     */
    public String getGroup() {
        return group;
    }


    /**
     * Return the type of <code>this</code>
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Return the display policy
     *
     * @return diplay ploicy
     */
    public String getDisplay() {
        return display;
    }

    /**
     * Return the width of <code>this</code> in the visualisers window (default is 500)
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the height of <code>this</code> in the visualizers window (default is 500)
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the expected minimal (resp. maximal) value of any of the domains
     *
     * @param min minimal value
     * @param max maximal value
     */
    public void setMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    protected String options() {
        StringBuilder st = new StringBuilder();
        if (x >= 0 && y >= 0) {
            st.append(" x=\"").append(x).append("\" y=\"").append(y).append("\"");
        }
        if (group != null) {
            st.append(" group=\"").append(group).append("\"");
        }
        if (min > Integer.MIN_VALUE && max < Integer.MAX_VALUE) {
            st.append(" min=\"").append(min).append("\" max=\"").append(max).append("\"");
        }
        return st.toString();
    }

    protected abstract void print(boolean focus, Decision decision);

}
