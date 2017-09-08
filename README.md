choco-cpviz
===========

An extension of [Choco](https://github.com/chocoteam/choco-solver) to deal with [cpviz](https://sourceforge.net/projects/cpviz/) library.

 [choco-cpviz-3.3.1.jar](https://github.com/chocoteam/choco-cpviz/releases/tag/choco-cpviz-3.3.1)
to the classpath, together with jar file 
from  [choco-solver-3.3.3.zip](https://github.com/chocoteam/choco-solver/releases/tag/3.3.3).
and add the following code, before the resolution:

    Visualization visu = new Visualization("aName", s, dir + "/out");
    visu.createTree();
    visu.createViz();
    // add component, such as vector, ...
    Vector vector = new Vector(Q, "expanded", n, n);
    vector.setMinMax(1, n);
    visu.addVisualizer(vector);

It produces the configuration, tree and visualization files required by cpviz to render the search.

Any suggestion or contribution will be appreciated.
