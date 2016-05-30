package ensembles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by 40056761 on 30/05/2016.
 */
public class Algorithms {
    private static final int NUMBER_OF_HEURISTICS = 7;
    private static ArrayList<int[]> algorithms;
    private static Random rnd;

    private static void generateAlgorithms() {
        algorithms = new ArrayList<>();
        rnd = new Random(1000);

        for (int i = 0; i < NUMBER_OF_HEURISTICS; i++) {
            for (int j = 0; j < NUMBER_OF_HEURISTICS; j++) {
                for (int k = 0; k < NUMBER_OF_HEURISTICS; k++) {
                    int[] algorithm = new int[3];
                    algorithm[0] = i;
                    algorithm[1] = j;
                    algorithm[2] = k;
                    algorithms.add(algorithm);
                }
            }
        }
    }

    public static ArrayList<int[]> getAlgorithms() {
        if (algorithms == null) {
            generateAlgorithms();
        }
        return algorithms;
    }

    public static ArrayList<int[]> getRandomAlgorithms() {
        if (algorithms == null) {
            generateAlgorithms();
        }
        ArrayList<int[]> randomAlg = (ArrayList<int[]>) algorithms.clone();

        Collections.shuffle(randomAlg, rnd);
        return randomAlg;
    }

}
