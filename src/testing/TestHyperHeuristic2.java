package testing;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class TestHyperHeuristic2 extends HyperHeuristic {


	public TestHyperHeuristic2(long seed) {
		super(seed);
	}

	public void generatePermutations(){
		
	}
	
	protected void solve(ProblemDomain problem) {
		System.out.println(toString());
		int numberOfHeurstics = problem.getNumberOfHeuristics();
		double currentSolutionFitness = Double.POSITIVE_INFINITY;
		problem.initialiseSolution(0);
		int heuristicToApply = 0;
		
		while (!hasTimeExpired()) {
			double newSolutionFitness = problem.applyHeuristic(heuristicToApply, 0, 1);
			if (heuristicToApply < numberOfHeurstics) {
				heuristicToApply++;
			} else {
				heuristicToApply = 0;
			}
			double delta = currentSolutionFitness - newSolutionFitness;
			
			if (delta > 0) {
				problem.copySolution(1, 0);
				currentSolutionFitness = newSolutionFitness;
			}
			System.out.println(currentSolutionFitness);
		}
		System.out.println(toString() + " time expiered");
	}

	
	public String toString() {
		return "Test Hyper Heurstic Two";
	}

	
}