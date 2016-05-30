package ensembles;

import java.util.*;

/**
 * Created by 40056761 on 30/05/2016.
 */
public class RunRandomEnsembles {

    private static final int NUMBER_OF_HEURISTICS = 7;
    private static ArrayList<int[]> algorithms;
    private static ArrayList<int[]> ensembles;
    private static int ensembleIndex = 1;
    private static Random rnd;

    private static void generateAlgorithms() {
        algorithms = new ArrayList<>();

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

    private static ArrayList<int[]> randomAlg() {
        ArrayList<int[]> randomAlg = (ArrayList<int[]>) algorithms.clone();

        Collections.shuffle(randomAlg, rnd);
        return randomAlg;
    }


    private static int[] generateEnsemble() {
        int x = 0;
        int[] ensemble = new int[(ensembleIndex * 5) * 3];

        ArrayList<int[]> algs = randomAlg();
        for (int i = 0; i < ensembleIndex * 5; i++) {
            int[] alg = algs.get(i);
            for (int a : alg
                    ) {
                ensemble[x] = a;
                x++;
            }

        }

        ensembleIndex++;
        return ensemble;
    }

    public static void main(String args[]) {

        for(int i = 0 ; i < 10 ; i++){
            System.out.println(Arrays.toString(Algorithms.getRandomAlgorithms().get(0)));
        }
    }
}