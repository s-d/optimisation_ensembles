package solver;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;
import FlowShop.FlowShop;
import PersonnelScheduling.PersonnelScheduling;
import SAT.SAT;
import factories.AlgorithmFactory;
import factories.EnsembleFactory;
import heuristics.Algorithm;
import heuristics.Ensemble;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

class Main {

    private static int iterations;
    private static int ensembleNumber;
    private static int problemInstance;
    private static String expType;
    private static FileWriter fw;
    private static Ensemble ensemble;
    private static ProblemDomain problem;
    private static String problemType;
    private static int iteratorStart;
    private static int iteratorEnd;
    private static Map expParams;


    /* writes data to output file */
    public static void main(String args[]) throws IOException {
        expType = "";
        problemType = "";
        ensemble = null;
        iterations = 50;
        ensembleNumber = 0;
        problemInstance = 0;
        iteratorStart = -1;
        iteratorEnd = -1;

        expParams = ArgParser.parseArgs(args);
        setParams();

        if (expParams.get("experiment").equals(ArgParser.Experiments.ALG)) {
            collectAlgorithmData();
        } else {
            collectExperimentData(ensemble);
        }

        /* close file writer */
        fw.close();
    }

    private static void setParams() throws IOException {
        String headerToken;
        ensembleNumber = (int) expParams.get("index");
        iterations = (int) expParams.get("iterations");
        ArgParser.Problems prob = (ArgParser.Problems) expParams.get("problem");
        ArgParser.Experiments exp = (ArgParser.Experiments) expParams.get("experiment");

        switch (prob) {
            case BIN:
                problemType = prob.name().toLowerCase();
                problem = new BinPacking(0);
                break;
            case FLO:
                problemType = prob.name().toLowerCase();
                problem = new FlowShop(0);
                break;
            case SAT:
                problemType = prob.name().toLowerCase();
                problem = new SAT(0);
                break;
        }

        AlgorithmFactory.setProblemHeuristics(problem);

        if (!exp.equals(ArgParser.Experiments.ALG)) {
            headerToken = "algorithms";

            for (int i = 0; i < ensembleNumber + 1; i++) {
                switch (exp) {
                    case ELT:
                        ensemble = EnsembleFactory.generateEliteEnsemble(expParams
                                .get("problem").toString().toLowerCase());
                        expType = exp.name().toLowerCase();
                        break;
                    case RAN:
                        ensemble = EnsembleFactory.generateRandomEnsemble();
                        expType = exp.name().toLowerCase();
                        break;
                    case ENS:
                        ensemble = EnsembleFactory.generateEnsemble();
                        expType = exp.name().toLowerCase();
                        break;
                    default:
                        System.out.println("Unexpected error in ensemble generation.");
                        System.exit(1);
                }
            }

        } else {
            headerToken = "heuristics";
            expType = exp.name().toLowerCase();
        }


        createDataLocation();

        String fullName = expParams.get("experiment").toString().toLowerCase();
        System.out.printf("Starting %s data collection.\r\n", fullName);
        System.out.println(problem.toString());

        /* write data headers to output file */
        String header = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                "iteration", "problem instance", "problem seed", "algorithm seed",
                "starting fitness", "ensemble number", "fitness", "number of runs", headerToken);

        writeData(header);

    }

    /* data collection for ensembles */
    private static void collectExperimentData(Ensemble ensemble) {
        int problemSeed;
        int algorithmSeed;
        long timeLimit;
        HyperHeuristic hh;
        problemInstance = 0;

        /* for every problem instance */
        for (int i = 0; i < problem.getNumberOfInstances(); i++) {
            problemSeed = 1000;
            algorithmSeed = 1000;
            timeLimit = 1000 * 60 * 15;

            /* for number of iterations (50) */
            for (int j = 0; j < iterations; j++) {
                /* initialize new problem and solver and run */
                switch (problemType) {
                    case "bin":
                        problem = new BinPacking(problemSeed);
                        break;
                    case "sat":
                        problem = new SAT(problemSeed);
                        break;
                    case "flo":
                        problem = new FlowShop(problemSeed);
                        break;
                    case "per":
                        problem = new PersonnelScheduling(problemSeed);
                        break;
                }
                hh = new Experimenter(ensemble, algorithmSeed, problemSeed, problemInstance, j, expType);

                problem.loadInstance(problemInstance);

                hh.setTimeLimit(timeLimit);
                hh.loadProblemDomain(problem);
                hh.run();

                /* increment seeds */
                problemSeed++;
                algorithmSeed++;

                if (expParams.get("experiment").equals(ArgParser.Experiments.RAN)) {
                    ensemble = EnsembleFactory.generateRandomEnsemble(1);
                }
            }
            problemInstance++;
        }
    }

    /* algorithm data collection */
    private static void collectAlgorithmData() {
        ArrayList<Algorithm> algorithms = AlgorithmFactory.getAlgorithms();
        int iteratorBegin = (iteratorStart > -1) ? iteratorStart : 0;
        int iteratorFinish = (iteratorEnd > -1) ? iteratorEnd : algorithms.size();
        /* generate and test ensembles of single algorithms */
        for (int i = iteratorBegin; i < iteratorFinish; i++) {
            ensemble = new Ensemble(i);
            ensemble.appendAlgorithm(algorithms.get(i));

            collectExperimentData(ensemble);
        }
    }

    private static void createDataLocation() throws IOException {
        boolean result = false;
        String testType = expParams.get("problem").toString().toLowerCase();
        String parentDir = "data";
        String ensDirName = parentDir + "/ensembles";
        String algDirName = parentDir + "/algorithms";
        String eliDirName = parentDir + "/eliteEnsembles";
        String ranDirName = parentDir + "/randomEnsembles";
        File dataDir = null;

        switch ((ArgParser.Experiments) expParams.get("experiment")) {
            case ENS:
                testType += "Ensemble";
                dataDir = new File(ensDirName);
                break;
            case ELT:
                testType += "EliteEnsemble";
                dataDir = new File(eliDirName);
                break;
            case RAN:
                testType += "RandomEnsemble";
                dataDir = new File(ranDirName);
                break;
            case ALG:
                testType += "Algorithm";
                dataDir = new File(algDirName);
                break;
        }
        if (!dataDir.exists()) {
            result = dataDir.mkdirs();
        }
        if (result) {
            System.out.printf("%s data directory created.\n", dataDir.getName());
        }
        int fileNum = (!expParams.get("experiment").equals(ArgParser.Experiments.ALG)
                ? ensemble.getID() : iterations);
        String FILE_PATH = String.format("%s/%s%ddata%d.csv",
                dataDir.getPath(), testType, fileNum, System.nanoTime());
        fw = new FileWriter(FILE_PATH, true);
    }

    static synchronized void writeData(String string) throws IOException {
        fw.write(string);
        fw.flush();
    }

}