package ensembles;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import sun.dc.pr.PRError;

import java.util.ArrayList;

/**
 * Created by 40056761 on 31/05/2016.
 */
public class EnsembleHyperHeuristic extends HyperHeuristic {

    private static ArrayList<int[]> algorithms;
    private int problemInstance;
    private long algorithmSeed;
    private Ensemble ensemble;
    private long problemSeed;
    private int iteration;

    EnsembleHyperHeuristic(Ensemble ensemble, long algorithmSeed, long problemSeed, int problemInstance, int iteration) {
        super(algorithmSeed);
        this.problemInstance = problemInstance;
        this.algorithmSeed = algorithmSeed;
        this.problemSeed = problemSeed;
        this.iteration = iteration;
        this.ensemble = ensemble;
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
                System.out.printf("problem %d, iteration %d complete.", problemInstance, this.iteration);
                return;
            }

        }   //while

    }   //method

    @Override
    public String toString() {
        return null;
    }
}
