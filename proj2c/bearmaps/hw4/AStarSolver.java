package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private ArrayHeapMinPQ<Vertex> fringes;
    private double time;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private SolverOutcome outcome;
    private int numStatesExplored;
    private List<Vertex> solution;
    private double solutionWeight;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        fringes = new ArrayHeapMinPQ<>();
        fringes.add(start, input.estimatedDistanceToGoal(start, end));
        distTo = new HashMap<>();
        distTo.put(start, 0.0);
        edgeTo = new HashMap<>();
        numStatesExplored = 0;
        solution = new LinkedList<>();
        solutionWeight = 0;
        time = 0;

        long timeStart = System.currentTimeMillis();
        while (fringes.size() != 0) {
            Vertex curr = fringes.removeSmallest();
            time = (System.currentTimeMillis() - timeStart) / 1000.0;
            numStatesExplored++;
            // If goal is found, stops running and updates the solution
            if (curr.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                // prepare the solution
                Vertex ptr = end;
                while (ptr != start) {
                    solution.add(ptr);
                    ptr = edgeTo.get(ptr);
                }
                solution.add(start);
                Collections.reverse(solution);
                // prepare the solution weight
                solutionWeight = distTo.get(end);
                return;
            }
            // If timeout is exceeded, stops running
            if (time > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                return;
            }
            relax(input, end, curr);
        }
        // unsolvable condition
        time = (System.currentTimeMillis() - timeStart) / 1000.0;
        outcome = SolverOutcome.UNSOLVABLE;
    }

    private void relax(AStarGraph<Vertex> input, Vertex end, Vertex p) {
        List<WeightedEdge<Vertex>> neighbors = input.neighbors(p);
        for (WeightedEdge<Vertex> neighbor : neighbors) {
            Vertex q = neighbor.to();
            double w = neighbor.weight();
            if (!distTo.containsKey(q) || distTo.get(p) + w < distTo.get(q)) {
                distTo.put(q, distTo.get(p) + w);
                edgeTo.put(q, p);
                if (fringes.contains(q)) {
                    fringes.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                } else {
                    fringes.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                }
            }
        }
    }

    @Override
    public SolverOutcome outcome() { return outcome; }

    @Override
    public List<Vertex> solution() { return solution; }

    @Override
    public double solutionWeight() { return solutionWeight; }

    @Override
    public int numStatesExplored() { return numStatesExplored; }

    @Override
    public double explorationTime() { return time; }
}