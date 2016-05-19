package testing;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class TestHyperHeuristic1 extends HyperHeuristic {

	// constructor
	public TestHyperHeuristic1(long seed) {
		super(seed);
	}

	// called by class run method
	protected void solve(ProblemDomain problem) {

		System.out.println(toString());

		// get the number of rules that are available to solve this problem
		int numberOfHeuristics = problem.getNumberOfHeuristics();

		// Initialise the starting fitness
		double currentSolutionFitness = Double.POSITIVE_INFINITY;

		// initialise the solution at index 0 in the solution memory array
		problem.initialiseSolution(0);

		// starting index of the the list of heuristics
		int heuristicToApply = 0;

		// loop until the timer expires
		while (!hasTimeExpired()) {

			// apply the chosen heuristic to the solution at index 0 in
			// memory
			// the new solution is then stored at index 1 of the solution memory
			// while we decide whether to accept it
			double newSolutionFitness = problem.applyHeuristic(heuristicToApply, 0, 1);

			System.out.println(heuristicToApply);
			
			if (heuristicToApply < numberOfHeuristics -1) {
				heuristicToApply++;
			} else {
				heuristicToApply = 0;
			}

			// calculate the change in fitness from the current solution to the
			// new solution
			double delta = currentSolutionFitness - newSolutionFitness;

			if (delta > 0) {
				// if there is an improvement then we 'accept' the solution by
				// copying the new solution into memory index 0
				problem.copySolution(1, 0);
				// we also set the current objective function value to the new
				// function value, as the new solution is now the current
				// solution
				currentSolutionFitness = newSolutionFitness;
				System.out.println(currentSolutionFitness);

			}

			// end of while loop
		}
		System.out.println(toString() + " time expiered");
		// end of solve method
	}

	public String toString() {
		return "Test Hyper Heurstic One";
	}

}