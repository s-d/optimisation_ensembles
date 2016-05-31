package ensembles;

import java.util.Arrays;

/**
 * Created by 40056761 on 30/05/2016.
 */
public class Algorithm {

    private int id;
    private int[] heuristics;

    public Algorithm(int id, int[] heuristics) {
        this.id = id;
        this.heuristics = heuristics;
    }

    public int getId() {
        return id;
    }

    public int[] getHeuristics() {
        return heuristics;
    }

    @Override
    public String toString(){
        return Arrays.toString(heuristics);
    }

}
