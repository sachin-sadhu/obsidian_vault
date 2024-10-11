## Module Info
Artificial intelligence: A modern approach S.Russell
# Search
Finding a goal solutions amongst a large range of solutions given an instance of a problem. We need to define 
* State space
* Actions
* Initial State
* Goal
* Successor/Transition model
* Cost
## Search Trees
Start from the initial state and progressively apply actions to transition to other states until the goal is reached. As we apply actions, we discard states that are redundant.
* Root represents the initial state
* Edges represent a single choice
* Nodes represent search states
* Leaf nodes are solutions/failures
## Depth Bounded DFS 
DFS but we introduce a limit on the depth we are allowed to go to.

## Informed Search
Uses a function f(n) to choose the next step. The function includes a heuristic h(n). A heuristic is the estimated cost of the path from the state at node n to the goal state. A good heuristic should be a lower bound on the cost to reach the goal.

### A* Search algorithm
Search algorithm used to find the least cost path from start node to goal node. Uses a function f(n) to pick which node to explore next. $f(n)=g(n)+h(n)$ , where $g(n)$ is the cost to get from the start node to n, and $h(n)$ is a heuristic that estimates the cost from n to the goal node.

Some heuristics that can be used for A* are euclidean distance/manhattan distance etc.

## Local Search
Solves the problem of exhaustive search methods they can be very resource hungry, continue working on a part of the search space even if super unpromising. Local search fixes this problem as it only searches parts of the search space that seem promising.

### Hill Climbing
Process to find the global maximum/minimum, basically starting at a given node, we check to see if it's neighbours are any higher/lower, if they are we move to that neighbour, else we assume we have reached the maximum. 

Problem that arises is that we may get stuck on a local maximum. Solutions to solve this introduce an element of randomness such as random-restart hill climbing, stochastic hill climbing and first-choice hill climbing.

### Monte Carlo Game Search
For each search tree, play each branch you take all the way to the end, make random moves along the way. Once you reach the end result, propogate back up to update win/loss values. Allows for nodes with higher chance of success to be picked more often.

Split into 4 stages : 
 * Selection - Starts from root node and selects path to take down explored nodes
 * Expansion - Moves one step down to explore an unexplored node.
 * Simulation - Plays out the game from this new point. Returns a score.
 * Backpropogation - Update all previous nodes that led to new node with updated score.

## Look Up
* feature engineering
* g-means clustering

## K-clustering
Choose random points as centre of cluster. Group points to that cluster, slowly centre of k-points to improve clustering. 
 
 
 

Mix of exploring vs exploiting. It periodically searches new strategies while also exploiting the best strategies found so far.




