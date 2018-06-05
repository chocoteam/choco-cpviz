/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:48:05Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.cpviz;

import org.chocosolver.cpviz.visualizers.*;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;
import org.testng.annotations.Test;

/**
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 6 dec. 2010
 */
public class CPVizTest {

    private String dir = ".";//System.getProperty("java.io.tmpdir");

    @Test(groups = "1s")
    public void testNoLog() {
        int n = 4;

        Model m = new Model();
        Solver s = m.getSolver();
        IntVar[] Q = m.intVarArray("Q", n, 1, n, false);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int k = j - i;
                m.post(m.arithm(Q[i], "!=", Q[j]));
                m.post(m.arithm(Q[i], "!=", Q[j], "+", k));
                m.post(m.arithm(Q[i], "!=", Q[j], "-", k));
            }
        }
        s.findAllSolutions();
    }

    @Test(groups = "1s")
    public void testVector() {
        int n = 4;

        Model m = new Model();
        Solver s = m.getSolver();
        IntVar[] Q = m.intVarArray("Q", n, 1, n, false);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int k = j - i;
                m.post(m.arithm(Q[i], "!=", Q[j]));
                m.post(m.arithm(Q[i], "!=", Q[j], "+", k));
                m.post(m.arithm(Q[i], "!=", Q[j], "-", k));
            }
        }


        Visualization visu = new Visualization("Vector", s, dir );

        visu.createTree();
        visu.createViz();

        Vector vector = new Vector(Q, "expanded", n, n);
        vector.setMinMax(1, n);

        visu.addVisualizer(vector);

        s.findAllSolutions();


    }

    @Test(groups = "1s")
    public void testVectorSize() {
        int n = 13;

        Model m = new Model();
        Solver s = m.getSolver();
        IntVar[] Q = m.intVarArray("Q", n, 1, n, false);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int k = j - i;
                m.post(m.arithm(Q[i], "!=", Q[j]));
                m.post(m.arithm(Q[i], "!=", Q[j], "+", k));
                m.post(m.arithm(Q[i], "!=", Q[j], "-", k));
            }
        }


        Visualization visu = new Visualization("VectorSize", s, dir );

        visu.createTree();
        visu.createViz();

        VectorSize vector = new VectorSize(Q, "expanded", n, n);
        vector.setMinMax(1, n);

        visu.addVisualizer(vector);

        s.findSolution();


    }

    @Test(groups = "1s")
    public void testVectorWaterfall() {
        int n = 4;

        Model m = new Model();
        Solver s = m.getSolver();
        IntVar[] Q = m.intVarArray("Q", n, 1, n, false);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int k = j - i;
                m.post(m.arithm(Q[i], "!=", Q[j]));
                m.post(m.arithm(Q[i], "!=", Q[j], "+", k));
                m.post(m.arithm(Q[i], "!=", Q[j], "-", k));
            }
        }


        Visualization visu = new Visualization("VectorWaterfall", s, dir );

        visu.createTree();
        visu.createViz();

        VectorWaterfall visualizer = new VectorWaterfall(Q, "expanded", n, n);
        visualizer.setMinMax(1, n);

        visu.addVisualizer(visualizer);

        s.findAllSolutions();


    }

    @Test(groups = "1s")
    public void testAllDifferent() {
        IntVar S, E, N, D, M, O, R, Y;
        Model m = new Model();
        Solver s = m.getSolver();

        S = m.intVar("S", 0, 9, false);
        E = m.intVar("E", 0, 9, false);
        N = m.intVar("N", 0, 9, false);
        D = m.intVar("D", 0, 9, false);
        M = m.intVar("M", 0, 9, false);
        O = m.intVar("0", 0, 9, false);
        R = m.intVar("R", 0, 9, false);
        Y = m.intVar("Y", 0, 9, false);

        m.post(m.arithm(S, "!=", 0));
        m.post(m.arithm(M, "!=", 0));
        m.post(m.arithm(S, "!=", 0));
        m.post(m.arithm(M, "!=", 0));
        m.post(m.allDifferent(new IntVar[]{S, E, N, D, M, O, R, Y}, "BC"));


        IntVar[] ALL = new IntVar[]{
                S, E, N, D,
                M, O, R, E,
                M, O, N, E, Y};
        int[] COEFFS = new int[]{
                1000, 100, 10, 1,
                1000, 100, 10, 1,
                -10000, -1000, -100, -10, -1
        };
        m.post(m.scalar(ALL, COEFFS, "=", m.intVar(0)));

        Visualization visu = new Visualization("AllDifferent", s, dir);

        visu.createTree();
        visu.createViz();

        Vector visualizer = new Vector(ALL, "expanded", 0, 0, 8, 10, "SENDMORY", 0, 9);

        visu.addVisualizer(visualizer);

        s.findSolution();


    }

    @Test(groups = "1s")
    public void testElement() {
        Model m = new Model();
        Solver s = m.getSolver();
        int[] values = new int[]{1, 2, 0, 4, -10};
        IntVar index = m.intVar("index", -3, 10, false);
        IntVar value = m.intVar("value", -20, 20, false);
        m.post(m.element(value, values, index, 0));

        Visualization visu = new Visualization("Element", s, dir );

        visu.createTree();
        visu.createViz();

        Element visualizer = new Element(index, values, value, "expanded", 13, 40);
        visualizer.setMinMax(-20, 20);

        visu.addVisualizer(visualizer);
        s.findSolution();
    }

    @Test(groups = "1s")
    public void testBinaryVector() {
        Model m = new Model();
        Solver s = m.getSolver();
        IntVar var = m.intVar("var", 1, 8, false);
        BoolVar[] bool = m.boolVarArray("b", 8);
        m.post(m.boolsIntChanneling(bool, var, 0));

        Visualization visu = new Visualization("BinaryVector", s, dir );

        visu.createTree();
        visu.createViz();


        BinaryVector visualizer = new BinaryVector(bool, "expanded", 8, 8);
        visualizer.setMinMax(0, 8);

        visu.addVisualizer(visualizer);

        s.findSolution();


    }

    @Test(groups = "1s")
    public void testDomainMatrix() {
        Model m = new Model();
        Solver s = m.getSolver();
        int n = 3;
        final int ub = n * n;
        final int ms = n * (n * n + 1) / 2;
        IntVar[][] vars = m.intVarMatrix("v", n, n, 1, ub, false);
        // All cells of the matrix must be different
        m.allDifferent(ArrayUtils.flatten(vars), "BC").post();
        final IntVar[] varDiag1 = new IntVar[n];
        final IntVar[] varDiag2 = new IntVar[n];
        for (int i = 0; i < n; i++) {
            // All rows must be equal to the magic sum
            m.post(m.sum(vars[i], "=", m.intVar(ms)));
            // All columns must be equal to the magic sum
            m.post(m.sum(ArrayUtils.getColumn(vars, i), "=", m.intVar(ms)));
            //record diagonals variable
            varDiag1[i] = vars[i][i];
            varDiag2[i] = vars[(n - 1) - i][i];
        }
        // Every diagonal have to be equal to the magic sum
        m.post(m.sum(varDiag1, "=", m.intVar(ms)));
        m.post(m.sum(varDiag2, "=", m.intVar(ms)));
        //symmetry breaking constraint: enforce that the upper left corner contains the minimum corner value.
        m.post(m.arithm(vars[0][0], "<", vars[0][n - 1]));
        m.post(m.arithm(vars[0][0], "<", vars[n - 1][n - 1]));
        m.post(m.arithm(vars[0][0], "<", vars[n - 1][0]));

        Visualization visu = new Visualization("DomainMatrix", s, dir );

        visu.createTree();
        visu.createViz();

        DomainMatrix visualizer = new DomainMatrix(vars, "expanded", 3, 3);
        visualizer.setMinMax(1, ub);

        visu.addVisualizer(visualizer);

        s.findSolution();


    }

    @Test(groups = "1s")
    public void testAllDifferentMatrix() {
        Model m = new Model();
        Solver s = m.getSolver();
        int n = 3;
        final int ub = n * n;
        final int ms = n * (n * n + 1) / 2;
        IntVar[][] vars = m.intVarMatrix("v", n, n, 1, ub, false);
        // All cells of the matrix must be different
        m.post(m.allDifferent(ArrayUtils.flatten(vars), "BC"));
        final IntVar[] varDiag1 = new IntVar[n];
        final IntVar[] varDiag2 = new IntVar[n];
        for (int i = 0; i < n; i++) {
            // All rows must be equal to the magic sum
            m.post(m.sum(vars[i], "=", m.intVar(ms)));
            // All columns must be equal to the magic sum
            m.post(m.sum(ArrayUtils.getColumn(vars, i), "=", m.intVar(ms)));
            //record diagonals variable
            varDiag1[i] = vars[i][i];
            varDiag2[i] = vars[(n - 1) - i][i];
        }
        // Every diagonal have to be equal to the magic sum
        m.post(m.sum(varDiag1, "=", m.intVar(ms)));
        m.post(m.sum(varDiag2, "=", m.intVar(ms)));
        //symmetry breaking constraint: enforce that the upper left corner contains the minimum corner value.
        m.post(m.arithm(vars[0][0], "<", vars[0][n - 1]));
        m.post(m.arithm(vars[0][0], "<", vars[n - 1][n - 1]));
        m.post(m.arithm(vars[0][0], "<", vars[n - 1][0]));


        Visualization visu = new Visualization("AllDifferentMatrix", s, dir );

        visu.createTree();
        visu.createViz();

        AllDifferentMatrix visualizer = new AllDifferentMatrix(vars, "expanded", 3, 3);
        visualizer.setMinMax(1, ub);

        visu.addVisualizer(visualizer);

        s.findSolution();


    }

    @Test(groups = "1s")
    public void testBinaryMatrix() {
        Model m = new Model();
        Solver s = m.getSolver();
        int n = 4;
        IntVar[] var = m.intVarArray("var", n, 0, n - 1, false);
        BoolVar[][] bool = new BoolVar[n][n];
        for (int i = 0; i < n; i++) {
            bool[i] = m.boolVarArray("bool_" + i, n);
            m.post(m.boolsIntChanneling(bool[i], var[i], 0));
        }
        m.post(m.allDifferent(var, "BC"));

        Visualization visu = new Visualization("BinaryMatrix", s, dir );

        visu.createTree();
        visu.createViz();


        BinaryMatrix visualizer = new BinaryMatrix(bool, "expanded", n, n);

        visu.addVisualizer(visualizer);

        s.findSolution();

    }

    @Test(groups = "1s")
    public void testBoolChanneling() {
        Model m = new Model();
        Solver s = m.getSolver();
        IntVar var = m.intVar("var", 1, 8, false);
        BoolVar[] bool = m.boolVarArray("b", 8);
        m.post(m.boolsIntChanneling(bool, var, 0));

        Visualization visu = new Visualization("BoolChanneling", s, dir );

        visu.createTree();
        visu.createViz();


        BoolChanneling visualizer = new BoolChanneling(var, bool, "expanded", 8, 8);
        visualizer.setMinMax(0, 8);

        visu.addVisualizer(visualizer);

        s.findSolution();

    }

