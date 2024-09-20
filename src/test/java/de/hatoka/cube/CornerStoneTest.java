package de.hatoka.cube;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CornerStoneTest
{
    @Test
    public void testFromNotation()
    {
        CornerStone result = CornerStone.fromNotation("RGB");
        assertEquals(Color.RED, result.getColor(0));
    }
}
