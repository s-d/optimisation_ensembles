package ensembles;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;

import java.io.File;
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
        int ensembleNumber = 0;
        int problemInstance = 0;
        boolean eliteFlag = false;
        HyperHeuristic hh;
        Ensemble ensemble = null;
        ProblemDomain problem = new BinPacking(0);
        String fileType = "e";
        String ensDirName = "Data/Ensembles";
        String eliteDirName = "Data/EliteEnsembles";
        String header = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                "iteration", "problem instance", "problem seed", "algorithm seed",
                "starting fitness", "ensemble number", "fitness", "number of runs", "heuristics");

        if (args != null) {
            int argIndex = 0;
            String arg;

            while (argIndex < args.length) {
                arg = args[argIndex++];

                if (arg.equals("-e") || arg.equals("-E")) {
                    eliteFlag = !eliteFlag;
                    System.out.println("Elite Mode: Engaged");
                } else {
                    try {
                        ensembleNumber = Integer.parseInt(arg);
                    } catch (NumberFormatException e) {
                        System.out.println("Illegal operation. Integer required");
                        System.exit(0);
                    }
                }
            }

        } else {
            System.out.println("Illegal operation. Integer required");
            System.exit(0);
        }

        for (int i = 0; i < ensembleNumber + 1; i++) {
            if (eliteFlag) {
                ensemble = Ensemble.generateEliteEnsemble();
            } else {
                ensemble = Ensemble.generateEnsemble();
            }
        }

        File dataDir;
        boolean result = false;

        if (eliteFlag) {
            dataDir = new File(eliteDirName);
        } else {
            dataDir = new File(ensDirName);
        }
        if (!dataDir.exists()) {
            result = dataDir.mkdirs();
        }
        if (result) {
            System.out.printf("%s data directory created.\n", dataDir.getName());
        }

        String saveDir = eliteFlag ? eliteDirName : ensDirName;

        assert ensemble != null;
        String FILE_PATH = String.format("%s/%snsemble%dData%d.csv", saveDir, fileType, ensemble.getID(), System.nanoTime());
        fw = new FileWriter(FILE_PATH, true);

        WriteData(header);

        for (int j = 0; j < problem.getNumberOfInstances(); j++) {
            problemSeed = 1000;
            algorithmSeed = 1000;
            timeLimit = 1000 * 60 * 10;

            for (int k = 0; k < iterations; k++) {
                problem = new BinPacking(problemSeed);
                hh = new EnsembleHyperHeuristic(ensemble, algorithmSeed, problemSeed, problemInstance, k, eliteFlag);

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