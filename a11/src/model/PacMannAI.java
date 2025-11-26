package model;

import graph.MazeGraph.MazeEdge;
import graph.MazeGraph.MazeVertex;

import java.util.*;

/**
 * PacMannAI uses a BFS-based greedy strategy:
 * It finds the nearest remaining DOT or PELLET using a breadth-first search
 * and selects the first edge in the shortest path toward that item.
 */
public class PacMannAI extends PacMann {

    public PacMannAI(GameModel model) {
        super(model);
    }

    @Override
    public MazeEdge nextEdge() {
        MazeVertex start = nearestVertex();

        // If moving along an edge, continue straight
        if (!location().atVertex()) {
            return currentEdge();
        }

        // BFS to compute shortest paths from start
        Map<MazeVertex, MazeEdge> parentEdge = new HashMap<>();
        Map<MazeVertex, Boolean> visited = new HashMap<>();
        Queue<MazeVertex> queue = new LinkedList<>();

        queue.add(start);
        visited.put(start, true);
        parentEdge.put(start, null);

        MazeVertex nearestItemVertex = null;

        while (!queue.isEmpty()) {
            MazeVertex v = queue.remove();

            // Check if this vertex contains an item
            GameModel.Item item = model.itemAt(v);
            if (item == GameModel.Item.DOT || item == GameModel.Item.PELLET) {
                nearestItemVertex = v;
                break; // BFS guarantees this is the closest
            }

            // Explore outgoing edges
            for (MazeEdge e : v.outgoingEdges()) {
                MazeVertex next = e.head();
                if (!visited.containsKey(next)) {
                    visited.put(next, true);
                    parentEdge.put(next, e);
                    queue.add(next);
                }
            }
        }

        if (nearestItemVertex == null) {
            return null; // no items left → do nothing
        }

        // Reconstruct path backward from nearestItemVertex to start
        MazeVertex current = nearestItemVertex;
        MazeEdge pathEdge = null;

        while (current != start) {
            pathEdge = parentEdge.get(current);
            if (pathEdge == null) break;
            current = pathEdge.tail();
        }

        return pathEdge; // first step in the BFS shortest path
    }
}