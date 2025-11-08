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

Therefore, we manually add them. Constraints that are implied but dont reduce search space are called redundant constraints (which are bad)sign 

## Forward Checking (FC)

After every assignment to a variable $x_i$, revise arc that point to $x_i$ once. Enforces local consistency

1. Assign a value to $X_i$, look at all unassigned variables $X_j$ that are connected to $X_i$ by a constraint. 
2. Eliminate values from $X_j$ domain that violate the constraint with $X_i$ assignment. 
3. If any unassignment variables are left with an empty domain, immediately backtrack and pick a different value for $X_i$ .

In simple words, after I make this assingment, does an unassigned variables no longer have any valid values. If yes, don't bother going down this path

FC does NOT need to check backwards. As when a variable is assigned, all previously assigned variables would already have pruned the current varaibles domains, meaning that whichever value the current variable is assigned is guaranteed to be consistent with previous variables constraints. 

FC is guaranteed to find a solution if one exists and to explore a search tree smaller than or equal to that of backtrack. 

```
Procedure ForwardChecking(depth) 
	For each value in Domain(depth) 
		assign(x_d, value)
		consistent = true
		
		for each future = depth + 1 : end while consistent
			consistent = revise(arc(x_future, x_d))
		if (consistent)
			if (depth = end) 
				showsolution
			else
				ForwardChecking(depth+1)
		undoPruning()
		
```

### AC1

Simple iterative algorithm for achieving global arc consistency (GAC). Basically every time we revise an arc, we pass over all arcs again. Obviously extremely inefficient as we are checking over arcs that might have nothing to do with the previous arc revision.

### AC3

Widely used algorithm for achieving GAC. Much more efficient then AC1 as instead of blindly checking all arcs again, it only checks those that might be inconsistent after an assignment. Does this through the use of a queue. 

Basic idea is that if you modify a variable, you want to add all the arcs that point to that variable to a queue. Keep on revising arcs until the queue is empty, at that point, should be in GAC. 

1. Initalise queue with arcs (X,Y), (Y,X), (Z,Y), (Y,Z)
2. Dequeue an arc from the queue. 
3. Revise (X,Y) and remove any values from X's domain if they have no support in Y
4. If X's domain changes, then add all arcs (neighbours of X) back to the queue
5. Continue 

### Forward Checking with 2-way branching

Combines search with forward checking. Basic idea is that after each assignment, we perform forward checking, if an unassigned variable has an empty domain, we backtrack. 

In 2 way branching, the left branch always assumes that we are assigning a variable a particular value ($x=1$), while the right branch assumes that we are not assigning a variable a particular value ($x\neq 1$)   

So when search, what we do is we have a function for branching left, which assigns the variable a value and performs forward checking. 

We also have a function for branching right, which removes the value from that variables domain, if it becomes empty we backtrack, else we perform forward checking and continue with the search

### Heuristics

General rule of thumbs that the CSP should follow to try and reduce the search space. Examples include which variables to try to assign first, or which values to assign to variables first. 

Dynamic heuristics involve examining the state of the problem after each timestep, and then making a decision based on some computation

#### Smallest Domain Variable Ordering

Idea is that whenever chosing a new variable to assign, always choose the variable with the domain of the smallest cardinality. Basic idea is that if a variable has few options, more likely to cause a conflict. When we assign it first, we can fail faster if assignment is impossible, avoid deep search. 

### MAC

Stands for maintaining arc consistency. Combines search with constraint progopogation, simliar to 2-way branching with FC, except that its STRONGER. FC just checks arcs that involve the current variable being assigned MAC uses ARC3 to ensure that alls constraints across all variables are maintained.  

Important that before the search commences, make 1 pass to ensure initial global arc consistency

Basically, every time a variable is assigned, run ARC3 to ensure that the CSP always stays arc consistent at each step of the search.  

```
procedure MAC3(varList)

	// Select variable and values to assign
	var = selectVar(varList)
	val = selectVal(domain(var))
	
	assign(var, val)
	
	if (completeAssignment()) showSolution()
	// After assigning, ensure arc consistecy
	else if (AC3())
		// Remove variable from list once its been assigned
		MAC3(varList - var)
	
	// Failed AC3, undo pruning of domain 
	undoPruning()
	undoAssign()
	
	// Remove value for right branch
	remove val from domain(var)
	if (domain(var) is not empty) 
		if (AC3())
			MAC3(varList)
		undoPruning()
		
	replace val in domain(var)

```
Basic idea is to make sure you run ARC3 every time a variable is assigned a value
