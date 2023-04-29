## Arguments-Call by Value
	In C, all function arguments are passed "by value". Function is given the vlaues of its argumnets in temporary variables rather than actual originals. Called function cannot directly alter a variable in the calling function; only able to alter its local copy.

Different for arrays, as value passed is the address of the array in memory

## Scope
	By default, all variables are local and can only be accessed in the scope of the method. A global variable must be defined exactly once outside of all functions. Must then be declared in each fuction that wants to access it.
