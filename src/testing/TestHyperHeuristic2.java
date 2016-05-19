package testing;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.util.ArrayList;

public class TestHyperHeuristic2 extends HyperHeuristic {

    private ProblemDomain problem;
    private int numberOfRules;
    private ArrayList<int[]> solutions;

    public TestHyperHeuristic2(long seed) {
        super(seed);
        System.out.println(toString());
    }

    private void generatePermutations() {
        //initialize array to hold solutions
        solutions = new ArrayList<>();

        //generate every possible combination of rules
        for (int i = 0; i < numberOfRules; i++) {
            for (int j = 0; j < numberOfRules; j++) {
                for (int k = 0; k < numberOfRules; k++) {
                    int[] solution = new int[3];
                    solution[0] = i;
                    solution[1] = j;
                    solution[2] = k;
                    solutions.add(solution);
                }
            }
        }

    }

    protected void solve(ProblemDomain problem) {
        this.problem = problem;
        this.numberOfRules = problem.getNumberOfHeuristics();
        this.generatePermutations();

        int[] heuristicToApply = solutions.get(4);

        problem.initialiseSolution(0);

        Fitness startingFitness = new Fitness(problem.getBestSolutionValue());
        Fitness bestFitness = new Fitness(0);

        double currentSolutionFitness = Double.POSITIVE_INFINITY;
        double newSolutionFitness = Double.POSITIVE_INFINITY;

        while (!hasTimeExpired()) {

            for (int i = 0; i < heuristicToApply.length; i++) {
                newSolutionFitness = problem.applyHeuristic(heuristicToApply[i], 0, 1);
                problem.copySolution(1, 0);
            }

            double delta = currentSolutionFitness - newSolutionFitness;

            if (delta > 0) {

                System.out.println(newSolutionFitness);
                problem.copySolution(1, 0);
                currentSolutionFitness = newSolutionFitness;
                bestFitness.setFitness(newSolutionFitness);
            }
        }

        System.out.println("\n" + this.toString() + " time has expired.");

        System.out.println("\nInitial fitness was: " + startingFitness.getFitness());

        System.out.println("\nBest fitness " + bestFitness.getFitness());

        double decrease = startingFitness.getFitness() - bestFitness.getFitness();
        double improvement = (decrease / bestFitness.getFitness()) * 100;

        System.out.print("\nImprovement of ");
        System.out.print(String.format("%.2f", improvement));
        System.out.print("%\n\n");


    }

    public String toString() {
        return "Test Hyper Heuristic Two";
    }

}