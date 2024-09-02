package de.hatoka.kube;

public enum Kube2x2Move
{
    U(Side.UP, Step.CLOCKWISE), U2(Side.UP, Step.DOUBLE), U_(Side.UP, Step.REVERSE),
    R(Side.RIGHT, Step.CLOCKWISE), R2(Side.RIGHT, Step.DOUBLE), R_(Side.RIGHT, Step.REVERSE),
    F(Side.FRONT, Step.CLOCKWISE), F2(Side.FRONT, Step.DOUBLE), F_(Side.FRONT, Step.REVERSE),
    L(Side.LEFT, Step.CLOCKWISE), L2(Side.LEFT, Step.DOUBLE), L_(Side.LEFT, Step.REVERSE),
    B(Side.BACK, Step.CLOCKWISE), B2(Side.BACK, Step.DOUBLE), B_(Side.BACK, Step.REVERSE),
    D(Side.DOWN, Step.CLOCKWISE), D2(Side.DOWN, Step.DOUBLE), D_(Side.DOWN, Step.REVERSE);

    private final Side side;
    private final Step steps;

    Kube2x2Move(Side side, Step steps)
    {
        this.side = side;
        this.steps = steps;
    }

    /**
     * @return side to turn
     */
    public Side getSide()
    {
        return side;
    }

    /**
     * @return step how (in which direction) and how often the move is executed
     */
    public Step getStep()
    {
        return steps;
    }
}