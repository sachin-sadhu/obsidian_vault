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

## Linear Regression

Trying to predict a certain value $y$ based on a certain number of inputs. eg. Relationship between weight and height. $y$ can depend on more than 1 input variable. Since it is linear, means relationship is usually a straight line. Linear regressions usually uses mean square error as it's loss function
## Feature Extraction

Idea of what features should be used in the prediction model.  A feature extractor is a function that takes in an input $x$ and outputs a set of (features name, feature values). For example for an email address hello@gmail.com. Some possible features are (length > 10 : true, contains_@ : true).

## Linear Classification

Basically, multiply feature vector by weight vector and sum up those values to give a score. Classifier function will then look at that score and decided what class it belongs to.

![[Pasted image 20241011203437.png]]

Here, $w^T$ is the weight vector and $X$ is the feature vector. Training our model involves finding $w$ the weight vector such that $f_w(X)$ fits the given data set.
![[Pasted image 20241011203619.png]]
The decision boundary refers to the line separating the different regions.

### Logistic Regression

Instead of outputting different numbers to give a value of what class it belongs to. We might use a logistic function which will then output the probability of $x$ belonging to that class. Logistic function : $$g(z)=\frac{1}{1+e^{-z}}$$
$g$ is is now a soft threshold function that is
* continuous and differentiable
* always in the range $[0,1]$ 

Our model is now : $$f_w(x)=g(w^Tx)=\frac{1}{1+e^{-w^Tx}}$$
So $f_w(x)=0.7$ might mean x has a 70% chance of belonging to class 1. $f_w(x)=0.3$ might mean x has a 30% chance of belonging to class 0.

Logistic regression had an advantage over linear classification in that values close to the boundary will be reflected in their P(X) values. 
## Loss function
View this video for recap : https://www.youtube.com/watch?v=SmZmBKc7Lrs

$Loss(x,y,w)$ quantifies how unhappy you would be if $w$ was used to make a prediction on $x$ when the correct output is $y$. Therefore, we want to find $w$ such that $Loss(x,y,w)$ is minimised.
### Squared Loss
Calculated by subtracting difference between actual value and predicted value and squaring that difference.
### Cross-Entropy Loss Function
View this video for recap : https://www.youtube.com/watch?v=KHVR587oW8I

Intuition is that we have an expectation for the probability distribution of a good model, and if view that the probability distribution is much different to what we expect, we will get a high entropy value. Measures surprise between the probability distribution of 2 different models.

Different formula for quantifying how good/bad or prediction model is. Formula is given as $$L(w)=-ylog_2f_w(x)-(1-y)log_2(1-f_w(x))$$
So if y = 1, meaning the point is actually belonging to class 1, and we get $f_w(x)=0.7$, we get $L(w)=0.51$. If y = 1, and $f_w(x)=0.4$, we get $L(w)=1.32$. Better predictions result in smaller cross-entropy loss values.
![[Pasted image 20241013162513.png]]
As you can see, cross-entropy loss function punishes bad predictions much heavier, therefore we are more likely to get a good solution quicker. 
## Gradient Descent
Method for finding minimums/maximums of a curve. In machine learning case, can be used to find where $Loss(x,y,w)$ is minimised. Basically we plot $w$ on x-axis and $Loss(x,y,w)$ on y-axis.
![[1_EZiBCBstS5malFC38HGKig.jpg]]
We start with an initial random value and compute the gradient at that point, if the gradient is negative, it means the curve is sloping downwards, therefore we want to move right. If the gradient is positive, it means the curve is sloping upwards, therefore we want to move left. 

### Intuition
Image we have a plot of points and we want to draw a curve to classify these points. Imagine the curve is a quintic function of degree $x^5$. This means the resulting curve is a linear combination of the terms $1,x,x^2,x^3,x^4,x^5$. Therefore curve can be expressed as $$f(x)=\alpha+\alpha_1x+\alpha_2x^2+\alpha_3x^3+\alpha_4x^4+\alpha_5x^5$$
The problem then becomes trying to find those parameters $[\alpha,\alpha_1,\alpha_2,\alpha_3,\alpha_4,\alpha_5]$ that results in the Loss function being minimised.

