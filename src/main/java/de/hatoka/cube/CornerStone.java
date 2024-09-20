package de.hatoka.cube;

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
