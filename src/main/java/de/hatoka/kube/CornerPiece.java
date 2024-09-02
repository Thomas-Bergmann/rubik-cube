package de.hatoka.kube;

public enum CornerPiece
{
    WGR(Color.WHITE, Color.GREEN, Color.RED), WRB(Color.WHITE, Color.RED, Color.BLUE),
    WBO(Color.WHITE, Color.BLUE, Color.ORANGE), WOG(Color.WHITE, Color.ORANGE, Color.GREEN),
    YOB(Color.YELLOW, Color.ORANGE, Color.BLUE), YBR(Color.YELLOW, Color.BLUE, Color.RED),
    YRG(Color.YELLOW, Color.RED, Color.GREEN), YGO(Color.YELLOW, Color.GREEN, Color.ORANGE);

    private final Color top;
    private final Color front;
    private final Color right;

    CornerPiece(Color top, Color front, Color right)
    {
        this.top = top;
        this.front = front;
        this.right = right;
    }
}
