package algorthims;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */

class AlgorithmFitnessAverage extends HyperHeuristic {

    private ArrayList<int[]> algorithms;
    private String filePath;
    private int numberOfHeuristics;
    private int problemInstance;
    private int iteration;
    private long algorithmSeed;
    private long problemSeed;

    /**
     * sole constructor
     *
     * @param algorithmSeed   seed used to generate algorithm
     * @param problemSeed     seed used to generate problem
     * @param problemInstance the instance of the problem being solved
     * @param iteration       the current iteration
     * @param filePath        path to output file
     */
    AlgorithmFitnessAverage(long algorithmSeed, long problemSeed, int problemInstance,
                            int iteration, String filePath) {
        super(algorithmSeed);
        this.algorithmSeed = algorithmSeed;
        this.problemSeed = problemSeed;
        this.problemInstance = problemInstance;
        this.filePath = filePath;
        this.iteration = iteration;
    }

    /**
     * fills  an array with all the possible combinations of algorithms
     */
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

    /**
     * writes a string to the output file
     *
     * @param data the string to be written
     * @throws IOException if file cannot be found/opened
     */
    private void writeToFile(String data) throws IOException {
        System.out.println("writing to file");
        FileWriter fw = new FileWriter(this.filePath, true);
        fw.append(data);
        fw.flush();
        fw.close();
    }

    /**
     * attempts to solve a problem using every possible algorithm
     *
     * @param problemDomain the problem to be solved
     */
    @Override
    protected void solve(ProblemDomain problemDomain) {
        StringBuilder sb = new StringBuilder();
        int noImprovementCounter = 0;
        int algorithmIndex = 0;
        int iterations = 0;
        int[] currentAlgorithm;
        double startingFitness;
        double currentFitness;
        double bestFitness;
        double newFitness;
        double delta;

        /* get number of available heuristics (minus last one which we don't want) */
        this.numberOfHeuristics = problemDomain.getNumberOfHeuristics() - 1;
        generateAlgorithms();
        currentAlgorithm = algorithms.get(algorithmIndex);

        /* initialise the problem to be solved */
        problemDomain.setMemorySize(3);
        problemDomain.initialiseSolution(0);
        problemDomain.copySolution(0, 1);

        startingFitness = problemDomain.getBestSolutionValue();
        currentFitness = startingFitness;
        bestFitness = Double.POSITIVE_INFINITY;

        while (!hasTimeExpired()) {

            for (int heuristic : currentAlgorithm) {
                iterations++;

                /* apply current heuristic to the problem */
                newFitness = problemDomain.applyHeuristic(heuristic, 1, 2);
                /* if new fitness is better than previous best, update best */
                bestFitness = (newFitness < bestFitness) ? newFitness : bestFitness;

                /* determine in new fitness is better than current solution */
                delta = currentFitness - newFitness;
                if (delta > 0) {    //better
                    problemDomain.copySolution(2, 1);
                    currentFitness = newFitness;
                    noImprovementCounter = 0;
                } else {            //not better
                    noImprovementCounter++;
                }
            }

            if (noImprovementCounter < 3) { //if at least one heuristic improved the fitness
                noImprovementCounter = 0;
            } else {                        //if all three heuristics failed to improve fitness
                /* append algorithm data to the string builder */
                sb.append(iteration).append(",").append(problemInstance).append(",").append(problemSeed)
                        .append(",").append(algorithmSeed).append(",").append(startingFitness).append(",")
                        .append(algorithmIndex).append(",").append(bestFitness).append(",").append(iterations/3)
                        .append(",\"").append(Arrays.toString(currentAlgorithm)).append("\"\n");

                /* determine if there are any more algorithms to test */
                if (algorithmIndex < algorithms.size() - 1) {   //if not final
                    /* increment algorithm index and reset parameters */
                    algorithmIndex++;
                    currentAlgorithm = algorithms.get(algorithmIndex);
                    iterations = 0;
                    noImprovementCounter = 0;
                    problemDomain.copySolution(0, 1);
                    currentFitness = startingFitness;
                    bestFitness = Double.POSITIVE_INFINITY;
                } else {    //if final algorithm
                    /* write all algorithm data to the output file */
                    try {
                        this.writeToFile(sb.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /* finish */
                    System.out.println("instance " + problemInstance + " complete");
                    return;
                }
            }
        }

    }

    /**
     * standard toString method
     *
     * @return name of class
     */
    @Override
    public String toString() {
        return "AlgorithmFitness";
    }

}