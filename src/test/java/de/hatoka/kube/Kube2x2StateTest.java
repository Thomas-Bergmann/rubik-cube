package de.hatoka.kube;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Kube2x2StateTest
{
    @Test
    public void isFinished()
    {
        Kube2x2State state = Kube2x2State.initial();
        assertTrue(state.isFinished());
    }
}
