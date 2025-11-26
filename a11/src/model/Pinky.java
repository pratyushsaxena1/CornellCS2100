package model;

import java.awt.Color;
import graph.MazeGraph.Direction;
import graph.MazeGraph.MazeVertex;
import graph.MazeGraph.IPair;

/**
 * Pinky is the pink ghost who tries to get in front of Pac-Mann. In CHASE state, Pinky targets the
 * vertex closest to the point that is 3 units in front of Pac-Mann in his current facing direction.
 * In FLEE state, Pinky targets the northeast corner, which is (model.width - 3, 2).
 */
public class Pinky extends Ghost {

    /**
     * Creates a new Pinky ghost for the given model
     */
    public Pinky(GameModel model) {
        super(model, Color.PINK, 4000);
    }

    @Override
    protected MazeVertex target() {
        int width = model.width();
        if (state() == GhostState.FLEE) {
            return model.graph().closestTo(width - 3, 2);
        }
        MazeVertex pacMannVertex = model.pacMann().nearestVertex();
        IPair pacMannLocation = pacMannVertex.loc();
        int xCoordinate = pacMannLocation.i();
        int yCoordinate = pacMannLocation.j();
        Direction facing = model.pacMann().currentEdge().direction();
        switch (facing) {
            case LEFT -> xCoordinate -= 3;
            case RIGHT -> xCoordinate += 3;
            case UP -> yCoordinate -= 3;
            case DOWN -> yCoordinate += 3;
        }
        return model.graph().closestTo(xCoordinate, yCoordinate);
    }
}