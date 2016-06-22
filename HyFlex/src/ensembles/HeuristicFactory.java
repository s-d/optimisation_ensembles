package ensembles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


class HeuristicFactory {

    private static final int NUMBER_OF_HEURISTICS = 7;
    private static ArrayList<Algorithm> algorithms;
    private static ArrayList<Algorithm> eliteAlgorithms;
    private static Random rnd;

    HeuristicFactory() {

    }

    private static void generateAlgorithms() {
        algorithms = new ArrayList<>();
        rnd = new Random();
        int index = 0;
        int heuristics[];

        for (int i = 0; i < NUMBER_OF_HEURISTICS; i++) {
            for (int j = 0; j < NUMBER_OF_HEURISTICS; j++) {
                for (int k = 0; k < NUMBER_OF_HEURISTICS; k++) {
                    heuristics = new int[3];
                    heuristics[0] = i;
                    heuristics[1] = j;
                    heuristics[2] = k;

                    Algorithm algorithm = new Algorithm(index, heuristics);
                    algorithms.add(algorithm);
                    index++;
                }
            }
        }
    }

    static ArrayList<Algorithm> getRandomAlgorithms() {
        if (algorithms == null) {
            generateAlgorithms();
        }
        ArrayList<Algorithm> randomAlgorithms = (ArrayList<Algorithm>) algorithms.clone();
        Collections.shuffle(randomAlgorithms, rnd);

        return randomAlgorithms;
    }

    ArrayList<Algorithm> getEliteAlgorithms() throws IOException {
        if (algorithms == null) {
            generateAlgorithms();
        }
        if (eliteAlgorithms == null) {
            eliteAlgorithms = new ArrayList<>();

            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("algorithmOrder.csv");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            int[] algOrder = new int[343];
            int counter = 0;
            String line;

            while ((line = br.readLine()) != null) {
                algOrder[counter] = Integer.parseInt(line);
                counter++;
            }

            for (int algIndex: algOrder) {
                eliteAlgorithms.add(algorithms.get(algIndex));
            }
        }

        return eliteAlgorithms;
    }


}