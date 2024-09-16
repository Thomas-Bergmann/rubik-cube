package de.hatoka.cube;

public enum Color
{
    WHITE('W'), YELLOW('Y'), GREEN('G'), ORANGE('O'), BLUE('B'), RED('R');

    private final char color;

    Color(char color)
    {
        this.color = color;
    }

    public char getColor()
    {
        return color;
    }

    static Color fromChar(char color)
    {
        for (Color c : Color.values())
        {
            if (c.getColor() == color)
            {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown color: " + color);
    }
}
