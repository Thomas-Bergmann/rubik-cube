package de.hatoka.cube.solver;

import de.hatoka.basicneuralnetwork.NetworkBuilder;
import de.hatoka.basicneuralnetwork.NeuralNetwork;
import de.hatoka.cube.CornerPosition;
import de.hatoka.cube.CornerStone;
import de.hatoka.cube.Move2x2;
import de.hatoka.cube.State2x2;

import java.util.ArrayList;
import java.util.List;

/**
 * A neural network, which has cube state as input and cube then next move/turn as output
 */
public class CubeNetwork2x2 implements Solver2x2
{
    private static final int AMOUNT_INPUT_CORNER_POSITION = 8;
    private static final int AMOUNT_INPUT_CORNER_ORIENTATION = 8;
    private static final int AMOUNT_INPUT = AMOUNT_INPUT_CORNER_ORIENTATION + AMOUNT_INPUT_CORNER_POSITION;
    private static final int NUMBER_OF_MOVES = Move2x2.values().length;
    private static final int AMOUNT_OUTPUT = NUMBER_OF_MOVES;
    private final NeuralNetwork network; // network which solves cube with one move less
    private final int maxNumberOfMoves;

    public static CubeNetwork2x2 create(int maxNumberOfMoves)
    {
        return new CubeNetwork2x2(maxNumberOfMoves, createNetwork(maxNumberOfMoves));
    }

    /**
     * Creates a network
     *
     * @param maxNumberOfMoves expected number of moves to solve the cube (could be less)
     * @return cube network
     */
    private static NeuralNetwork createNetwork(int maxNumberOfMoves)
    {
        return NetworkBuilder.create(AMOUNT_INPUT, AMOUNT_OUTPUT).setHiddenLayers(1, AMOUNT_INPUT * AMOUNT_OUTPUT).build();
    }

    private CubeNetwork2x2(int maxNumberOfMoves, NeuralNetwork network)
    {
        this.maxNumberOfMoves = maxNumberOfMoves;
        this.network = network;
    }

    @Override
    public List<Move2x2> solve(State2x2 state)
    {
        List<Move2x2> result = new ArrayList<>();
        while(!state.isFinished() && result.size() < this.maxNumberOfMoves)
        {
            Move2x2 move = guess(state);
            state = state.move(move);
            result.add(move);
        }
        return result;
    }

    /**
     * Guess the next move/turn
     *
     * @param state state of cube
     * @return next move to solve the cube (guess)
     */
    private Move2x2 guess(State2x2 state)
    {
        return getOutput(network.guess(getInput(state)));
    }

    /**
     * Train the next move/turn
     * @param training training step ( state of cube + correct move to solve cube)
     * @return adaption of network
     */
    public double train(Training2x2 training)
    {
        return train(training.state(), training.move());
    }

    /**
     * @param training training step ( state of cube + correct move to solve cube)
     * @return true if network answers with correct move
     */
    public boolean verify(Training2x2 training)
    {
        return training.move() == guess(training.state());
    }

    /**
     * Train the next move/turn
     *
     * @param state state of cube
     * @param move correct move to solve cube
     * @return adaption of network
     */
    private double train(State2x2 state, Move2x2 move)
    {
        double[] input = getInput(state);
        double[] corrected = getOutput(move);
        return network.train(input, corrected);
    }

    /**
     * Adapt guessed values with the "correct" values in case the cube is finished.
     * @param move correct move
     * @return adapted guessed values
     */
    private static double[] getOutput(Move2x2 move)
    {
        double[] corrected = new double[NUMBER_OF_MOVES];
        corrected[move.ordinal()] = 1;
        return corrected;
    }

    /**
     * Convert cube state to input for network
     *
     * @param state cube state
     * @return input for network
     */
    private static double[] getInput(State2x2 state)
    {
        double[] result = new double[AMOUNT_INPUT];
        int i = 0;
        for (CornerStone cornerStone : CornerStone.values())
        {
            result[i++] = (double)(state.getCornerPosition(cornerStone).ordinal() + 1) / CornerPosition.values().length;
            result[i++] = (double)(state.getRotation(cornerStone) + 1) / 3;
        }
        return result;
    }

    /**
     * Maps the output of the network to one move
     *
     * @param guessed guess of network for one move
     * @return move
     */
    private static Move2x2 getOutput(double[] guessed)
    {
        Move2x2 bestMove = Move2x2.fromOrdinal(0);
        for (int currentMoveIndex = 1; currentMoveIndex < guessed.length; currentMoveIndex++)
        {
            if (guessed[currentMoveIndex] > guessed[bestMove.ordinal()])
            {
                bestMove = Move2x2.fromOrdinal(currentMoveIndex);
            }
        }
        return bestMove;
    }
}
