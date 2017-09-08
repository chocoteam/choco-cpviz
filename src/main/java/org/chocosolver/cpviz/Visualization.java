/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:46:36Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.cpviz;

import org.chocosolver.cpviz.visualizers.Writer;
import org.chocosolver.memory.IStateLong;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.search.loop.monitors.*;
import org.chocosolver.solver.search.strategy.decision.Decision;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.SetVar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.chocosolver.cpviz.CPVizConstant.*;
import static org.chocosolver.cpviz.Show.TREE;
import static org.chocosolver.cpviz.Show.VIZ;

/**
 * A class to produce log files of a problem resolution.
 * <br/>
 * It creates 3 files:<br/>
 * - configuration.xml <br/>
 * - tree.xml <br/>
 * - visualization.xml <br/>
 * respecting the log formats defined by Helmut Simonis.
 * <br/>
 * These files are created at the root directory of the project (see logback.xml for more details).
 * <br/>
 * These files can be treaten with <a href="https://sourceforge.net/projects/cpviz/">cpviz<a/>
 * <p/>
 * <br/>
 * <p/>
 * Before calling the solve step of the program, one can create a new instance of <code>Visualization</code>.
 * <p/>
 * This object provides 3 main services:<br/>
 * - {@code createTree()}: declare the tree search visualizer<br/>
 * - {@code createViz()}: declare the constraint and variable visualizers container<br/>
 * - {@code addVisualizer(Visualizervisualizer)}: add a visualizer to the container<br/>
 * - {@code close()} : close the log files
 * <p/>
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 9 dec. 2010
 */

