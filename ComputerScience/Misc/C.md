## Arguments-Call by Value
	In C, all function arguments are passed "by value". Function is given the vlaues of its argumnets in temporary variables rather than actual originals. Called function cannot directly alter a variable in the calling function; only able to alter its local copy.

Different for arrays, as value passed is the address of the array in memory

## Scope
	By default, all variables are local and can only be accessed in the scope of the method. A global variable must be defined exactly once outside of all functions. Must then be declared in each fuction that wants to access it.

## Data-Types
	*char - single byte, stores one character, surrounded by single qoutes.   		Converted into integer in machines character set
	*int - integer, usually either 16 or 32 bits depending on machine
	*float - single precision floating point
	*double - double precision floating point
	*string - sequence of 0 or more chars surrounded by double qoutes, array 		  of characters. Contains null char '\0' at end of string.
	Value of integers can be in either octal (base 8) or hexadecimal (base 16). To specify an int as an octal, prepend the value with a 0. To specify an int as a hexadecimal, prepent the value with a 0x.

	
## Increment/Decrement- Operators
	
	Has both (x++/x--) and (++x,--x). ++x increments the value of x before it is used, while x++ increments the value of x after it is used. 

	eg.
	x = n++, if n = 5, value of x is set to 5
	x = ++n, if n = 5, value of x is set to 6

## Bitwise-Operators
	6 operators are provided for bit manipulation (&(and),|(inclusive or),^(exclusive or),<<(left shift),>>(right shift),~(not))

Left shifting shift bits by a certain position. eg x << 2, shofts value of x left by 2 positions, equaivalent to multiplying by 4

Left shifting shift bits by a certain position. eg x << 2, shifts value of x left by 2 positions, equaivalent to multiplying x by 4. x >> 2, shifts value of x right by 2 positions equivalent to dividing by 4.

## Looping
	Has normal for loops, while , do - while loops

	goto command allows you to exit a loop and go to a certain label
	
	eg. for (...) {
		for (...) {
			if (number found) {
				goto found
	    found:	
		printf("Number has been found");

## Static-Variables
	Static declaration applied to an external variable/routine mean that they can only be called, referenced from within the same source file
	Static declaration applied to an internal variable means that instead of coming in and out of existence, it is stored permamantly.

## Macros-Subsititution
	Replaces certain tokens with replacement text by preprocceser. This occurs prior to compilation.
	eg. #define name replacement text : replaces all subsequent occurences of 'name' with 'replacement text'

	Can also include arguments
	eg. #define add(a,b) (a+b)

	If an argument is preceeded by a # in replacement text, will be expanded into a quoted string with parameter replaced by actual argumet
	eg. #define nameprint(name) printf("hello" " #name)

	## Advantages	
		Could just use normal function, however macros are usually faster	
		Allow you to use different types, eg in the add macros, could pass in int or double as argument type
	
	##Disadvantages
		No type checking
