package ensembles;

import java.util.Arrays;

class Algorithm {

    private int id;
    private int[] heuristics;

    Algorithm(int id, int[] heuristics) {
        this.id = id;
        this.heuristics = heuristics;
    }

    int getId() {
        return id;
    }

    int[] getHeuristics() {
        return heuristics;
    }

    @Override
    public String toString() {
        return Arrays.toString(heuristics);
    }

}
