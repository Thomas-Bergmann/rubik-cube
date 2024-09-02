package de.hatoka.kube;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the positions of pieces of the 2x2 cube.
 */
public class Kube2x2State
{
    public static final Kube2x2State INITIAL = initial();

    private static Kube2x2State initial()
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
        return INITIAL.equals(this);
    }

    public Kube2x2State move(Kube2x2Move move)
    {
        Kube2x2State doubleResult = switch(move)
        {
            case L2 -> move(Kube2x2Move.L).move(Kube2x2Move.L);
            case R2 -> move(Kube2x2Move.R).move(Kube2x2Move.R);
            case F2 -> move(Kube2x2Move.F).move(Kube2x2Move.F);
            case U2 -> move(Kube2x2Move.U).move(Kube2x2Move.U);
            case D2 -> move(Kube2x2Move.D).move(Kube2x2Move.D);
            case B2 -> move(Kube2x2Move.B).move(Kube2x2Move.B);
            default -> null;
        };
        if (doubleResult != null)
        {
            return doubleResult;
        }

        Map<CornerPosition, CornerState> newPositions = new HashMap<>(positions);
        switch(move)
        {
            case L :
            {
                newPositions.put(CornerPosition.TLF, positions.get(CornerPosition.TLB));
                newPositions.put(CornerPosition.TLB, positions.get(CornerPosition.DLB));
                newPositions.put(CornerPosition.DLB, positions.get(CornerPosition.DLF));
                newPositions.put(CornerPosition.DLF, positions.get(CornerPosition.TLF));
                break;
            }
            case L_ :
            {
                newPositions.put(CornerPosition.TLF, positions.get(CornerPosition.DLF));
                newPositions.put(CornerPosition.DLF, positions.get(CornerPosition.DLB));
                newPositions.put(CornerPosition.DLB, positions.get(CornerPosition.TLB));
                newPositions.put(CornerPosition.TLB, positions.get(CornerPosition.TLF));
                break;
            }
            case R :
            {
                newPositions.put(CornerPosition.TRF, positions.get(CornerPosition.DRF));
                newPositions.put(CornerPosition.DRF, positions.get(CornerPosition.DRB));
                newPositions.put(CornerPosition.DRB, positions.get(CornerPosition.TRB));
                newPositions.put(CornerPosition.TRB, positions.get(CornerPosition.TRF));
                break;
            }
            case R_:
            {
                newPositions.put(CornerPosition.TRF, positions.get(CornerPosition.TRB));
                newPositions.put(CornerPosition.TRB, positions.get(CornerPosition.DRB));
                newPositions.put(CornerPosition.DRB, positions.get(CornerPosition.DRF));
                newPositions.put(CornerPosition.DRF, positions.get(CornerPosition.TRF));
                break;
            }
            case D:
            {
                newPositions.put(CornerPosition.DLF, positions.get(CornerPosition.DLB));
                newPositions.put(CornerPosition.DLB, positions.get(CornerPosition.DRB));
                newPositions.put(CornerPosition.DRB, positions.get(CornerPosition.DRF));
                newPositions.put(CornerPosition.DRF, positions.get(CornerPosition.DLF));
                break;
            }
            case D_:
            {
                newPositions.put(CornerPosition.DLF, positions.get(CornerPosition.DRF));
                newPositions.put(CornerPosition.DRF, positions.get(CornerPosition.DRB));
                newPositions.put(CornerPosition.DRB, positions.get(CornerPosition.DLB));
                newPositions.put(CornerPosition.DLB, positions.get(CornerPosition.DLF));
                break;
            }
            case U:
            {
                newPositions.put(CornerPosition.TLF, positions.get(CornerPosition.TRF));
                newPositions.put(CornerPosition.TRF, positions.get(CornerPosition.TRB));
                newPositions.put(CornerPosition.TRB, positions.get(CornerPosition.TLB));
                newPositions.put(CornerPosition.TLB, positions.get(CornerPosition.TLF));
                break;
            }
            case U_:
            {
                newPositions.put(CornerPosition.TLF, positions.get(CornerPosition.TLB));
                newPositions.put(CornerPosition.TLB, positions.get(CornerPosition.TRB));
                newPositions.put(CornerPosition.TRB, positions.get(CornerPosition.TRF));
                newPositions.put(CornerPosition.TRF, positions.get(CornerPosition.TLF));
                break;
            }
            case F:
            {
                newPositions.put(CornerPosition.TLF, positions.get(CornerPosition.DLF));
                newPositions.put(CornerPosition.DLF, positions.get(CornerPosition.DRF));
                newPositions.put(CornerPosition.DRF, positions.get(CornerPosition.TRF));
                newPositions.put(CornerPosition.TRF, positions.get(CornerPosition.TLF));
                break;
            }
            case F_:
            {
                newPositions.put(CornerPosition.TLF, positions.get(CornerPosition.TRF));
                newPositions.put(CornerPosition.TRF, positions.get(CornerPosition.DRF));
                newPositions.put(CornerPosition.DRF, positions.get(CornerPosition.DLF));
                newPositions.put(CornerPosition.DLF, positions.get(CornerPosition.TLF));
                break;
            }
            case B:
            {
                newPositions.put(CornerPosition.TLB, positions.get(CornerPosition.TRF));
                newPositions.put(CornerPosition.TRF, positions.get(CornerPosition.DRB));
                newPositions.put(CornerPosition.DRB, positions.get(CornerPosition.DLB));
                newPositions.put(CornerPosition.DLB, positions.get(CornerPosition.TLB));
                break;
            }
            case B_:
            {
                newPositions.put(CornerPosition.TLB, positions.get(CornerPosition.DLB));
                newPositions.put(CornerPosition.DLB, positions.get(CornerPosition.DRB));
                newPositions.put(CornerPosition.DRB, positions.get(CornerPosition.TRF));
                newPositions.put(CornerPosition.TRF, positions.get(CornerPosition.TLB));
                break;
            }
        }
        return new Kube2x2State(newPositions);
    }

    /**
     * Turn the cube side clockwise.
     * @param side to look at the cube
     * @return state after turning
     */
    public Kube2x2State turn(Side side)
    {
        return switch(side)
        {
            case RIGHT -> move(Kube2x2Move.R).move(Kube2x2Move.L_);
            case LEFT -> move(Kube2x2Move.L).move(Kube2x2Move.R_);
            case UP -> move(Kube2x2Move.U).move(Kube2x2Move.D_);
            case DOWN -> move(Kube2x2Move.D).move(Kube2x2Move.U_);
            case FRONT -> move(Kube2x2Move.F).move(Kube2x2Move.B_);
            case BACK -> move(Kube2x2Move.B).move(Kube2x2Move.F_);
        };
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Kube2x2State that)) return false;
        return Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(positions);
    }

    @Override
    public String toString()
    {
        return "Kube2x2State{" + "positions=" + positions + '}';
    }
}
