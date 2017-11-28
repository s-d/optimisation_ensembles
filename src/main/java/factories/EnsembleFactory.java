package factories;

import heuristics.Algorithm;
import heuristics.Ensemble;

import java.io.IOException;
import java.util.ArrayList;

public class EnsembleFactory {

    private static int index = 1;

    /* return an ensemble from ordered algorithms */
    public static Ensemble generateEnsemble() {
        ArrayList<Algorithm> orderedAlgorithms = AlgorithmFactory.getAlgorithms();
        return generate(orderedAlgorithms);
    }

    /* return an ensemble comprised of random algorithms */
    public static Ensemble generateRandomEnsemble() {
        ArrayList<Algorithm> randomAlgorithms = AlgorithmFactory.getRandomAlgorithms();
        return generate(randomAlgorithms);
    }

    public static Ensemble generateRandomEnsemble(int index){
        ArrayList<Algorithm> randomAlgorithms = AlgorithmFactory.getRandomAlgorithms();
        return generate(randomAlgorithms, index);
    }

    /* return an ensemble comprised of elite algorithms */
    public static Ensemble generateEliteEnsemble(String problemType) throws IOException {
        ArrayList<Algorithm> eliteAlgorithms = AlgorithmFactory.getEliteAlgorithms(problemType);
        return generate(eliteAlgorithms);
    }

    /* base generation method */
    private static Ensemble generate(ArrayList<Algorithm> algorithms) {
        Ensemble ensemble = new Ensemble(index -1);
        for (int i = 0; i < index * 5; i++) {
            if (i < algorithms.size()) {
                ensemble.appendAlgorithm(algorithms.get(i));
            }
        }
        index++;
        return ensemble;
    }

    private static Ensemble generate(ArrayList<Algorithm> algorithms, int index) {
        Ensemble ensemble = new Ensemble(index -1);
        for (int i = 0; i < index * 5; i++) {
            if (i < algorithms.size()) {
                ensemble.appendAlgorithm(algorithms.get(i));
            }
        }
        return ensemble;
    }

}