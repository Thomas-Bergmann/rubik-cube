package de.hatoka.cube.solver;

import de.hatoka.cube.Move2x2;
import de.hatoka.cube.State2x2;

import java.util.List;

/**
 * Solves a 2x2 cube using predefined algorithms.
 */
public class AlgorithmSolver2x2 implements Solver2x2
{
    @Override
    public List<Move2x2> solve(State2x2 state)
    {
        for(Move2x2 move : Move2x2.values())
        {
            State2x2 newState = state.move(move);
            if(newState.isFinished())
            {
                return List.of(move);
            }
        }
        return List.of();
    }
}
