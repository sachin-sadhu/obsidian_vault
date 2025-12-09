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

### AC3 Revision

Algorithm for altering domain of variable depending on whether it has a support or not. Basic idea is to just loop through the domain and check whether it has a support

```
Procedue ReviseARC(arc(x_i,x_j))
	changed = false // Flag to check whether the domain has been adjusted
	
	for value in domain(x_i):
		supported = false
		for support in domain(x_j) while not supported:
			if (satisfies constraint(value, support))
				supported = true
			
		if not supported
			remove value from domain(x_i)
			changed = true
	if empty(domain(x_i)) fail()
	return changed
```

### Forward Checking with 2-way branching

Combines search with forward checking. Basic idea is that after each assignment, we perform forward checking, if an unassigned variable has an empty domain, we backtrack. 

In 2 way branching, the left branch always assumes that we are assigning a variable a particular value ($x=1$), while the right branch assumes that we are not assigning a variable a particular value ($x\neq 1$)   

So when search, what we do is we have a function for branching left, which assigns the variable a value and performs forward checking. 

We also have a function for branching right, which removes the value from that variables domain, if it becomes empty we backtrack, else we perform forward checking and continue with the search

```
Procedure reviseFutureArcs(varList, var):
	for each futureVar in varList where var != futureVar:
		if  !revise(arc(futureVar, var)) return False // Returns false when the futureVar has an empty domain after the revision
	return True
```

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

### Static Variable Heuristics

### Primal Graph

Has an edge between 2 variables iff they are in a constraint together. 

#### Maximum Degree

Order variables in decreasing order of their degrees in the graph, break ties arbitrarily. Idea is that variable with more constraint are more likely to cause conflicts, and by attempting to assign these first, you detect these earlier in the search tree. 

#### Maximum Cardinality

Pick the variable that is connected to the largest number of already assigned variables. Variables that share constraints with loads of already assigned variables, again helps to fail fast.  

#### Minimum Width

Order the nodes in some order, then the width of each node defines how many edges there are linking it to a previous variable in the ordering
![[Pasted image 20251116205655.png]]
For each ordering, we take the maximum width at any ordered nodes.

We then iterate through all the different orderings, for each one getting the maximum width, and we pick the ordering with the minimum width.  
![[Pasted image 20251116205802.png]]

## AC3.1

In AC3, we would check for supports, by looping through the domain of the variable every time. This is naive, no need to do a full search through the domain every time. 

Instead, we now just track what the last value that provided support that we found. Now when we revise the arc, we just check if that last value is still present in the domain, if it is all is good. If it's not then instead of searching from the start of the domain, we just start search from the last value + 1. 


## AC4

AC3 sometimes go over arcs that won't even result in any further pruning, therefore, revising these arcs are useless.

Idea behind AC4 is that we store how many elements of a variable's domain support another variable value. Also, we store the assignements for which a value in a domain supports.   

### Initilisation 

Process of building up the table of how many supports an element has and which elements support it.

For example, the counter for {arc<$x_1,x_2$,>,2}  represents the number of supports that the variable $x_1$ with a value 2 has from the variable $x_2$. If these support values are 2,3

then we also add
- $S[x_2,9]=<x_1,2>$
- $S[x_2,3]=<x_1,2>$
indicating the list of values of variables that $x_2,9$  support.

If a value has no supports, we add it to the deleted table. Repeat until L is empty

#### AC4 vs AC3

AC4 has a worst case time complexity better than AC3, however it always reaches this time complexity. Therefore, people generally prefer to just use AC3. 

### Propogation 

Once all counters and supports have been initalised, we pop a deletion value off the list, iterate over all values that were supported by this value, and decreement their num_support counter. If the counter reaches 0, then we delete it 


Now, for each element, we know how many supports it has, and for which elements it provides a support for. 

Now we also maintain 
- a table of deleted domain values
- a list of deleted domain values that still need to be propogated. 
## Backjumping

Basic idea is to replace chronological backtracking with a jump back to the variable that is actually causing the dead-end.

maxCheckLevel is the deepest level that a check is made against, compute this for every time as assign a variable a domain value.

When all values of a domain have been exhausted, then we calculate returnDepth, which is the maximum (deepest) value of all values in the domain.

Intuition is that if we exhaust a domain only checking constraints up to a certain depth, then we know that variables deeper than this cannot be causing the issue. 

Only do backjumping once domain for variable has been exhausted. That is then we go through each domain value and identify the maximum maxCheckLevel for each domain value, and identify return depth. 

### Max-fail backjumping

Doesnt work because incomplete, could skip over valid solutions

### Conflicted-directed backjumping

When making an assignment to $x_k$, maintain the set of assignments failed against. So if $x_3$ fails against $x_1$, we also see if it fails against $x_2$, if it does, then we add both $x_1$ and $x_2$ to the conflict set. 

When we exhaust a domain, we then jump back to the maximum value in the conflict set. So in this case, we would jump back to $x_2$. 

However, when we jump back, we merge the conflict sets, so now we do not forget that we were also failing against $x_1$

## Conflict Recording

Adds new constraints to prevent same conflicts from reoccuring 

When dead-end is encountered, identify reason for the dead-end. Add a new constraint to stop that dead-end from reocurring 

## Non-Binary Forward Checking 

Forward checking usually only works on binary constraints. However, we can extend it to work on non-binary constraints.

### nFC0

Type of non-binary forward checking that is only activated when all but 1 variable involved in a constraint are assigned. 

