package threadedAlgorithm;

import java.io.FileWriter;


/**
 * Created by 40056761 on 30/05/2016.
 */
public class RunDataCollection {
    private static final String FILE_PATH = "data/threadedData.csv";
    private static FileWriter fw;

    public static void main(String args[]) throws Exception {
        fw = new FileWriter(FILE_PATH, true);
        int iterations = 2;

        AlgorithmData.setNumberOfHeuristics(7);
        AlgorithmData.generateAlgorithms();

        WriteData("iteration,problem instance,problem seed,algorithm seed,starting fitness,algorithm number,fitness,number of iterations,heuristics\n");


        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(new ProblemThread(iterations,i));
            thread.start();
        }

        fw.close();
    }

    static synchronized void WriteData(String string) throws Exception {
        fw.write(string);
        fw.flush();
    }


}
