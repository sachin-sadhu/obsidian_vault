## Module Info
8 total quizzes, none in week 5, 6 & 11. Tuesday 9am - 9pm 
* 8 total quizzes
* Every week except week 5, 6 & 11
* Tuesday 9am - 9pm
## Reading list
* Logic in Computer Science - Michael Huth 
* Logic - Wilfrid Hodges
* Discrete Mathematics & its applications - Kenneth Rosen
* Beginnning Logic - EJ Lemmon
### Definitions
* Valid - A formula is valid is a proof of it can be found.
* Proposition - A declarative statement that is either true of false.
## Proof rules for propositional logic
* Need to be able to prove all valid sequents
* Should not be able to prove invalid sequents
## Natural Deduction
Given a set of proof/inference rules, we are able to infer formulae from other formulae. This allows us to infer a conclusion from a set of premises.
## Sequents
$$\Phi_1,\Phi_2...,\Phi_n\vdash \psi$$
This notation means all the symbols to the left of the turnstyle and premises, and the conclusion is the notation on the right of the turnstyle.
### Provable Equivalence
Let $\Phi$ and $\psi$ be formulae of propositional logic. We can say that $\Phi$ and $\psi$ are provably equivalent if and only if the sequents $\Phi\vdash\psi$   and $\psi\vdash\Phi$ are both valid. 

## Syntactic entailment 
Focuses on strucutre/form of the statements rather than the actual meaning. If statement A can be derived from the statement B using purely formal rules, we say that $B \vdash A$ .

## Semantic Entailment
We say that $A\models B$ if and only if B is true when A is true. For example, the sentence 'John is a good doctor' semantically entails the sentence 'John is a doctor'. Preserves truth values

### Contradiction Test
We can test for semantic entailment in the following way. If A entails B, then $A\land \lnot B$ should lead to a contradiction.          

## Consistency
A proof system is **consistent** if it cannot prove both $\vdash \phi$ and $\vdash \lnot \phi$ for any formula $\phi$ .

## Completeness
Means we can prove every true thing. That is if $$\phi_1,...,\phi_n\models \omega$$, then $$\phi_1,...,\phi_n\vdash \omega$$
## Soundness
A proof system is **sound** if every inference it proves is also a semantic entailment. That is if $$\phi_1,...,\phi_n\vdash \omega$$, then $$\phi_1,...\phi_n\models \omega$$ Basically, anything we can prove with natural deduction is true under every model. If our logic is sound, it is also consistent. Soundness means we cannot prove untrue things.

If our logic is both sound and complete, it means that semantic and syntactic entailment are equivalent.

## Decidability

Is there are algorithm which takes in a first order language and some formulae and outputs true if $$\phi_1,...,\phi_n\vdash \omega$$ and false otherwise? Propositional logic is decidable, predicate logic is not.

## Models
Allows us to evaluate the semantics of a Predicate formula to evaluate if a certain WFF is true. i.e Seeing what the truth value of $\forall x.\exists y.R(x,y)$ requires us to know what $R(x,y)$ means.

A model M of a first order language consists of:
* A set $D$, the domain of concrete values.
* For every constant $c\in C$, a concrete element $c_m\in D$ .
* For every predicate $P\in P$ with arity $n$, a subset $P_m\subset D^n$ of n-tuples for which P evaluates to be true
* For every function $f\in F$ with arity n, a concrete function $f_m:D^n\rightarrow D$   
![[Pasted image 20241012165925.png]]
This means that under $M_1$, R evaluates to true for all the enclosed 2-tuples.
## Ex Falso Quodlibet

Stands for proof by explosion. 
Basic idea of this is as follows :
* $P\land ¬P$  
* P
* $P\lor Q$ 
* Q (as ¬P is also true)
Basically, we are taking advantage of when we use P and when we use $¬P$. Allows us to prove anything following a contradiction.

## Proof of Euler's formula

$$v-e+f=2$$
Start with a tree, in this case $v=e+1$, $f=1$, $\therefore v-e+f=2$ . If we add a single vertex, and graph is still a tree, f stays the same, $v$ and $e$ both increase by 1, $\therefore$ no overall change. 

If we add a vertex and graph is now no longer a tree, $f$ increases by 1, $v$ increase by 1, and $e$ will increase by 2. $\therefore$ again there is no overall change. 

## Substitution 

Definition : 

Unification takes 2 formula $f$ & $g$ and returns a substitution $\theta$ which is the most general unifier. 
$Unify[f,g]=\theta$ such that $Subst[\theta,f]=Subst[\theta,g]$  or "fail" if no such $\theta$ exists. 

Replacing a variable in a formula with a term. A term can be a constant, another variable or a function. 

Example, a substitution of the formula $$f(x)\implies f(Alice)$$
would be $[x / alice]$ , where we are replacing all occurences of the variable x with the term alice.

