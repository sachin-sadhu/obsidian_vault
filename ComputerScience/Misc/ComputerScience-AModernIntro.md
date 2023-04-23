* For a processor to intepret a program, it must be able to :
	* Understand what each step means
	* Carry out the corresponding operation

## Algorithms

	*Definite iterations are loops that the exact number of iterations is known
	Indefnite iterations are loops that the exact number is iterations in unknown


	## Recursion
		An algorithm which calls itself 

	## Binary Tree
		A special type of tree in which each branch has at most 2 nodes

	## Computability
		Whether an algorithm exists for a certain process

	## Halting Problem
		Is there a program that can determine whether another program given a certain input halts or runs forever?
		
		## Proof By Contradiction
			
				Assume program D is able to determine whether another program halts or no  given a certain input
			Program H takes program D and inverses its output. So if D says it halts program H will say it runs forever, vice versa
			If you pass program H itself as an input and program D says it halts, program H will run forever, contradiction
			If you pass program H itself as an input and program D says it runs forever, program H will halt, contradiction
			Therefore, assumption that program d is able to determine halting problem is false. 	

	## Complexity
		Complexity refers to to number of steps/time needed to execute an algorithm
		
		Feasible problems are problems that can be solved using an algorithm that a computer can execute in a feasable amount of time, vice versa

		
		## P Vs NP
			Polynomial problems vs Non-Polynomial problems. Non-polynomials are problems that take a infeasible amount of time to solve, but when given a solution, it is very fast to verify whether that solution is correct or not
