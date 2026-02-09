## Vector Inner Product
![[Pasted image 20260201203219.png]]
### Geometric Intuition

Inner product is defined as $<u,v>=||u||||v||cos (\theta)$.
- Large positive: vectors point in similar directions
- Zero: vectors are orthogonal
- Negative: vectors point in opposite directions

### Projection

#### Vector 

$$\text{proj}_u(v)=\frac{v\cdot u}{u\cdot u}u$$
- $v\cdot u$ measures how aligned vectors are
- dividing by denominator normalises for length of u
- multiplying by u gives a vector in u's direction

Basically, take a vector u and finds the parts of it that point in the direction of the other vector v. 

### Linear Transformations

Some combination of scale, rotate shift. Basically origin stays at origin, parallel lines remain parallel. Must satisfy both of the following conditions $$T(x+y)=T(x)+T(y)$$ $$T(\alpha x)=\alpha T(x)$$ 
### Linear Regression

Supervised learning with continous targets. For example, might give a list of features to make a prediction on. 

Using:
- rooms
- area
- year build

A linear regression model might try to predict how much that house will cost. 

In linear regression, the prediction function is assumed to be linear.  

### Multiple linear regression

Making a prediction based on multiple inputs
![[Pasted image 20260209222708.png]]
Where each of the weights $w_0, w_1 ... w_{14}$ are weights that the model can adjust as it learns.

The above is messy to write, neater form is 
![[Pasted image 20260209222825.png]]

### Learning

Models way of evaluating how good its current function is (list of parameters) at predicitng the dataset. Can use labelled data to evaluate this. 

#### Sum of Square Errors (SSE)

One good way of evaluating of good our model is is defining the loss function as the Sum of Squared Errors. 

For each data point:
- compute the error. error = $y_{true} - y_{pred}$ 
- square it (so negatives don't cancel out positives)
- sum all the squared errors
![[Pasted image 20260209223149.png]]

In order to optimise this loss function, we aim to minimise the loss. 





