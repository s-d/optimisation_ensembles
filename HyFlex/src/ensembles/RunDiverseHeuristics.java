package ensembles;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;
import algorthims.RunAlgorithmData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class RunDiverseHeuristics {
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
        String fileName = "e";
        String ensDirName = "Data/Ensembles";
        String eliteDirName = "Data/EliteEnsembles";
        String header = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                "iteration", "problem instance", "problem seed", "algorithm seed",
                "starting fitness", "ensemble number", "fitness", "number of runs", "heuristics");

        if (args.length != 0) {
            for (String arg : args) {
                switch (arg) {
                    case "-a":
                    case "--algorithm":
                        System.out.println("Algorithm Mode: Engaged.");
                        RunAlgorithmData.main(null);
                        System.exit(1);

                    case "-h":
                    case "--help":
                        usage(false);
                        System.exit(0);

                    case "-e":
                    case "--elite":
                        if (args.length < 2) {
                            usage(true);
                            System.exit(1);
                        }
                        eliteFlag = true;
                        fileName = "eliteE";
                        System.out.println("Elite Mode: Engaged.");
                        break;

                    default:
                        try {
                            ensembleNumber = Integer.parseInt(arg);
                        } catch (NumberFormatException e) {
                            usage(true);
                            System.exit(0);
                        }
                        break;
                }
            }
        } else {
            usage(true);
            System.exit(0);
        }

        for (int i = 0; i < ensembleNumber + 1; i++) {
            if (eliteFlag) {
                ensemble = EnsembleFactory.generateEliteEnsemble();
            } else {
                ensemble = EnsembleFactory.generateEnsemble();
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
        String FILE_PATH = String.format("%s/%snsemble%dData%d.csv", saveDir, fileName, ensemble.getID(), System.nanoTime());
        fw = new FileWriter(FILE_PATH, true);

        WriteData(header);

        for (int j = 0; j < problem.getNumberOfInstances(); j++) {
            problemSeed = 1000;
            algorithmSeed = 1000;
            timeLimit = 1000 * 60 * 10;

            for (int k = 0; k < iterations; k++) {
                problem = new BinPacking(problemSeed);
                hh = new EnsembleExecutor(ensemble, algorithmSeed, problemSeed, problemInstance, k, eliteFlag);

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

    private static void usage(boolean illegal) {
        if (illegal) {
            System.out.println("Illegal usage.");
        }
        System.out.println("Usage:\r\n" +
                "\tEnsemble ensemble [-e|--elite]\r\n" +
                "\tensemble: The integer ID of the ensemble to be run.\r\n" +
                "\t-e|--elite: Run elite version of the ensemble.");
    }

}