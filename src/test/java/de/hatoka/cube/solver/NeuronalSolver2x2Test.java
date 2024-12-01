package de.hatoka.cube.solver;

import de.hatoka.cube.Move2x2;
import de.hatoka.cube.State2x2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NeuronalSolver2x2Test
{
    private static final Solver2x2 SOLVER = new NetworkTrainer2x2();

    @Test
    public void testThis()
    {
        testOneMove(Move2x2.B);
    }

    @ParameterizedTest
    @EnumSource(Move2x2.class)
    public void testOneMove(Move2x2 move)
    {
        State2x2 startPosition = State2x2.INITIAL.move(move);
        List<Move2x2> moves = SOLVER.solve(startPosition);
        assertEquals(1, moves.size());
        assertEquals(move.getReverseMove(), moves.getFirst());
        State2x2 expectedFinished = startPosition.move(moves);
        assertTrue(expectedFinished.isFinished());
    }

    @Test
    public void testTwoMoves()
    {
        for (Move2x2 move1 : Move2x2.values())
        {
            for (Move2x2 move2 : Move2x2.values())
            {
                State2x2 startPosition = State2x2.INITIAL.move(List.of(move1, move2));
                List<Move2x2> moves = SOLVER.solve(startPosition);
                State2x2 expectedFinished = startPosition.move(moves);
                if (! expectedFinished.isFinished())
                {
                    assertTrue(expectedFinished.isFinished(), "INIT(" + move1 +","+ move2 + ") ->" + SOLVER.solve(startPosition));
                }
                assertTrue(expectedFinished.isFinished(), "INIT(" + move1 +","+ move2 + ") ->" + moves);
            }
        }
    }
}
