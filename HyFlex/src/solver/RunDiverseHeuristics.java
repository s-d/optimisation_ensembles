package solver;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;
import factories.AlgorithmFactory;
import factories.EnsembleFactory;
import heuristics.Algorithm;
import heuristics.Ensemble;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class RunDiverseHeuristics {

    private static String type;
    private static String flag;
    private static FileWriter fw;
    private static long timeLimit;
    private static int iterations;
    private static int problemSeed;
    private static int algorithmSeed;
    private static HyperHeuristic hh;
    private static Ensemble ensemble;
    private static int ensembleNumber;
    private static int problemInstance;
    private static ProblemDomain problem;

    static synchronized void writeData(String string) throws IOException {
        fw.write(string);
        fw.flush();
    }

    public static void main(String args[]) throws IOException {
        String headerToken = "heuristics";
        ensemble = null;
        type = "";
        flag = "";
        ensembleNumber = 0;
        iterations = 50;
        problemInstance = 0;
        problem = new BinPacking(0);

        parseArguments(args);

        if (!type.equals("-a")) {
            headerToken = "algorithms";
            for (int i = 0; i < ensembleNumber + 1; i++) {
                if (flag.equals("--elite")) {
                    ensemble = EnsembleFactory.generateEliteEnsemble();
                } else if (flag.equals("--random")) {
                    ensemble = EnsembleFactory.generateRandomEnsemble();
                } else {
                    ensemble = EnsembleFactory.generateEnsemble();
                }
            }
        }
        createDataLocation();

        String header = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                "iteration", "problem instance", "problem seed", "algorithm seed",
                "starting fitness", "ensemble number", "fitness", "number of runs", headerToken);

        writeData(header);


        if (type.equals("-a")) {
            collectAlgorithmData();
        } else {
            collectEnsembleData(ensemble);
        }
        fw.close();
    }

    private static void collectEnsembleData(Ensemble ensemble) {
        problemInstance = 0;
        for (int i = 0; i < problem.getNumberOfInstances(); i++) {
            problemSeed = 1000;
            algorithmSeed = 1000;
            timeLimit = 1000 * 60 * 10;

            for (int j = 0; j < iterations; j++) {
                problem = new BinPacking(problemSeed);
                hh = new ExecuteHyperHeuristic(ensemble, algorithmSeed, problemSeed, problemInstance, j, type, flag);

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

    private static void collectAlgorithmData() {
        ArrayList<Algorithm> algorithms = AlgorithmFactory.getAlgorithms();
        for (int i = 0; i < algorithms.size(); i++) {
            ensemble = new Ensemble(i);
            ensemble.appendAlgorithm(algorithms.get(i));

            collectEnsembleData(ensemble);
        }
    }

    private static void createDataLocation() throws IOException {
        File dataDir = null;
        boolean result = false;
        String fileName = "ensemble";
        String ensDirName = "Data/Ensembles";
        String algDirName = "Data/Algorithms";
        String eliDirName = "Data/EliteEnsembles";
        String ranDirName = "Data/RandomEnsembles";

        switch (type) {
            case "-e":
                switch (flag) {
                    case "--elite":
                        fileName = "eliteEnsemble";
                        dataDir = new File(eliDirName);
                        break;
                    case "--random":
                        fileName = "randomEnsemble";
                        dataDir = new File(ranDirName);
                        break;
                    default:
                        dataDir = new File(ensDirName);
                        break;
                }
                break;
            case "-a":
                fileName = "algorithm";
                dataDir = new File(algDirName);
                break;
        }

        assert dataDir != null;
        if (!dataDir.exists()) {
            result = dataDir.mkdirs();
        }
        if (result) {
            System.out.printf("%s data directory created.\n", dataDir.getName());
        }
        int fileNum = (!type.equals("-a") ? ensemble.getID() : iterations);
        String FILE_PATH = String.format("%s/%s%dData%d.csv",
                dataDir.getPath(), fileName, fileNum, System.nanoTime());
        fw = new FileWriter(FILE_PATH, true);
    }

    private static void parseArguments(String[] args) throws IOException {
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                switch (arg) {
                    case "-h":
                    case "--help":
                        printUsage(false);
                        System.exit(0);

                    case "-a":
                        type = arg;
                        if (args.length < 2) {
                            printUsage(true);
                            System.exit(1);
                        } else {
                            try {
                                iterations = Integer.parseInt(args[i + 1]);
                            } catch (NumberFormatException e) {
                                printUsage(false);
                                System.exit(1);
                            }
                            if (iterations < 0) {
                                System.out.println("iterations must be positive.");
                                printUsage(false);
                                System.exit(1);
                            }
                        }
                        break;

                    case "-e":
                        type = arg;
                        if (args.length < 2) {
                            printUsage(true);
                            System.exit(1);
                        } else {
                            try {
                                ensembleNumber = Integer.parseInt(args[i + 1]);
                            } catch (NumberFormatException e) {
                                printUsage(false);
                                System.exit(1);
                            }
                            if (ensembleNumber < 0) {
                                System.out.println("ensembleNo must be positive.");
                                printUsage(false);
                                System.exit(1);
                            }
                        }
                        break;

                    case "--elite":
                        flag = arg;
                        break;
                    case "--random":
                        flag = arg;
                        break;
                }
            }
        } else {
            printUsage(true);
            System.exit(1);
        }
        if (type.equals("")) {
            printUsage(true);
        }
    }

    private static void printUsage(boolean number) {
        if (number) {
            System.out.println("Unexpected number of arguments.");
        }
        System.out.println("Usage:\r\n" +
                "\tDiverseHeuristics  -e ensembleNo [--elite|--random] | -a iterations\r\n" +
                "\t-e: Test an ensemble\r\n" +
                "\tensembleNo: The integer ID of the ensemble to be tested.\r\n" +
                "\t--elite: Test elite version of the ensemble.\r\n" +
                "\t--random: Test a randomly generated ensemble.\r\n"+
                "\t-a: Test individual algorithms.\r\n" +
                "\titerations: The number of times to test each algorithm.");
    }

}