package ensembles;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;

import java.io.FileWriter;
import java.io.IOException;


class RunSpecificEnsembles {
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
        int problemInstance = 0;
        int ensembleNumber = 0;
        Ensemble ensemble = null;
        HyperHeuristic hh;
        ProblemDomain problem = new BinPacking(0);
        String header = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                "iteration", "problem instance", "problem seed", "algorithm seed",
                "starting fitness", "ensemble number", "fitness", "number of runs", "heuristics");

        if (args[0] != null) {
            try {
                ensembleNumber = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Argument must be an int.");
                System.exit(0);
            }
        } else {
            System.out.println("Argument must be an int.");
            System.exit(0);
        }

        for (int i = 0; i < ensembleNumber + 1; i++) {
            ensemble = Ensemble.generateEnsemble();
        }

        String FILE_PATH = String.format("Data/ensemble%dData%d.csv", ensemble.getId(), System.nanoTime());
        fw = new FileWriter(FILE_PATH, true);


        WriteData(header);


        for (int j = 0; j < problem.getNumberOfInstances(); j++) {
            problemSeed = 1000;
            algorithmSeed = 1000;
            timeLimit = 1000 * 60 * 10;

            for (int k = 0; k < iterations; k++) {
                problem = new BinPacking(problemSeed);
                hh = new EnsembleHyperHeuristic(ensemble, algorithmSeed, problemSeed,
                        problemInstance, k);

                problem.loadInstance(problemInstance);

                hh.setTimeLimit(timeLimit);
                hh.loadProblemDomain(problem);
                hh.run();

                problemSeed++;
                algorithmSeed++;
            }
            problemInstance++;
        }

        fw.close();
    }

}