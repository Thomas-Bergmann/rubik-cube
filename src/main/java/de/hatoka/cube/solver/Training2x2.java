package de.hatoka.cube.solver;

import de.hatoka.cube.Move2x2;
import de.hatoka.cube.State2x2;

import java.util.List;

public record Training2x2(List<Move2x2> scrambleMoves, State2x2 state, Move2x2 move)
{
}
