package solver;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import heuristics.Algorithm;
import heuristics.Ensemble;

class ExecuteHyperHeuristic extends HyperHeuristic {

    private int iteration;
    private int problemInstance;
    private long problemSeed;
    private long algorithmSeed;
    private Ensemble ensemble;
    private String type = "Ensemble";
    private boolean algorithmData = false;

    ExecuteHyperHeuristic(Ensemble ensemble, long algorithmSeed, long problemSeed, int problemInstance, int iteration, String type, String flag) {
        super(algorithmSeed);
        this.ensemble = ensemble;
        this.algorithmSeed = algorithmSeed;
        this.problemSeed = problemSeed;
        this.problemInstance = problemInstance;
        this.iteration = iteration;
        if (flag.equals("--elite")) {
            this.type = "Elite ensemble";
        }

        if (type.equals("-a")) {
            algorithmData = true;
            this.type = "Algorithm";
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
                String heu = (algorithmData) ? ((ensemble.getAlgorithms()).get(0)).toString() : (ensemble).toString();
                output = String.format("%d,%d,%d,%d,%f,%d,%f,%d,\"%s\"\r\n",
                        iteration, problemInstance, problemSeed, algorithmSeed,
                        startingFitness, ensemble.getID(), bestFitness, runs, heu);
                try {
                    RunDiverseHeuristics.writeData(output);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.printf("%s %d, problem %d, iteration %d complete.\n",
                        type, ensemble.getID(), problemInstance, this.iteration);
                return;
            }
        }   //while
    }   //method

    @Override
    public String toString() {
        return null;
    }
}