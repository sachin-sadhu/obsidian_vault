## Finding-Files
To find path of certain file use **find** command:
	find {root directory to search from} -type {file type} -name {filename}
	eg.
find . -type f -name "*.java*
finds all java files in the current directory and its subdirectories

## Searching Inside Files
To find lines of certain string patterns inside files use the **grep** command:
	eg. grep 'public' Test.java : returns all lines containing the pattern public in Test.java file
	
	grep -r 'class' . : Returns all lines containing the pattern class in all files in subdirectories of current directory

	grep -r -c 'class' . : Same as above, but also returns line count

