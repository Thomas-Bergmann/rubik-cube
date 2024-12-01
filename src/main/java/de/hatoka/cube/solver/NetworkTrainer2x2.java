package de.hatoka.cube.solver;

import de.hatoka.cube.Move2x2;
import de.hatoka.cube.State2x2;
import org.slf4j.LoggerFactory;

import java.util.*;

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
            this.trainedNetwork = train();
        }
        return trainedNetwork.solve(state);
    }

    private CubeNetwork2x2 train()
    {
        CubeNetwork2x2 network = train(1, Optional.empty());
        network = train(2, Optional.of(network));
        return network;
    }

    private CubeNetwork2x2 train(int numberOfMoves, Optional<CubeNetwork2x2> previousNetwork)
    {
        CubeNetwork2x2 networkUnderTraining = previousNetwork.orElse(CubeNetwork2x2.create(numberOfMoves));
        List<Training2x2> trainingProgram = createTrainingProgram(numberOfMoves);
        // unknown how to train yet
        if (trainingProgram.isEmpty())
        {
            return previousNetwork.orElse(null);
        }
        long percentage = 0;
        int countTrainings = 0;
        while(percentage < 99)
        {
            // do training
            long trainingAdaption = trainingProgram.stream().map(networkUnderTraining::train).reduce(0.0, Double::sum).longValue();
            countTrainings++;
            // verify training
            long correct = trainingProgram.stream().filter(networkUnderTraining::verify).count();
            percentage = correct * 100 / trainingProgram.size(); // 100% / 1000 iterations
            if (countTrainings % 10 == 0 || numberOfMoves > 1)
            {
                LoggerFactory.getLogger(getClass())
                             .debug("solves to {}% and with {} training sessions with effort {}.", percentage, countTrainings, trainingAdaption);
                if (percentage > 85)
                {
                    trainingProgram.stream()
                                   .filter(t -> !networkUnderTraining.verify(t))
                                   .forEach(t -> LoggerFactory.getLogger(getClass()).debug("can't solve {}.", t.scrambleMoves()));
                }
            }
        }
        LoggerFactory.getLogger(getClass()).debug("solves it with {} training sessions.", countTrainings);
        return networkUnderTraining;
    }

    private List<Training2x2> createTrainingProgram(int numberOfMoves)
    {
        List<Training2x2> trainingLastLevel = createTrainingProgram(new Training2x2(List.of(), State2x2.INITIAL, null));
        if (numberOfMoves == 1)
        {
            return trainingLastLevel;
        }
        List<Training2x2> trainingNextLevel = new ArrayList<>(trainingLastLevel);
        for (Training2x2 lastLevel : trainingLastLevel)
        {
            trainingNextLevel.addAll(createTrainingProgram(lastLevel));
        }
        return trainingNextLevel;
    }

    private List<Training2x2> createTrainingProgram(Training2x2 previousTraining)
    {
        return Arrays.stream(Move2x2.values()).map(move -> {
            List<Move2x2> scrambleMoves = new ArrayList<>(previousTraining.scrambleMoves());
            scrambleMoves.add(move);
            return new Training2x2(scrambleMoves, previousTraining.state().move(move), move.getReverseMove());
        }).filter(t -> !t.state().isFinished()).toList();
    }

    private List<Move2x2> randomMoves(int numberOfMoves)
    {
        List<Move2x2> result = new ArrayList<>(numberOfMoves);
        for (int i = 0; i < numberOfMoves; i++)
        {
            result.add(Move2x2.fromOrdinal(RANDOMIZER.nextInt(NUMBER_OF_MOVES)));
        }
        return result;
    }
}
