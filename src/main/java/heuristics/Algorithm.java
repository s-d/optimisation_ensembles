package heuristics;

import java.util.Arrays;

public class Algorithm {

    private int id;
    private int[] heuristics;

    public Algorithm(int id, int[] heuristics) {
        this.id = id;
        this.heuristics = heuristics;
    }

    int getId() {
        return id;
    }

    public int[] getHeuristics() {
        return heuristics;
    }

    @Override
    public String toString() {
        return Arrays.toString(heuristics);
    }

}
