package de.hatoka.kube;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Kube2x2StateTest
{
    Kube2x2State initialState = Kube2x2State.INITIAL;
    @Test
    public void isFinished()
    {
        assertTrue(initialState.isFinished());
    }

    static Stream<Arguments> counterMoves()
    {
        return Stream.of(Arguments.of(Kube2x2Move.L, Kube2x2Move.L_), Arguments.of(Kube2x2Move.R, Kube2x2Move.R_),
                        Arguments.of(Kube2x2Move.U, Kube2x2Move.U_), Arguments.of(Kube2x2Move.D, Kube2x2Move.D_),
                        Arguments.of(Kube2x2Move.F, Kube2x2Move.F_), Arguments.of(Kube2x2Move.B, Kube2x2Move.B_));
    }

    @ParameterizedTest
    @MethodSource("counterMoves")
    public void counterMove(Kube2x2Move move1, Kube2x2Move move2)
    {
        Kube2x2State state = initialState.move(move1);
        assertNotEquals(initialState.toString(), state.toString());
        assertFalse(state.isFinished());
        state = state.move(move2);
        assertTrue(state.isFinished());
    }

    @ParameterizedTest
    @EnumSource(Kube2x2Move.class)
    public void repeatMove(Kube2x2Move move)
    {
        // turn one
        Kube2x2State state = initialState.move(move);
        assertNotEquals(initialState.toString(), state.toString());
        assertFalse(state.isFinished());
        // turn two
        state = state.move(move);
        if (move.getStep() == Step.DOUBLE)
        {
            assertTrue(state.isFinished());
            return;
        }
        assertFalse(state.isFinished());
        // turn three
        state = state.move(move);
        assertFalse(state.isFinished());
        // turn four
        state = state.move(move);
        assertTrue(state.isFinished());
    }

    static Stream<Arguments> counterTurns()
    {
        return Stream.of(Arguments.of(Side.RIGHT, Side.LEFT), Arguments.of(Side.UP, Side.DOWN),
                        Arguments.of(Side.FRONT, Side.BACK));
    }

    @ParameterizedTest
    @MethodSource("counterTurns")
    public void testTurn(Side side1, Side side2)
    {
        Kube2x2State state = initialState.turn(side1);
        assertFalse(state.isFinished());
        state = state.turn(side2);
        assertTrue(state.isFinished());
        // reverse
        state = initialState.turn(side2);
        assertFalse(state.isFinished());
        state = state.turn(side1);
        assertTrue(state.isFinished());
    }

    @Test
    public void testRotatingEdges()
    {
        // turning top right front corner
        List<Kube2x2Move> moves = Kube2x2Move.fromNotation("RF'R'FRF'R'F");
        // right upper corner
        Kube2x2State firstCorner = initialState.move(moves);
        Kube2x2State secondCorner = firstCorner.move(Kube2x2Move.U_).move(moves);
        Kube2x2State thirdCorner = secondCorner.move(Kube2x2Move.U_).move(moves);
        Kube2x2State correctPosition = thirdCorner.move(Kube2x2Move.U_).move(Kube2x2Move.U_);
        assertFalse(correctPosition.isFinished());
    }
}