//    @Test
//    public void testBinPacking() {
//        Solver solver = new Solver();
//        IntVar[] loads = new IntVar[3];
//        loads[0] = VariableFactory.enumerated("load1", 0, 5, solver);
//        loads[1] = VariableFactory.enumerated("load2", 0, 5, solver);
//        loads[2] = VariableFactory.enumerated("load3", 0, 5, solver);
//        IntVar[] bins = VariableFactory.enumerated("bin", 4, 0, 1);
//        int[] _sizes = new int[]{4, 3, 1, 1};
//        IntegerConstantVariable[] sizes = constantArray(_sizes);
//
//        Solver s = new Solver();
//        m.addConstraint(Choco.pack(new PackModel(bins, sizes, loads)));
//
//        Solver solver = new CPSolver();
//        solver.read(m);
//
//        Visualization visu = new Visualization("BinPacking", solver, dir );
//        visu.createTree();
//        visu.createViz();
//
//        BinPacking visualizer = new BinPacking(solver.getVar(bins), _sizes, solver.getVar(loads), "expanded", 15, 15);
//
//        visu.addVisualizer(visualizer);
//        solver.solve();
//
//
//    }

    @Test(groups = "1s")
    public void testLexLe() {
        Model m = new Model();
        Solver s = m.getSolver();

        IntVar[] X = m.intVarArray("X", 3, 0, 1, false);
        IntVar[] Y = m.intVarArray("Y", 3, 0, 1, false);
        m.post(m.lexLessEq(X, Y));

        Visualization visu = new Visualization("LexLe", s, dir );
        visu.createTree();
        visu.createViz();

        LexLe visualizer = new LexLe(X, Y, "expanded", 3, 2);

        visu.addVisualizer(visualizer);

        s.findSolution();
    }

    @Test(groups = "1s")
    public void testInverse() {
        Model m = new Model();
        Solver s = m.getSolver();
        IntVar[] X = m.intVarArray("X", 3, 0, 2, false);
        IntVar[] Y = m.intVarArray("Y", 3, 0, 2, false);

        m.post(m.inverseChanneling(X, Y, 0, 0));

        Visualization visu = new Visualization("Inverse", s, dir );
        visu.createTree();
        visu.createViz();

        Inverse visualizer = new Inverse(X, Y, "expanded", 2, 3);

        visu.addVisualizer(visualizer);

        s.findSolution();


    }

    @Test(groups = "1s")
    public void testGcc() {
        Model m = new Model();
        Solver s = m.getSolver();
        IntVar[] X = m.intVarArray("X", 3, 0, 4, false);
        int[] values = new int[]{1, 2, 3};
        int[] low = new int[]{0, 1, 0};
        int[] up = new int[]{1, 2, 1};
        IntVar[] cards = new IntVar[]{
                m.intVar("card 0", 0, 1, true),
                m.intVar("card 1", 1, 2, true),
                m.intVar("card 2", 0, 1, true)
        };

        m.post(m.globalCardinality(X, values, cards, false));

        Visualization visu = new Visualization("Gcc", s, dir );
        visu.createTree();
        visu.createViz();

        Gcc visualizer = new Gcc(X, values, low, up, "expanded", 30, 30);

        visu.addVisualizer(visualizer);

        s.findSolution();
    }

    @Test(groups = "1s")
    public void testCumulative() {
//        Solver s = new Solver();
//        // data
//        int n = 11 + 3; //number of tasks (include the three fake tasks)
//        int[] heights_data = new int[]{2, 1, 4, 2, 3, 1, 5, 6, 2, 1, 3, 1, 1, 2};
//        int[] durations_data = new int[]{1, 1, 1, 2, 1, 3, 1, 1, 3, 4, 2, 3, 1, 1};
//        // variables
//        IntVar[] starts = VariableFactory.boundedArray("start", n, 0, 5, s);
//        IntVar[] ends = VariableFactory.boundedArray("end", n, 0, 6, s);
//        IntVar[] duration = new IntVar[n];
//        IntVar[] height = new IntVar[n];
//        for (int i = 0; i < height.length; i++) {
//            duration[i] = VariableFactory.fixed(durations_data[i], s);
//            height[i] = VariableFactory.enumerated("height " + i, new int[]{0, heights_data[i]}, s);
//        }
//        IntVar[] bool = VariableFactory.boolArray("taskIn?", n, s);
//        IntVar obj = VariableFactory.bounded("obj", 0, n, s);
//        //post the cumulative
//        s.post(new solver.constraints.nary.scheduling.Cumulative(starts, duration, ends, height, 7, s));
//        //post the channeling to know if the task is scheduled or not
//        for (int i = 0; i < n; i++) {
//            s.post(new Channel(height[i], bool[i]));
//            m.addConstraint(boolChanneling(bool[i], height[i], heights_data[i]));
//        }
//        //state the objective function
//        m.addConstraint(eq(sum(bool), obj));
//        CPSolver solver = new CPSolver();
//        solver.read(m);
//        //set the fake tasks to establish the profile capacity of the ressource
//        try {
//            solver.getVar(starts[0]).setVal(1);
//            solver.getVar(ends[0]).setVal(2);
//            solver.getVar(height[0]).setVal(2);
//            solver.getVar(starts[1]).setVal(2);
//            solver.getVar(ends[1]).setVal(3);
//            solver.getVar(height[1]).setVal(1);
//            solver.getVar(starts[2]).setVal(3);
//            solver.getVar(ends[2]).setVal(4);
//            solver.getVar(height[2]).setVal(4);
//        } catch (ContradictionException e) {
//            System.out.println("error, no contradiction expected at this stage");
//        }
//        Visualization visu = new Visualization("Cumulative", solver, dir );
//        visu.createTree();
//        visu.createViz();
//
//        Cumulative visualizer = new Cumulative(solver.getVar(tasks), solver.getVar(capa), solver.getMakespan(), "expanded", 30, 30);
//
//        visu.addVisualizer(visualizer);
//
//        // maximize the number of tasks placed in this profile
////        solver.maximize(solver.getVar(obj), false);
//        solver.solve();
//        System.out.println("Objective : " + (solver.getVar(obj).getVal() - 3));
//        for (int i = 3; i < starts.length; i++) {
//            if (solver.getVar(height[i]).getVal() != 0)
//                System.out.println("[" + solver.getVar(starts[i]).getVal() + " - "
//                        + (solver.getVar(ends[i]).getVal() - 1) + "]:"
//                        + solver.getVar(height[i]).getVal());
//        }

    }
}
