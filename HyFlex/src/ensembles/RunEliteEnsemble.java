package ensembles;

import java.io.FileNotFoundException;

/**
 * Created by Sam on 21/06/2016.
 */
public class RunEliteEnsemble {
    public static void main(String[] args) throws FileNotFoundException {
        for (int i = 0; i < 72;i++){
            Ensemble ens = Ensemble.generateEliteEnsemble();
            System.out.println(ens.getAlgorithms());
        }
    }

}
