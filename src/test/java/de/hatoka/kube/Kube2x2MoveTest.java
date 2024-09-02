package de.hatoka.kube;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Kube2x2MoveTest
{
    @Test
    public void testSimpleMove()
    {
        List<Kube2x2Move> moves = Kube2x2Move.fromNotation("LRU");
        assertEquals(3, moves.size());
        assertEquals(Kube2x2Move.L, moves.get(0));
        assertEquals(Kube2x2Move.R, moves.get(1));
        assertEquals(Kube2x2Move.U, moves.get(2));
    }

    @Test
    public void testComplexMoves()
    {
        List<Kube2x2Move> moves = Kube2x2Move.fromNotation("L'RU2");
        assertEquals(3, moves.size());
        assertEquals(Kube2x2Move.L_, moves.get(0));
        assertEquals(Kube2x2Move.R, moves.get(1));
        assertEquals(Kube2x2Move.U2, moves.get(2));
    }

    @Test
    public void testEmptyMoves()
    {
        List<Kube2x2Move> moves = Kube2x2Move.fromNotation("");
        assertEquals(0, moves.size());
    }

    @Test
    public void testWrongMoves()
    {
        assertThrows(IllegalArgumentException.class, () -> Kube2x2Move.fromNotation("'L"));
    }

    @Test
    public void testWithSpaces()
    {
        List<Kube2x2Move> moves = Kube2x2Move.fromNotation("L'R U2");
        assertEquals(3, moves.size());
        assertEquals(Kube2x2Move.L_, moves.get(0));
        assertEquals(Kube2x2Move.R, moves.get(1));
        assertEquals(Kube2x2Move.U2, moves.get(2));
    }
}