For example, if a constraint C(X,Y,Z) exists. Then forward checking will only begin when 2 out of the 3 variables are assigned, and then prune the domain of the final unassigned variable.

### Local Path Consistency

A constraint C($x_i,x_j$) is path conssitent with respect to a third variable $x_k$ iff for every pair $d_i,d_j$ that satisties the constraint C($x_i,x_j$), there exists a variable in the domain of $d_k$ that satisfies the constraint connecting both  C($x_i,x_k$) and C($x_j,x_k$). 

Basically, find if a certain tuple of variables $x_i$ and $x_j$ has a support through $x_k$ 

If there is no support, remove that tuple from the set of satisfiable tuples. Now, instead of removing values from domains, we are removing binary tuples from a set of binary tuples. 

### Global Path Consistency

Consiering each pair of variables, checks it against ALL POSSIBLE third variable Y. Usually requires generating implicit constraints between variable pairs that were not originally connected

Path consistency does not necessarily imply arc consistency.


### K-Consistency

#### Strong Consistency

Refers to the fact that we can enforce k-consistency, and every lower level of consistency as well

### 3-consistency example

CSP with 3 variables, all with domains {1,2,3} and one constraint:
- c($x_1,x_2,x_3$) = {<1,1,1>, <2,2,2>, <3,3,3>}

Since the binary tuple between $x_1,x_2$  (1,2) has no support in the constraint. This CSP is not 3-consistent.

So, need to add some constraints that forbid this kind of pairing

In general, enforcing k-consistency makes 'nogoods' of k-1 explicit. 

For example, for 2-consistency, we create unary constraints that prune values from domains. that eliminate binary tuples that cannot be found in any solution.

### Backtrack-free Search

A variable ordering is backtrack free if level of strong consistency exceeds width of cooresponding ordered constraint graph. 

![[Screenshot 2025-12-05 at 1.55.51 PM.png]]

For exammple, for this graph, a strong 2-consistency is sufficient (arc consistency). All elements in $D_1$'s pruned domain will have some support in $D_2$. All elements of $D_2$ have some support in $D_1$ and $D_3$. All elements of $D_3$ have some support in $D_2$. 

## Generalised Arc Consistency

Constraint-centric view of encforcing consisteny. Global Arc Consistency only worked on binary constraints, this works on non-binary constraints. 

Like global arc consistency, whenever a variable's domain is pruned, we must revisit all other constraints that involve that variable and reestablish GAC. 

A constraint c($x_1,...x_k$) is generalised arc consistent iff
- For every variable $x_i$ involved in the constraint, for every element d of $d_i$ , there exits a supporting tuple.

Example:
![[Screenshot 2025-12-05 at 2.14.36 PM.png]]

### GAC2001/3.1

Algorithm for enforcing generalised arc consistency. 

Basic idea is to for each variable of every constraint, and for every domain value of that variable. Use a data structure that stores for that value of the variable in the domain, store something called LastSupport, which is the last current tuple that supported this value. 

When checking if that value of the variable still has a support, check the last stored support, it still valid, move on. If it's invalid, try to find a new support. If no support can be found, then prune that value from the variable and all all constraints involved the variable back onto the queue. 

### GAC-Schema

Uses the idea of multi-directionality as an optimisation technique.

For example, if the constraint C(A,B,C) = {<1,1,2>,<2,2,1>}

Then instead of rechecking a tuple every time we want to see if A,B,C has a certain support. We just check each tuple once, and note which variable's values it provides a support for.

With multidirectionality, the tuple <1,1,2> provides support for 
- A: 1
- B: 1
- C: 2

and we do this all in one swoop

### Special-Purpose Algorithms

Can enforce GAC via special-purpose algorithms if we understand the mathematics behind the constraint and what it is enforcing. 

For example, for the constraint $12x_1+5x_2+x_3=30$ , finding all sets of tuples that satisfy this constraint is an NP-complete problem. 

Solution is to settle weaker propogation, here we could split the constraint into finding sets of tuples for sum adding to $\leq30$  and sum adding to $\geq30$, then enforce GAC on each. 

## Triggers

In modern CSPs, constraints ask to be notified of events (triggers) such as
- Upper/Lower bound changing on a variable
- Value $a$ being removed from a domain $D_i$
- Any change to $D_i$

## Bound Consistency

Only ever prune the minimum/maximum of a domain. 

GAP never weaker than Bound-Consistency

Stuff that is bound consistent might not be globally arc consistent (GAC),
however, everything that is GAC is bound consistent

### Bound(D) Consistency

Constraint is bound consistent if for every variable in the constraint, there exists a tuple that satisfies the constraint that contains both the minimum and maximum value of that variables domain, and the other values in the tuple are still valid for the other variables. 

### Bound(Z) Consistency

Same as bound(D) consistency, except that now we check if each value within the tuple is an INTEGER between the minimum and maximum of that variables domain.

So now, instead of checking if the other variables in the tuple actually exist in that variables domain, we just check if they are in the range. 

### Bound(R) Consistency

Same as Bound(Z) consistency, except that now each value in the tuple is a REAL NUMBER between the min and max of that variables domain 

Therefore, no longer requires an integer solution to some constraints.  

Example, if we have a constraint $5x_1+3x_2+10x_3=10$, then to find the maximum value of $x_1$, we can rearrange to make $x_1$ the subject: $\frac{10-3x_2-10x_3}{5}$. To maximise $x_1$, we should maximise $x_2$ and $x_3$. 