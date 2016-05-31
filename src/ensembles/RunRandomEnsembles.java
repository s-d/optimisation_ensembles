package ensembles;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;

import java.io.FileWriter;
import java.io.IOException;

class RunRandomEnsembles {

    private static final String FILE_PATH = "data/testEnsembleData.csv";
    private static FileWriter fw;

    static synchronized void WriteData(String string) throws IOException {
        fw.write(string);
        fw.flush();
    }

    public static void main(String args[]) throws IOException {
        fw = new FileWriter(FILE_PATH, true);
        Ensemble ens = Ensemble.generateEnsemble();

        String header = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                "iteration", "problem instance", "problem seed", "algorithm seed", "starting fitness", "ensemble number", "fitness", "number of runs", "heuristics");
        WriteData(header);

        ProblemDomain problem = new BinPacking(0);
        HyperHeuristic hh = new EnsembleHyperHeuristic(ens, 1000, 1000, 0, 0);
        problem.loadInstance(0);
        hh.setTimeLimit(60000);
        hh.loadProblemDomain(problem);
        hh.run();


        fw.close();
    }

}