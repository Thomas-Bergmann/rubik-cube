package de.hatoka.kube;

public record CornerState(CornerPiece piece, CornerRotation rotation)
{
    public static CornerState valueOf(CornerPiece piece)
    {
        return new CornerState(piece, CornerRotation.ZERO);
    }
    public static CornerState valueOf(CornerPiece piece, CornerRotation rotation)
    {
        return new CornerState(piece, rotation);
    }
}
