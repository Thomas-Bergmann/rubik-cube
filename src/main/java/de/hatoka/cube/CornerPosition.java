package de.hatoka.cube;

/**
 * Represents the position of a corner piece. The ordinal of the enum is important for rotating method.
 * The first corner is the top left front corner, followed around the clock on top level.
 * Then down from same position, also around the clock, but from side "Down" perspective.
 * <ol>
 *     <li>First letter T/D: Top or Down</li>
 *     <li>Second letter L/R: Left or Right</li>
 *     <li>Third letter F/B: Front or Back</li>
 * </ol>
 */
public enum CornerPosition
{
    TLF, TLB, TRB, TRF,
    DRF, DRB, DLB, DLF;
}