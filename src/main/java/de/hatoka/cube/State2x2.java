package de.hatoka.cube;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents the positions of pieces of the 2x2 cube.
 */
public class State2x2
{
    public static final State2x2 INITIAL = initial();
    // Immutable corner stones representing the eight corners of the cube
    private static final CornerStone[] cornerStones = new CornerStone[] { CornerStone.fromNotation("WRG"),
                    // Top-front-left
                    CornerStone.fromNotation("WGO"), // Top-back-left
                    CornerStone.fromNotation("WOB"), // Top-back-right
                    CornerStone.fromNotation("WBR"), // Top-front-right
                    CornerStone.fromNotation("YRB"), // Bottom-front-right
                    CornerStone.fromNotation("YBO"), // Bottom-back-right
                    CornerStone.fromNotation("YOG"), // Bottom-back-left
                    CornerStone.fromNotation("YGR")  // Bottom-front-left
    };

    private static State2x2 initial()
    {
        // The solved state: corners are in their default positions with orientation 0
        int[] cornerIndices = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };  // Indices point to corner stones
        int[] orientations = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };   // All orientations are 0 (solved)
        return new State2x2(cornerIndices, orientations);
    }

    // Indices to track corner positions, we simply rotate these indices
    private final int[] cornerIndices;
    // Array to track orientations for each corner (no modulo applied during rotation)
    private final int[] orientations;

    private State2x2(int[] cornerIndices, int[] orientations)
    {
        this.cornerIndices = cornerIndices;
        this.orientations = orientations;
    }

    /**
     * @return true if the cube is in the solved state
     */
    public boolean isFinished()
    {
        return INITIAL.equals(this);
    }

    /**
     * @param position corner position
     * @return true if the corner is solved
     */
    public boolean isFinished(CornerPosition position)
    {
        return getColors(position).equals(INITIAL.getColors(position));
    }

    public State2x2 move(Move2x2 move)
    {
        return switch(move)
        {
            case L -> rotate(CornerPosition.TLB, CornerPosition.DLB, CornerPosition.DLF, CornerPosition.TLF);
            case L_ -> rotate(CornerPosition.TLB, CornerPosition.TLF, CornerPosition.DLF, CornerPosition.DLB);
            case R -> rotate(CornerPosition.TRF, CornerPosition.DRF, CornerPosition.DRB, CornerPosition.TRB);
            case R_ -> rotate(CornerPosition.TRF, CornerPosition.TRB, CornerPosition.DRB, CornerPosition.DRF);
            case D -> rotateHorizontal(CornerPosition.DRF, CornerPosition.DLF, CornerPosition.DLB, CornerPosition.DRB);
            case D_ -> rotateHorizontal(CornerPosition.DRF, CornerPosition.DRB, CornerPosition.DLB, CornerPosition.DLF);
            case U -> rotateHorizontal(CornerPosition.TLF, CornerPosition.TRF, CornerPosition.TRB, CornerPosition.TLB);
            case U_ -> rotateHorizontal(CornerPosition.TLF, CornerPosition.TLB, CornerPosition.TRB, CornerPosition.TRF);
            case B -> rotate(CornerPosition.TRB, CornerPosition.DRB, CornerPosition.DLB, CornerPosition.TLB);
            case B_ -> rotate(CornerPosition.TRB, CornerPosition.TLB, CornerPosition.DLB, CornerPosition.DRB);
            case F -> rotate(CornerPosition.TLF, CornerPosition.DLF, CornerPosition.DRF, CornerPosition.TRF);
            case F_ -> rotate(CornerPosition.TLF, CornerPosition.TRF, CornerPosition.DRF, CornerPosition.DLF);
            case L2 -> move(Move2x2.L).move(Move2x2.L);
            case R2 -> move(Move2x2.R).move(Move2x2.R);
            case F2 -> move(Move2x2.F).move(Move2x2.F);
            case U2 -> move(Move2x2.U).move(Move2x2.U);
            case D2 -> move(Move2x2.D).move(Move2x2.D);
            case B2 -> move(Move2x2.B).move(Move2x2.B);
        };
    }

    private State2x2 rotateOpen()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        return new State2x2(newCornerIndices, newOrientations);
    }

    private State2x2 rotateHorizontal(CornerPosition... positions)
    {
        int[] rotatedCornerIndices = calculateCornerIndices(positions);
        int[] newCornerIndices = rotateIndices(rotatedCornerIndices);
        int[] newOrientations = rotateOrientationsHorizontal(rotatedCornerIndices);
        return new State2x2(newCornerIndices, newOrientations);
    }

    /**
     * Update the orientations of the corners after rotation
     * @param rotatedCornerIndices indices of corners to rotate
     * @return new orientations
     */
    private int[] rotateOrientationsHorizontal(int... rotatedCornerIndices)
    {
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        newOrientations[rotatedCornerIndices[0]] = orientations[rotatedCornerIndices[1]];
        newOrientations[rotatedCornerIndices[1]] = orientations[rotatedCornerIndices[2]];
        newOrientations[rotatedCornerIndices[2]] = orientations[rotatedCornerIndices[3]];
        newOrientations[rotatedCornerIndices[3]] = orientations[rotatedCornerIndices[0]];
        return newOrientations;
    }

    /**
     * Swap the corner indices for rotation
     * @param rotatedCornerIndices indices of corners to rotate
     * @return new corner indices
     */
    private int[] rotateIndices(int... rotatedCornerIndices)
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        newCornerIndices[rotatedCornerIndices[0]] = cornerIndices[rotatedCornerIndices[1]];
        newCornerIndices[rotatedCornerIndices[1]] = cornerIndices[rotatedCornerIndices[2]];
        newCornerIndices[rotatedCornerIndices[2]] = cornerIndices[rotatedCornerIndices[3]];
        newCornerIndices[rotatedCornerIndices[3]] = cornerIndices[rotatedCornerIndices[0]];
        return newCornerIndices;
    }

    /**
     * Update the orientations of the corners after rotation
     * @param rotatedCornerIndices indices of corners to rotate
     * @return new orientations
     */
    private int[] rotateOrientations(int... rotatedCornerIndices)
    {
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        newOrientations[rotatedCornerIndices[0]] = (orientations[rotatedCornerIndices[1]] + 1) % 3;
        newOrientations[rotatedCornerIndices[1]] = (orientations[rotatedCornerIndices[2]] + 2) % 3;
        newOrientations[rotatedCornerIndices[2]] = (orientations[rotatedCornerIndices[3]] + 1) % 3;
        newOrientations[rotatedCornerIndices[3]] = (orientations[rotatedCornerIndices[0]] + 2) % 3;
        return newOrientations;
    }

    /**
     * rotate corner indices and update orientation
     * @param positions corners to rotate
     * @return new cube state
     */
    private State2x2 rotate(CornerPosition... positions)
    {
        int[] rotatedCornerIndices = calculateCornerIndices(positions);
        int[] newCornerIndices = rotateIndices(rotatedCornerIndices);
        int[] newOrientations = rotateOrientations(rotatedCornerIndices);
        return new State2x2(newCornerIndices, newOrientations);
    }

    private static int[] calculateCornerIndices(CornerPosition[] positions)
    {
        int[] rotatedCornerIndices = new int[positions.length];
        for (int i = 0; i < positions.length; i++)
        {
            rotatedCornerIndices[i] = positions[i].ordinal();
        }
        return rotatedCornerIndices;
    }

    /**
     * Turn the cube side clockwise.
     *
     * @param side to look at the cube
     * @return state after turning
     */
    public State2x2 turn(Side side)
    {
        return switch(side)
        {
            case RIGHT -> move(Move2x2.R).move(Move2x2.L_);
            case LEFT -> move(Move2x2.L).move(Move2x2.R_);
            case UP -> move(Move2x2.U).move(Move2x2.D_);
            case DOWN -> move(Move2x2.D).move(Move2x2.U_);
            case FRONT -> move(Move2x2.F).move(Move2x2.B_);
            case BACK -> move(Move2x2.B).move(Move2x2.F_);
        };
    }

    public State2x2 move(List<Move2x2> moves)
    {
        State2x2 state = this;
        for (Move2x2 move : moves)
        {
            state = state.move(move);
        }
        return state;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof State2x2 that)) return false;
        return getCornerColors().equals(that.getCornerColors());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getCornerColors());
    }

    @Override
    public String toString()
    {
        StringBuilder b = new StringBuilder("Cube2x2State\n");
        b.append("Corners:").append(getCornerColors()).append("\n");
        for (CornerPosition position : CornerPosition.values())
        {
            b.append("{ Corner " + position.ordinal() + ": ");
            for (Color color : getColors(position))
            {
                b.append(color).append(" ");
            }
            b.append("}\n");
        }
        return b.toString();
    }

    /**
     * @return list of colors of the corner stones (sorted by corner and clockwise from top or bottom)
     */
    public List<Color> getCornerColors()
    {
        List<Color> colors = new ArrayList<>();
        for (CornerPosition position : CornerPosition.values())
        {
            colors.addAll(getColors(position));
        }
        return colors;
    }

    /**
     * @param orientation number of rotations
     * @param index index of position
     * @return color of position
     */
    private int getCornerPosition(int orientation, int index)
    {
        return (index + orientation) % 3;
    }

    /**
     * @param position corner position
     * @return list of colors of the corner stone (started at top or bottom and then clockwise)
     */
    public List<Color> getColors(CornerPosition position)
    {
        int index = position.ordinal();
        List<Color> colors = new ArrayList<>();
        for (int j = 0; j < 3; j++)
        {
            colors.add(cornerStones[cornerIndices[index]].getColor(getCornerPosition(orientations[index], j)));
        }
        return colors;
    }
}
