/*
 * RunAlgorithmFitnessAverage
 * Sam Dixon
 */

package algorthims;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;

import java.io.FileWriter;
import java.io.IOException;

public class RunAlgorithmFitnessAverage {

    public static void main(String[] args) throws IOException {

        int iterations = 1;        //number of times to run each problem instance
        int problemInstance = 0;    //starting problem instance
        String filePath = "data/newAlgorithmData" + iterations + ".csv";

        //file writer that creates new file and adds data headers
        FileWriter fw = new FileWriter(filePath, true);
        fw.write("iteration,problem instance,problem seed,algorithm seed,starting fitness,algorithm " +
                "number,fitness,number of iterations,heuristics, , \n");
        fw.flush();
        fw.close();

        //initialise the problem inorder to get the number of instances (12)
        ProblemDomain problem = new BinPacking(0);

        //outer loop, each iteration is a new problem
        for (int i = 0; i < /*problem.getNumberOfInstances()*/1; i++) {
            int problemSeed = 1000;
            int algorithmSeed = 1000;
            long timeLimit = 100000;

            //inner loop, runs a problem "iterations" times
            for (int j = 0; j < iterations; j++) {
                problem = new BinPacking(problemSeed);
                HyperHeuristic algorithm = new AlgorithmFitnessAverage(algorithmSeed, problemSeed,
                        problemInstance, j, filePath);

                problem.loadInstance(problemInstance);
                algorithm.setTimeLimit(timeLimit);
                algorithm.loadProblemDomain(problem);
                algorithm.run();

                problemSeed++;
                algorithmSeed++;
                System.out.println(j);
            }

            System.out.println("Problem instance " + problemInstance + " complete");
            problemInstance++;
        }
    }

}