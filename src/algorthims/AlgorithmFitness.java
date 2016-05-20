package algorthims;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 40056761 on 20/05/2016.
 */
public class AlgorithmFitness extends HyperHeuristic {

    private int numberOfHeuristics;
    private ArrayList<int[]> algorithms;

    public AlgorithmFitness(long seed) {
        super(seed);
    }

    private void generateAlgorithms() {
        this.algorithms = new ArrayList<>();

        for (int i = 0; i < numberOfHeuristics; i++) {
            for (int j = 0; j < numberOfHeuristics; j++) {
                for (int k = 0; k < numberOfHeuristics; k++) {

                    int[] algorithm = new int[3];
                    algorithm[0] = i;
                    algorithm[1] = j;
                    algorithm[2] = k;
                    System.out.printf(String.valueOf(algorithm));
                    this.algorithms.add(algorithm);
                }
            }
        }

    }

    @Override
    protected void solve(ProblemDomain problemDomain) {

        this.numberOfHeuristics = problemDomain.getNumberOfHeuristics()-1;
        generateAlgorithms();

        int[] currentAlgorithm = algorithms.get(0);

    }

    @Override
    public String toString() {
        return "AlgorithmFitness";
    }
}
