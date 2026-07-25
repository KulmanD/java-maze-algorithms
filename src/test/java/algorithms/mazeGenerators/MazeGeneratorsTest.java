package algorithms.mazeGenerators;

import algorithms.search.BreadthFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MazeGeneratorsTest {

    @Test
    void generatorsCreateSolvableMazesWithOpenEndpoints() {
        List<IMazeGenerator> generators = List.of(
                new EmptyMazeGenerator(),
                new SimpleMazeGenerator(),
                new MyMazeGenerator()
        );

        for (IMazeGenerator generator : generators) {
            Maze maze = generator.generate(21, 31);
            Position start = maze.getStartPosition();
            Position goal = maze.getGoalPosition();

            assertAll(generator.getClass().getSimpleName(),
                    () -> assertEquals(21, maze.getRows()),
                    () -> assertEquals(31, maze.getColumns()),
                    () -> assertNotNull(start),
                    () -> assertNotNull(goal),
                    () -> assertNotEquals(start, goal),
                    () -> assertTrue(isInside(maze, start)),
                    () -> assertTrue(isInside(maze, goal)),
                    () -> assertEquals(0, maze.getCell(start.getRowIndex(), start.getColumnIndex())),
                    () -> assertEquals(0, maze.getCell(goal.getRowIndex(), goal.getColumnIndex()))
            );

            Solution solution = new BreadthFirstSearch().solve(new SearchableMaze(maze));
            assertFalse(solution.getSolutionPath().isEmpty(),
                    generator.getClass().getSimpleName() + " should create a solvable maze");
        }
    }

    private boolean isInside(Maze maze, Position position) {
        return position.getRowIndex() >= 0
                && position.getRowIndex() < maze.getRows()
                && position.getColumnIndex() >= 0
                && position.getColumnIndex() < maze.getColumns();
    }
}
