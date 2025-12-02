package model;

import graph.MazeGraph.MazeEdge;
import graph.MazeGraph.MazeVertex;

import java.util.*;

public class PacMannAI extends PacMann {

    public PacMannAI(GameModel model) {
        super(model);
    }

    private Map<MazeVertex, MazeEdge> lastParentEdge;

    @Override
    public MazeEdge nextEdge() {
        MazeVertex start = nearestVertex();
        if (!location().atVertex()) {
            return currentEdge();
        }
        List<Ghost> ghosts = new ArrayList<>();
        boolean anyGhostsFleeing = false;
        List<MazeVertex> fleeingGhostVertices = new ArrayList<>();
        for (Actor actor : model.actors()) {
            if (actor instanceof Ghost ghost) {
                ghosts.add(ghost);
                if (ghost.state() == Ghost.GhostState.FLEE) {
                    anyGhostsFleeing = true;
                    fleeingGhostVertices.add(ghost.nearestVertex());
                }
            }
        }
        Map<MazeVertex, Integer> ghostDistanceMap = computeGhostDistances(ghosts);
        Set<MazeVertex> dangerous = createDangerSet(start, ghostDistanceMap);
        MazeVertex target = chooseBestTargetViaBfs(start, dangerous, ghostDistanceMap,
                fleeingGhostVertices, anyGhostsFleeing);
        if (target == null) {
            target = chooseBestTargetViaBfs(start, Collections.emptySet(), ghostDistanceMap,
                    fleeingGhostVertices, anyGhostsFleeing);
        }
        if (target == null) {
            MazeEdge fallback = safestNeighborEdge(start, ghostDistanceMap);
            if (fallback != null) {
                return fallback;
            }
            for (MazeEdge edge : start.outgoingEdges()) {
                return edge;
            }
            return currentEdge();
        }
        MazeEdge first = firstEdgeOnPath(start, target, lastParentEdge);
        if (first == null) {
            MazeEdge fallback = safestNeighborEdge(start, ghostDistanceMap);
            if (fallback != null) {
                return fallback;
            }
            for (MazeEdge edge : start.outgoingEdges()) {
                return edge;
            }
            return currentEdge();
        }
        return first;
    }

    private Map<MazeVertex, Integer> computeGhostDistances(List<Ghost> ghosts) {
        Map<MazeVertex, Integer> distanceMap = new HashMap<>();
        Queue<MazeVertex> queue = new ArrayDeque<>();
        for (Ghost ghost : ghosts) {
            if (ghost.state() == Ghost.GhostState.CHASE) {
                MazeVertex ghostVertex = ghost.nearestVertex();
                if (!distanceMap.containsKey(ghostVertex)) {
                    distanceMap.put(ghostVertex, 0);
                    queue.add(ghostVertex);
                }
            }
        }
        while (!queue.isEmpty()) {
            MazeVertex vertex = queue.remove();
            int distance = distanceMap.get(vertex);
            for (MazeEdge edge : vertex.outgoingEdges()) {
                MazeVertex next = edge.head();
                if (!distanceMap.containsKey(next)) {
                    distanceMap.put(next, distance + 1);
                    queue.add(next);
                }
            }
        }
        return distanceMap;
    }

    private Set<MazeVertex> createDangerSet(MazeVertex start,
            Map<MazeVertex, Integer> ghostDistanceMap) {
        int startGhostDistance = ghostDistanceMap.getOrDefault(start, 1000);
        int radius = startGhostDistance <= 5 ? 3 : 2;
        Set<MazeVertex> dangerous = new HashSet<>();
        for (Map.Entry<MazeVertex, Integer> entry : ghostDistanceMap.entrySet()) {
            MazeVertex vertex = entry.getKey();
            int distance = entry.getValue();
            if (distance <= radius) {
                dangerous.add(vertex);
            }
        }
        return dangerous;
    }

