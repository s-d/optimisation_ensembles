package heuristics;

import java.util.ArrayList;
import java.util.Arrays;

public class Ensemble {

    private int id;
    private ArrayList<Algorithm> algorithms;

    public Ensemble(int id) {
        this.id = id;
        this.algorithms = new ArrayList<>();
    }

    public void appendAlgorithm(Algorithm alg) {
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

    public int getID() {
        return id;
    }

    public ArrayList<Algorithm> getAlgorithms() {
        return algorithms;
    }

}