package de.hatoka.kube;

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
