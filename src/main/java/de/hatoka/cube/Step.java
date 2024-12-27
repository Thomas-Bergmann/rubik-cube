package de.hatoka.cube;

/**
 * Represents how to turn a side of a cube. clockwise, twice or reverse clockwise
 */
public enum Step
{
    CLOCKWISE(1), DOUBLE(2), REVERSE(-1);

    private final int stepCount;

    Step(int stepCount)
    {
        this.stepCount = stepCount;
    }

    public int getStepCount()
    {
        return stepCount;
    }
}
