##Streams

	A sequence of data elements made avaiable over time
	May be infinite
	Stream computation is formed by a pipeline of stream operations
	Usually starts with a generator, followed by a number of stream operations, and ending with a terminal operation
	Intermediate operations take in a stream and output a stream

	eg.
	int totalAge = people.stream()
			     .mapToInt(p -> p.getAge())
			     .sum();

##Lambda Expressions

	A class with many methods (and fields)
	A class with a single method
	Lightweight syntax for declaring the mehtod close to call site


	Functional interfaces from java standard libraries
		Function<T,R> input T, output R
		Predicate<T> input T, output bool
		UnaryOperator<T> input T, output T
		Supplier<T> no input, output T
		Consumer<T> input T, no output

	Interediate Operations
		.filter() 
		.map()
		.flatMap()
##Parallelism
	Loops are inerentyly stateful, iterations are independant
	Structure of stream programming discourages use of shares state
	Easy to parallelise a stream computation, which allows multiple processors to execute, allows for faster computation
	

	eg. 
	IntStream.range(largeNumber, largeNumber + 100) {
		.parallel()
		.filter(n -> isPrime(n))
		.forEach(n -> System.out.println(n);

