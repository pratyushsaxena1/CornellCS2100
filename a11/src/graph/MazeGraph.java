package graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import util.GameMap;
import util.MazeGenerator;

/**
 * A graph representing a game's maze, connecting the "path" tiles of a tile grid.
 */
public class MazeGraph {

    /* ****************************************************************
     * Helper types (defined here as nested types to avoid writing    *
     * even more .java files for each one)                            *
     **************************************************************** */

    /**
     * An ordered pair of integers.  In the context of the tile grid, `i` corresponds to the column
     * (horizontal coordinate, increasing to the right) and `j` corresponds to the row (vertical
     * direction, increasing down).
     */
    public record IPair(int i, int j) {

    }

    /**
     * The direction of a (directed) edge in this graph.
     */
    public enum Direction {
        LEFT, RIGHT, UP, DOWN;

        // Enums are still classes in Java, so they can have methods too.

        /**
         * Return the direction that is the opposite of this direction.
         */
        public Direction reverse() {
            return switch (this) {
                case LEFT -> RIGHT;
                case RIGHT -> LEFT;
                case UP -> DOWN;
                case DOWN -> UP;
            };
        }
    }

    /**
     * A vertex in our graph, corresponding to a `PATH` tile in the tile grid.
     */
    public static class MazeVertex implements Vertex<MazeEdge> {

        /**
         * The location of this vertex's tile within the tile grid.
         */
        private final IPair loc;

        /**
         * This vertex's outgoing edges, each associated with the direction it points in.
         */
        private final HashMap<Direction, MazeEdge> edgeMap;


        /**
         * Construct a new vertex at location `loc` with no outgoing edges.
         */
        public MazeVertex(IPair loc) {
            this.loc = loc;
            edgeMap = new HashMap<>();
        }

        /**
         * Return the edge leaving this vertex in the direction `direction`, or null if no such edge
         * exists.  The direction of a "tunnel" edge is from the source to the grid's nearest
         * boundary (that is, an edge connecting a top tile to a bottom tile points "up").
         */
        public MazeEdge edgeInDirection(Direction direction) {
            return edgeMap.get(direction);
        }

        /**
         * Return the coordinates of this vertex's tile in the tile grid.
         */
        public IPair loc() {
            return loc;
        }

        @Override
        public Iterable<MazeEdge> outgoingEdges() {
            return edgeMap.values();
        }

        /**
         * Add `edge` as an outgoing edge from this vertex.  Requires that this vertex is the edge's
         * source and that no outgoing edge has already been added in the same direction.  This
         * method has restricted visibility, as it is only meant to be called when constructing a
         * `MazeGraph`.
         */
        public void addOutgoingEdge(MazeEdge edge) {
            assert edge.tail().equals(this);
            assert !edgeMap.containsKey(edge.direction());
            edgeMap.put(edge.direction(), edge);
        }

        @Override
        public String toString() {
            return "MazeVertex(" + loc.i() + "," + loc.j() + ")";
        }
    }

    /**
     * Represents a directed edge from `src` to `dst` with weight `weight`, which points in
     * direction `direction` on the tile grid.
     */
    public record MazeEdge(MazeVertex tail, MazeVertex head, Direction direction,
                           double weight) implements WeightedEdge<MazeVertex> {

        /**
         * Return the edge pointing from `dst` to `src` in the maze graph.  Requires that the maze
         * graph has been fully constructed.
         */
        public MazeEdge reverse() {
            return head.edgeInDirection(direction.reverse());
        }
    }

    /* ****************************************************************
     * Fields of MazeGraph                                            *
     **************************************************************** */

    /**
     * The vertices of this graph, each associated with the location of its corresponding path tile
     * in the tile grid.
     */
    private final HashMap<IPair, MazeVertex> vertices;

    /**
     * The width of the tile grid defining this maze.
     */
    private final int width;

    /**
     * The height of the tile grid defining this maze.
     */
    private final int height;

    /* ****************************************************************
     * Methods of MazeGraph                                           *
     **************************************************************** */

