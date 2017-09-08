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
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

/**
 * A specialized visualizer for the boolean channeling constraint.
 * (in Choco: domainChanneling)
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 13/12/10
 */
public final class BoolChanneling extends Visualizer {

    private static final String type = "bool_channeling";

    final IntVar var;

    final BoolVar[] bool;

    final int offset;

    /**
     * Build a visualizer for the boolean channeling constraint
     *
     * @param var     domain variable
     * @param bool    collection of boolean variables
     * @param offset  starting value
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public BoolChanneling(IntVar var, IntVar[] bool, int offset, String display, int width, int height) {
        super(type, display, width, height);
        this.var = var;
        this.bool = new BoolVar[bool.length];
        for (int b = 0; b < bool.length; b++) {
            this.bool[b] = (BoolVar) bool[b];
        }
        this.offset = offset;
    }

    /**
     * Build a visualizer for the boolean channeling constraint
     *
     * @param var     domain variable
     * @param bool    collection of boolean variables
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public BoolChanneling(IntVar var, IntVar[] bool, String display, int width, int height) {
        super(type, display, width, height);
        this.var = var;
        this.bool = new BoolVar[bool.length];
        for (int b = 0; b < bool.length; b++) {
            this.bool[b] = (BoolVar) bool[b];
        }
        this.offset = var.getLB();
    }

    /**
     * Build a visualizer for the boolean channeling constraint
     *
     * @param var     domain variable
     * @param bool    collection of boolean variables
     * @param offset  starting value
     * @param display "expanded" or "compact"
     * @param x       coordinate of the visualizer in the x-axis (horizontal)
     * @param y       coordinate of the visualizer in the y-axis (vertical)
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     * @param group   group name (to group multiple constraints)
     * @param min     expected minimal value of any of the domains
     * @param max     expected maximal value of any of the domains
     */
    public BoolChanneling(IntVar var, IntVar[] bool, int offset, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.var = var;
        this.bool = new BoolVar[bool.length];
        for (int b = 0; b < bool.length; b++) {
            this.bool[b] = (BoolVar) bool[b];
        }
        this.offset = offset;
    }

    /**
     * Build a visualizer for the boolean channeling constraint
     *
     * @param var     domain variable
     * @param bool    collection of boolean variables
     * @param display "expanded" or "compact"
     * @param x       coordinate of the visualizer in the x-axis (horizontal)
     * @param y       coordinate of the visualizer in the y-axis (vertical)
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     * @param group   group name (to group multiple constraints)
     * @param min     expected minimal value of any of the domains
     * @param max     expected maximal value of any of the domains
     */
    public BoolChanneling(IntVar var, IntVar[] bool, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.var = var;
        this.bool = new BoolVar[bool.length];
        for (int b = 0; b < bool.length; b++) {
            this.bool[b] = (BoolVar) bool[b];
        }
        this.offset = var.getLB();
    }

    @Override
    protected void print(boolean focus, Decision decision) {
        writer.argumentIn(Writer._1, 3).ivar(var, Writer._1, 4).argumentOut(3);

        if (decision != null && decision.getDecisionVariables() == var) {
            if (focus) {
                writer.focus(Writer._1 + Writer._S + Integer.toString(1), group, type);
            } else {
                writer.fail(Writer._1 + Writer._S + Integer.toString(1), group, (Integer) decision.getDecisionValue());
            }
        }

        writer.argumentIn(Writer._2, 3).arrayDvar(bool, 4).argumentOut(3);

        if (decision != null) {
            for (int i = 0; i < bool.length; i++) {
                if (decision.getDecisionVariables() == bool[i]) {
                    if (focus) {
                        writer.focus(Writer._3 + Writer._S + Integer.toString(i + 1), group, type);
                        break;
                    } else {
                        writer.fail(Writer._3 + Writer._S + Integer.toString(i + 1), group, (Integer) decision.getDecisionValue());
                        break;
                    }
                }
            }
        }
        writer.argumentIn(Writer._3, 3).integer(offset, Writer._3, 4).argumentOut(3);
    }
}
