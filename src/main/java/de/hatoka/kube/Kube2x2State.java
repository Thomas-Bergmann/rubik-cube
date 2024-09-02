package de.hatoka.kube;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the positions of pieces of the 2x2 cube.
 */
public class Kube2x2State
{
    public static Kube2x2State initial()
    {
        Map<CornerPosition, CornerState> positions = new HashMap<>();
        positions.put(CornerPosition.TLF, CornerState.valueOf(CornerPiece.WOG));
        positions.put(CornerPosition.TRF, CornerState.valueOf(CornerPiece.WGR));
        positions.put(CornerPosition.TRB, CornerState.valueOf(CornerPiece.WRB));
        positions.put(CornerPosition.TLB, CornerState.valueOf(CornerPiece.WBO));

        positions.put(CornerPosition.DLF, CornerState.valueOf(CornerPiece.YGO));
        positions.put(CornerPosition.DRF, CornerState.valueOf(CornerPiece.YRG));
        positions.put(CornerPosition.DRB, CornerState.valueOf(CornerPiece.YBR));
        positions.put(CornerPosition.DLB, CornerState.valueOf(CornerPiece.YOB));

        return new Kube2x2State(positions);
    }

    private final Map<CornerPosition, CornerState> positions;

    private Kube2x2State(Map<CornerPosition, CornerState> positions)
    {
        this.positions = Collections.unmodifiableMap(positions);
    }

    public Map<CornerPosition, CornerState> getPositions()
    {
        return positions;
    }

    public boolean isFinished()
    {
        return true;
    }
}
