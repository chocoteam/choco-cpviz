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
 * A specialized visualizer for the bin packing constraint.
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 14/12/10
 */
public class BinPacking extends Visualizer {

    private static final String type = "bin_packing";

    final IntVar[] items;

    final int[] sizes;

    final IntVar[] bins;

    /**
     * Build a visualizer for the bin packing constraint
     *
     * @param items   todo to complete
     * @param sizes   todo to complete
     * @param bins    todo to complete
     * @param display "compact" or "expanded"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public BinPacking(IntVar[] items, int[] sizes, IntVar[] bins, String display, int width, int height) {
        super(type, display, width, height);
        // BEWARE: duplicate first item, as fake object, at position 0, due to iteration starting at 1 in CPViz...
        this.items = new IntVar[items.length + 1];
        this.items[0] = items[0];
        System.arraycopy(items, 0, this.items, 1, items.length);
        // BEWARE: duplicate first size, as fake object, at position 0, due to iteration starting at 1 in CPViz...
        this.sizes = new int[sizes.length + 1];
        this.sizes[0] = sizes[0];
        System.arraycopy(sizes, 0, this.sizes, 1, sizes.length);
        // BEWARE: duplicate first bin, as fake object, at position 0, due to iteration starting at 1 in CPViz...
        this.bins = new IntVar[bins.length + 1];
        this.bins[0] = bins[0];
        System.arraycopy(bins, 0, this.bins, 1, bins.length);
    }

    /**
     * Build a visualizer for the bin packing constraint
     *
     * @param items   todo to complete
     * @param sizes   todo to complete
     * @param bins    todo to complete
     * @param display "expanded" or "compact"
     * @param x       coordinate of the visualizer in the x-axis (horizontal)
     * @param y       coordinate of the visualizer in the y-axis (vertical)
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     * @param group   group name (to group multiple constraints)
     * @param min     expected minimal value of any of the domains
     * @param max     expected maximal value of any of the domains
     */
    public BinPacking(IntVar[] items, int[] sizes, IntVar[] bins, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.items = items;
        this.sizes = sizes;
        this.bins = bins;
    }

    @Override
    protected void print(boolean focus, Decision decision) {
        writer.argumentIn("items", 3).arrayDvar(items, 4).argumentOut(3);
        writer.argumentIn("sizes", 3).array(sizes, 4).argumentOut(3);
        writer.argumentIn("bins", 3).arrayDvar(bins, 4).argumentOut(3);
    }
}
