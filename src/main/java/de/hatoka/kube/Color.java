package de.hatoka.kube;

public enum Color
{
    WHITE(Side.TOP), YELLOW(Side.DOWN), GREEN(Side.FRONT), ORANGE(Side.LEFT), BLUE(Side.BACK), RED(Side.RIGHT);

    private final Side targetSide;

    Color(Side targetSide)
    {
        this.targetSide = targetSide;
    }

    public Side getTargetSide()
    {
        return targetSide;
    }
}
