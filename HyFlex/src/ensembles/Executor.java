package ensembles;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

class Executor extends HyperHeuristic {

    private int iteration;
    private int problemInstance;
    private long problemSeed;
    private long algorithmSeed;
    private Ensemble ensemble;
    private String ensembleType = "Ensemble";

    Executor(Ensemble ensemble, long algorithmSeed, long problemSeed, int problemInstance, int iteration, boolean eliteFlag) {
        super(algorithmSeed);
        this.ensemble = ensemble;
        this.algorithmSeed = algorithmSeed;
        this.problemSeed = problemSeed;
        this.problemInstance = problemInstance;
        this.iteration = iteration;
        if (eliteFlag) {
            ensembleType = "Elite ensemble";
        }
    }

    @Override
    protected void solve(ProblemDomain problemDomain) {
        String output;
        int runs = 0;
        int noImprovement = 0;
        double startingFitness;
        double currentFitness;
        double bestFitness;
        double newFitness;
        double delta;

        problemDomain.setMemorySize(3);
        problemDomain.initialiseSolution(0);
        problemDomain.copySolution(0, 1);

        startingFitness = problemDomain.getBestSolutionValue();
        currentFitness = startingFitness;
        bestFitness = Double.POSITIVE_INFINITY;

        while (!hasTimeExpired()) {
            for (Algorithm currentAlg : ensemble.getAlgorithms()) {
                for (int currentHeuristic : currentAlg.getHeuristics()) {

                    newFitness = problemDomain.applyHeuristic(currentHeuristic, 1, 2);
                    bestFitness = (newFitness < bestFitness) ? newFitness : bestFitness;

                    delta = currentFitness - newFitness;

                    if (delta > 0) {
                        problemDomain.copySolution(2, 1);
                        currentFitness = newFitness;
                        noImprovement = 0;
                    } else {
                        noImprovement++;
                    }
                }   //heuristic
            }   //algorithm

            runs++;

            if (noImprovement < ensemble.getAlgorithms().size() * 3) {
                noImprovement = 0;
            } else {
                // "%d,%d,%d,%d,%f,%d,%f,%d,\"%s\" \r\n"
                output = ("" + iteration + "," + problemInstance + "," + problemSeed + "," +
                        algorithmSeed + "," + startingFitness + "," + ensemble.getID() + "," +
                        bestFitness + "," + runs + ",\"" + ensemble + "\"\n");
                try {
                    RunDiverseHeuristics.WriteData(output);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.printf("%s %d, problem %d, iteration %d complete.\n", ensembleType, ensemble.getID(), problemInstance, this.iteration);
                return;
            }
        }   //while
    }   //method

    @Override
    public String toString() {
        return null;
    }
}
