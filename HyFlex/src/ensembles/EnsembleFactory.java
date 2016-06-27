package ensembles;

import java.io.IOException;
import java.util.ArrayList;

class EnsembleFactory {

    private static int index = 1;

    static Ensemble generateEnsemble() {
        ArrayList<Algorithm> randomAlgorithms = AlgorithmFactory.getRandomAlgorithms();
        Ensemble ens = new Ensemble(index - 1);

        for (int i = 0; i < index * 5; i++) {
            if (i < randomAlgorithms.size()) {
                ens.appendAlgorithm(randomAlgorithms.get(i));
            }
        }
        index++;
        return ens;
    }

    static Ensemble generateEliteEnsemble() throws IOException {
        ArrayList<Algorithm> eliteAlgorithms = AlgorithmFactory.getEliteAlgorithms();
        Ensemble ens = new Ensemble(index - 1);

        for (int i = 0; i < index * 5; i++) {
            if (i < eliteAlgorithms.size()) {
                ens.appendAlgorithm(eliteAlgorithms.get(i));
            }
        }
        index++;
        return ens;
    }
}
