package de.hatoka.cube;

/**
 * Represents a cornerstone of a cube. The order of colors starts at top or downside of cube and goes around the clock.
 */
public class CornerStone
{
    private final Color[] colors;
    CornerStone(Color[] colors)
    {
        this.colors = colors;
    }

    static CornerStone fromNotation(String notation)
    {
        Color[] colors = new Color[notation.length()];
        for (int i = 0; i < notation.length(); i++)
        {
            colors[i] = Color.fromChar(notation.charAt(i));
        }
        return new CornerStone(colors);
    }

    public Color getColor(int index)
    {
        return colors[index];
    }
}
