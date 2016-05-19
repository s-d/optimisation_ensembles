package testing;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.util.ArrayList;
import java.util.Arrays;

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

        int[] heuristicToApply = solutions.get(0);

        problem.initialiseSolution(0);

        Fitness startingFitness = new Fitness(problem.getBestSolutionValue());

        double currentSolutionFitness = Double.POSITIVE_INFINITY;

        System.out.println("Starting fitness is " + problem.getBestSolutionValue());
        while (!hasTimeExpired()) {

            double newSolutionFitness = Double.POSITIVE_INFINITY;

            for (int i = 0; i < heuristicToApply.length; i++) {
                newSolutionFitness = problem.applyHeuristic(heuristicToApply[i], 0, 1);
                problem.copySolution(1, 0);

            }

            double delta = currentSolutionFitness - newSolutionFitness;
            System.out.println(newSolutionFitness);


            if (delta > 0) {
                problem.copySolution(1, 0);
                currentSolutionFitness = newSolutionFitness;
            }
        }
        System.out.println(toString() + " time expired");

        if(problem.getBestSolutionValue() < startingFitness.getFitness()){
            System.out.println("no improvement found");
        }
    }


    public String toString() {
        return "Test Hyper Heuristic Two";
    }


    public ProblemDomain getProblem() {
        return problem;
    }

    public int getNumberOfRules() {
        return numberOfRules;
    }

}