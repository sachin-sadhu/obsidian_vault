## Probability Theory

Random variables: value of the variable is random
![[Pasted image 20260201205521.png]]

- Discrete Random Variables: Values that the variable can take are discrete. eg raining or not, hot or cold
- Continuous Random Variables: Values that the variable can take are continuous, eg weight of a dog, temperature

### Bernoulli Random Variable
The variable X is binary.

### Categorical Random Variable (Multinoulli)
The variable X can take more than 2 values. 

### Continuous Random Variables - Gaussian 

$P(x|\mu, \sigma^2)$, where $\mu$  is the mean and $\sigma^2$ is the variance of the data.
![[Pasted image 20260201205929.png]]

## Expectation

Given a random variable X and its probability distribution p(X), expectation allows us to summarise the distribution with a single typical number. $$E[X]=\sum_xx\cdot p(x)$$ 
For example, for a fair coin toss where heads is 1 and tails is 0, the expected value is 1 * 0.5 + 0 * 0.5 = 0.5. Which makes sense. 

- Expectation of a constant is constant itself. $E[c]=c$ 
- Expectation is a linear operator
	- $E[cX]=cE[X]$
	- $E[aX_1+bX_2]=aE[X_1]+bE[X_2]$
#### Mode

Mode is the most likely outcome of a random variable. Basically, find the value with the higest probability of happening. 

### Variance
![[Pasted image 20260201210544.png]]
### Surprise

Common to want to quantise the idea of being 'surprised' at an event occuring given a probability function for a random variable. 

Can be denoted as S(event). We want this function to have the following properties
- If P(event)=1, S(event)=0. If something is definitley going to happen, then zero surprise when it happens
- If P(event)$\rightarrow 0$   , S(event)$\rightarrow \inf$, something extremely rare happens, infinite surprise

Good candidate for this function is $-ln(p)$  or $ln\frac{1}{p}$ 
![[Pasted image 20260201211719.png]]

### Entropy 

Allows us to describe the average surprise of a random variable X with p(X)
- event $X=x_i$ happens with probability $p(x_i)$
- Therefore its surprise is $-ln p(x_i)$  

Entropy is average surpriseness of random variable
![[Pasted image 20260201211926.png]]

## Joint distribution

Calculating probability of $P(X_1=x_1,X_2=x_2,...,X_n=x_n)$, probability of it must be at least 0

### Sum Rule

$P(X_1)$ is called the marginal probability distribution. Given a join distribution, the sum rule allows us to find the marginal probability distribution 
![[Pasted image 20260201212217.png]]
Basically just sum out the nuisance variables.

### Conditional Probability
Ratio betwen joint and marginal probabilities. Probability of event a happening given b 
![[Pasted image 20260201212303.png]]

### Product / Chain Rule

$$P(X,Y)=P(X)P(Y|X)$$
If the 2 variables are independant, then its just the product of their maginal probabilities. 

Also order does not matter, $P(X,Y)=P(Y)P(X|Y)$ 

### Conditional Independance

$P(x|z,y)P(x|z)$ , basically means that knowining z, y no longer influences x.

### Maximum Likelihood Estimation

Given some observed data D, we want to decide which model H has the best chance of producing that observed data. 
$$P(D|h)$$
- data D is observed
- hypothesis h is unknown

$$h_{MLE}\leftarrow argmaxP(D|h)$$ 
Basically, want to find the variable h that maximises P(D|h)

![[Screenshot 2026-02-08 at 1.32.03 PM.png]]
### Log-likelihood

Common practice to maximise log-likelihood instead. ![[Screenshot 2026-02-08 at 1.45.23 PM.png]]
Take log-likelihood as log turns products into sums. For example if we have 100 data points, each with probability of 0.01, then $0.01^{100}$ turns into a very small number. And on computers, very small numbers will underflow. 

However, when you take ln, it becomes addition instead of multiplication.

### Kullback-Leibler dirvergence

Allows us to measure how different 2 probability distributions are.
![[Screenshot 2026-02-08 at 1.56.13 PM.png]]
When the 2 distributions are the same, then KL(q||p) should be equal to 0.

KL is not symmetric. $KL(P||Q)\neq KL(Q||P)$   
- $p(x)$ is the distribution we are trying to mimic
- $q(x)$ is the learnable approximation distribution 

KL(P||Q) is always non-negative