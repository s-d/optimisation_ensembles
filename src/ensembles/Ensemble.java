package ensembles;

import java.util.ArrayList;


/**
 * Created by 40056761 on 31/05/2016.
 */
public class Ensemble {

    private static int index = 1;
    private int id;
    private ArrayList<Algorithm> algorithms;

    public Ensemble(int id) {
        this.id = id;
        this.algorithms = new ArrayList<>();
    }

    public Ensemble(int id, ArrayList<Algorithm> algorithms) {
        this(id);
        this.algorithms = algorithms;
    }

    public void appendAlgorithm(Algorithm alg) {
        this.algorithms.add(alg);
    }

    public static Ensemble generateEnsemble() {
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

    @Override
    public String toString() {
        String out = "";

        for (Algorithm alg : algorithms
                ) {
            out += alg.getId() + " ";
        }
        return out;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Algorithm> getAlgorithms() {
        return algorithms;
    }


}
