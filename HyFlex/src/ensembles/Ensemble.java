package ensembles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class Ensemble {

    private static int index = 1;
    private int id;
    private ArrayList<Algorithm> algorithms;

    private Ensemble(int id) {
        this.id = id;
        this.algorithms = new ArrayList<>();
    }

    static Ensemble generateEnsemble() {
        ArrayList<Algorithm> randomAlgorithms = HeuristicFactory.getRandomAlgorithms();
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
        ArrayList<Algorithm> eliteAlgorithms = HeuristicFactory.getEliteAlgorithms();
        Ensemble ens = new Ensemble(index - 1);

        for (int i = 0; i < index * 5; i++) {
            if (i < eliteAlgorithms.size()) {
                ens.appendAlgorithm(eliteAlgorithms.get(i));
            }
        }
        index++;
        return ens;
    }

    private void appendAlgorithm(Algorithm alg) {
        this.algorithms.add(alg);
    }

    @Override
    public String toString() {
        int out[] = new int[this.algorithms.size()];
        int index = 0;
        for (Algorithm alg : algorithms) {
            out[index] = alg.getId();
            index++;
        }
        return Arrays.toString(out);
    }

    int getID() {
        return id;
    }

    ArrayList<Algorithm> getAlgorithms() {
        return algorithms;
    }


}
