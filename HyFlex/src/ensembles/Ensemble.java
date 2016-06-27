package ensembles;

import java.util.ArrayList;
import java.util.Arrays;

class Ensemble {

    private int id;
    private ArrayList<Algorithm> algorithms;

    Ensemble(int id) {
        this.id = id;
        this.algorithms = new ArrayList<>();
    }

    void appendAlgorithm(Algorithm alg) {
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