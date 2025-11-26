package model;

import java.awt.Color;
import graph.MazeGraph.MazeVertex;
import graph.MazeGraph.IPair;

/**
 * Inky is the cyan ghost who tries to trap Pac-Mann between himself and Blinky. In CHASE state,
 * Inky targets the vertex whose coordinates are such that Pac-Mann's nearest vertex is the midpoint
 * between Blinky's nearest vertex and this target. In FLEE state, Inky heads for the southwest
 * corner, which is (2, model.height - 3).
 */
public class Inky extends Ghost {

    /**
     * Creates a new Inky ghost for the given model
     */
    public Inky(GameModel model) {
        super(model, Color.CYAN, 6000);
    }

    @Override
    protected MazeVertex target() {
        int height = model.height();
        if (state() == GhostState.FLEE) {
            return model.graph().closestTo(2, height - 3);
        }
        MazeVertex pacMannVertex = model.pacMann().nearestVertex();
        MazeVertex inkyVertex = model.blinky().nearestVertex();
        IPair pacMannLocation = pacMannVertex.loc();
        IPair inkyLocation = inkyVertex.loc();
        int pacMannXCoordinate = pacMannLocation.i();
        int pacMannYCoordinate = pacMannLocation.j();
        int inkyXCoordinate = inkyLocation.i();
        int inkyYCoordinate = inkyLocation.j();
        int targetXCoordinate = 2 * pacMannXCoordinate - inkyXCoordinate;
        int targetYCoordinate = 2 * pacMannYCoordinate - inkyYCoordinate;
        return model.graph().closestTo(targetXCoordinate, targetYCoordinate);
    }
}