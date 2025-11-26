package model;

import graph.MazeGraph.Direction;
import graph.MazeGraph.MazeEdge;
import graph.MazeGraph.MazeVertex;

/**
 * A PacMann Actor who is controlled by the player's keyboard input.
 */
public class PacMannManual extends PacMann {

    /**
     * Creates a PacMann actor based on the model.
     */
    public PacMannManual(GameModel model) {
        super(model);
    }

    /**
     * Return the next edge of the maze that PacMann should traverse.
     */
    @Override
    public MazeEdge nextEdge() {
        MazeVertex vertexNear = nearestVertex();
        Direction command = model.playerCommand();
        if (command != null) {
            MazeEdge edge = edgeFrom(vertexNear, command);
            if (edge != null) {
                return edge;
            }
        }
        MazeEdge current = currentEdge();
        if (current != null) {
            MazeEdge straight = edgeFrom(vertexNear, current.direction());
            if (straight != null) {
                return straight;
            }
        }
        return null;
    }

    /**
     * Return the outgoing edge from vertex 'v' that has direction 'd'
     * or null if such a edge doesn't exist.
     */
    private MazeEdge edgeFrom(MazeVertex v, Direction d) {
        for (MazeEdge e : v.outgoingEdges()) {
            if (e.direction() == d) {
                return e;
            }
        }
        return null;
    }
}