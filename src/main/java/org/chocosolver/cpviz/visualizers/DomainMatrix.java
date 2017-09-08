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
import org.chocosolver.solver.search.strategy.decision.Decision;
import org.chocosolver.solver.variables.Variable;

/**
 * ,
 * A specialized visualizer for a matrix of domain variables
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 14/12/10
 */
public class DomainMatrix extends Visualizer {

    private static final String type = "domain_matrix";

    final Variable[][] vars;

    /**
     * Build a visualizer for a matrix of domain variables
     *
     * @param vars    domain variables
     * @param display "expanded" or "compact"
     * @param width   width of the visualizer
     * @param height  height of the visualizer
     */
    public DomainMatrix(Variable[][] vars, String display, int width, int height) {
        super(type, display, width, height);
        this.vars = vars;
    }

    protected DomainMatrix(Variable[][] vars, String type, String display, int width, int height) {
        super(type, display, width, height);
        this.vars = vars;
    }

    /**
     * Build a visualizer for a matrix of domain variables
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
    public DomainMatrix(Variable[][] vars, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.vars = vars;
    }

    public DomainMatrix(Variable[][] vars, String type, String display, int x, int y, int width, int height, String group, int min, int max) {
        super(type, display, x, y, width, height, group, min, max);
        this.vars = vars;
    }

    @Override
    protected void print(boolean focus, Decision decision) {
        for (int i = 0; i < vars.length; i++) {
            for (int j = 0; j < vars[i].length; j++) {
                writer.var(vars[i][j], (i + 1) + " " + (j + 1), 3);
            }
        }
        if (decision != null) {
            for (int i = 0; i < vars.length; i++) {
                for (int j = 0; j < vars[i].length; j++) {
                    if (decision.getDecisionVariables()== vars[i][j]) {
                        if (focus) {
                            writer.focus((i + 1) + Writer._S + (j + 1), group);
                        } else {
                            writer.fail((i + 1) + Writer._S + (j + 1), group, (Integer)decision.getDecisionValue());
                        }
                    }
                }
            }
        }
    }
}
