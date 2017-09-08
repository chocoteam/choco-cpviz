/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:46:36Z, IMT Atlantique. All rights reserved.
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
 * A specialized visualizer for the Cumulative constraint
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 14/12/10
 */
public class Cumulative extends Visualizer {
    private static final String type = "cumulative";

    final IntVar[] starts;
    final IntVar[] durations;

    final IntVar limit;

    final IntVar end;

    /**
     * Build a visualizer for the cumulative constraint
     *
     * @param starts    start variables
     * @param durations duration variables
     * @param limit     domain variable
     * @param end       domain variable
     * @param display   "compact", "expanded" or "gantt"
     * @param width     width of the visualizer
     * @param height    height of the visualizer
     */
    public Cumulative(IntVar[] starts, IntVar[] durations, IntVar limit, IntVar end, String display, int width, int height) {
        super(type, display, width, height);
        this.starts = starts;
        this.durations = durations;
        this.limit = limit;
        this.end = end;
    }

    /**
     * Build a visualizer for the cumulative constraint
     *
     * @param starts    start variables
     * @param durations duration variables
     * @param limit     domain variable
     * @param end       domain variable
     * @param display   "compact", "expanded" or "gantt"
     * @param x         coordinate of the visualizer in the x-axis (horizontal)
     * @param y         coordinate of the visualizer in the y-axis (vertical)
     * @param width     width of the visualizer
     * @param height    height of the visualizer
     * @param group     group name (to group multiple constraints)
     * @param min       expected minimal value of any of the domains
     * @param max       expected maximal value of any of the domains
     */
    public Cumulative(IntVar[] starts, IntVar[] durations, IntVar limit, IntVar end, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.starts = starts;
        this.durations = durations;
        this.limit = limit;
        this.end = end;
    }

    @Override
    protected void print(boolean focus, Decision decision) {
        writer.argumentIn("tasks", 3);
        for (int i = 0; i < starts.length; i++) {
            writer.tupleIn(Integer.toString(i + 1), 4)
                    .ivar(starts[i], "start", 5)
                    .ivar(durations[i], "dur", 5)
                    .integer(1, "res", 5)
                    .tupleOut(4);
        }
        writer.argumentOut(3);

        writer.argumentIn("limit", 3).ivar(limit, Writer._1, 4).argumentOut(3);
        writer.argumentIn("end", 3).ivar(end, Writer._1, 4).argumentOut(3);
    }
}
