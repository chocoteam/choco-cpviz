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
 * A specialized visualizer for a list of allDifferent constraints.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 14/12/10
 */
public class AllDifferentMatrix extends DomainMatrix {

    private static final String type = "alldifferent_matrix";

    /**
     * Build a visualizer for a list of allDifferent constraints
     *
     * @param vars    domain variables
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public AllDifferentMatrix(IntVar[][] vars, String display, int width, int height) {
        super(vars, type, display, width, height);
    }

    /**
     * Build a visualizer for a list of allDifferent constraints
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
    public AllDifferentMatrix(IntVar[][] vars, String type, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(vars, type, display, x, y, width, height, group, min, max);
    }
}