    private MazeVertex chooseBestTargetViaBfs(MazeVertex start, Set<MazeVertex> blocked,
            Map<MazeVertex, Integer> ghostDistanceMap, List<MazeVertex> fleeingGhostVertices,
            boolean anyGhostsFleeing) {
        Map<MazeVertex, MazeEdge> parentEdge = new HashMap<>();
        Map<MazeVertex, Integer> stepsMap = new HashMap<>();
        Queue<MazeVertex> queue = new ArrayDeque<>();
        queue.add(start);
        stepsMap.put(start, 0);
        parentEdge.put(start, null);
        double bestScore = Double.NEGATIVE_INFINITY;
        MazeVertex bestTarget = null;
        int maxDepth = 60;
        int startGhostDistance = ghostDistanceMap.getOrDefault(start, 1000);
        int pelletBaseScore = startGhostDistance <= 6 ? 30 : 18;
        while (!queue.isEmpty()) {
            MazeVertex vertex = queue.remove();
            int steps = stepsMap.get(vertex);
            if (steps > maxDepth) {
                continue;
            }
            int ghostDistance = ghostDistanceMap.getOrDefault(vertex, 1000);
            if (ghostDistance <= 0) {
                for (MazeEdge edge : vertex.outgoingEdges()) {
                    MazeVertex next = edge.head();
                    if (!stepsMap.containsKey(next)) {
                        if (blocked.contains(next) && !next.equals(start)) {
                            continue;
                        }
                        stepsMap.put(next, steps + 1);
                        parentEdge.put(next, edge);
                        queue.add(next);
                    }
                }
                continue;
            }
            GameModel.Item item = model.itemAt(vertex);
            int foodScore = 0;
            if (item == GameModel.Item.DOT) {
                foodScore = 5;
            } else if (item == GameModel.Item.PELLET) {
                foodScore = pelletBaseScore;
            }

            int fleeKillScore = 0;
            if (anyGhostsFleeing && !fleeingGhostVertices.isEmpty()) {
                int vertexI = vertex.loc().i();
                int vertexJ = vertex.loc().j();
                for (MazeVertex fleeVertex : fleeingGhostVertices) {
                    int fleeI = fleeVertex.loc().i();
                    int fleeJ = fleeVertex.loc().j();
                    int manhattanDistance = Math.abs(vertexI - fleeI) + Math.abs(vertexJ - fleeJ);
                    if (manhattanDistance <= 6 && ghostDistance >= 3) {
                        int localScore = 120 - manhattanDistance * 15;
                        if (localScore > fleeKillScore) {
                            fleeKillScore = localScore;
                        }
                    }
                }
            }
            if (foodScore > 0 || fleeKillScore > 0) {
                int cappedGhostDistance = Math.min(ghostDistance, 10);
                double score =
                        foodScore * 10.0 + fleeKillScore * 1.2 + cappedGhostDistance * 2.5 - steps;
                if (score > bestScore) {
                    bestScore = score;
                    bestTarget = vertex;
                }
            }
            for (MazeEdge edge : vertex.outgoingEdges()) {
                MazeVertex next = edge.head();
                if (!stepsMap.containsKey(next)) {
                    if (blocked.contains(next) && !next.equals(start)) {
                        continue;
                    }
                    stepsMap.put(next, steps + 1);
                    parentEdge.put(next, edge);
                    queue.add(next);
                }
            }
        }
        lastParentEdge = parentEdge;
        return bestTarget;
    }

    private MazeEdge safestNeighborEdge(MazeVertex start,
            Map<MazeVertex, Integer> ghostDistanceMap) {
        MazeEdge best = null;
        double bestScore = Double.NEGATIVE_INFINITY;
        for (MazeEdge edge : start.outgoingEdges()) {
            MazeVertex next = edge.head();
            int ghostDistance = ghostDistanceMap.getOrDefault(next, 1000);
            GameModel.Item item = model.itemAt(next);
            int foodBonus = 0;
            if (item == GameModel.Item.DOT) {
                foodBonus = 1;
            } else if (item == GameModel.Item.PELLET) {
                foodBonus = 3;
            }
            double score = ghostDistance * 2.0 + foodBonus;
            if (score > bestScore) {
                bestScore = score;
                best = edge;
            }
        }
        return best;
    }

    private MazeEdge firstEdgeOnPath(MazeVertex start, MazeVertex target,
            Map<MazeVertex, MazeEdge> parentEdge) {
        if (target == null || parentEdge == null) {
            return null;
        }
        MazeVertex current = target;
        MazeEdge edgeOnPath = null;
        while (!current.equals(start)) {
            edgeOnPath = parentEdge.get(current);
            if (edgeOnPath == null) {
                return null;
            }
            current = edgeOnPath.tail();
        }
        return edgeOnPath;
    }
}