## Accuracy

The proportion of correctly identified elements. $$\frac{TP+TN}{TP+TN+FP+FN}$$
## Precision

Of elements identified as positive, what elements were true positives. $$\frac{TP}{TP+FP}$$
## Recall

Of all elements that are actually positive, what elements were correctly identified as positive. $$\frac{TP}{TP+FN}$$
## Neural Networks

### Intuition
Thing of each neuron as a number. Have each layer do some sort of abstraction. For example for recognising hand-written digits, have the first layer be the inputs of the gray-scale of each pixel. Have the next layer recognise some sort of patterns of loops/lines. Maybe second to last layer can recognise having a big circle at the top followed by a straight line. Basically, we need to learn what patterns of nodes lighting up at the previous layer should cause which nodes to light up at the next layer. 
### Layers
Each node is connected to every other node in the next layer. This connection has a **weight** which is just a real number. The value at each node will be calculated by a linear combination of all the previous layer's node values multiplied by the weights. Then this number will be fed into some function, for example the sigmoid function which will output a value between $[0,1]$.

### Backpropagation

Technique for adjusting weights/biases to reduce cost function. Basically, we have 2 things we can adjust, the weights of a connection and the biases. For example, image we have a image 2, but the NN is only predicting a 0.2 probability that the image is a 2.  We obviously will want to increase this probability its being given by a lot. Similarly, if it gives a 0.1 probability that the image is a 8, we also will want to decrease that probability its being assigned but not at such a great rate.

We can increase the probability that the 2 node is being assigned by going to the previous layer, obviously we want to increase weights of connections from bright neurons more, as they overall product is greater.  Or we can also increase/decrease the value at each node, for example, for nodes with a positive weight, we would want to increase them, whilst for nodes with a negative weight, we would want to decrease them. However, we can not directly change the value at each node, only the weights/biases that connect to them.

We should not only consider what the 2 node wants, we only need to consider what should happen to every other digit node, therefore we sum up the desires of each node and work from there.
### SoftMax Function
Function for generating probability distribution of all the output nodes. Sum of probability of all output nodes should equal to 1.

## Stochastic Gradient Descent (SGD)

Gradient's descent problem is that it requires us to calculate all the elements partial derivatives for all weights across all training examples. This takes a long time. 

SGD splits training samples into batches. Calculates the weight gradient vector on the first batch and the updates all the weights in W using the calculated gradients. Repeat for all batches. Shuffles the whole training set again. Idea is that while it might be a bit less accurate for each mini-batch, we can do a lot more descent steps over the same period of time. 

An epoch refers to 1 pass of the entire dataset.
![[Pasted image 20241103210010.png]]
![[Pasted image 20241103210023.png]]

## Testing Algorithms

Overfitting : Model that fits training data very well, but performs poorly on test set.
Underfitting : Model that poorly fits training data. 
![[Pasted image 20241109160423.png]]
If 2 models have same predictive power, we pick the simpler one, as it is more likely to generalise better to unseen data. 

### K-fold cross validation

Splitting data into k different segments. Use 1 of those segments as testing data and $k-1$ segments as training data. Record how well algorithm performs. Repeat this using every individual segment as the testing data. Pick model with the best overall performance. Retrain the selected model on the entire training set and then report model's performance on test set. 
![[Pasted image 20241109160644.png]]

## Regularisation

Method for preventing models from overfitting to training data. Models with many weights set to 0 will be preferred. 

L1 regularization leads to weight sparsity. This comes from the shape
of the L1 loss. Since even small weights are penalised the same amount as large
weights, more weight values will tend closer to 0.
L2 on the other hand penalizes smaller weights less, which leads to smaller weights
but does not ensure sparsity.

