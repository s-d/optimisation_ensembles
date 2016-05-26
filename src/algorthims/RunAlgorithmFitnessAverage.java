package algorthims;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 40056761 on 20/05/2016.
 */
public class RunAlgorithmFitnessAverage {

    public static void main(String[] args) throws IOException {


        int iterations = 50;
        int problemInstance = 0;

        String filePath = "data/newAlgorithmData" + iterations + ".csv";


        FileWriter fw = new FileWriter(filePath, true);
        fw.write("iteration,problem instance,problem seed,algorithm seed,starting fitness,algorithm number,fitness,number of iterations,heuristics, , \n");
        fw.flush();
        fw.close();

        ProblemDomain problem = new BinPacking(0);

        for (int i = 0; i < problem.getNumberOfInstances(); i++) {
            int problemSeed = 1000;
            int algorithmSeed = 1000;
            long timeLimit = 100000;

            for (int j = 0; j < iterations; j++) {
                problem = new BinPacking(problemSeed);
                HyperHeuristic algorithm = new AlgorithmFitnessAverage(algorithmSeed, problemSeed, problemInstance, timeLimit, j, filePath);
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
