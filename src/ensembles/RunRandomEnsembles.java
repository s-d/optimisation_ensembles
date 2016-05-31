package ensembles;

/**
 * Created by 40056761 on 30/05/2016.
 */
public class RunRandomEnsembles {
    private static int ensembleIndex = 1;

    public static void main(String args[]) {

        for (int i = 0; i < 100; i++) {
            System.out.println(Ensemble.generateEnsemble());
        }
    }
}