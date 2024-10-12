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
For each search tree, play each branch you take all the way to the end, make random moves along the way. Once you reach the end result, propagate back up to update win/loss values. Allows for nodes with higher chance of success to be picked more often.

Split into 4 stages : 
 * Selection - Starts from root node and selects path to take down explored nodes
 * Expansion - Moves one step down to explore an unexplored node.
 * Simulation - Plays out the game from this new point. Returns a score.
 * Backpropagation - Update all previous nodes that led to new node with updated score.

# Machine Learning

## Types of predictions
 * Binary classification - Only 2 possible classes. eg dogs/cats, spam/not spam
 * Regression classification - Trying to give a numerical output. eg. height, cost etc
 * Multiclass classification - Many possible classes. eg dogs/cats/horse etc

## Feature Extraction

Idea of what features should be used in the prediction model.  A feature extractor is a function that takes in an input $x$ and outputs a set of (features name, feature values). For example for an email address hello@gmail.com. Some possible features are (length > 10 : true, contains_@ : true).

## Linear Classification

Basically, multiply feature vector by weight vector and sum up those values to give a score. Classifier function will then look at that score and decided what class it belongs to.

![[Pasted image 20241011203437.png]]

Here, $w^T$ is the weight vector and $X$ is the feature vector. Training our model involves finding $w$ the weight vector such that $f_w(X)$ fits the given data set.
![[Pasted image 20241011203619.png]]
The decision boundary refers to the line separating the different regions.

### Loss function
$Loss(x,y,w)$ quantifies how unhappy you would be if $w$ was used to make a prediction on $x$ when the correct output is $y$. Therefore, we want to find $w$ such that $Loss(x,y,w)$ is minimised.
### Squared Loss
Calculated by subtracting difference between actual value and predicted value and squaring that difference.

## Gradient Descent
Method for finding minimums/maximums of a curve. In machine learning case, can be used to find where $Loss(x,y,w)$ is minimised. Basically we plot $w$ on x-axis and $Loss(x,y,w)$ on y-axis.
![[1_EZiBCBstS5malFC38HGKig.jpg]]
We start with an initial random value and compute the gradient at that point, if the gradient is negative, it means the curve is sloping downwards, therefore we want to move right. If the gradient is positive, it means the curve is sloping upwards, therefore we want to move left. 

#### Learning Rate
Need to make sure that the steps we take are appropriate, if we are very far from 0, should take a larger step, if we are very close to 0, should take a smaller step to avoid overshooting. The step we take is called the **learning rate**.  $$w_{new}=w_{old} - \alpha\frac{dL(w)}{dw}$$ where $\alpha$ is the learning rate.

### Precision / Sensitivity

Precision for a class is the number of true positives divided by the total number of elements labelled as belonging to the positive class. i.e true_positives / (true_postives + false_positives). If there are 8 images selected as being dogs but also 5 of the 8 are actually dogs. Then the precision is 5/8.

The sensitivity (recall) of a class is the fraction of relevant instances that were retrieved. i.e relevant retrieved instances / all relevant instances. If there are 12 images of dogs and only 5 get identified as dogs, the sensitivity is 5/12.


## Look Up
* feature engineering
* g-means clustering

## K-clustering
Choose random points as centre of cluster. Group points to that cluster, slowly centre of k-points to improve clustering. 

Mix of exploring vs exploiting. It periodically searches new strategies while also exploiting the best strategies found so far.




