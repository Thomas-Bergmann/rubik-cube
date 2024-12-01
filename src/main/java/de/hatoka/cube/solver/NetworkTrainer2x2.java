package de.hatoka.cube.solver;

import de.hatoka.cube.Move2x2;
import de.hatoka.cube.State2x2;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Solves a 2x2 cube using a neural network.
 */
public class NetworkTrainer2x2 implements Solver2x2
{
    private static final int NUMBER_OF_MOVES = Move2x2.values().length;
    private static final Random RANDOMIZER = new Random(10);
    private CubeNetwork2x2 trainedNetwork; // network which solves cube with one move less

    @Override
    public List<Move2x2> solve(State2x2 state)
    {
        if (trainedNetwork == null)
        {
            train();
        }
        return trainedNetwork.solve(state);
    }

    private void train()
    {
        train(1);
        train(2);
    }

    private void train(int numberOfMoves)
    {
        CubeNetwork2x2 networkUnderTraining = CubeNetwork2x2.create(numberOfMoves);
        List<Training2x2> trainingProgram = createTrainingProgram(numberOfMoves);
        // unknown how to train yet
        if (trainingProgram.isEmpty())
        {
            return;
        }
        long percentage = 0;
        int countTrainings = 0;
        double trainingAdaption = 0;
        while(percentage < 99)
        {
            // do training
            trainingAdaption = trainingProgram.stream()
                                              .map(networkUnderTraining::train)
                                              .reduce(0.0, Double::sum);
            countTrainings++;
            // verify training
            long correct = trainingProgram.stream().filter(networkUnderTraining::verify).count();
            percentage = correct * 100 / trainingProgram.size(); // 100% / 1000 iterations
            if (countTrainings % 10 == 0) {
                LoggerFactory.getLogger(getClass()).debug("solves to {}% and with {} training sessions with effort {}.", percentage, countTrainings, trainingAdaption);
            }
        }
        LoggerFactory.getLogger(getClass()).debug("solves it with {} training sessions.", countTrainings);
        this.trainedNetwork = networkUnderTraining;
    }

    private List<Training2x2> createTrainingProgram(int numberOfMoves)
    {
        List<Training2x2> trainingLastLevel = createTrainingProgram(State2x2.INITIAL);
        if (numberOfMoves == 1)
        {
            return trainingLastLevel;
        }
        if (numberOfMoves == 2)
        {
            return List.of();
        }
        List<Training2x2> trainingNextLevel = new ArrayList<>(trainingLastLevel);
        for(Training2x2 lastLevel : trainingLastLevel)
        {
            trainingNextLevel.addAll(createTrainingProgram(lastLevel.state()));
        }
        return trainingNextLevel;
    }

    private List<Training2x2> createTrainingProgram(State2x2 previousState)
    {
        return Arrays.stream(Move2x2.values()).map(move -> new Training2x2(previousState.move(move), move.getReverseMove())).toList();
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
}
