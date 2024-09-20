package de.hatoka.cube;

public enum Side
{
    LEFT(Color.GREEN), RIGHT(Color.BLUE), UP(Color.WHITE), DOWN(Color.YELLOW), FRONT(Color.RED), BACK(Color.ORANGE);

    private final Color color;

    Side(Color color)
    {
        this.color = color;
    }

    public Color getColor()
    {
        return color;
    }
}
