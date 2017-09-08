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
import org.slf4j.Logger;
import org.chocosolver.solver.search.strategy.decision.Decision;
import org.chocosolver.solver.variables.IntVar;

/**
 * A sepcialized visualizer for the Inverse constraint.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 14/12/10
 */
public class Inverse extends Visualizer {

    private static final String type = "inverse";

    final IntVar[] X, Y;

    /**
     * Build a sepcialized visualizer for the Inverse constraint.
     *
     * @param X       domain variables
     * @param Y       domain variables
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public Inverse(IntVar[] X, IntVar[] Y, String display, int width, int height) {
        super(type, display, width, height);
        this.X = X;
        this.Y = Y;
    }

    /**
     * Build a sepcialized visualizer for the Inverse constraint.
     *
     * @param X       domain variables
     * @param Y       domain variables
     * @param display "expanded" or "compact"
     * @param x       coordinate of the visualizer in the x-axis (horizontal)
     * @param y       coordinate of the visualizer in the y-axis (vertical)
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     * @param group   group name (to group multiple constraints)
     * @param min     expected minimal value of any of the domains
     * @param max     expected maximal value of any of the domains
     */
    public Inverse(IntVar[] X, IntVar[] Y, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.X = X;
        this.Y = Y;
    }

    @Override
    protected void print(boolean focus, Decision decision) {
        writer.argumentIn("1", 3).arrayDvar(X, 4).argumentOut(3);
        writer.argumentIn("2", 3).arrayDvar(X, 4).argumentOut(3);
        // CPVis#inverse do not deal with focus & fail
    }
}
