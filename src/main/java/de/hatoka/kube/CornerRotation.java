package de.hatoka.kube;

public enum CornerRotation
{
    ZERO(0), ONE(1), TWO(2);

    private final int numberOfRotations;

    CornerRotation(int numberOfRotations)
    {
        this.numberOfRotations = numberOfRotations;
    }

    public int getNumberOfRotations()
    {
        return numberOfRotations;
    }
}