// BEWARE: indices must start at 1
public class Visualization implements IMonitorClose, IMonitorInitialize,
        IMonitorDownBranch, IMonitorContradiction, IMonitorSolution {

    private String pbid;

    private String dir;

    protected PrintWriter configuration;

    protected PrintWriter tree;

    protected PrintWriter visualization;

    protected Writer writer;

    public int trace_tools = 0;

    List<Visualizer> visualizers;

    private long node_id = -1;

    private IStateLong parent_id;

    private long state_id = 0;

    //Decision currentDecision;

    final Solver solver;

    boolean hasFailed = false;

    /**
     * Build a new instance of <code>Visualization</code>.
     *
     * @param solver associated solver
     * @param dir    output directory
     */
    public Visualization(Solver solver, String dir) {
        this(Long.toString(new Random().nextLong()), solver, dir);
    }

    /**
     * Build a new instance of <code>Visualization</code>.
     *
     * @param pbname name of the treated problem (suffix of log files)
     * @param solver associated solver
     * @param dir    output directory
     */
    public Visualization(String pbname, Solver solver, String dir) {
        this.pbid = pbname;
        this.dir = dir;
        this.solver = solver;
        this.parent_id = this.solver.getEnvironment().makeLong();
        try {
            configuration = new PrintWriter(new File(dir, "configuration-" + pbid + ".xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        configuration.printf(CPVizConstant.C_CONF_TAG_IN, dir, pbid);
        solver.plugMonitor(this);
    }

    /**
     * Declare the tree search visualization.<br/>
     * Append to the configuration xml file
     *
     * @param type    "layout", "graph" or "values"
     * @param display "compact" or "expanded"
     * @param repeat  "all", "final", "i" or "-i"
     * @param width   width of SVG canvas in screen pixels
     * @param height  height of SVG canvas in screen pixels
     */
    public void createTree(String type, String display, String repeat, int width, int height) {
        if (configuration != null) {
            if ((trace_tools & Show.TREE.mask) == 0) {
                trace_tools += Show.TREE.mask;
                try {
                    tree = new PrintWriter(new File(dir, "tree-" + pbid + ".xml"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (tree != null) {
                    configuration.printf(C_TOOL_TAG,
                            TREE, type, display, repeat, Integer.toString(width), Integer.toString(height), "tree-" + pbid);
                    tree.printf(HEADER);
                    tree.printf(T_TREE_TAG_IN);
                } else {
                    throw new UnsupportedOperationException("Unable to create tree");
                }
            }
        } else {
            throw new UnsupportedOperationException("Configuration is null");
        }

    }

    /**
     * Declare the tree search visualization, with default construction parameters.<br/>
     * - type : "layout" <br/>
     * - display : "compact" <br/>
     * - repeat: "all" <br/>
     * - width : 500 <br/>
     * - height : 500 <br/>
     * Append to the configuration xml file.
     */
    public void createTree() {
        createTree(CPVizConstant.LAYOUT, CPVizConstant.COMPACT, CPVizConstant.ALL, 500, 500);
    }

    /**
     * Declare the constraint and variable visualizers container.<br/>
     * Append to the configuration xml file
     *
     * @param type    "layout"
     * @param display "compact" or "expanded"
     * @param repeat  "all", "final", "i" or "-i"
     * @param width   width of SVG canvas in screen pixels
     * @param height  height of SVG canvas in screen pixels
     */
    public void createViz(String type, String display, String repeat, int width, int height) {
        visualizers = new ArrayList<>(8);
        if (configuration != null) {
            if ((trace_tools & Show.VIZ.mask) == 0) {
                trace_tools += Show.VIZ.mask;
                try {
                    visualization = new PrintWriter(new File(dir, "visualization-" + pbid + ".xml"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (visualization != null) {
                    writer = new Writer(visualization);
                    configuration.printf(C_TOOL_TAG,
                            VIZ, type, display, repeat, Integer.toString(width), Integer.toString(height), "visualization-" + pbid);
                    visualization.printf(HEADER);
                    visualization.printf(V_VISUALIZATION_TAG_IN);
                } else {
                    throw new UnsupportedOperationException("Cannot create viz");
                }
            }
        } else {
            throw new UnsupportedOperationException("Cannot create viz");
        }
    }

    /**
     * Declare the constraint and variable visualizers container, with default construction parameters.<br/>
     * - type : "layout" <br/>
     * - display : "compact" <br/>
     * - repeat: "all" <br/>
     * - width : 500 <br/>
     * - height : 500 <br/>
     * Append to the configuration xml file
     */
    public void createViz() {
        createViz(CPVizConstant.LAYOUT, CPVizConstant.COMPACT, CPVizConstant.ALL, 500, 500);
    }

    /**
     * Add a constraint/variable visualizer to the container
     *
     * @param visualizer the visualizer to add
     */
    public void addVisualizer(Visualizer visualizer) {
        visualizers.add(visualizer);
        visualizer.setWriter(writer);
        visualizer.setId(visualizers.size());
        if (visualization != null) {
            visualization.printf(CPVizConstant.V_VISUALIZER_TAG,
                    visualizer.getId(), visualizer.getType(), visualizer.getDisplay(),
                    visualizer.getWidth(), visualizer.getHeight(),
                    visualizer.options());
        }
    }


    @Override
    public void beforeClose() {
        if (configuration != null) {
            configuration.printf(C_CONF_TAG_OUT);
            configuration.close();
        }
        if (tree != null) {
            tree.printf(T_TREE_TAG_OUT);
            tree.close();
        }
        if (visualization != null) {
            visualization.printf(V_VISUALIZATION_TAG_OUT);
            visualization.close();
        }
    }

    @Override
    public void afterClose() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //****************************************************************************************************************//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void beforeInitialize() {
        node_id = 0;
        parent_id.set(0);
        state_id = 1;

        if (tree != null) {
            tree.printf(T_ROOT_TAG);
        }
        if (visualization != null) {
            printVisualizerStat(state_id, -1, false, null);
        }
        state_id++;
    }

    @Override
    public void afterInitialize() {
        if (visualization != null) {
            printVisualizerStat(state_id, 0, false, null);
        }
        state_id++;
    }

    @Override
    public void beforeDownBranch(boolean left) {
    }

    @Override
    public void afterDownBranch(boolean left) {
        node();
    }

    @Override
    public void onContradiction(ContradictionException cex) {
        this.hasFailed = true;
    }

    void node() {
        node_id++;
        Decision currentDecision = solver.getSearchLoop().getLastDecision();
        if (tree != null) {
            Object bo = currentDecision.getDecisionVariables();
            String name = bo.toString();
            String dsize = "?";
            if (bo instanceof IntVar) {
                IntVar ivar = (IntVar) bo;
                name = ivar.getName();
                dsize = Integer.toString(ivar.getDomainSize());
            } else if (bo instanceof SetVar) {
                SetVar svar = (SetVar) bo;
                name = svar.getName();
                dsize = Integer.toString(svar.getEnvelopeSize());
            }
            if (hasFailed) {
                hasFailed = false;
                tree.printf(CPVizConstant.T_FAIL_TAG, node_id, parent_id.get(), name, dsize,
                        currentDecision.getDecisionValue());
            } else {
                tree.printf(CPVizConstant.T_TRY_TAG, node_id, parent_id.get(), name, dsize, currentDecision.getDecisionValue());
            }
            if (visualization != null) {
                printVisualizerStat(state_id, node_id, !hasFailed, currentDecision);
            }
            hasFailed = false;
            parent_id.set(node_id);
        }
        state_id++;
    }

    @Override
    public void onSolution() {
        if (tree != null) {
            tree.printf(CPVizConstant.T_SUCC_TAG, node_id);
        }
    }

    private void printVisualizerStat(long s_id, long n_id, Boolean focus, Decision currentDecision) {
        if (visualization != null && visualizers != null) {
            visualization.printf(V_STATE_TAG_IN, s_id, n_id);
            for (int i = 0; i < visualizers.size(); i++) {
                Visualizer vv = visualizers.get(i);
                visualization.printf(V_VISUALIZER_STATE_TAG_IN, vv.getId());
                vv.print(focus, currentDecision);
                visualization.printf(V_VISUALIZER_STATE_TAG_OUT);
            }
            visualization.printf(V_STATE_TAG_OUT);
        }
    }

}
