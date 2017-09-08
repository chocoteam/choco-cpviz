/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:47:56Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.cpviz.visualizers;

import org.slf4j.Logger;
import org.chocosolver.cpviz.Visualizer;
import org.chocosolver.solver.search.strategy.decision.Decision;
import org.chocosolver.solver.variables.IntVar;

/**
 * A specialized visualizer for the lex less than constraint.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 14/12/10
 */
public class LexLt extends Visualizer {

    private static final String type = "lex_lt";

    final IntVar[] X, Y;

    /**
     * Build a visualizer for the lex less than constraint
     *
     * @param X       array of domain variables
     * @param Y       array of domain variables
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public LexLt(IntVar[] X, IntVar[] Y, String display, int width, int height) {
        super(type, display, width, height);
        this.X = X;
        this.Y = Y;
    }

    /**
     * Build a visualizer for the lex less than constraint
     *
     * @param X       array of domain variables
     * @param Y       array of domain variables
     * @param display "expanded" or "compact"
     * @param x       coordinate of the visualizer in the x-axis (horizontal)
     * @param y       coordinate of the visualizer in the y-axis (vertical)
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     * @param group   group name (to group multiple constraints)
     * @param min     expected minimal value of any of the domains
     * @param max     expected maximal value of any of the domains
     */
    public LexLt(IntVar[] X, IntVar[] Y, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.X = X;
        this.Y = Y;
    }

    @Override
    protected void print(boolean focus, Decision decision) {
        writer.argumentIn(Writer._1, 3).arrayDvar(X, 4).argumentOut(3);
        writer.argumentIn(Writer._2, 3).arrayDvar(Y, 4).argumentOut(3);

        /*if (decision != null) {
            for (int x = 0; x < X.length; x++) {
                if (decision.getBranchingObject() == X[x]) {
                    if (focus) {
                        writer.focus(1 + "," + Integer.toString(x + 1), group, type);
                    } else {
                        writer.fail(1 + "," + Integer.toString(x + 1), group, decision.getBranchingValue());
                    }
                }
            }
            for (int y = 0; y < Y.length; y++) {
                if (decision.getBranchingObject() == Y[y]) {
                    if (focus) {
                        writer.focus(2 + "," + Integer.toString(y + 1), group, type);
                    } else {
                        writer.fail(2 + "," + Integer.toString(y + 1), group, decision.getBranchingValue());
                    }
                }
            }
        }*/
    }
}
