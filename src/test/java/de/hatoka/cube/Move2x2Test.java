package de.hatoka.cube;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Move2x2Test
{
    @Test
    public void testSimpleMove()
    {
        List<Move2x2> moves = Move2x2.fromNotation("LRU");
        assertEquals(3, moves.size());
        assertEquals(Move2x2.L, moves.get(0));
        assertEquals(Move2x2.R, moves.get(1));
        assertEquals(Move2x2.U, moves.get(2));
    }

    @Test
    public void testComplexMoves()
    {
        List<Move2x2> moves = Move2x2.fromNotation("L'RU2");
        assertEquals(3, moves.size());
        assertEquals(Move2x2.L_, moves.get(0));
        assertEquals(Move2x2.R, moves.get(1));
        assertEquals(Move2x2.U2, moves.get(2));
    }

    @Test
    public void testEmptyMoves()
    {
        List<Move2x2> moves = Move2x2.fromNotation("");
        assertEquals(0, moves.size());
    }

    @Test
    public void testWrongMoves()
    {
        assertThrows(IllegalArgumentException.class, () -> Move2x2.fromNotation("'L"));
    }

    @Test
    public void testWithSpaces()
    {
        List<Move2x2> moves = Move2x2.fromNotation("L'R U2");
        assertEquals(3, moves.size());
        assertEquals(Move2x2.L_, moves.get(0));
        assertEquals(Move2x2.R, moves.get(1));
        assertEquals(Move2x2.U2, moves.get(2));
    }
}
