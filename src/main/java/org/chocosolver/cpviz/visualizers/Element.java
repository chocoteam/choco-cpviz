/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:48:05Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.cpviz.visualizers;

import org.chocosolver.cpviz.Visualizer;
import org.chocosolver.solver.search.strategy.decision.Decision;
import org.chocosolver.solver.variables.IntVar;

/**
 * A specialized visualizer for a vector the element constraint.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 13/12/10
 */
public final class Element extends Visualizer {

    private static final String type = "element";

    final IntVar index;
    final int[] values;
    final IntVar value;


    /**
     * Build a visualizer for the element constraint
     *
     * @param index   domain variable
     * @param values  collection of values
     * @param value   domain variable
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public Element(IntVar index, int[] values, IntVar value, String display, int width, int height) {
        super(type, display, width, height);
        this.index = index;
        this.values = values;
        this.value = value;
    }

    /**
     * Build a visualizer for the element constraint
     *
     * @param index   domain variable
     * @param values  collection of values
     * @param value   domain variable
     * @param display "expanded" or "compact"
     * @param x       coordinate of the visualizer in the x-axis (horizontal)
     * @param y       coordinate of the visualizer in the y-axis (vertical)
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     * @param group   group name (to group multiple constraints)
     * @param min     expected minimal value of any of the domains
     * @param max     expected maximal value of any of the domains
     */
    public Element(IntVar index, int[] values, IntVar value, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.index = index;
        this.values = values;
        this.value = value;
    }

    @Override
    protected void print(boolean focus, Decision decision) {
        writer.argumentIn(Writer._1, 3).ivar(index, Writer._1, 4).argumentOut(3);

        if (decision != null && decision.getDecisionVariables() == index) {
            if (focus) {
                writer.focus(Writer._1 + Writer._S + Integer.toString(1), group, type);
            } else {
                writer.fail(Writer._1 + Writer._S + Integer.toString(1), group, (Integer) decision.getDecisionValue());
            }
        }
        writer.argumentIn(Writer._2, 3).array(values, 4).argumentOut(3);

        writer.argumentIn(Writer._3, 3).ivar(value, Writer._3, 4).argumentOut(3);
        if (decision != null && decision.getDecisionVariables() == value) {
            if (focus) {
                writer.focus(Writer._3 + Writer._S + Integer.toString(3), group, type);
            } else {
                writer.fail(Writer._3 + Writer._S + Integer.toString(3), group, (Integer) decision.getDecisionValue());
            }
        }
    }
}
