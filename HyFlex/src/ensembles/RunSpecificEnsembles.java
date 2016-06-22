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
        int problemInstance = 0;
        int ensembleNumber = 0;
        boolean eliteFlag = false;
        String fileType = "e";
        String ensDirName = "Data/Ensembles";
        String eliteDirName = "Data/EliteEnsembles";
        Ensemble ensemble = null;
        HyperHeuristic hh;
        ProblemDomain problem = new BinPacking(0);
        String header = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                "iteration", "problem instance", "problem seed", "algorithm seed",
                "starting fitness", "ensemble number", "fitness", "number of runs", "heuristics");


        if (args != null) {
            int argIndex = 0;
            String arg;

            while (argIndex < args.length) {
                arg = args[argIndex++];

                if (arg.equals("-e")) {
                    eliteFlag = !eliteFlag;
                    System.out.println("Elite Mode: Engaged");
                } else {
                    try {
                        ensembleNumber = Integer.parseInt(arg);
                    } catch (NumberFormatException e) {
                        System.out.println("Illegal operation. Integer required");
                    }
                }
            }
        } else {
            System.out.println("Illegal operation. Integer required");
        }


        for (int i = 0; i < ensembleNumber + 1; i++) {
            if (eliteFlag) {
                ensemble = Ensemble.generateEliteEnsemble();
            } else {
                ensemble = Ensemble.generateEnsemble();
            }
        }

        if (eliteFlag) {
            File eliteDir = new File(eliteDirName);
            if (!eliteDir.exists()) {
                eliteDir.mkdirs();
            }
        } else {
            File ensDir = new File(ensDirName);
            if (!ensDir.exists()) {
                ensDir.mkdirs();
            }
        }

        String saveDir = eliteFlag ? eliteDirName : ensDirName;

        String FILE_PATH = String.format("%s/%snsemble%dData%d.csv", saveDir, fileType, ensemble.getId(), System.nanoTime());
        fw = new FileWriter(FILE_PATH, true);

        WriteData(header);

        for (int j = 0; j < problem.getNumberOfInstances(); j++) {
            problemSeed = 1000;
            algorithmSeed = 1000;
            timeLimit = 1000 * 60 * 10;

            for (int k = 0; k < iterations; k++) {
                problem = new BinPacking(problemSeed);
                hh = new EnsembleHyperHeuristic(ensemble, algorithmSeed, problemSeed,
                        problemInstance, k, eliteFlag);

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