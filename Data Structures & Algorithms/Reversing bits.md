```
 public static int countPathsDFS(int[][] grid, Point point, Set<Point> visitedPoints) {

        if (point.row == grid.length - 1 && point.col == grid[grid.length-1].length) return 1;

        if (point.row < 0 || point.row >= grid.length || point.col < 0 || point.col >= grid[point.row].length) return 0;

        if (grid[point.row][point.col] == 1) return 0;

        if (point.isVisited(visitedPoints)) return 0;

        visitedPoints.add(point);

        int counter = 0;

        // go up 
        counter += countPathsDFS(grid, new Point(point.row-1, point.col), visitedPoints);
        // go down
        counter += countPathsDFS(grid, new Point(point.row+1, point.col), visitedPoints);
        //go left
        counter += countPathsDFS(grid, new Point(point.row, point.col-1), visitedPoints);
        //go right
        counter += countPathsDFS(grid, new Point(point.row, point.col+1), visitedPoints);

        visitedPoints.remove(point);

        return counter;
    }
```