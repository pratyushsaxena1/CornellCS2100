package model;

import java.awt.Color;
import graph.MazeGraph.MazeVertex;
import graph.MazeGraph.IPair;

/**
 * Blinky is the red ghost who enters the maze after an initial delay of 2 seconds and directly
 * chases Pac-Mann. In CHASE state, Blinky targets Pac-Mann's nearest vertex. In FLEE state, he
 * targets the northwest corner of the board, which is (2, 2).
 */
public class Blinky extends Ghost {

    /**
     * Creates a new Blinky ghost for the given model
     */
    public Blinky(GameModel model) {
        super(model, Color.RED, 2000);
    }

    @Override
    protected MazeVertex target() {
        if (state() == GhostState.CHASE) {
            return model.pacMann().nearestVertex();
        }
        if (state() == GhostState.FLEE) {
            return model.graph().closestTo(2, 2);
        }
        MazeVertex start = model.graph().ghostStartingEdge().tail();
        return start;
    }
}