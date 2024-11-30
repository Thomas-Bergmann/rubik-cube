package de.hatoka.cube.solver;

import de.hatoka.basicneuralnetwork.NetworkBuilder;
import de.hatoka.basicneuralnetwork.NeuralNetwork;
import de.hatoka.cube.CornerPosition;
import de.hatoka.cube.CornerStone;
import de.hatoka.cube.Move2x2;
import de.hatoka.cube.State2x2;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final Random RANDOMIZER = new Random(10);
    private NeuralNetwork network;

    public void train()
    {
        this.network = NetworkBuilder.create(AMOUNT_INPUT, AMOUNT_OUTPUT).setHiddenLayers(1, AMOUNT_INPUT * AMOUNT_OUTPUT).build();
        train(1);
    }

    private void train(int numberOfMoves)
    {
        int percentage = 0;
        while(percentage < 99)
        {
            double training = 0;
            int correct = 0;
            for (int i = 0; i < 1000; i++)
            {
                training = 0;
                for (int j = 0; j < numberOfMoves * NUMBER_OF_MOVES; j++)
                {
                    State2x2 startState = State2x2.INITIAL.move(randomMoves(numberOfMoves));
                    double[] input = getInput(startState);
                    double[] guessed = network.guess(input);
                    Move2x2 guessedMove = mapMove(guessed);
                    boolean isFinished = startState.move(guessedMove).isFinished();
                    if (isFinished)
                    {
                        correct++;
                    }
                    double[] corrected = getCorrected(guessed, isFinished, guessedMove.ordinal());
                    training += network.train(input, corrected);
                }
            }
            percentage = correct / (NUMBER_OF_MOVES * 10); // 100% / 1000 iterations
            LoggerFactory.getLogger(getClass()).debug("solves to {}% and training {}.", percentage, training);
        }
    }

    private List<Move2x2> randomMoves(int numberOfMoves)
    {
        List<Move2x2> result = new ArrayList<>(numberOfMoves);
        for(int i=0; i<numberOfMoves; i++)
        {
            result.add(Move2x2.fromOrdinal(RANDOMIZER.nextInt(NUMBER_OF_MOVES)));
        }
        return result;
    }

    /**
     * Adapt guessed values with the "correct" values in case the cube is finished.
     *
     * @param guessed guessed values from network
     * @param isFinished is the cube finished
     * @param guessedPosition position of guessed move
     * @return adapted guessed values
     */
    private static double[] getCorrected(double[] guessed, boolean isFinished, int guessedPosition)
    {
        double[] corrected = guessed.clone();
        for (int i = 0; i < guessed.length; i++)
        {
            // correct guess - set moved to 1; set non-moved to 0;
            // incorrect guess - set moved to 0; set non-moved to 1
            if (i != guessedPosition)
            {
                corrected[i] = isFinished ? 0 : 1;
            }
            else
            {
                corrected[i] = isFinished ? 1 : 0;
            }
        }
        return corrected;
    }

    /**
     * Convert cube state to input for network
     *
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
     * Maps the output of the network to one move
     *
     * @param guessed guess of network for one move
     * @return move
     */
    private Move2x2 mapMove(double[] guessed)
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

    @Override
    public List<Move2x2> solve(State2x2 state)
    {
        if (network == null)
        {
            train();
        }
        int numberOfMoves = 0;
        List<Move2x2> result = new ArrayList<>();
        while(!state.isFinished() && numberOfMoves++ < 10)
        {
            Move2x2 move = guess(state);
            state = state.move(move);
            result.add(move);
        }
        return result;
    }

    /**
     * Tries to solve the cube
     *
     * @param state state of cube
     * @return list of moves to solve the cube (guesses)
     */
    private Move2x2 guess(State2x2 state)
    {
        return mapMove(network.guess(getInput(state)));
    }
}
