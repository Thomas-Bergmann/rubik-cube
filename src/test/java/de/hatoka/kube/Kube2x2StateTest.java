package de.hatoka.kube;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
        Kube2x2State state = initialState.turn(move1);
        assertNotEquals(initialState.toString(), state.toString());
        assertFalse(state.isFinished());
        state = state.turn(move2);
        assertTrue(state.isFinished());
    }
}
