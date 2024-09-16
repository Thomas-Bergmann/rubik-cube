package de.hatoka.cube;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class State2x2Test
{
    State2x2 initialState = State2x2.INITIAL;

    @Test
    public void isFinished()
    {
        assertTrue(initialState.isFinished());
        // top left front corner
        assertEquals(Color.WHITE, initialState.getCornerColors().get(0));
        assertEquals(Color.RED, initialState.getCornerColors().get(1));
        assertEquals(Color.GREEN, initialState.getCornerColors().get(2));
    }

    @Test
    public void isCounterMoveFront()
    {
        Move2x2 move = Move2x2.F;
        Move2x2 move2 = Move2x2.F_;
        State2x2 turned = initialState.move(move);
        // top left front corner
        assertEquals(Color.GREEN, turned.getCornerColors().get(0));
        assertEquals(Color.RED, turned.getCornerColors().get(1));
        assertEquals(Color.YELLOW, turned.getCornerColors().get(2));
        // top left back corner (still correct)
        assertEquals(Color.WHITE, turned.getCornerColors().get(3));
        assertEquals(Color.GREEN, turned.getCornerColors().get(4));
        assertEquals(Color.ORANGE, turned.getCornerColors().get(5));
        // top right back corner
        assertEquals(Color.WHITE, turned.getCornerColors().get(6));
        assertEquals(Color.ORANGE, turned.getCornerColors().get(7));
        assertEquals(Color.BLUE, turned.getCornerColors().get(8));
        // top right front corner
        assertEquals(Color.GREEN, turned.getCornerColors().get(9));
        assertEquals(Color.WHITE, turned.getCornerColors().get(10));
        assertEquals(Color.RED, turned.getCornerColors().get(11));
        // bottom right front corner
        assertEquals(Color.BLUE, turned.getCornerColors().get(12));
        assertEquals(Color.RED, turned.getCornerColors().get(13));
        assertEquals(Color.WHITE, turned.getCornerColors().get(14));
        // bottom right back corner
        assertEquals(Color.YELLOW, turned.getCornerColors().get(15));
        assertEquals(Color.BLUE, turned.getCornerColors().get(16));
        assertEquals(Color.ORANGE, turned.getCornerColors().get(17));
        // bottom left back corner
        assertEquals(Color.YELLOW, turned.getCornerColors().get(18));
        assertEquals(Color.ORANGE, turned.getCornerColors().get(19));
        assertEquals(Color.GREEN, turned.getCornerColors().get(20));
        // bottom left front corner
        assertEquals(Color.BLUE, turned.getCornerColors().get(21));
        assertEquals(Color.YELLOW, turned.getCornerColors().get(22));
        assertEquals(Color.RED, turned.getCornerColors().get(23));

        assertEquals(initialState.getCornerColors(), turned.move(move2).getCornerColors());
        assertEquals(initialState, turned.move(move2));
    }

    static Stream<Arguments> counterMoves()
    {
        return Stream.of(Arguments.of(Move2x2.L, Move2x2.L_), Arguments.of(Move2x2.R, Move2x2.R_),
                        Arguments.of(Move2x2.U, Move2x2.U_), Arguments.of(Move2x2.D, Move2x2.D_),
                        Arguments.of(Move2x2.F, Move2x2.F_), Arguments.of(Move2x2.B, Move2x2.B_));
    }

    @ParameterizedTest
    @MethodSource("counterMoves")
    public void counterMove(Move2x2 move1, Move2x2 move2)
    {
        State2x2 state = initialState.move(move1);
        assertNotEquals(initialState.toString(), state.toString());
        assertFalse(state.isFinished());
        state = state.move(move2);
        assertTrue(state.isFinished());
    }

    @ParameterizedTest
    @EnumSource(Move2x2.class)
    public void repeatMove(Move2x2 move)
    {
        // turn one
        State2x2 state = initialState.move(move);
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
        State2x2 state = initialState.turn(side1);
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
        List<Move2x2> moves = Move2x2.fromNotation("RF'R'FRF'R'F");
        // right upper corner
        State2x2 firstCorner = initialState.move(moves);
        State2x2 secondCorner = firstCorner.move(Move2x2.U_).move(moves);
        State2x2 thirdCorner = secondCorner.move(Move2x2.U_).move(moves);
        State2x2 correctPosition = thirdCorner.move(Move2x2.U_).move(Move2x2.U_);
        assertFalse(correctPosition.isFinished());
    }
}
