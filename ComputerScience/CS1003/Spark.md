##Spark

##Transformations vs Actions

	Transformations are lazy, actions are eager
	Transformations create a new dataset from an existing one
	Actions return a value after running a computation on the dataset

	//this line is not actually executed, only executed when an action is called on it. Tells Spark to add this line to execution path. this is an eg of a transformation

	JavaRDD<Integer> numbersSquared = numbers.map(x -> x * x);

	//this line is an action, hence causes all previous transformations to be executed upon calling the action.
	numbersSquared.forEach(x -> Sytem.out.println(x));


##Reading in Text Files
JavaRDD<String> lines = sc.textFile("file.txt");

##Key-Value pairs

Datasets that require a key value pair are represented using the JavaPairRDD class.

eg. to count how many times a line of text occurs in a file
JavaRDD<String> lines = sc.textFile("textFile");
JavaPairRDD<String,Integer> pairs = lines.mapToPair(s -> new Tuple(s,1));
JavaPairRDD<String,Integer> counts = pairs.reduceByKey((a,b) -> a + b);