### L1-regularisation
Do this by adding $\lambda\sum|w|$ to the loss function. Therefore the formula for the loss function if we are using least squared is $$(y-y_1)^2+\lambda\sum|w|$$ Therefore models that fit the training data perfectly but have a lot of high weight values might end up having a higher cost function that models that fit training data less well but have fewer high weight values.  
### L2-regularisation
Very similar to L1, except now we add $\lambda\sum w^2$ to the loss function . Therefore the formula for the loss function if we are using least squared is $$(y-y_1)^2+\lambda\sum w^2$$ Now models with many weights close to 0 will be preferred.

## Early stopping

Used to stop training the model when it start to show signs of overfitting
![[Pasted image 20241109163152.png]]
If nPatience is too low, then we might stop training the model at points when we can still minimise the loss function further. However, if nPatience is too high, increases time taken to train models with no benefit. 
### Differences

L1 is useful when a model might have many parameters that are completely useless to the prediction as L1 prefers models with weights set to 0. Such as using height to predict how fast someone types. 

L2 is useful when a model might have many parameters that are useful to the prediction as L2 prefers models with weights closely set to 0.

## Decision Trees

Takes an input vector of attributes, outputs a value. Can be a continuous number or a binary value. The decision is reached by performing a sequence of tests on the features. 

### Entropy

Expected amount of surprise. Surprise is how surprised you would be to observe a certain outcome of a random variable. For example, if a bias coin had a 99% chance of turning heads, you would be super surprised to roll a tails. This surprised is quantified as $$\frac{1}{log(P(x))}$$
The total entropy of a system is the sum of all the probabilities of each outcome time the respective surprised per outcome.

A fair coin has an expected entropy of 1. A coin that always turns heads has an expected entropy of 0. 

When choosing which attributes to test in our decision trees, we want to choose attributes that lead to the greatest decrease in entropy. 
![[Pasted image 20241118220237.png]]
## Probability

### Conditional Probability

If A and B are 2 random variables, the conditional probability of A happening given B has happened is represented by the notation $$P(A|B)$$
$$P(A|B) = \frac{P(A\cap B)}{P(B)}$$ 
![[Pasted image 20241116225113.png]]

If $P(A|B)=P(A)$, then B happening does not affect the probability of A happening. And therefore, they are independent variables. 

If A and B are dependant variables, the formula for calculating the probability that they both occur is as follows $$P(A,B)=P(B)\times P(A|B)$$
This makes sense as it is saying what is the probability of B happening, times the probability that A happens given B has happened. 

### Conditional Independence

2 random variables X & Y are dependant on each other. However, when we introduce a third variable Z, we gain no extra information about X by observing Y. This means X & Y are now independent of each other. 

Example. Let A be the ethnicity of a brother, and B be the ethnicity of a sister. Clearly, knowing A affect knowing B, therefore A and B are dependant on each other. However, if we introduce a third variable C, where C is the ethnicity of the parents. Now that we know C, knowing B will give no extra information on A. And therefore, A and B are independent on the condition of C. 


### Bayes' Rule

Concept of how you should update your initial beliefs given some evidence. For example, if the change someone has cancer is 0.1%, and you receive a positive cancer test where the test is 99% accurate. The probability of you having cancer is not now 99%, it should just update your belief of the probability that you have cancer given this new evidence. 

$$P(H|D)=\frac{P(H)P(D|H)}{P(D)}$$
Here, H is called the $hypothesis$ and is your initial belief. D is the observation or evidence you recieve 

## Look Up

* feature engineering
* g-means clustering

## Questions

with gradient descent, how do you know you don't get stuck at local minimum instead of global minimum

## K-clustering
Choose random points as centre of cluster. Group points to that cluster, slowly centre of k-points to improve clustering. 

Mix of exploring vs exploiting. It periodically searches new strategies while also exploiting the best strategies found so far.

does every hiddeen layer use same activation function or is it different for each
why cant we use cross entropy for regressions




