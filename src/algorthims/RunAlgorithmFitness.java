package algorthims;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by 40056761 on 20/05/2016.
 */
public class RunAlgorithmFitness {

    public static void main(String[] args) throws FileNotFoundException {
        String filePath = "data/test.csv";
        int problemSeed = 1234;
        int algorithmSeed = 1234;
        int problemInstance = 1;
        long timeLimit = 10000;

        PrintWriter pw = new PrintWriter(new File(filePath));
        StringBuilder sb = new StringBuilder();

        sb.append("problem seed" + "," + problemSeed + "\n");
        sb.append("algorithm seed" + "," + algorithmSeed + "\n");
        sb.append("problem instance" + "," + problemInstance + "\n");
        sb.append("time limit" + "," + timeLimit + "\n");

        pw.write(sb.toString());
        pw.flush();
        pw.close();

        ProblemDomain problem = new BinPacking(problemSeed);

        HyperHeuristic algorithm = new AlgorithmFitness(algorithmSeed,filePath);

        problem.loadInstance(problemInstance);

        algorithm.setTimeLimit(timeLimit);

        algorithm.loadProblemDomain(problem);

        algorithm.run();
    }

}
