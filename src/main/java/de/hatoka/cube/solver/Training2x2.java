package de.hatoka.cube.solver;

import de.hatoka.cube.Move2x2;
import de.hatoka.cube.State2x2;

import java.util.List;
import java.util.Objects;

public record Training2x2(List<Move2x2> scrambleMoves, State2x2 state, Move2x2 move)
{
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Training2x2 that)) return false;
        return Objects.equals(state, that.state);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(state);
    }

    @Override
    public String toString()
    {
        return "T: " + scrambleMoves + " -> " + move;
    }
}
