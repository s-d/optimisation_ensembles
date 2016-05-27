/*
 * AlgorithmFitnessAverage
 * Sam Dixon
 */

package algorthims;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class AlgorithmFitnessAverage extends HyperHeuristic {

    private ArrayList<int[]> algorithms;
    private String filePath;
    private int numberOfHeuristics;
    private int problemInstance;
    private int iteration;
    private long algorithmSeed;
    private long problemSeed;

    AlgorithmFitnessAverage(long algorithmSeed, long problemSeed, int problemInstance,
                            int iteration, String filePath) {
        super(algorithmSeed);
        this.algorithmSeed = algorithmSeed;
        this.problemSeed = problemSeed;
        this.problemInstance = problemInstance;
        this.filePath = filePath;
        this.iteration = iteration;
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
                    this.algorithms.add(algorithm);
                }
            }
        }

    }

    private void writeToFile(String data) throws IOException {
        System.out.println("writing to file");
        FileWriter fw = new FileWriter(this.filePath, true);
        fw.append(data);
        fw.flush();
        fw.close();
    }

    @Override
    protected void solve(ProblemDomain problemDomain) {
        StringBuilder sb = new StringBuilder();
        int noImprovementCounter = 0;
        int algorithmIndex = 0;
        int iterations = 0;
        int[] currentAlgorithm;

        this.numberOfHeuristics = problemDomain.getNumberOfHeuristics() - 1;

        generateAlgorithms();
        currentAlgorithm = algorithms.get(algorithmIndex);

        problemDomain.setMemorySize(3);
        problemDomain.initialiseSolution(0);
        problemDomain.copySolution(0, 1);

        while (!hasTimeExpired()) {
            double startingFitness = problemDomain.getBestSolutionValue();
            double currentFitness = startingFitness;
            double bestFitness = Double.POSITIVE_INFINITY;
            double delta;

            for (int algorithm : currentAlgorithm) {
                double newFitness = problemDomain.applyHeuristic(algorithm, 1, 2);

                if (newFitness < bestFitness) {
                    bestFitness = newFitness;
                }

                delta = currentFitness - newFitness;

                if (delta > 0) {
                    problemDomain.copySolution(2, 1);
                    currentFitness = newFitness;
                    noImprovementCounter = 0;
                } else {
                    noImprovementCounter++;
                }

                iterations++;
            }

            if (noImprovementCounter < 3) {
                noImprovementCounter = 0;
            } else {
                sb.append(iteration).append(",").append(problemInstance).append(",").append(problemSeed)
                        .append(",").append(algorithmSeed).append(",").append(startingFitness).append(",")
                        .append(algorithmIndex).append(",").append(bestFitness).append(",").append(iterations)
                        .append(",").append(Arrays.toString(currentAlgorithm)).append("\n");

                if (algorithmIndex < algorithms.size() - 1) {
                    algorithmIndex++;
                    currentAlgorithm = algorithms.get(algorithmIndex);
                    iterations = 0;
                    noImprovementCounter = 0;
                    problemDomain.copySolution(0, 1);
                } else {
                    try {
                        this.writeToFile(sb.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("instance " + problemInstance + " complete");
                    return;
                }
            }
        }

    }

    @Override
    public String toString() {
        return "AlgorithmFitness";
    }

}