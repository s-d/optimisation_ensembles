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
    private static boolean algFlag;
    private static boolean eliteFlag;
    private static int ensembleNumber;
    private static Ensemble ensemble;

    static synchronized void WriteData(String string) throws IOException {
        fw.write(string);
        fw.flush();
    }

    public static void main(String args[]) throws IOException {
        ensemble = null;
        eliteFlag = false;
        ensembleNumber = 0;
        long timeLimit;
        int problemSeed;
        int algorithmSeed;
        int iterations = 50;
        int problemInstance = 0;
        HyperHeuristic hh;
        ProblemDomain problem = new BinPacking(0);

        //// TODO: 28/06/2016 rewrite header
        String header = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                "iteration", "problem instance", "problem seed", "algorithm seed",
                "starting fitness", "ensemble number", "fitness", "number of runs", "heuristics");

        if (args.length != 0) {
            parseArguments(args);
        } else {
            printUsage();
            System.exit(0);
        }

        for (int i = 0; i < ensembleNumber + 1; i++) {
            if (eliteFlag) {
                ensemble = EnsembleFactory.generateEliteEnsemble();
            } else {
                ensemble = EnsembleFactory.generateEnsemble();
            }
        }

        createDataLocation();

        WriteData(header);

        for (int j = 0; j < problem.getNumberOfInstances(); j++) {
            problemSeed = 1000;
            algorithmSeed = 1000;
            timeLimit = 1000 * 60 * 10;

            for (int k = 0; k < iterations; k++) {
                problem = new BinPacking(problemSeed);
                hh = new Executor(ensemble, algorithmSeed, problemSeed, problemInstance, k, eliteFlag);

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

    private static void createDataLocation() throws IOException {
        String fileName = "ensemble";
        String ensDirName = "Data/Ensembles";
        String eliteDirName = "Data/EliteEnsembles";
        String algDirName = "Data/Algorithms";
        File dataDir;
        boolean result = false;

        if (eliteFlag) {
            fileName = "eliteEnsemble";
            dataDir = new File(eliteDirName);
        } else if (algFlag) {
            fileName = "algorithm";
            dataDir = new File(algDirName);
        } else {
            dataDir = new File(ensDirName);
        }
        if (!dataDir.exists()) {
            result = dataDir.mkdirs();
        }
        if (result) {
            System.out.printf("%s data directory created.\n", dataDir.getName());
        }
        if (!algFlag) {
            String FILE_PATH = String.format("%s/%s%dData%d.csv",
                    dataDir.getPath(), fileName, ensemble.getID(), System.nanoTime());
            fw = new FileWriter(FILE_PATH, true);
        }
    }

    private static void parseArguments(String[] args) throws IOException {
        for (String arg : args) {
            switch (arg) {
                case "-a":
                    //// TODO: 28/06/2016 rewrite algorithm data collection
                    System.out.println("Algorithm Mode: Engaged.");
                    RunAlgorithmData.main(null);
                    System.exit(1);

                case "-h":
                case "--help":
                    printUsage();
                    System.exit(0);


                case "-e":
                case "--elite":
                    if (args.length < 2) {
                        System.out.println("EnsembleNo required");
                        printUsage();
                        System.exit(1);
                    }
                    eliteFlag = true;
                    System.out.println("Elite Mode: Engaged.");
                    break;

                default:
                    try {
                        ensembleNumber = Integer.parseInt(arg);
                        if (ensembleNumber < 0) {
                            System.out.println("ensemble_ID must be positive.");
                            printUsage();
                            System.exit(1);
                        }
                    } catch (NumberFormatException e) {
                        printUsage();
                        System.exit(1);
                    }
                    break;
            }
        }

    }

    private static void printUsage() {
        System.out.println("Usage:\r\n" +
                "\tDiverseHeuristics  ensembleNo [-e|--elite] | -a\r\n" +
                "\tensembleNo: The integer ID of the ensemble to be tested.\r\n" +
                "\t-e|--elite: Test elite version of the ensemble.");
    }

}