Idea of finding a set of assignments from a given domain to a set of varibales such that they satisfy all given rule constaints.

Given:
1. A set of decision variables
2. For each variable, a domain of potential values that variable can take
3. A set of constraints on those decision variables

Find:
An assignment of values to each variable such that all constraints are met.
![[Screenshot 2025-09-20 at 4.05.27 PM.png]]

### Finding Solutions
A good constraint solver uses a mix of:
1. Search 
2. Deduction

Deduction helps to eliminate values from the domain that if assigned to a given variable, will not satisfyingly  all the constraints. 

Once all rules of deduction have been applied, if each variable still has more than 1 potential assignment, then the solver will begin some sort of backtracking search
## Problem classes vs instances
A problem class describes the family of problems, where each instance of the problem is realted to each other by a common set of parameters. In order to gain an instance of that class, give values for all parameters.

Example: the n-queen problem is the class, where the variables are n, and where on the board each queen is. An example instance of this class would be the 4-queen problem, stating where each of the 4 queens lies on the board.
## Multisets
Just like a normal set, except that the same element can appear more than once. eg. {1,2,3,1}

Issue with multiset is that since elements can appear any number of times, run into the issue where it has an infinite domain. eg if the elements the set can take is 1. The domain of this multiset is {{1}, {1,1}, {1,1,1}}

To combat this issue, usually a bound on the cardinality or the number of times an element appears is bounded
## Relations
Set of tuples that described allowed combination of variables. For example
if $x=\{1,2\}$ and $y=\{4,5\}$ and the relation is x < y. Then the resulting tuples will be {{1,4}, {1,5}, {2,4}, {2,5}}
### Projection
Idea of projecting a relation onto one or more of its arguments. Basically a selection where we only want the variables we are interested in. From the above example, projecting onto $x$ would result in {2,2}, which is then simplified to {2}. Shows us which values of a certain variable are present in the relation.
## Functions
### Total/Partial Function
In a total function, every element of the domain has an image under the function. Basically, every value in the domain is mapped to some value

Opposite is a partial function, where some elements of the domain arent mapped under the function
### Injective Functions
No 2 elements of the codomain point to the same element under the image. Partial Injection is just a partial function that is also injective.
### Surjective Functions
Every element in the codomain is the image of some element in the domain under the function
# Saville Row

## Running 
```
./savillerow -run-solver -solutions-to-stdout test.eprime test.params
```

eprime file defines problem class. params class defines the individual parameters, hence savillerow solves instances of problem classes.

## Variables
Define variables using letting keyword
```
letting number be domain int(1..9)
```

## Define constraints
Use **such that** keyword followed by a comma separated list of constraints

```
such that
	number != 0,
	number % 2 = 0
```
## Matrix
```
letting m: matrix indexed by [int(1..10)] of int(1..10)
```
Above creates a array of 10 values, where each value is an int from 1-10 inclusive

## Variables
To define the variable you want the solver to find values for using the find keyword
```
find age: int(1..10)
such that
	age % 2 = 0
```
The above finds an even number from 1-10 that is even

## Explicit/Occurence Representation

Example: Find a set of n digits that sum to s

For an explicit representation we could find an example solution that is the array of digits: {1,2,3,4}. Note that the exact same solution is also {4,3,2,1}, we don't want the solver to explore both as they are equivalent, so we can break this symmetry by ordering the set in ascending order.

For an occurence representation, we could instead have an array of all 10 digits, and then use flags to represent whether that digit is present in the solution or not.
 ```
 [0,1,0,1,1,1,0,0,0,1,0]
 ```
For the occurence representation, it does not introduce equivalent classes. 

each stacker says which array it is moving from and which it is moving to

if a stacker has no stacks it can interact with, then make the move value -1

for every move, should not be the case that all the stackers are not making a move, 
unless arrays are already sorted
## Lexicographical Ordering

In order to break symmetry, add some sort of lexicographical ordering on both variables and solutions
## Branching 

Not necessary to assign all variables in order to get a solution. Variables we branch over are the ones that must be assigned values
## Implied Constraints

Logical consequences that can be derived from existing constraints but the solver is not smart enough to deduce them.

Therefore, we manually add them. Constraints that are implied but dont reduce search space are called redundant constraints (which are bad)

## Search

Systematic search results in a guarantee that is a solution exists, it will be found, and if there is no solution, then the search space will be exhausted and the search will report that no solution exists. 

