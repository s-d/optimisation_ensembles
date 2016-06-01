package ensembles;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.util.ArrayList;

/**
 * Created by 40056761 on 31/05/2016.
 */
class EnsembleHyperHeuristic extends HyperHeuristic {

    private static ArrayList<int[]> algorithms;
    private int iteration;
    private long algorithmSeed;
    private int problemInstance;
    private long problemSeed;
    private Ensemble ensemble;

    EnsembleHyperHeuristic(Ensemble ensemble, long algorithmSeed, long problemSeed, int problemInstance, int iteration) {
        super(algorithmSeed);
        this.ensemble = ensemble;
        this.algorithmSeed = algorithmSeed;
        this.problemSeed = problemSeed;
        this.problemInstance = problemInstance;
        this.iteration = iteration;
    }

    @Override
    protected void solve(ProblemDomain problemDomain) {
        String output;
        int runs = 0;
        int noImprovement = 0;
        double startingFitness;
        double currentFitness;
        double bestFitness;
        double newFitness;
        double delta;

        problemDomain.setMemorySize(3);
        problemDomain.initialiseSolution(0);
        problemDomain.copySolution(0, 1);

        startingFitness = problemDomain.getBestSolutionValue();
        currentFitness = startingFitness;
        bestFitness = Double.POSITIVE_INFINITY;

        while (!hasTimeExpired()) {
            for (Algorithm currentAlg : ensemble.getAlgorithms()) {
                for (int currentHeuristic : currentAlg.getHeuristics()) {

                    newFitness = problemDomain.applyHeuristic(currentHeuristic, 1, 2);
                    bestFitness = (newFitness < bestFitness) ? newFitness : bestFitness;

                    delta = currentFitness - newFitness;

                    if (delta > 0) {
                        problemDomain.copySolution(2, 1);
                        currentFitness = newFitness;
                        noImprovement = 0;
                    } else {
                        noImprovement++;
                    }
                }   //heuristic
            }   //algorithm

            runs++;

            if (noImprovement < ensemble.getAlgorithms().size() * 3) {
                noImprovement = 0;
            } else {
                output = ("" + iteration + "," + problemInstance + "," + problemSeed + "," + algorithmSeed + "," + startingFitness + "," + ensemble.getId() + "," + bestFitness + "," + runs + ",\"" + ensemble + "\"\n");
                try {
                    RunRandomEnsembles.WriteData(output);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.printf("ensemble %d, problem %d, iteration %d complete.\n",ensemble.getId(), problemInstance, this.iteration);
                return;
            }

        }   //while

    }   //method

    @Override
    public String toString() {
        return null;
    }
}
