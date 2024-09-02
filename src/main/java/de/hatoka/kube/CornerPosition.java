package de.hatoka.kube;

public enum CornerPosition
{
    TLF(Side.TOP, Side.LEFT, Side.FRONT), TLB(Side.TOP, Side.LEFT, Side.BACK), TRF(Side.TOP, Side.RIGHT, Side.FRONT),
    TRB(Side.TOP, Side.RIGHT, Side.BACK), DLF(Side.DOWN, Side.LEFT, Side.FRONT), DLB(Side.DOWN, Side.LEFT, Side.BACK),
    DRF(Side.DOWN, Side.RIGHT, Side.FRONT), DRB(Side.DOWN, Side.RIGHT, Side.BACK);

    private final Side side1;
    private final Side side2;
    private final Side side3;

    CornerPosition(Side side1, Side side2, Side side3)
    {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    /**
     * @return Side.TOP or Side.DOWN
     */
    public Side getSide1()
    {
        return side1;
    }

    /**
     * @return Side.LEFT or Side.RIGHT
     */
    public Side getSide2()
    {
        return side2;
    }

    /**
     * @return Side.FRONT or Side.BACK
     */
    public Side getSide3()
    {
        return side3;
    }
}