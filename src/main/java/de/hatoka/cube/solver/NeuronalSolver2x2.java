package de.hatoka.cube.solver;

import de.hatoka.basicneuralnetwork.NetworkBuilder;
import de.hatoka.basicneuralnetwork.NeuralNetwork;
import de.hatoka.cube.CornerPosition;
import de.hatoka.cube.CornerStone;
import de.hatoka.cube.Move2x2;
import de.hatoka.cube.State2x2;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Solves a 2x2 cube using a neural network.
 */
public class NeuronalSolver2x2 implements Solver2x2
{
    private static final int AMOUNT_INPUT_CORNER_POSITION = 8;
    private static final int AMOUNT_INPUT_CORNER_ORIENTATION = 8;
    private static final int AMOUNT_INPUT = AMOUNT_INPUT_CORNER_ORIENTATION + AMOUNT_INPUT_CORNER_POSITION;
    private static final int NUMBER_OF_MOVES = Move2x2.values().length;
    private static final int AMOUNT_OUTPUT = NUMBER_OF_MOVES;
    private NeuralNetwork network;

    public void train()
    {
        NeuralNetwork network = NetworkBuilder.create(AMOUNT_INPUT, AMOUNT_OUTPUT)
                                              .setHiddenLayers(1, AMOUNT_INPUT * AMOUNT_OUTPUT)
                                              .build();
        this.network = network;
        int percentage = 0;
        while(percentage < 99)
        {
            double training = 0;
            int correct = 0;
            for (int i = 0; i < 1000; i++)
            {
                training = 0;
                for (Move2x2 move : Move2x2.values())
                {
                    State2x2 state = State2x2.INITIAL.move(move);
                    double[] input = getInput(state);
                    double[] guessed = network.guess(input);
                    List<Move2x2> moves = mapMoves(guessed);
                    boolean isFinished = state.move(moves).isFinished();
                    if (isFinished)
                    {
                        correct++;
                    }
                    double[] corrected = getCorrected(guessed, isFinished, moves);
                    training += network.train(input, corrected);
                }
            }
            percentage = correct / (NUMBER_OF_MOVES * 10); // 100% / 1000 iterations
            LoggerFactory.getLogger(getClass()).debug("solves to {}% and training {}.", percentage, training);
        }
    }

    /**
     * Adapt guessed values with the "correct" values in case the cube is finished.
     *
     * @param guessed guessed values from network
     * @param isFinished is the cube finished
     * @param moves moves to solve the cube
     * @return adapted guessed values
     */
    private static double[] getCorrected(double[] guessed, boolean isFinished, List<Move2x2> moves)
    {
        double[] corrected = guessed.clone();
        for (int moveIndex = 0; moveIndex < moves.size(); moveIndex++)
        {
            int ordinalMove = moves.get(moveIndex).ordinal() + moveIndex * NUMBER_OF_MOVES;
            for (int i = moveIndex * NUMBER_OF_MOVES; i < (moveIndex + 1) * NUMBER_OF_MOVES; i++)
            {
                // correct guess - set moved to 1; set non-moved to 0;
                // incorrect guess - set moved to 0; set non-moved to 1
                if (i != ordinalMove)
                {
                    corrected[i] = isFinished ? 0 : 1;
                }
                else
                {
                    corrected[i] = isFinished ? 1 : 0;
                }
            }
        }
        return corrected;
    }

    /**
     * Convert cube state to input for network
     * @param state cube state
     * @return input for network
     */
    private double[] getInput(State2x2 state)
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
     * Maps the output ( of the network to moves
     * @param guessed guess of network (number of moves * number of output per move)
     * @return moves
     */
    private List<Move2x2> mapMoves(double[] guessed)
    {
        double[][] grouped = new double[guessed.length / NUMBER_OF_MOVES][NUMBER_OF_MOVES];
        for (int i = 0; i < guessed.length; i++)
        {
            grouped[i / NUMBER_OF_MOVES][i % NUMBER_OF_MOVES] = guessed[i];
        }
        return Arrays.stream(grouped).map(this::mapMove).toList();
    }

    /**
     * Maps the output of the network to one move
     * @param guessed guess of network for one move
     * @return move
     */
    private Move2x2 mapMove(double[] guessed)
    {
        Move2x2 bestMove = Move2x2.values()[0];
        for (int currentMoveIndex = 1; currentMoveIndex < guessed.length; currentMoveIndex++)
        {
            if (guessed[currentMoveIndex] > guessed[bestMove.ordinal()])
            {
                bestMove = Move2x2.values()[currentMoveIndex];
            }
        }
        return bestMove;
    }

    @Override
    public List<Move2x2> solve(State2x2 state)
    {
        if (network == null)
        {
            train();
        }
        return guess(state);
    }

    /**
     * Tries to solve the cube
     * @param state state of cube
     * @return list of moves to solve the cube (guesses)
     */
    private List<Move2x2> guess(State2x2 state)
    {
        return mapMoves(network.guess(getInput(state)));
    }
}
