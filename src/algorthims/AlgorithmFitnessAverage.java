package algorthims;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 40056761 on 20/05/2016.
 */
public class AlgorithmFitnessAverage extends HyperHeuristic {

    private int numberOfHeuristics;
    private ArrayList<int[]> algorithms;
    private String filePath;
    private long algorithmSeed, problemSeed, timeLimit;
    private int problemInstance, itteration;

    public AlgorithmFitnessAverage(long algorithmSeed, long problemSeed, int problemInstance, long timeLimit, int iteration, String filePath) {
        super(algorithmSeed);
        this.algorithmSeed = algorithmSeed;
        this.problemSeed = problemSeed;
        this.problemInstance = problemInstance;
        this.timeLimit = timeLimit;
        this.filePath = filePath;
        this.itteration = iteration;

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
        double delta;
        double newFitness;

        double currentFitness = Double.POSITIVE_INFINITY;
        this.numberOfHeuristics = problemDomain.getNumberOfHeuristics() - 1;

        generateAlgorithms();

        int[] currentAlgorithm = algorithms.get(algorithmIndex);

        problemDomain.setMemorySize(3);
        problemDomain.initialiseSolution(0);
        problemDomain.copySolution(0, 1);

        double startingFitness = problemDomain.getBestSolutionValue();

        while (!hasTimeExpired()) {

            for (int i = 0; i < currentAlgorithm.length; i++) {

                newFitness = problemDomain.applyHeuristic(currentAlgorithm[i], 1, 2);

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

                sb.append(itteration + "," + problemInstance + "," + problemSeed + "," + algorithmSeed + "," + startingFitness + "," + algorithmIndex + "," + currentFitness + "," + iterations + "," + Arrays.toString(currentAlgorithm) + "\n");

                if (algorithmIndex < algorithms.size() - 1) {
                    algorithmIndex++;

                    currentAlgorithm = algorithms.get(algorithmIndex);
                    iterations = 0;
                    noImprovementCounter = 0;
                    problemDomain.copySolution(0, 1);
                    currentFitness = Double.POSITIVE_INFINITY;
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
