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
            case L -> rotateLeftClockwise();
            case L_ -> rotateLeftCounterClockwise();
            case R -> rotateRightClockwise();
            case R_ -> rotateRightCounterClockwise();
            case D -> rotateDownClockwise();
            case D_ -> rotateDownCounterClockwise();
            case U -> rotateUpClockwise();
            case U_ -> rotateUpCounterClockwise();
            case B -> rotateBackClockwise();
            case B_ -> rotateBackCounterClockwise();
            case F -> rotateFrontClockwise();
            case F_ -> rotateFrontCounterClockwise();
            case L2 -> move(Move2x2.L).move(Move2x2.L);
            case R2 -> move(Move2x2.R).move(Move2x2.R);
            case F2 -> move(Move2x2.F).move(Move2x2.F);
            case U2 -> move(Move2x2.U).move(Move2x2.U);
            case D2 -> move(Move2x2.D).move(Move2x2.D);
            case B2 -> move(Move2x2.B).move(Move2x2.B);
        };
    }

    private State2x2 rotateBackCounterClockwise()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        return new State2x2(newCornerIndices, newOrientations);
    }

    private State2x2 rotateBackClockwise()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        return new State2x2(newCornerIndices, newOrientations);
    }

    private State2x2 rotateUpCounterClockwise()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        return new State2x2(newCornerIndices, newOrientations);
    }

    private State2x2 rotateUpClockwise()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        return new State2x2(newCornerIndices, newOrientations);
    }

    private State2x2 rotateDownCounterClockwise()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        return new State2x2(newCornerIndices, newOrientations);
    }

    private State2x2 rotateDownClockwise()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        return new State2x2(newCornerIndices, newOrientations);
    }

    private State2x2 rotateRightCounterClockwise()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        return new State2x2(newCornerIndices, newOrientations);
    }

    private State2x2 rotateRightClockwise()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        return new State2x2(newCornerIndices, newOrientations);
    }

    /**
     * Rotate the front face (clockwise) and return a new RubiksCube2x2 instance
     *
     * @return new cube state
     */
    public State2x2 rotateFrontClockwise()
    {
        // Update the corner indices (just swapping references)
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        newCornerIndices[0] = cornerIndices[7];
        newCornerIndices[7] = cornerIndices[4];
        newCornerIndices[4] = cornerIndices[3];
        newCornerIndices[3] = cornerIndices[0];

        // Update the orientations
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        newOrientations[0] = orientations[7] + 1;
        newOrientations[3] = orientations[4] + 2;
        newOrientations[4] = orientations[3] + 1;
        newOrientations[7] = orientations[0] + 2;

        // Return a new Cube2x2State with the updated state
        return new State2x2(newCornerIndices, newOrientations);
    }

    /**
     * Rotate the front face (counter-clockwise) and return a new cube instance
     *
     * @return new cube state
     */
    public State2x2 rotateFrontCounterClockwise()
    {
        // Update the corner indices (just swapping references)
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        newCornerIndices[0] = cornerIndices[3];
        newCornerIndices[3] = cornerIndices[4];
        newCornerIndices[4] = cornerIndices[7];
        newCornerIndices[7] = cornerIndices[0];

        // Update the orientations
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        newOrientations[0] = orientations[3] + 2;
        newOrientations[3] = orientations[4] + 1;
        newOrientations[4] = orientations[7] + 2;
        newOrientations[7] = orientations[0] + 1;

        // Return a new Cube2x2State with the updated state
        return new State2x2(newCornerIndices, newOrientations);
    }

    /**
     * Rotate the front face (clockwise) and return a new RubiksCube2x2 instance
     *
     * @return new cube state
     */
    public State2x2 rotateLeftClockwise()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        newCornerIndices[0] = cornerIndices[1];
        newCornerIndices[1] = cornerIndices[6];
        newCornerIndices[6] = cornerIndices[7];
        newCornerIndices[7] = cornerIndices[0];
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        newOrientations[0] = orientations[1] + 2;
        newOrientations[1] = orientations[6] + 1;
        newOrientations[6] = orientations[7] + 2;
        newOrientations[7] = orientations[0] + 1;
        return new State2x2(newCornerIndices, newOrientations);
    }

    /**
     * Rotate the front face (counter-clockwise) and return a new cube instance
     *
     * @return new cube state
     */
    public State2x2 rotateLeftCounterClockwise()
    {
        int[] newCornerIndices = Arrays.copyOf(cornerIndices, cornerIndices.length);
        int[] newOrientations = Arrays.copyOf(orientations, orientations.length);
        return new State2x2(newCornerIndices, newOrientations);
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
