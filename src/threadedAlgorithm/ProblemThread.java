package threadedAlgorithm;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;

/**
 * Created by 40056761 on 30/05/2016.
 */
 public class ProblemThread implements Runnable {
    private int iterations;        //number of times to run each problem instance
    private int problemInstance;    //starting problem instance

    ProblemThread(int iterations, int problemInstance) {
        this.iterations = iterations;
        this.problemInstance = problemInstance;
    }

    private static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.printf("%s: %s%n", threadName, message);
    }

    @Override
    public void run() {
        threadMessage("Starting problem " + problemInstance + ".");

        int problemSeed = 1000;
        int algorithmSeed = 1000;
        long timeLimit = 100000;

            /* inner loop, runs a problem "iterations" times */
        for (int j = 0; j < iterations; j++) {
            ProblemDomain problem = new BinPacking(problemSeed);
            HyperHeuristic algorithm = new AlgorithmData(algorithmSeed, problemSeed,
                    problemInstance, j);

            problem.loadInstance(problemInstance);

            algorithm.setTimeLimit(timeLimit);
            algorithm.loadProblemDomain(problem);
            algorithm.run();

            problemSeed++;
            algorithmSeed++;
        }
        threadMessage("Problem " + problemInstance + " complete.");
    }

}
