package de.hatoka.kube;

public enum Kube2x2Move
{
    U(Axis.UP, Step.CLOCKWISE), U2(Axis.UP, Step.DOUBLE), U_(Axis.UP, Step.REVERSE),
    R(Axis.RIGHT, Step.CLOCKWISE), R2(Axis.RIGHT, Step.DOUBLE), R_(Axis.RIGHT, Step.REVERSE),
    F(Axis.FRONT, Step.CLOCKWISE), F2(Axis.FRONT, Step.DOUBLE), F_(Axis.FRONT, Step.REVERSE),
    L(Axis.LEFT, Step.CLOCKWISE), L2(Axis.LEFT, Step.DOUBLE), L_(Axis.LEFT, Step.REVERSE),
    B(Axis.BACK, Step.CLOCKWISE), B2(Axis.BACK, Step.DOUBLE), B_(Axis.BACK, Step.REVERSE),
    D(Axis.DOWN, Step.CLOCKWISE), D2(Axis.DOWN, Step.DOUBLE), D_(Axis.DOWN, Step.REVERSE);

    private final Axis axis;
    private final Step steps;

    Kube2x2Move(Axis axis, Step steps)
    {
        this.axis = axis;
        this.steps = steps;
    }

    public Axis getAxis()
    {
        return axis;
    }

    /**
     * @return step how (in which direction) and how often the move is executed
     */
    public Step getStep()
    {
        return steps;
    }
}