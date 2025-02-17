# Relational Query Languages
Language in which user requests information from database. Relations refer to specific table within a database. 

## Select
$\sigma$ is used as select symbol. Returns all records in a table that satisfy some predicate. $$\sigma_{A=B\land D\gt5}(r)$$ This will select all records from table 'r' that satisfy the predicate 'A=B' and 'D > 5'.

## Project
$\pi$ is used a as project symbol. Returns all specified columns from a table. $$\pi_{A,C}(r)$$
This will select columns A and C from table r. 

## Union
![[Pasted image 20250209141706.png]]
Both r and s must have same arity (same number of attributes). Each attribute domain must be compatible (same/similar types)
## Cartesian Product
Combines 2 tables together. Each row in r is joined with each row in s $$r\times s$$![[Pasted image 20250209143145.png]]
## Natural Join
$\bowtie$ is used as natural join symbol. Combines 2 tables by matching values of common attributes
![[Pasted image 20250209143809.png]]
# Functional Dependancies
Let A and B be sets of attributes of a relational schema R
B is functionally dependant on A ($A\to B$) if each possible value of A in R is associated with exactly one value of B in R. 
Basically means that if we know what A is, we can then tell what B is.
![[Pasted image 20250216140512.png]]
Here ID$\to$ dept_name. As the one ID is always associated with one department. However the converse is not true as 1 dept_name could have mutliple ID's.
## Full/Partial Functional Dependancies
B is partially functionally dependant on A, if B is functinally dependant on a strict subset of A. Basically, if you remove an attribute from A, the dependancy relationship still exists. 

Full dependancy is the opposite, remove any attribute from A results in the relationship breaking.
## Transitive Dependencies
If $A\to B$ and $B\to C$, then we have $A\to C$ 
## Closure
Closure of a set of attributes X, is denoted as $X^+$. This is the set of all attributes that can be functionally determined by X. Closure allows us to determine which attributes are possible candidate keys.
![[Pasted image 20250217103853.png]]
### Armstrong's Axioms
Helps us to infer all possible functional dependancies.
* Reflexivity - If B is a subset of A, then $A\to B$ always holds (An attribute always functionally determines itself)
* Augmentation - If $A\to B$ holds, then $A\gamma \to B\gamma$ also holds (Adding the same attributes to both sides does not affect validity)
* Transitivity - If $A\to B$ and $B\to C$, then $A\to C$ must hold