    /**
     * Construct the maze graph corresponding to the tile grid `map`. Requires `map.types()[2][2]`
     * to be a `TileType.PATH` and that all `PATH` tiles belong to the same orthogonally connected
     * component. Requires `map.types()` and `map.elevations()` have the same shape, with the first
     * index corresponding to columns and the second index corresponding to rows.
     */
    public MazeGraph(GameMap map) {
        width = map.types().length;
        height = map.types()[0].length;
        vertices = new HashMap<>();

        // Store the tile types and elevations once at the beginning
        MazeGenerator.TileType[][] tileTypes = map.types();
        double[][] elevations = map.elevations();

        IPair startingLocation = new IPair(2, 2);
        assert tileTypes[startingLocation.i()][startingLocation.j()] == MazeGenerator.TileType.PATH;

        // Create a queue to be used for the BFS and enqueue the starting vertex
        ArrayDeque<MazeVertex> vertexQueue = new ArrayDeque<>();
        MazeVertex startingVertex = new MazeVertex(startingLocation);
        vertices.put(startingLocation, startingVertex);
        vertexQueue.add(startingVertex);

        // BFS - find all the reachable PATH tiles and add outgoing edges
        while (!vertexQueue.isEmpty()) {
            MazeVertex currentVertex = vertexQueue.removeFirst();
            int currentColumn = currentVertex.loc().i();
            int currentRow = currentVertex.loc().j();

            // Check left neighbor
            int leftColumn = (currentColumn - 1 + width) % width;
            int leftRow = currentRow;
            if (tileTypes[leftColumn][leftRow] == MazeGenerator.TileType.PATH) {
                IPair leftLocation = new IPair(leftColumn, leftRow);
                MazeVertex leftVertex = vertices.get(leftLocation);
                if (leftVertex == null) {
                    leftVertex = new MazeVertex(leftLocation);
                    vertices.put(leftLocation, leftVertex);
                    vertexQueue.addLast(leftVertex);
                }
                double weight = edgeWeight(elevations[currentColumn][currentRow],
                        elevations[leftColumn][leftRow]);
                currentVertex.addOutgoingEdge(
                        new MazeEdge(currentVertex, leftVertex, Direction.LEFT, weight));
            }

            // Check right neighbor
            int rightColumn = (currentColumn + 1) % width;
            int rightRow = currentRow;
            if (tileTypes[rightColumn][rightRow] == MazeGenerator.TileType.PATH) {
                IPair rightLocation = new IPair(rightColumn, rightRow);
                MazeVertex rightVertex = vertices.get(rightLocation);
                if (rightVertex == null) {
                    rightVertex = new MazeVertex(rightLocation);
                    vertices.put(rightLocation, rightVertex);
                    vertexQueue.addLast(rightVertex);
                }
                double weight = edgeWeight(elevations[currentColumn][currentRow],
                        elevations[rightColumn][rightRow]);
                currentVertex.addOutgoingEdge(
                        new MazeEdge(currentVertex, rightVertex, Direction.RIGHT, weight));
            }

            // Check up neighbor
            int upColumn = currentColumn;
            int upRow = (currentRow - 1 + height) % height;
            if (tileTypes[upColumn][upRow] == MazeGenerator.TileType.PATH) {
                IPair upLocation = new IPair(upColumn, upRow);
                MazeVertex upVertex = vertices.get(upLocation);
                if (upVertex == null) {
                    upVertex = new MazeVertex(upLocation);
                    vertices.put(upLocation, upVertex);
                    vertexQueue.addLast(upVertex);
                }
                double weight = edgeWeight(elevations[currentColumn][currentRow],
                        elevations[upColumn][upRow]);
                currentVertex.addOutgoingEdge(
                        new MazeEdge(currentVertex, upVertex, Direction.UP, weight));
            }

            // Check down neighbor
            int downColumn = currentColumn;
            int downRow = (currentRow + 1) % height;
            if (tileTypes[downColumn][downRow] == MazeGenerator.TileType.PATH) {
                IPair downLocation = new IPair(downColumn, downRow);
                MazeVertex downVertex = vertices.get(downLocation);
                if (downVertex == null) {
                    downVertex = new MazeVertex(downLocation);
                    vertices.put(downLocation, downVertex);
                    vertexQueue.addLast(downVertex);
                }
                double weight = edgeWeight(elevations[currentColumn][currentRow],
                        elevations[downColumn][downRow]);
                currentVertex.addOutgoingEdge(
                        new MazeEdge(currentVertex, downVertex, Direction.DOWN, weight));
            }
        }
    }

    /**
     * Return the weight that an edge should have if it connects a vertex with elevation `tailElev`
     * to a vertex with elevation `headElev`.
     */
    static double edgeWeight(double tailElev, double headElev) {
        // Uphill edges should have higher weight
        double elevDiff = Math.clamp(headElev - tailElev, -0.25, 0.25);
        double weight = 1 + elevDiff * 3;
        assert weight >= 0;
        return weight;
    }

    /**
     * Return a vertex that is close to the tile location `(i, j)` (where `i` is column number and
     * `j` is row number).  Ghosts are expected to use this to ensure that they are targeting a
     * reachable path tile.  (Most of the time, this will be a closest such vertex if "tunnels" are
     * ignored.)
     */
    public MazeVertex closestTo(int i, int j) {
        // clamp i,j within maze bounds
        i = Math.clamp(i, 0, width - 2);
        j = Math.clamp(j, 0, height - 2);

        // The maze generator guarantees that tiles with coordinates (3x+2,3y+2) are path tiles.
        // (ip,jp) is the closest such coordinates to (i,j).
        int ip = (((i - 1) / 3) * 3 + 2);
        int jp = (((j - 1) / 3) * 3 + 2);

        for (IPair loc : new IPair[]{new IPair(i, j), new IPair(i, jp), new IPair(ip, j),
                new IPair(ip, jp)}) {
            if (vertices.containsKey(loc)) {
                return vertices.get(loc);
            }
        }

        // the only time we reach here is if (ip,jp) is inside the ghost box. In this case,
        // (ip,jp+3) is guaranteed to be a path tile outside the ghost box.
        assert (vertices.get(new IPair(ip, jp + 3)) != null);
        return vertices.get(new IPair(ip, jp + 3));
    }

    /**
     * Return the full collection of vertices in this graph.
     */
    public Iterable<MazeVertex> vertices() {
        return vertices.values();
    }

    /**
     * Return the first edge that PacMann will traverse at the start of a game.
     */
    public MazeEdge pacMannStartingEdge() {
        IPair startingLoc = new IPair((width - 1) / 2, 3 * ((3 * (height / 3) - 1) / 4) + 2);
        MazeVertex t = vertices.get(startingLoc);
        if (t.edgeMap.containsKey(Direction.LEFT)) {
            return t.edgeMap.get(Direction.LEFT).reverse();
        } else {
            return t.edgeMap.get(Direction.UP).reverse();
        }
    }

    /**
     * Return the first edge that a ghost will traverse upon transitioning from the WAIT to the
     * CHASE state.
     */
    public MazeEdge ghostStartingEdge() {
        IPair startingLoc = new IPair((width - 1) / 2, 3 * ((height - 3) / 6) - 1);
        MazeVertex s = vertices.get(startingLoc);
        return s.edgeMap.get(Direction.RIGHT);
    }
}
