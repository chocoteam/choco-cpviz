CPViz
=====

An extension of Choco3 to deal with [cpviz](https://sourceforge.net/projects/cpviz/) library.

Simply add the choco-cpviz-X.Y.Z.jar file to the classpath, together with choco-solver.X.Y.Z.jar, and add the following code, before the resolution:

    Visualization visu = new Visualization("aName", s, dir + "/out");
    visu.createTree();
    visu.createViz();
    // add component, such as vector, ...
    Vector vector = new Vector(Q, "expanded", n, n);
    vector.setMinMax(1, n);
    visu.addVisualizer(vector);

It produces the configuration, tree and visualization files required by cpviz to render the search.

Any suggestion or contribution will be appreciated.