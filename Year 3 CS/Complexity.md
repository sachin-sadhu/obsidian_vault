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
# Regular Expressions
![[Pasted image 20250201182229.png]]
Some languages have no regular expression. Such as $\{a^nb^n | n \geq 0\}$ 
![[Pasted image 20250201182434.png]]
Therefore, the set of languages described by regular expressions, NFA and DFA recognised languages are all the same. 
# Pumping Lemma
![[Pasted image 20250201182537.png]]
Intuition is that if M reads a word of length p or more, it must enter some state twice. This loop can therefore be repeated an arbriatary number of times. 

Second condition states that length of y-string must be greater than 0. i.e cannot be the empty string. Third condition states that y must appear before p. 