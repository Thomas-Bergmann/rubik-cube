package de.hatoka.cube;

import org.junit.jupiter.api.Disabled;
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
        for(CornerPosition position : CornerPosition.values())
        {
            assertTrue(initialState.isFinished(position), "Position " + position);
        }
    }

    @Test
    public void testMoveFront()
    {
        Move2x2 move = Move2x2.F;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.TRB));
        assertTrue(turned.isFinished(CornerPosition.TLB));
        assertTrue(turned.isFinished(CornerPosition.DLB));
        assertTrue(turned.isFinished(CornerPosition.DRB));

        assertCorner(turned, CornerPosition.TLF, Color.GREEN, Color.RED, Color.YELLOW);
        assertCorner(turned, CornerPosition.TRF, Color.GREEN, Color.WHITE, Color.RED);
        assertCorner(turned, CornerPosition.DRF, Color.BLUE, Color.RED, Color.WHITE);
        assertCorner(turned, CornerPosition.DLF, Color.BLUE, Color.YELLOW, Color.RED);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    @Test
    public void testMoveFrontReverse()
    {
        Move2x2 move = Move2x2.F_;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.TRB));
        assertTrue(turned.isFinished(CornerPosition.TLB));
        assertTrue(turned.isFinished(CornerPosition.DLB));
        assertTrue(turned.isFinished(CornerPosition.DRB));

        assertCorner(turned, CornerPosition.TLF, Color.BLUE, Color.RED, Color.WHITE);
        assertCorner(turned, CornerPosition.TRF, Color.BLUE, Color.YELLOW, Color.RED);
        assertCorner(turned, CornerPosition.DLF, Color.GREEN, Color.WHITE, Color.RED);
        assertCorner(turned, CornerPosition.DRF, Color.GREEN, Color.RED, Color.YELLOW);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    @Test
    public void testMoveLeft()
    {
        Move2x2 move = Move2x2.L;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.TRB));
        assertTrue(turned.isFinished(CornerPosition.TRF));
        assertTrue(turned.isFinished(CornerPosition.DRF));
        assertTrue(turned.isFinished(CornerPosition.DRB));

        assertCorner(turned, CornerPosition.TLF, Color.ORANGE, Color.WHITE, Color.GREEN);
        assertCorner(turned, CornerPosition.TLB, Color.ORANGE, Color.GREEN, Color.YELLOW);
        assertCorner(turned, CornerPosition.DLB, Color.RED, Color.YELLOW, Color.GREEN);
        assertCorner(turned, CornerPosition.DLF, Color.RED, Color.GREEN, Color.WHITE);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    @Test
    public void testMoveLeftReverse()
    {
        Move2x2 move = Move2x2.L_;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.TRB));
        assertTrue(turned.isFinished(CornerPosition.TRF));
        assertTrue(turned.isFinished(CornerPosition.DRF));
        assertTrue(turned.isFinished(CornerPosition.DRB));

        assertCorner(turned, CornerPosition.TLF, Color.RED, Color.YELLOW, Color.GREEN);
        assertCorner(turned, CornerPosition.TLB, Color.RED, Color.GREEN, Color.WHITE);
        assertCorner(turned, CornerPosition.DLB, Color.ORANGE, Color.WHITE, Color.GREEN);
        assertCorner(turned, CornerPosition.DLF, Color.ORANGE, Color.GREEN, Color.YELLOW);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    @Test
    public void testMoveRight()
    {
        Move2x2 move = Move2x2.R;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.TLB));
        assertTrue(turned.isFinished(CornerPosition.TLF));
        assertTrue(turned.isFinished(CornerPosition.DLF));
        assertTrue(turned.isFinished(CornerPosition.DLB));

        assertCorner(turned, CornerPosition.TRF, Color.RED, Color.BLUE, Color.YELLOW);
        assertCorner(turned, CornerPosition.TRB, Color.RED, Color.WHITE, Color.BLUE);
        assertCorner(turned, CornerPosition.DRB, Color.ORANGE, Color.BLUE, Color.WHITE);
        assertCorner(turned, CornerPosition.DRF, Color.ORANGE, Color.YELLOW, Color.BLUE);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    @Test
    public void testMoveRightReverse()
    {
        Move2x2 move = Move2x2.R_;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.TLB));
        assertTrue(turned.isFinished(CornerPosition.TLF));
        assertTrue(turned.isFinished(CornerPosition.DLF));
        assertTrue(turned.isFinished(CornerPosition.DLB));

        assertCorner(turned, CornerPosition.TRF, Color.ORANGE, Color.BLUE, Color.WHITE);
        assertCorner(turned, CornerPosition.TRB, Color.ORANGE, Color.YELLOW, Color.BLUE);
        assertCorner(turned, CornerPosition.DRB, Color.RED, Color.BLUE, Color.YELLOW);
        assertCorner(turned, CornerPosition.DRF, Color.RED, Color.WHITE, Color.BLUE);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    @Test
    public void testMoveBottom()
    {
        Move2x2 move = Move2x2.D;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.TLB));
        assertTrue(turned.isFinished(CornerPosition.TLF));
        assertTrue(turned.isFinished(CornerPosition.TRF));
        assertTrue(turned.isFinished(CornerPosition.TRB));

        assertCorner(turned, CornerPosition.DRF, Color.YELLOW, Color.GREEN, Color.RED);
        assertCorner(turned, CornerPosition.DRB, Color.YELLOW, Color.RED, Color.BLUE);
        assertCorner(turned, CornerPosition.DLB, Color.YELLOW, Color.BLUE, Color.ORANGE);
        assertCorner(turned, CornerPosition.DLF, Color.YELLOW, Color.ORANGE, Color.GREEN);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    @Test
    public void testMoveBottomReverse()
    {
        Move2x2 move = Move2x2.D_;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.TLB));
        assertTrue(turned.isFinished(CornerPosition.TLF));
        assertTrue(turned.isFinished(CornerPosition.TRF));
        assertTrue(turned.isFinished(CornerPosition.TRB));

        assertCorner(turned, CornerPosition.DRF, Color.YELLOW, Color.BLUE, Color.ORANGE);
        assertCorner(turned, CornerPosition.DRB, Color.YELLOW, Color.ORANGE, Color.GREEN);
        assertCorner(turned, CornerPosition.DLB, Color.YELLOW, Color.GREEN, Color.RED);
        assertCorner(turned, CornerPosition.DLF, Color.YELLOW, Color.RED, Color.BLUE);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    @Test
    public void testMoveTop()
    {
        Move2x2 move = Move2x2.U;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.DLB));
        assertTrue(turned.isFinished(CornerPosition.DLF));
        assertTrue(turned.isFinished(CornerPosition.DRF));
        assertTrue(turned.isFinished(CornerPosition.DRB));

        assertCorner(turned, CornerPosition.TRF, Color.WHITE, Color.ORANGE, Color.BLUE);
        assertCorner(turned, CornerPosition.TRB, Color.WHITE, Color.GREEN, Color.ORANGE);
        assertCorner(turned, CornerPosition.TLB, Color.WHITE, Color.RED, Color.GREEN);
        assertCorner(turned, CornerPosition.TLF, Color.WHITE, Color.BLUE, Color.RED);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    @Test
    public void testMoveTopReverse()
    {
        Move2x2 move = Move2x2.U_;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.DLB));
        assertTrue(turned.isFinished(CornerPosition.DLF));
        assertTrue(turned.isFinished(CornerPosition.DRF));
        assertTrue(turned.isFinished(CornerPosition.DRB));

        assertCorner(turned, CornerPosition.TLB, Color.WHITE, Color.ORANGE, Color.BLUE);
        assertCorner(turned, CornerPosition.TLF, Color.WHITE, Color.GREEN, Color.ORANGE);
        assertCorner(turned, CornerPosition.TRF, Color.WHITE, Color.RED, Color.GREEN);
        assertCorner(turned, CornerPosition.TRB, Color.WHITE, Color.BLUE, Color.RED);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    @Test
    public void testMoveBack()
    {
        Move2x2 move = Move2x2.B;
        State2x2 turned = initialState.move(move);
        assertTrue(turned.isFinished(CornerPosition.TLF));
        assertTrue(turned.isFinished(CornerPosition.TRF));
        assertTrue(turned.isFinished(CornerPosition.DLF));
        assertTrue(turned.isFinished(CornerPosition.DRF));

        assertCorner(turned, CornerPosition.TLB, Color.BLUE, Color.WHITE, Color.ORANGE);
        assertCorner(turned, CornerPosition.TRB, Color.BLUE, Color.ORANGE, Color.YELLOW);
        assertCorner(turned, CornerPosition.DLB, Color.GREEN, Color.ORANGE, Color.WHITE);
        assertCorner(turned, CornerPosition.DRB, Color.GREEN, Color.YELLOW, Color.ORANGE);

        State2x2 turned4 = initialState.move(move).move(move).move(move).move(move);
        assertTrue(turned4.isFinished());
    }

    private void assertCorner(State2x2 state, CornerPosition position, Color... colors)
    {
        assertFalse(state.isFinished(position));
        assertEquals(colors[0], state.getColors(position).get(0));
        assertEquals(colors[1], state.getColors(position).get(1));
        assertEquals(colors[2], state.getColors(position).get(2));
    }

    static Stream<Arguments> counterMoves()
    {
        return Stream.of(Arguments.of(Move2x2.L, Move2x2.L_), Arguments.of(Move2x2.R, Move2x2.R_),
                        Arguments.of(Move2x2.U, Move2x2.U_), Arguments.of(Move2x2.D, Move2x2.D_),
                        Arguments.of(Move2x2.F, Move2x2.F_), Arguments.of(Move2x2.B, Move2x2.B_));
    }

    @ParameterizedTest
    @MethodSource("counterMoves")
    @Disabled
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
    @Disabled
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
    @Disabled
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
    @Disabled
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
