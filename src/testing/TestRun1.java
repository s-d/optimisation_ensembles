package testing;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;
import Examples.ExampleHyperHeuristic1;


public class TestRun1 {
	public static void main(String[] args) {

		//create a ProblemDomain object with a seed for the random number generator
		ProblemDomain problem = new BinPacking(1234);

		//creates an ExampleHyperHeuristic object with a seed for the random number generator
		HyperHeuristic hyper_heuristic_object = new TestHyperHeurstic1(1234);

		//we must load an instance within the problem domain, in this case we choose instance 2
		problem.loadInstance(2);

		//we must set the time limit for the hyper-heuristic in milliseconds, in this example we set the time limit to 1 minute
		hyper_heuristic_object.setTimeLimit(10000);

		//a key step is to assign the ProblemDomain object to the HyperHeuristic object. 
		//However, this should be done after the instance has been loaded, and after the time limit has been set
		hyper_heuristic_object.loadProblemDomain(problem);

		//now that all of the parameters have been loaded, the run method can be called.
		//this method starts the timer, and then calls the solve() method of the hyper_heuristic_object.
		hyper_heuristic_object.run();

		//obtain the best solution found within the time limit
		System.out.println(hyper_heuristic_object.getBestSolutionValue());
	}
}