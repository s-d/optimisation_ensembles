package testing;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.util.ArrayList;

public class TestHyperHeuristic2 extends HyperHeuristic {

    private int numberOfRules;
    private ArrayList<int[]> solutions;

    //constructor
    public TestHyperHeuristic2(long seed) {
        super(seed);
        System.out.println(toString());
    }

    //create every sequence of three sub heuristics
    private void generatePermutations() {
        //initialize array to hold solutions
        solutions = new ArrayList<>();

        //generate combination of rules
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

    //called by abstract run method
    protected void solve(ProblemDomain problem) {
        //get number of sub heuristics
        this.numberOfRules = problem.getNumberOfHeuristics();
        //create sequences from the sub heuristics
        this.generatePermutations();

        //select a sequence
        int[] heuristicToApply = solutions.get(1);

        //initialise the problem
        problem.initialiseSolution(0);

        //get the  fitness of the initial solution
        Fitness startingFitness = new Fitness(problem.getBestSolutionValue());

        //declare variable to hold best fitness found by the the sequence
        Fitness bestFitness = new Fitness(0);

        //set current fitness and new fitness to infinity
        double currentSolutionFitness = Double.POSITIVE_INFINITY;
        double newSolutionFitness = Double.POSITIVE_INFINITY;

        //set iteration counter to 0
        int iterationCounter = 0;

        while (!hasTimeExpired()) {
            iterationCounter++;

            for (int i = 0; i < heuristicToApply.length; i++) {
                newSolutionFitness = problem.applyHeuristic(heuristicToApply[i], 0, 1);
                problem.copySolution(1, 0);
            }

            double delta = currentSolutionFitness - newSolutionFitness;


            if (delta > 0) {
                System.out.println("Iteration: "+iterationCounter);
                System.out.println(newSolutionFitness);
                problem.copySolution(1, 0);
                currentSolutionFitness = newSolutionFitness;
                bestFitness.setFitness(newSolutionFitness);
            }
        }

        System.out.println("\n" + this.toString() + " time has expired.");

        System.out.println("\nInitial fitness was: " + startingFitness.getFitness());

        System.out.println("\nIterations run: "+iterationCounter);

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