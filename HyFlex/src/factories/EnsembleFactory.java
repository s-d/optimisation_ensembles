package factories;

import heuristics.Algorithm;
import heuristics.Ensemble;

import java.io.IOException;
import java.util.ArrayList;

public class EnsembleFactory {

    private static int index = 1;

    public static Ensemble generateEnsemble() {
        ArrayList<Algorithm> orderedAlgorithms = AlgorithmFactory.getAlgorithms();
        return generate(orderedAlgorithms);
    }

    public static Ensemble generateRandomEnsemble() {
        ArrayList<Algorithm> randomAlgorithms = AlgorithmFactory.getRandomAlgorithms();
        return generate(randomAlgorithms);
    }

    public static Ensemble generateEliteEnsemble() throws IOException {
        ArrayList<Algorithm> eliteAlgorithms = AlgorithmFactory.getEliteAlgorithms();

        return generate(eliteAlgorithms);
    }

    private static Ensemble generate(ArrayList<Algorithm> algorithms) {
        Ensemble ensemble = new Ensemble(index - 1);
        for (int i = 0; i < index * 5; i++) {
            if (i < algorithms.size()) {
                ensemble.appendAlgorithm(algorithms.get(i));
            }
        }
        index++;
        return ensemble;
    }

}