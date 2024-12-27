package de.hatoka.cube;

/**
 * Represents a cornerstone of a cube. The order of colors starts at top or downside of cube and goes around the clock.
 */
public enum CornerStone
{
    WRG, WGO, WOB, WBR, YRB, YBO, YOG, YGR;

    private final Color[] colors;

    CornerStone()
    {
        colors = fromNotation(name());
    }

    static Color[] fromNotation(String notation)
    {
        Color[] colors = new Color[notation.length()];
        for (int i = 0; i < notation.length(); i++)
        {
            colors[i] = Color.fromChar(notation.charAt(i));
        }
        return colors;
    }

    public static CornerStone fromOrdinal(int index)
    {
        return CornerStone.values()[index];
    }

    public Color getColor(int index)
    {
        return colors[index];
    }
}
