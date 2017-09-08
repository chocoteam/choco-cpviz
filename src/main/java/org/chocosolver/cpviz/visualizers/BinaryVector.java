/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:46:36Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.cpviz.visualizers;

import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

/**
 * A specialized visualizer for a vector of 0/1 variables.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 13/12/10
 */
public final class BinaryVector extends Vector {


    private static final String type = "binary_vector";

    /**
     * Build a visualizer for a vector of 0/1 variables
     *
     * @param bool    0/1 variables
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public BinaryVector(IntVar[] bool, String display, int width, int height) {
        super(bool, type, display, width, height);
        check(bool);
    }

    /**
     * Build a visualizer for a vector of 0/1 variables
     *
     * @param bool    0/1 variables
     * @param display "expanded" or "compact"
     * @param x       coordinate of the visualizer in the x-axis (horizontal)
     * @param y       coordinate of the visualizer in the y-axis (vertical)
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     * @param group   group name (to group multiple constraints)
     * @param min     expected minimal value of any of the domains
     * @param max     expected maximal value of any of the domains
     */
    public BinaryVector(IntVar[] bool, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(bool, type, display, x, y, width, height, group, min, max);
        check(bool);
    }

    private static void check(IntVar[] bool) {
        for (IntVar b : bool) {
            BoolVar bv = (BoolVar) b;
        }
    }
}
