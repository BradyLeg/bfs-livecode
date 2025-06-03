import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Search {
    /**
     * Finds the location of the nearest reachable cheese from the rat's position.
     * Returns a 2-element int array: [row, col] of the closest 'c'. If there are
     * multiple
     * cheeses that are tied for the same shortest distance to reach, return
     * any one of them.
     * 
     * 'R' - the rat's starting position (exactly one)
     * 'o' - open space the rat can walk on
     * 'w' - wall the rat cannot pass through
     * 'c' - cheese that the rat wants to reach
     * 
     * If no rat is found, throws EscapedRatException.
     * If more than one rat is found, throws CrowdedMazeException.
     * If no cheese is reachable, throws HungryRatException
     *
     * oooocwco
     * woowwcwo
     * ooooRwoo
     * oowwwooo
     * oooocooo
     *
     * The method will return [0,4] as the nearest cheese.
     *
     * @param maze 2D char array representing the maze
     * @return int[] location of the closest cheese in row, column format
     * @throws EscapedRatException  if there is no rat in the maze
     * @throws CrowdedMazeException if there is more than one rat in the maze
     * @throws HungryRatException   if there is no reachable cheese
     */
    public static int[] nearestCheese(char[][] maze)
            throws EscapedRatException, CrowdedMazeException, HungryRatException {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        int[] start = ratLocation(maze);

        // queue = []
        Queue<int[]> queue = new LinkedList<>();
        // queue.add(start)
        queue.add(start);

        // while queue not empty
        while (!queue.isEmpty()) {
            // current = queue.poll
            int[] current = queue.poll();
            int curR = current[0];
            int curC = current[1];
            // if visited: continue
            if (!visited[curR][curC]) {
                // mark visited
                visited[curR][curC] = true;
                // if current is cheese: return current
                if (maze[curR][curC] == 'c') {
                    return current;
                }
                // add neighbors to queue
                queue.addAll(getNeighbors(maze, current));
            }
        }

        // throw hungee
        throw new HungryRatException();
    }

    public static List<int[]> getNeighbors(char[][] maze, int[] current) {
        int curR = current[0];
        int curC = current[1];

        // UP [-1, 0]
        // DOWN [+1, 0]
        // LEFT [0, -1]
        // RIGHT [0, +1]
        int[][] directions = {
                { -1, 0 }, // UP
                { 1, 0 }, // DOWN
                { 0, -1 }, // LEFT
                { 0, 1 } // RIGHT
        };

        List<int[]> possibleMoves = new ArrayList<>();

        for (int[] direction : directions) {
            int changeR = direction[0];
            int changeC = direction[1];

            int newR = curR + changeR;
            int newC = curC + changeC;

            if (newR >= 0 && newR < maze.length &&
                    newC >= 0 && newC < maze[newR].length &&
                    maze[newR][newC] != 'w') {
                // valid move!
                int[] validMove = { newR, newC };
                possibleMoves.add(validMove);
            }
        }

        return possibleMoves;
    }

    public static int[] ratLocation(char[][] maze) throws EscapedRatException, CrowdedMazeException {
        int[] location = null;

        // Searching

        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[r].length; c++) {
                if (maze[r][c] == 'R') {
                    if (location != null) {
                        throw new CrowdedMazeException();
                    }
                    location = new int[] { r, c };
                }
            }
        }

        if (location == null) {
            throw new EscapedRatException();
        }
        return location;
    }
}