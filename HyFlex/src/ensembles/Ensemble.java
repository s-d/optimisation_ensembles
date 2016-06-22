package ensembles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


class Ensemble {

    private static int index = 1;
    private int id;
    private ArrayList<Algorithm> algorithms;

    Ensemble(int id) {
        this.id = id;
        this.algorithms = new ArrayList<>();
    }

    Ensemble(int id, ArrayList<Algorithm> algorithms) {
        this(id);
        this.algorithms = algorithms;
    }

    void appendAlgorithm(Algorithm alg) {
        this.algorithms.add(alg);
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
        HeuristicFactory hf = new HeuristicFactory();
        ArrayList<Algorithm> eliteAlgorithms = hf.getEliteAlgorithms();
        Ensemble ens = new Ensemble(index - 1);

        for (int i = 0; i < index * 5; i++) {
            if (i < eliteAlgorithms.size()) {
                ens.appendAlgorithm(eliteAlgorithms.get(i));
            }
        }
        index++;
        return ens;
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

    int getId() {
        return id;
    }

    ArrayList<Algorithm> getAlgorithms() {
        return algorithms;
    }


}
