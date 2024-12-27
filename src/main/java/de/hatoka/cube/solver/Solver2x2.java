package de.hatoka.cube.solver;

import de.hatoka.cube.Move2x2;
import de.hatoka.cube.State2x2;

import java.util.List;

public interface Solver2x2
{
    List<Move2x2> solve(State2x2 state);
}
