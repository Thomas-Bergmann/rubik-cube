package de.hatoka.kube;

import java.util.ArrayList;
import java.util.List;

public enum Kube2x2Move
{
    U("U", Side.UP, Step.CLOCKWISE), U2("U2", Side.UP, Step.DOUBLE), U_("U'", Side.UP, Step.REVERSE),
    R("R", Side.RIGHT, Step.CLOCKWISE), R2("R2", Side.RIGHT, Step.DOUBLE), R_("R'", Side.RIGHT, Step.REVERSE),
    F("F", Side.FRONT, Step.CLOCKWISE), F2("F2", Side.FRONT, Step.DOUBLE), F_("F'", Side.FRONT, Step.REVERSE),
    L("L", Side.LEFT, Step.CLOCKWISE), L2("L2", Side.LEFT, Step.DOUBLE), L_("L'", Side.LEFT, Step.REVERSE),
    B("B", Side.BACK, Step.CLOCKWISE), B2("B2", Side.BACK, Step.DOUBLE), B_("B'", Side.BACK, Step.REVERSE),
    D("D", Side.DOWN, Step.CLOCKWISE), D2("D2", Side.DOWN, Step.DOUBLE), D_("D'", Side.DOWN, Step.REVERSE);

    private final String notation;
    private final Side side;
    private final Step steps;

    Kube2x2Move(String notation, Side side, Step steps)
    {
        this.notation = notation;
        this.side = side;
        this.steps = steps;
    }

    /**
     * @return side to turn
     */
    public Side getSide()
    {
        return side;
    }

    /**
     * @return step how (in which direction) and how often the move is executed
     */
    public Step getStep()
    {
        return steps;
    }

    public String getNotation()
    {
        return notation;
    }

    private static Kube2x2Move single(String notation)
    {
        for (Kube2x2Move move : Kube2x2Move.values())
        {
            if (move.getNotation().equals(notation))
            {
                return move;
            }
        }
        throw new IllegalArgumentException("Unknown move: " + notation);
    }

    public static List<Kube2x2Move> fromNotation(String notation)
    {
        if (notation.isEmpty())
        {
            return List.of();
        }
        String[] parts = notation.replaceAll(" ", "").split("");
        int position = 0;
        Kube2x2Move lastMove = null;
        List<Kube2x2Move> result = new ArrayList<>();
        while(position < parts.length)
        {
            // can check for double or reverse moves
            if (lastMove != null)
            {
                if (parts[position].equals("'"))
                {
                    result.add(Kube2x2Move.values()[lastMove.ordinal() + 2]);
                    lastMove = null;
                    position++;
                    continue;
                }
                if (parts[position].equals("2"))
                {
                    result.add(Kube2x2Move.values()[lastMove.ordinal() + 1]);
                    lastMove = null;
                    position++;
                    continue;
                }
            }
            if (lastMove != null)
            {
                result.add(lastMove);
            }
            lastMove = single(parts[position]);
            position++;
        }
        if (lastMove != null)
        {
            result.add(lastMove);
        }
        return result;
    }
}
