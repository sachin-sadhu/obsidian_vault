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

