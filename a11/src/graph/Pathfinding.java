package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pathfinding {

    /**
     * Represents a path ending at `lastEdge.end()` along with its length (distance).
     */
    record PathEnd<E extends WeightedEdge<?>>(double distance, E lastEdge) {

    }

    /**
     * Returns a list of `E` edges comprising the shortest non-backtracking simple path from vertex
     * `src` to vertex `dst`. A non-backtracking path never contains two consecutive edges between
     * the same two vertices (e.g., v -> w -> v). As a part of this requirement, the first edge in
     * the returned path cannot back-track `previousEdge` (when `previousEdge` is not null). If
     * there is not a non-backtracking path from `src` to `dst`, then null is returned. Requires
     * that if `previousEdge != null` then `previousEdge.head().equals(src)`.
     */
    public static <V extends Vertex<E>, E extends WeightedEdge<V>> List<E> shortestNonBacktrackingPath(
            V src, V dst, E previousEdge) {

        Map<V, PathEnd<E>> paths = pathInfo(src, previousEdge);
        return paths.containsKey(dst) ? pathTo(paths, src, dst) : null;
    }

    /**
     * Returns a map that associates each vertex reachable from `src` along a non-backtracking path
     * with a `PathEnd` object. The `PathEnd` object summarizes relevant information about the
     * shortest non-backtracking simple path from `src` to that vertex. A non-backtracking path
     * never contains two consecutive edges between the same two vertices (e.g., v -> w -> v). As a
     * part of this requirement, the first edge in the returned path cannot backtrack `previousEdge`
     * (when `previousEdge` is not null). Requires that if `previousEdge != null` then
     * `previousEdge.head().equals(src)`.
     */
    static <V extends Vertex<E>, E extends WeightedEdge<V>> Map<V, PathEnd<E>> pathInfo(V src,
            E previousEdge) {

        assert previousEdge == null || previousEdge.head().equals(src);

        // Associate vertex labels with info about the shortest-known path from `start` to that
        // vertex.  Populated as vertices are discovered (not as they are settled).
        Map<V, PathEnd<E>> pathInfo = new HashMap<>();

        // Make a min-priority queue for the "frontier"
        graph.MinPQueue<V> frontier = new graph.MinPQueue<>();
        pathInfo.put(src, new PathEnd<>(0.0, null));
        frontier.addOrUpdate(src, 0.0);

        // Dijkstra loop
        while (!frontier.isEmpty()) {
            V currentVertex = frontier.remove();
            PathEnd<E> currentInfo = pathInfo.get(currentVertex);
            double currentDistance = currentInfo.distance();
            E lastEdgeToCurrent = currentInfo.lastEdge();
            for (E outEdge : currentVertex.outgoingEdges()) {
                V neighbor = outEdge.head();
                // Don't backtrack on the first step
                if (previousEdge != null && currentVertex.equals(src)) {
                    if (outEdge.head().equals(previousEdge.tail())
                            && outEdge.tail().equals(previousEdge.head())) {
                        continue;
                    }
                }
                // Don't backtrack in general
                if (lastEdgeToCurrent != null) {
                    if (outEdge.head().equals(lastEdgeToCurrent.tail())
                            && outEdge.tail().equals(lastEdgeToCurrent.head())) {
                        continue;
                    }
                }
                // Update best distance to neighbor
                double newDistance = currentDistance + outEdge.weight();
                PathEnd<E> neighborInfo = pathInfo.get(neighbor);
                if (neighborInfo == null || newDistance < neighborInfo.distance()) {
                    pathInfo.put(neighbor, new PathEnd<>(newDistance, outEdge));
                    frontier.addOrUpdate(neighbor, newDistance);
                }
            }
        }
        return pathInfo;
    }

    /**
     * Return the list of edges in the shortest non-backtracking path from `src` to `dst`, as
     * summarized by the given `pathInfo` map. Requires `pathInfo` conforms to the specification as
     * documented by the `pathInfo` method; it must contain the last edge on the shortest
     * non-backtracking simple paths from `src` to all reachable vertices.
     */
    static <V, E extends WeightedEdge<V>> List<E> pathTo(Map<V, PathEnd<E>> pathInfo, V src,
            V dst) {
        // Stores the edges from the 'src' to the 'dst', except it'll be reversed
        java.util.ArrayList<E> pathEdges = new java.util.ArrayList<>();
        V currentVertex = dst;

        // Go backward until the source is reached
        while (!currentVertex.equals(src)) {
            PathEnd<E> info = pathInfo.get(currentVertex);
            E lastEdge = info.lastEdge();
            if (lastEdge == null) {
                break;
            }
            pathEdges.add(lastEdge);
            currentVertex = lastEdge.tail();
        }

        // Account for the fact that it was backwards by reversing it to make it in normal order
        java.util.Collections.reverse(pathEdges);
        return pathEdges;
    }
}
