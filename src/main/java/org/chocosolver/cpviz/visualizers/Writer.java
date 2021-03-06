/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:48:05Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.cpviz.visualizers;

import org.chocosolver.cpviz.CPVizConstant;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.util.iterators.DisposableIntIterator;
import org.chocosolver.util.iterators.DisposableValueIterator;

import java.io.PrintWriter;

/**
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 14/12/10
 */
public class Writer {

    private static final String[] PREFIX = {"", "\t", "\t\t", "\t\t\t", "\t\t\t\t", "\t\t\t\t\t", "\t\t\t\t\t\t"};

    public static final String _1 = "1", _2 = "2", _3 = "3", _S = " ";

    PrintWriter visualization;

    private StringBuilder _st = new StringBuilder();


    public Writer(PrintWriter visualization) {
        this.visualization = visualization;
    }

    protected Writer var(Variable var, String idx, int pf) {
        if (var instanceof IntVar) {
            IntVar _ivar = (IntVar) var;
            return ivar(_ivar, idx, pf);
        } else if (var instanceof SetVar) {
            SetVar svar = (SetVar) var;
            return svar(svar, idx, pf);
        }
        throw new UnsupportedOperationException("unknown type of var " + var.getClass());
    }

    protected Writer ivar(IntVar ivar, String idx, int pf) {
        if (ivar.isInstantiated()) {
            visualization.printf(CPVizConstant.V_INTEGER_TAG, prefix(pf), idx, ivar.getValue());
        } else {
            _st.setLength(0);
            if (ivar.hasEnumeratedDomain()) {
                DisposableValueIterator it = ivar.getValueIterator(true);
                while (it.hasNext()) {
                    _st.append(it.next()).append(' ');
                }
                it.dispose();
            } else {
                _st.append(ivar.getLB()).append(" .. ").append(ivar.getUB());
            }

            visualization.printf(CPVizConstant.V_DVAR_TAG, prefix(pf), idx, _st.toString());
        }
        return this;
    }

    protected Writer integer(int value, String idx, int pf) {
        visualization.printf(CPVizConstant.V_INTEGER_TAG, prefix(pf), idx, value);
        return this;
    }

    protected Writer svar(SetVar svar, String idx, int pf) {
        if (svar.isInstantiated()) {
            //visualization.printf(CPVizConstant.V_SINTEGER_TAG, new Object[]{prefix(pf), idx, domain(svar.getDomain().getKernelIterator())});
        } else {
            //visualization.printf(CPVizConstant.V_SVAR_TAG, new Object[]{prefix(pf), idx, domain(svar.getDomain().getKernelIterator()),
            //        domain(svar.getDomain().getEnveloppeIterator())});
        }
        throw new UnsupportedOperationException();
        //return this;
    }

    protected Writer arrayDvar(Variable[] vars, int pf) {
        for (int i = 0; i < vars.length; i++) {
            var(vars[i], Integer.toString(i + 1), pf);
        }
        return this;
    }

    protected Writer arrayDvar(IntVar[] vars, int pf) {
        for (int i = 0; i < vars.length; i++) {
            ivar(vars[i], Integer.toString(i + 1), pf);
        }
        return this;
    }

    protected Writer arrayDvar(SetVar[] vars, int pf) {
        for (int i = 0; i < vars.length; i++) {
            svar(vars[i], Integer.toString(i + 1), pf);
        }
        return this;
    }

    private String domain(DisposableIntIterator it) {
        _st.setLength(0);
        while (it.hasNext()) {
            _st.append(it.next()).append(' ');
        }
        it.dispose();

        return _st.toString();
    }

    protected Writer array(int[] values, int pf) {
        for (int i = 0; i < values.length; i++) {
            visualization.printf(CPVizConstant.V_INTEGER_TAG, prefix(pf), (i + 1), values[i]);
        }
        return this;
    }

    protected Writer argumentIn(String idx, int pf) {
        visualization.printf(CPVizConstant.V_ARGUMENT_TAG_IN, prefix(pf), idx);
        return this;
    }

    protected Writer argumentOut(int pf) {
        visualization.printf(CPVizConstant.V_ARGUMENT_TAG_OUT, prefix(pf));
        return this;
    }

    protected Writer tupleIn(String idx, int pf) {
        visualization.printf(CPVizConstant.V_TUPLE_TAG_IN, prefix(pf), idx);
        return this;
    }

    protected Writer tupleOut(int pf) {
        visualization.printf(CPVizConstant.V_TUPLE_TAG_OUT, prefix(pf));
        return this;
    }

    protected Writer collectionIn(String idx, int pf) {
        visualization.printf(CPVizConstant.V_COLLECTION_TAG_IN, prefix(pf), idx);
        return this;
    }

    protected Writer collectionOut(int pf) {
        visualization.printf(CPVizConstant.V_COLLECTION_TAG_OUT, prefix(pf));
        return this;
    }

    protected void focus(String idx, String group, String type) {
        visualization.printf(CPVizConstant.V_FOCUS_TAG, prefix(3), idx, group, type);
    }

    protected void focus(String idx, String group) {
        visualization.printf(CPVizConstant.V_FOCUS_NO_TYPE_TAG, prefix(3), idx, group);
    }

    protected void fail(String idx, String group, int value) {
        visualization.printf(CPVizConstant.V_FAILED_TAG, prefix(3), idx, group, value);
    }

    protected String prefix(int nb) {
        if (nb >= 0 && nb <= PREFIX.length) {
            return PREFIX[nb];
        } else if (nb > PREFIX.length) {
            final StringBuilder st = new StringBuilder();
            for (int i = 0; i < nb; i++) {
                st.append(PREFIX[1]);
            }
            return st.toString();
        }
        return PREFIX[0];

    }

}
