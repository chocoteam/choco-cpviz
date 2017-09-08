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
 * A specialized visualizer for the global cardinality constraint.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 14/12/10
 */
public class Gcc extends Visualizer {

    private static final String type = "gcc";

    final IntVar[] vars;
    final int[] values, low, high;

    /**
     * Build a visualizer for the global cardinality constraint
     *
     * @param vars    domain variables
     * @param values  collection of values
     * @param low     minimum number of occurrences of values
     * @param high    maximum number of occurrences
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public Gcc(IntVar[] vars, int[] values, int[] low, int[] high, String display, int width, int height) {
        super(type, display, width, height);
        this.vars = vars;
        this.values = values;
        this.low = low;
        this.high = high;
    }

    /**
     * Build a visualizer for the global cardinality constraint
     *
     * @param vars    domain variables
     * @param values  collection of values
     * @param low     minimum number of occurrences of values
     * @param high    maximum number of occurrences
     * @param display "expanded" or "compact"
     * @param x       coordinate of the visualizer in the x-axis (horizontal)
     * @param y       coordinate of the visualizer in the y-axis (vertical)
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     * @param group   group name (to group multiple constraints)
     * @param min     expected minimal value of any of the domains
     * @param max     expected maximal value of any of the domains
     */
    public Gcc(IntVar[] vars, int[] values, int[] low, int[] high, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.vars = vars;
        this.values = values;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void print(boolean focus, Decision decision) {
        writer.argumentIn("1", 3);
        for (int i = 0; i < values.length; i++) {
            writer.tupleIn(Integer.toString(i + 1), 4)
                    .integer(values[i], "value", 5)
                    .integer(low[i], "low", 5)
                    .integer(high[i], "high", 5)
                    .tupleOut(4);
        }
        writer.argumentOut(3);

        writer.argumentIn("2", 3).arrayDvar(vars, 4).argumentOut(3);
    }
}
