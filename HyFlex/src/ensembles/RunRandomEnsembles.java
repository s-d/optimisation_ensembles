package ensembles;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;

import java.io.FileWriter;
import java.io.IOException;

class RunRandomEnsembles {

    private static final String FILE_PATH = "Data/EnsembleData.csv";
    private static FileWriter fw;

    static synchronized void WriteData(String string) throws IOException {
        fw.write(string);
        fw.flush();
    }

    public static void main(String args[]) throws IOException {
        long timeLimit;
        int problemSeed;
        int algorithmSeed;
        int iterations = 50;
        int problemInstance;
        Ensemble ensemble;
        HyperHeuristic hh;
        fw = new FileWriter(FILE_PATH, true);
        ProblemDomain problem = new BinPacking(0);
        String header = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                "iteration", "problem instance", "problem seed", "algorithm seed",
                "starting fitness", "ensemble number", "fitness", "number of runs", "heuristics");

        WriteData(header);

        for (int i = 0; i < 69; i++) {
            ensemble = Ensemble.generateEnsemble();
            problemInstance = 0;

            for (int j = 0; j < problem.getNumberOfInstances(); j++) {
                problemSeed = 1000;
                algorithmSeed = 1000;
                timeLimit = 1000*60*10;

                for (int k = 0; k < iterations; k++) {
                    problem = new BinPacking(problemSeed);
                    hh = new EnsembleHyperHeuristic(ensemble, algorithmSeed, problemSeed, problemInstance, k);

                    problem.loadInstance(problemInstance);

                    hh.setTimeLimit(timeLimit);
                    hh.loadProblemDomain(problem);
                    hh.run();

                    problemSeed++;
                    algorithmSeed++;
                }
                problemInstance++;
            }

        }

        fw.close();
    }

}