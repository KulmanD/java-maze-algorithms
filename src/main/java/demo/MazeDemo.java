package demo;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.util.List;

/**
 * Small command-line showcase for maze generation and search.
 */
public final class MazeDemo {
    private static final int DEFAULT_ROWS = 15;
    private static final int DEFAULT_COLUMNS = 31;

    private MazeDemo() {
    }

    public static void main(String[] args) {
        int rows = parseDimension(args, 0, DEFAULT_ROWS, "rows");
        int columns = parseDimension(args, 1, DEFAULT_COLUMNS, "columns");

        IMazeGenerator generator = new MyMazeGenerator();
        Maze maze = generator.generate(rows, columns);
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        System.out.printf("Generated %dx%d maze using %s%n%n",
                rows, columns, generator.getClass().getSimpleName());
        maze.print();
        System.out.println();

        List<ISearchingAlgorithm> algorithms = List.of(
                new BreadthFirstSearch(),
                new DepthFirstSearch(),
                new BestFirstSearch()
        );

        for (ISearchingAlgorithm algorithm : algorithms) {
            Solution solution = algorithm.solve(searchableMaze);
            System.out.printf("%-20s nodes evaluated: %-6d path states: %d%n",
                    algorithm.getName(),
                    algorithm.getNumberOfNodesEvaluated(),
                    solution.getSolutionPath().size());
        }
    }

    private static int parseDimension(String[] args, int index, int fallback, String label) {
        if (args.length <= index) {
            return fallback;
        }

        int value;
        try {
            value = Integer.parseInt(args[index]);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(label + " must be an integer", exception);
        }

        if (value < 3) {
            throw new IllegalArgumentException(label + " must be at least 3");
        }
        return value;
    }
}
