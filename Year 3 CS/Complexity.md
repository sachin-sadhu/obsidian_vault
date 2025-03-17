# Finite Automata
![[Pasted image 20250201181349.png]]
* Elements of the alphabet $\sum$ are called letters
* A word is a finite sequence of letters over $\sum$
* The length of a word is the number of letters it contains
* Set of all words over $\sum$ is denoted $\sum$* and includes empty word
* Subset of $\sum$* is called a language
![[Pasted image 20250201181624.png]]
## Nondeterministic finite automaton
![[Pasted image 20250201181709.png]]
Every NFA has a cooresponding DFA.
![[Pasted image 20250201182053.png]]
Given 2 regular languages A & B. Their union, concatenation and kleene star are all also regular languages. Therefore these 3 operations express closure property.
## Regular Expressions
![[Pasted image 20250201182229.png]]
Some languages have no regular expression. Such as $\{a^nb^n | n \geq 0\}$ 
![[Pasted image 20250201182434.png]]
Therefore, the set of languages described by regular expressions, NFA and DFA recognised languages are all the same. 
### Converting from NFA to Regular Expressions
* Make sure there is only 1 explicit start state and accept state. If there are currently multiple, add epsilon transitions from them to 1 new accept state.
* Pick a state to remove and adjust other states transitions based on this deletion.
* Continue until you are only left with the start state and accept state.
## Pumping Lemma
![[Pasted image 20250201182537.png]]
Intuition is that if M reads a word of length p or more, it must enter some state twice. This loop can therefore be repeated an arbriatary number of times. 

Second condition states that length of y-string must be greater than 0. i.e cannot be the empty string. Third condition states that y must appear before p. 

# Context-free grammars
Way of describing a language based on variables and rules.
![[Pasted image 20250209102124.png]]
![[Pasted image 20250209102348.png]]

Each CFG defines a language L(G) over $\sum$. Each word of the language is built as follows: 
* Start with start variables S
* Choose a variable A in the current word, and a rule of the form $A\to w$ . Replace A with w.
* Repeat step 2 until no variables remain in the current word.
A sequence of substitutions is called a derivation, written as : $$S\to w_1\to w_2\to...\to w$$
![[Pasted image 20250209102757.png]]

A language L is **context-free** if there exists a CFG such that L = L(G).
## Pushdown automata

Basically a finite automata with a stack attached to it. Can push/pop letters of the alphabet on to the stack. 
![[Pasted image 20250209102955.png]]****
Notation $a,x\to y$ means
* read letter a from the input
* pop x from the stack
* push y onto the stack
Can only take this transition function if reading 'a' and 'x' is on top of the stack. 

0, $\epsilon\to 0$ means
* read letter 0 from the input
* pop nothing off the stack
* push 0 onto the stack
![[Pasted image 20250209103158.png]]

PDA's recognise all regular languages, and also some non-regular languages. 
### Converting CFG to PDA
Assume CFG that generates ${a^nb^n|n\geq 1}$  
Grammar Rules :
* $S\to aSb$
* $S\to \epsilon$ 
![[Pasted image 20250209104534.png]]

Basic idea is as follows:
* Push some delimiter '$' onto the stack (signals when we have read all letters in the word)
* Push the start state 'S' onto the stack
* If there is ever a variable on the stack. Pop the variable off the stack and push the letters it's rule describe onto the stack in reverse order. i.e if we read an A on the stack and $A\to xy$ is a rule, then we want to push 'y' then 'x' onto the stack.
* If we read a terminal from the input, check it's corresponding letter is also at the top of the stack
* If we reach the end of the word and there is a '$' on the stack. Means that word is part of the language of the CFG.

## Context-free and regular languages
![[Pasted image 20250209104818.png]]
This illustrates that regular languages are a subset of context-free languages. i.e All regular languages are also context-free languages, but not all context-free languages are regular languages. 
### Pumping Lemma
![[Pasted image 20250209114759.png]]

# Church-Turing Thesis
## Turing Machines
* Similar to a DFA, but with a tape that it can read and write and move about on
* Tape has a left hand end, but is infinite in other direction. Broken into squares called cells
* Tape starts with input word
![[Pasted image 20250216132455.png]]
![[Pasted image 20250216132511.png]]
Turing machines only have 1 accept set (not a set of them). They also have an explicit reject state. Machine will terminate when it reaches an accept/reject state.
### Turing-recognisable languages
![[Pasted image 20250216132748.png]]
Basically, there exists a Turing machine such that:
* If the word is in the langugae, then M halts and accepts the word
* If the word is not in the language, then M either rejects or loops forever
### Turing-decidable languages
Some machines might keep on running forever.  Hard to tell if a machine running on a given word will halt or not.
![[Pasted image 20250216132917.png]]
## Enumerators
![[Pasted image 20250216133236.png]]
## Algorithm
![[Pasted image 20250216134359.png]]
### Turing completeness
A programming language is said to be "Turing-complete" if we can simulate every Turing machine in it.
## Reduction
Reduction is the concept of mapping a problem B to a problem A  And if we have a solution to problem A, then we can use that solution to solve problem B.

If problem A can be reduced to problem B, and problem B is decidable. Then so is problem A. 

If problem A can be reduced to problem B, and problem B is undecidable, this does not necessarily mean problem A is also undecidable. As problem A can be reduced to many different problems.

If problem A can be reduced to problem B, and problem A is undecidable, then so is problem B. This makes sense as if B was decidable, we could use that decider to decide problem A, and therefore problem A would also be decidable.

# Rice's theorem
#### Property
A property of a Turing-recognisable language is any statement about that language that is either true or false.

Examples are this language contains all non-empty words. This language contains only words with the letter a.

A property P is non-trivial if there is at least one Turing machine whose language satisfies P, and at least one that does not. Basically, this property holds for some languages and does not hold for others.
![[Pasted image 20250304125206.png]]
Rice's theorem states that there is not a single Turing machine, where if given another turing machine M, you can say definitively if L(M) satisfies P.   


assume we have a decider (R) for halting problem. use R to construct a decider (S) for acceptance problem. given a machine M and a word W, S needs to decide whether M will accept/reject W. if R says that M does not halt on W, then S can reject W. However, if R says that M does halt on W, then we can check if M accepts W simply by simulating it. therefore S is able to decide the acceptance problem by assuming R

assume we have a decider R that decides emptiness problem. use R to construct a turing machine S that decides acceptance problem. so given a turing machine M, we need to decide if M accepts/rejects W. first modify M such that M will reject all strings except W, but on W, M will work as normal. only string that M will aceept is now w, so its langugage is non-empty if and only if M accepts w. since we have a decider for emptiness problem R, if R accepts M, it means that L(M) is empty and therefore M does not accept W. however, if R does not accept M, then we know that L(M) is not empty and therefore M accepts W. therefore we have created a machine S that decides the acceptance problem