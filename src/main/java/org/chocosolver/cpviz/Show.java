/**
 * This file is part of choco-cpviz, https://github.com/chocoteam/choco-cpviz
 *
 * Copyright (c) 2017-09-08T13:48:05Z, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.cpviz;

/**
 * tree or viz
 * <br/>
 *
 * @author Charles Prud'homme
 * @since 9 dec. 2010
 */
public enum Show {
    TREE(1), VIZ(2);

    final int mask;

    Show(int i) {
        this.mask = i;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
