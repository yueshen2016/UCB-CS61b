package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private ExtrinsicMinPQ<Vertex> fringe;
    private double time;
    private Map<Vertex, Double> distTo;
    private SolverOutcome outcome;
    private Map<Vertex, Vertex> edgeTo;
    private int numStatesExplored;
    private Set<Vertex> visitedVertices;
    private List<Vertex> solution;
    private double solutionWeight;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        fringe = new ArrayHeapMinPQ<>();
        fringe.add(start, input.estimatedDistanceToGoal(start, end));
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        distTo.put(start, 0.0);
        numStatesExplored = 0;
        visitedVertices = new HashSet<>();
        solution = new ArrayList<>();
        solutionWeight = 0;

        while (fringe.size() > 0) {
            // start timing
            if(sw.elapsedTime() > timeout * Math.pow(10, 6)) {
                outcome = SolverOutcome.TIMEOUT;
                break;
            }
            Vertex curr = fringe.removeSmallest();
            visitedVertices.add(curr);
            numStatesExplored++;
            // find the goal
            if (curr.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                break;
            }
            for (WeightedEdge<Vertex> edge : input.neighbors(curr)) {
                if (!visitedVertices.contains(edge.to())) {
                    relax(edge, input, end);
                }
            }
        }
        if (distTo.containsKey(end)) {
            solutionWeight = distTo.get(end);
        }
        // generate solution if possible
        if (outcome == SolverOutcome.SOLVED) {
            Vertex ptr = end;
            while (ptr != start) {
                solution.add(ptr);
                ptr = edgeTo.get(ptr);
            }
            solution.add(start);
            Collections.reverse(solution);
        }

        if (outcome != SolverOutcome.SOLVED && outcome != SolverOutcome.TIMEOUT) outcome = SolverOutcome.UNSOLVABLE;
        time = sw.elapsedTime();
    }

    private void relax(WeightedEdge<Vertex> edge, AStarGraph<Vertex> input, Vertex end) {
        Vertex p = edge.from();
        Vertex q = edge.to();
        double w = edge.weight();

        if ((distTo.containsKey(q) && (distTo.get(p) + w < distTo.get(q))) || !distTo.containsKey(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            } else {
                fringe.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return time;
    }
}
