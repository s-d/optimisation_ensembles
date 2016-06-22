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
        boolean elite = false;
        String eliteFile = "e";
        String ensDir = "Data/Ensembles";
        String eliteDir = "Data/EliteEnsembles";
        Ensemble ensemble = null;
        HyperHeuristic hh;
        ProblemDomain problem = new BinPacking(0);
        String header = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                "iteration", "problem instance", "problem seed", "algorithm seed",
                "starting fitness", "ensemble number", "fitness", "number of runs", "heuristics");

        if (args != null) {
            if (args[0].equals("E") || args[0].equals("e")) {
                elite = true;
                eliteFile = "eliteE";
                System.out.println("Elite Mode: Engaged.");
                try {
                    ensembleNumber = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Argument must be an int.");
                    System.exit(0);
                }
            } else {
                try {
                    ensembleNumber = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("Argument must be an int.");
                    System.exit(0);
                }
            }
        } else {
            System.out.println("Argument must be an int.");
            System.exit(0);
        }

        for (int i = 0; i < ensembleNumber + 1; i++) {
            if (elite) {
                ensemble = Ensemble.generateEliteEnsemble();
            } else {
                ensemble = Ensemble.generateEnsemble();
            }
        }

        File data = new File("Data");
        File ens = new File(ensDir);
        File el = new File(eliteDir);

        if (!data.exists()) {
            data.mkdir();
        }

        if (!ens.exists()) {
            ens.mkdir();
        }
        if (!el.exists()) {
            el.mkdir();
        }
        String saveDir = elite ? eliteDir : ensDir;

        String FILE_PATH = String.format("%s/%snsemble%dData%d.csv", saveDir, eliteFile, ensemble.getId(), System.nanoTime());
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