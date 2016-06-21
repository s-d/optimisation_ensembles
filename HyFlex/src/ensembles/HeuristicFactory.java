package ensembles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

class HeuristicFactory {

    private static final int NUMBER_OF_HEURISTICS = 7;
    private static ArrayList<Algorithm> algorithms;
    private static ArrayList<Algorithm> eliteAlgorithms;
    private static Random rnd;

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

    static ArrayList<Algorithm> getAlgorithms() {
        if (algorithms == null) {
            generateAlgorithms();
        }
        return algorithms;
    }

    static ArrayList<Algorithm> getRandomAlgorithms() {
        if (algorithms == null) {
            generateAlgorithms();
        }
        ArrayList<Algorithm> randomAlgorithms = (ArrayList<Algorithm>) algorithms.clone();
        Collections.shuffle(randomAlgorithms, rnd);

        return randomAlgorithms;
    }

    static ArrayList<Algorithm> getEliteAlgorithms() throws FileNotFoundException {
        if (algorithms == null) {
            generateAlgorithms();
        }
        if (eliteAlgorithms == null) {
            eliteAlgorithms=new ArrayList<>();
            Scanner scanner = new Scanner(new File("res/algorithmOrder.csv"));
            scanner.useDelimiter("\r\n");

            int[] algOrder = new int[343];
            int counter = 0;

            while (scanner.hasNext()) {
                algOrder[counter] = Integer.parseInt(scanner.next());
                counter++;
            }
            scanner.close();

            for (int i : algOrder) {
                eliteAlgorithms.add(algorithms.get(i));
            }
        }

        return eliteAlgorithms;
    }


}