Will use a backtracking approach, where a variable is assigned a value from its domain and a solution is attempted to be found using this value. If this value results in no solution being found, we backtrack and try a different value, continuing this until the domain has been exhausted, or a solution is found. 

### d-way branching

Common way of branching. Each branch under a parent node represents assignment of one of d domain values from the domain of a variable. For example if the domain of x is {1,2,3}, then 
![[Pasted image 20251101221404.png]]
### Binary branching

Create 2 branches, one where x = v, and one where x is not equal to v
![[Pasted image 20251101221455.png]]
Most modern constraint solvers use binary branching
### Generate and Test

Very simple method for solving a CSP, however, very inefficient. Works by generating a complete assignment of the problem, and then testing to see if it satisfies all the constraints. 

For example, if we were trying to solve sudoku, we would generate a complete board and then test. 
### Backtracking

Incrementally extends partial solutions, every time an assignment is made, we check to see if a constraint has been violated. If it has, we continue to assign the next variable a value, otherwise, we either try a different value or backtrack.

### Branch and Bound

Augments backtrack where we can track what the current best solution is. Therefore, if our current search path will be guaranteed to be worse than the current best solution, we can terminate exploring this branch
## Binary Constraint Graphs

Method of modelling a CSP, where nodes represent variables, and constraints are represented by edges
![[Pasted image 20251101222931.png]]
Can have a 'hyper-edge' if many variables all are involved in the same constraint. 

A binary constraint CSP is a model where every constraint involves at most 2 variables. A non binary constraint CSP is a model where a constraint can have more than 2 variables. 

Often, non-binary CSP are converted to binary CSP, as algorithms are better suited to working on them.  

Usually involves introducing auxillary variables. For example, for the constraint $X_1+X_2+X_3=10$, we can introduce an auxillary variable A for partial sums. Then we can create binary constraints like $$X_1+X_2=A,\text{   } A+X_3=10$$
Usually involves increasing variable count

### Dual Representation

another way of converting non-binary CSP to a binary CSP. Every non-binary constraint becomes a new variable. The domain of those variables are the tuple of values that satisfy the original constraint. 

if constraint A involves variables x,y,z, and constraint B involves variables x,t,u, then we can introduce a new varaible c representing constraint A, and a varaible d representing constraint B. So if A allows {{1,2,3}, {2,3,4}, {3,4,5}}, then the domain of c will be that same set. 

Since constraint A and constraint B both invovle variable x, we need to add a constraint between c and d to ensure that consistency is maintained. Example, here the constrain would be that the value of x must be the same. 

### Constraint Propogation

Pruning values from domains to reduce search space. 
![[Pasted image 20251101225449.png]]

If we have this constraint graph and we remove a value from $X_1$, then we might also be able to remove a value from $X_2$ since there is a constraint between the 2. This could potentially propogate all the way to $X_5$

#### Local Node Consistency

Local node consistency refers to the fact that given a unary constraint, every value in the domain satisfies the constraint when the varaible is assigned that value. 

For example, if given the constraint $X_1\lt 5$ , when $D_1=\{3,4,5,6\}$ , this node is inconsistent. But when $D_1=\{3,4\}$ then it is.

Called local because we are only thinking of this variable in the context of a single constraint, when it might be involved in many others. 

#### Global Node Consistency

Refers to the fact that every variable in the CSP satisfies ALL unary constraints it participates in. This can be enfoced by encforcing node consistency for each unary constraint once. 

#### Arc Consistency

Constraint graph can have each constraitn be a directional edge, instead of undirected. Invovles splitting a undirected edge into 2 seperate directed edges. 
![[Pasted image 20251101230231.png]]

For example, if we have the binary constraint $X\neq Y$ , the arcs are $X\rightarrow Y$ and $Y\rightarrow X$. 

A variable X is arc consistent with respect to variable Y if for every value in X's domain, there exists a value in Y's domain such that the constraint is satisfied.  
#### Global Arc Consistency

Hodls if local arcs are all consistent. However, this cannot be enforced simply by enfocing local arc consistency for all arcs once. 
![[Pasted image 20251101230627.png]]

For example, here, when we revise the arc between $X_1$ and $X_3$, the domains remain as {5,6,7} for both. However, when we revise the arc between $X_1$ and $X_2$, the domain of $X_1$ now becomes {6,7}. 

Now, if we don't revise the arc between $X_1$ and $X_3$  again, then there will clearly be an inconsistency 