package exploration;


import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;
import FlowShop.FlowShop;
import PersonnelScheduling.PersonnelScheduling;
import SAT.SAT;

import java.util.ArrayList;

/**
 * Created by Sam on 05/01/2017.
 */
public class Testing {

    public static void main(String[] args) {
        ArrayList<ProblemDomain.HeuristicType> heuristicTypes = new ArrayList<>();
        heuristicTypes.add(ProblemDomain.HeuristicType.LOCAL_SEARCH);
        heuristicTypes.add(ProblemDomain.HeuristicType.RUIN_RECREATE);
        heuristicTypes.add(ProblemDomain.HeuristicType.MUTATION);

        ProblemDomain problem;
        ArrayList<ProblemDomain> problemDomains = new ArrayList<>();
        problemDomains.add(problem = new BinPacking(0));
        problemDomains.add(problem = new SAT(0));
        problemDomains.add(problem = new FlowShop(0));
        problemDomains.add(problem = new PersonnelScheduling(0));

        ArrayList<Integer> heuristicNumbers;

        for (ProblemDomain pd: problemDomains) {
            heuristicNumbers = new ArrayList<>();
            for (ProblemDomain.HeuristicType h : heuristicTypes) {
                for (int i : pd.getHeuristicsOfType(h)) {
                    heuristicNumbers.add(i);
                }
            }
            System.out.println(pd.toString() + heuristicNumbers);
        }
    }
}
