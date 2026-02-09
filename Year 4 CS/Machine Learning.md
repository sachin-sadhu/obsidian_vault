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
### Functions

### Linear functions
$$f(x)=b^Tx+c$$
### Quadratic Functions
$$f(x)=x^TAx+b^Tx+c$$
Equivalent to $f(x)=x\cdot a\cdot x + bx + c$ ![[Screenshot 2026-02-08 at 2.52.59 PM.png]]
![[Screenshot 2026-02-08 at 3.03.07 PM.png]]
Negative definite is the opposite. $x^TAx\lt 0 \text{ for all x} \in R^n$      
![[Screenshot 2026-02-08 at 3.05.34 PM.png]]

### Partial Derivatives

Basically, when differentiating with respect to a certain variable, treat all other variables as constants. Eg.
$$f(x)=30+ax_1+bx_2^3$$
$$\frac{\partial f}{\partial x_1}=a$$
$$\frac{\partial f}{\partial x_2}=3bx_2^2$$

$$f(x)=x_1^2\cdot x_2$$  
$$\frac{\partial f}{\partial x_1}=2x_2x_1$$
### Gradient Function

Takes in a position in space, and returns the direction of steepest increase at that position in space. 

#### Example
![[Screenshot 2026-02-08 at 3.38.46 PM.png]]
### Hessian Matrix
Matrix of all second order partial derivatives 
![[Screenshot 2026-02-08 at 4.00.56 PM.png]]
The diagonal entries are pure second order differentials, measure how the function changes purely with respect to a single axis. 

Offdiagonal entries are mixed partial derivatives, measures how the slope in the $x_1$ direction changes as you move in the $x_2$ direction. 

If $∂²f/∂x₁∂x₂ > 0: As x₂ increases, the slope in the x₁ direction gets steeper. The two variables have a positive interaction.

If ∂²f/∂x₁∂x₂ = 0: The variables are independent—changing x₂ doesn't affect how f changes with x₁.

### Notes
- matrix not invertible if fewer data points than feature vectors.
- also data points need to be linearly dependant of each other.

WHY ARE THEY ASKING ME THAT!!!