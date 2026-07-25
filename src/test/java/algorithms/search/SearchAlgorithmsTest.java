package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchAlgorithmsTest {

    @Test
    void allSearchAlgorithmsFindAPathThroughTheSameDomain() {
        Maze maze = new Maze(new int[][]{
                {0, 0, 1, 0, 0},
                {1, 0, 1, 0, 1},
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}
        }, new Position(0, 0), new Position(4, 4));
        SearchableMaze domain = new SearchableMaze(maze);

        List<ISearchingAlgorithm> algorithms = List.of(
                new BreadthFirstSearch(),
                new DepthFirstSearch(),
                new BestFirstSearch()
        );

        for (ISearchingAlgorithm algorithm : algorithms) {
            Solution solution = algorithm.solve(domain);
            List<AState> path = solution.getSolutionPath();

            assertFalse(path.isEmpty(), algorithm.getName() + " should find a path");
            assertEquals(domain.getStartState(), path.get(0));
            assertEquals(domain.getGoalState(), path.get(path.size() - 1));
            assertTrue(algorithm.getNumberOfNodesEvaluated() > 0);
        }
    }

    @Test
    void diagonalMovementDoesNotCutThroughBlockedCorners() {
        Maze maze = new Maze(new int[][]{
                {0, 1},
                {1, 0}
        }, new Position(0, 0), new Position(1, 1));
        SearchableMaze domain = new SearchableMaze(maze);

        assertTrue(domain.getPossibleStates(domain.getStartState()).isEmpty());
        assertTrue(new BestFirstSearch().solve(domain).getSolutionPath().isEmpty());
    }
}
