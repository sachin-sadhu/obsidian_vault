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

	## Intepreters
		Used to translate a high level language into machine code that a CPU can execute. Intepreters do this by going through the high level program, translating the next high level statement, and then executing it 
	
	eg. while (not end of high level program) {
		find next high level statement
		translate high level statement
		execute translated high level statement
	  }

	## Compilation
		Also used to translate a high level language into machine code that a CPU can execute. Unlike Intepreters, compilers will first translate the entire high level source program into machine code that the CPU can execute (object program). It will then arrange for this object program to be executed. 

	eg. (while not end of high level program) {
		find next high level statement
		translate high level statement
	 }
	execute translated object program 
		
		Compiled programs only need to be compiled once and can then be executed an abritary amount of times. 
	
	## Data Models
		*Is structured, model must provide a means of representing entities, attributes & relationsships.
		*Must be able to manipulate the data, for example by adding a new entr, or by changin existing attributes
		*Integrity - Numerous constraints which are applied to relationsships, entities, atrributes etc. eg. Every bank account must have an ownder, an airplane cannot be allocated to 2 flights simultaneously

	##Relational model
		Rows represent entities, columns represent attributes
		Every relation must have a key, each each tuple in a relation must have a key value which is unique and not null.	
