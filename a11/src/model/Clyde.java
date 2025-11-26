package model;

import java.awt.Color;
import java.util.Random;
import graph.MazeGraph.MazeVertex;
import graph.MazeGraph.IPair;

/**
 * Clyde is the orange ghost. In CHASE state, Clyde behaves like Blinky when he is far away from
 * Pac-Mann, but chooses random targets when he is too close. In FLEE state, he heads for the
 * southeast corner, which is (model.width - 3, model.height - 3).
 */
public class Clyde extends Ghost {

    private final Random rng;

    /**
     * Creates a new Clyde ghost for the given model and includes the randomness for picking the
     * target
     */
    public Clyde(GameModel model, Random rng) {
        super(model, Color.ORANGE, 8000);
        this.rng = rng;
    }

    @Override
    protected MazeVertex target() {
        int width = model.width();
        int height = model.height();
        if (state() == GhostState.FLEE) {
            return model.graph().closestTo(width - 3, height - 3);
        }
        MazeVertex clydeVertex = nearestVertex();
        MazeVertex pacMannVertex = model.pacMann().nearestVertex();
        IPair clydeLocation = clydeVertex.loc();
        IPair pacMannLocation = pacMannVertex.loc();
        double distanceX = pacMannLocation.i() - clydeLocation.i();
        double distanceY = pacMannLocation.j() - clydeLocation.j();
        double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        if (distanceTotal >= 10.0) {
            return pacMannVertex;
        } else {
            int randomX = rng.nextInt(width);
            int randomY = rng.nextInt(height);
            return model.graph().closestTo(randomX, randomY);
        }
    }
}