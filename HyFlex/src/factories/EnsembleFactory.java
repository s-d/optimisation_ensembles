package factories;

import heuristics.Algorithm;
import heuristics.Ensemble;

import java.io.IOException;
import java.util.ArrayList;

public class EnsembleFactory {

    private static int index = 1;

    public static Ensemble generateEnsemble() {
        ArrayList<Algorithm> orderedAlgorithms = AlgorithmFactory.getAlgorithms();
        Ensemble ensemble = new Ensemble(index - 1);

        for (int i = 0; i < index * 5; i++) {
            if (i < orderedAlgorithms.size()) {
                ensemble.appendAlgorithm(orderedAlgorithms.get(i));
            }
        }
        index++;
        return ensemble;
    }

    public static Ensemble generateRandomEnsemble() {
        ArrayList<Algorithm> randomAlgorithms = AlgorithmFactory.getRandomAlgorithms();
        Ensemble ensemble = new Ensemble(index - 1);

        for (int i = 0; i < index * 5; i++) {
            if (i < randomAlgorithms.size()) {
                ensemble.appendAlgorithm(randomAlgorithms.get(i));
            }
        }
        index++;
        return ensemble;
    }

    public static Ensemble generateEliteEnsemble() throws IOException {
        ArrayList<Algorithm> eliteAlgorithms = AlgorithmFactory.getEliteAlgorithms();
        Ensemble ensemble = new Ensemble(index - 1);

        for (int i = 0; i < index * 5; i++) {
            if (i < eliteAlgorithms.size()) {
                ensemble.appendAlgorithm(eliteAlgorithms.get(i));
            }
        }
        index++;
        return ensemble;
    }
}
