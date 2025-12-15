# DFS Algorithm for finding if path between 2 points exists

```
  private boolean hasPathDFSFinder(int src, int dst, Set<Integer> visitedNodes) {
        if (src == dst) return true; // Reached dst node.
        if (visitedNodes.contains(src)) return false; // On a visited node.
        visitedNodes.add(src);

        for (int neighbour : this.adjList.get(src)) {
            if (hasPathDFSFinder(neighbour, dst, visitedNodes)) {
                return true;
            }
        }
        visitedNodes.remove(src);
        return false;
    }
```