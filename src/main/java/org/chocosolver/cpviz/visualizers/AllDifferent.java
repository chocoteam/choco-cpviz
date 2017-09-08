/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:47:56Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.cpviz.visualizers;

import org.chocosolver.solver.variables.IntVar;

/**
 * A specialized visualizer for the allDifferent constraint.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 13/12/10
 */
public final class AllDifferent extends Vector {


    private static final String type = "alldifferent";

    /**
     * Build a visualizer for the allDifferent constraint
     *
     * @param vars    domain variables
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public AllDifferent(IntVar[] vars, String display, int width, int height) {
        super(vars, type, display, width, height);
    }

    /**
     * Build a visualizer for the allDifferent constraint
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
    public AllDifferent(IntVar[] vars, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(vars, type, display, x, y, width, height, group, min, max);
    }
}
