/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:47:56Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.cpviz.visualizers;

import org.chocosolver.cpviz.Visualizer;
import org.chocosolver.solver.search.strategy.decision.Decision;
import org.chocosolver.solver.variables.Variable;

/**
 * A specialized visualizer for a vector of domain variables.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 9 dec. 2010
 */
public class Vector extends Visualizer {
    private static final String type = "vector";

    final Variable[] variables;

    /**
     * Build a visualizer for a vector of domain variables
     *
     * @param vars    domain variables
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public Vector(Variable[] vars, String display, int width, int height) {
        super(type, display, width, height);
        this.variables = vars;
    }

    protected Vector(Variable[] vars, String type, String display, int width, int height) {
        super(type, display, width, height);
        this.variables = vars;
    }

    /**
     * Build a visualizer for a vector of domain variables
     *
     * @param vars    domain variables
     * @param display "expanded" or "compact"
     * @param x       coordinate of the visualizer in the x-axis (horizontal)
     * @param y       coordinate of the visualizer in the y-axis (vertical)
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     * @param group   group name (to group multiple constraints)
     * @param min     expected minimal value of any of the domains
     * @param max     expected maximal value of any of the domains
     */
    public Vector(Variable[] vars, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.variables = vars;
    }

    protected Vector(Variable[] vars, String type, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.variables = vars;
    }

    @Override
    protected void print(boolean focus, Decision decision) {
        writer.arrayDvar(variables, 3);
        for (int i = 0; i < variables.length; i++) {
            if (decision != null && decision == variables[i]) {
                if (focus) {
                    writer.focus(Integer.toString(i + 1), group, type);
                } else {
                    writer.fail(Integer.toString(i + 1), group, (Integer) decision.getDecisionValue());
                }
            }
        }
    }
}
