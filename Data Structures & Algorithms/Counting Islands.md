```
public static int numIslands(char[][] grid) {

        int islandCounter = 0;
		
        for (int row = 0; row < grid.length; row++) {

            for (int column = 0; column < grid[row].length; column++) {

                if (grid[row][column] == '1') {

                    islandCounter++;

                    numIslandsDFS(grid, row, column);

                }
            }
        }
        return islandCounter;
    }

    public void numIslandsDFS(char[][] grid, int row, int column) {

        if (row < 0 || column < 0) return;

        if (row >= grid.length || column >= grid[row].length) return;

        if (grid[row][column] == '0') return;

        grid[row][column] = '0';

        numIslandsDFS(grid, row+1, column);

        numIslandsDFS(grid, row, column+1);

        numIslandsDFS(grid, row-1, column);

        numIslandsDFS(grid, row, column-1);

    }
```

Basic idea of algorithm is you loop through the char array, every time you see a 1, you increment island counter, you then want to remove all 1's that are also connected and change them to a 0. Reason you do this is so you do not repeat counts. Could also maybe do it by using a HashSet of visited points. 