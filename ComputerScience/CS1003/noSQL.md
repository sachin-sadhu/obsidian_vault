##Transactions

	Wrapping operations up in transactions to assure *atomicity*
	Transactions happen entire or not happen at all - all or nothing

	Most relational systems support ACID transactions
		-ATOMIC - all or nothing
		-CONSISTENT - only mutually consistent data is saved
		-ISOLATED - Transactions do not effect each other
		-Durability - Written data is not lost

	*ACID IS HARD TO ACHIEVE*


##Scaling up VS Scaling out

	Scaling up refers to increasing your firepower eg. increasing processing power and storage capacity of systems
	However, there is a limit 

	Scaling out refers to an architecture that focuses on horizontal growth, addition of new resources instead of increasing capacity of current resources

##Sharding

	Sharding involves splitting and distributing one logical data set across multiple databases that share nothing and can be deployed across multiple servers. To achieve sharding, the rows or columns of a larger database table are split into multiple smaller tables.

##CAP Theorem

	C - Consistency
	A - Availability
	P - Partition

	Says that no database can have all 3 at the same time, one must be sacrificed

	Analogy :
	Image you are someone else are writing a movie script and are updating each other of your progress over the internet, however both your scripts might not be the exact same at every second. If someone asks to see your script, you can show them (AVAILABILITY), however your data might not be CONSISTENT with the other persons script. Or, you can not show them (NOT AVAILABLE), in order to preserve CONSISTENCY.

##NOSQL

	-Relaxing ACID conditions
	-Less rigid storage strucutre
	-Fewer constraints to check (eg. foreign keys)