## Unification

Process of trying to find a valid substitutions that makes 2 formulas equivalent.

Example, a substitution of the 2 formulas $$Knows(alice, arithmetic), Knows(x, arithmetic)$$ would be $\{x/alice\}$  

Another example would be $$Knows(alice, y), Knows(x, F(x))$$
would be $\{x/alice, y/F(x)\}$ .

Now, that we have unification, we can apply modus ponens to first-order logic. 
![[Pasted image 20241116130742.png]]

## Robinson's Unification Algorithm

```
function Unify(t_1, t_2)
	# Checking if one of the terms (either t_1 or t_2 is a variable x)
	# And t_j is another term
	if t_i = x, t_j = t (where {i,j} in {1,2} and i != j) then 
		# If the variable is the same as the other term, no work needed
		if (x == t) then return {}
		# Cannot substitution if x is part of t eg f(x)
		else if x is_subexpression of t then return False
		else return {x -> t}
	else Let t_1 = f(t_11, ...,t_1n), t_2 = g(t_21,..,t_2m)
		# If different function names cannot unify
		if f != g then return False
		else
			* <- {}
			# Interate through each function argument pair
			foreach i in {1,m} do
				# Make sure to apply unification rule to arguments beofre calling 
				Unify function
				** <- Unify(t_1i*, t_2i*)
				if ** == False then return False
				# Apply new subsitutitions to set
				else * <- * **
			return *
```

## Resolution

Method of simplifying a list of clauses. If clause A contains a positive literal, and clause 2 contains the complement of that literal, you can combine the 2 clauses and remove both literals $$(P\lor R)\land (\lnot P\lor Q)\implies (R\lor Q))$$  
## Skolemization
Used to get rid of existential quantifiers. For example in the formula $\forall x\exists y$  means that for all $x$ there exists some $y$. $y$ is dependant on $x$, therefore we should replace all occurences of $y$ with some formula $f(x)$ that captures this relationship

If $y$ does not depend on any variable $x$, we can just replace all occurences with $y$ with a contant $k$. For example in the formula $\exists x \forall y$.

# Induction

Used to prove all elements of a domain have a certain property. $$\forall xP(x)$$
## Ordinary Induction

Show P(x) is true for base case, then show that $P(k)\rightarrow P(k+1)$ . Formally $$P(basecase)\land P(k)\rightarrow P(k+1)$$
## Strong Induction

What is P(k+1) does not rely solely on P(k), might also rely on P(k-1). For strong induction, instead of just assuming P(k) is true, we assume P(k), P(k-1)...P(base_case) are all true. Then we use this to prove P(k+1) is true. 

## Structural Induction

Used to prove properties about recursively defined structures. Steps are prove property holds for base case, assume P holds for sub-structures used in recursive definition, show that P holds for recursively constructed structure. 

* Show that P holds for elements in base case. 
* Assume P is true for ever existing element in recursive rule
* Prove P for every new element of S created in recursive step

Basically, show predicate satisfies base case. Assume predicate satisfies elements in recursive rule. Then generate a new element from recursive rule and show that it also satisfies predicate. 

# Analytical Tableuax

Technique for automated reasoning. Basically, search through different branches in the search tree, if there is a contradiction in a particular branch i.e $P\land \lnot P$, close off that branch. If all branches are closed, means that the set of formulas is unsatisfiable. 

### How to check if a set of premises entails a conclusion? 
* Add negation of conclusion to list of formulas
* Search through proof tree
* If tree is closed, means negation of that conclusion is unsatisfiable, therefore original conclusion is valid
#### Tips
* Tick off lines in search tree when we have fully explored them
* For existential quantifiers, make sure to introduce a fresh variable

# Boolean Algebra

In boolean algebra, the '+' operator can be thought of as equivalent to the $\lor$ operator. This is because, $a+b=1$ when either a or b = 1.

Similarly, the '$\times$' operator can be thought of as equivalent to the $\land$ operator. This is because $a\times b=1$ is only true when both a & b = 1.
## Question
if there are some true statements that cannot be proved, how do we know they are true?

Godel showed that there were some true statements that cannot be proven in mathematics, but isn;t predicate shown to be complete, therefore either predicate is not complete or predicate is not sufficient to encapsulate all statements of mathematics?

what does it actually mean for a statmenet ot be true?

are there more unprovably true statements than provably true statements

if we know there are unprovably true statements, why bother finding a proof to show somethat is true

for models, if we have an infirnite domain, how can we evaluate whether WFF is true/false if intepreter of predicate also provides an infinite set


***
My preferred way to describe soundness and completeness is pretending we have an analyzer which, given an input program, returns "SAFE" (technically, accepts) or "NOT SAFE" (technically, rejects). A sound analyzer is one that can be trusted when it says "SAFE". A complete analyzer is one that can be trusted when it says "NOT SAFE".
***

