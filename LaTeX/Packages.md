# Packages

## Margins

In order to force the text to take up full page, i.e. decrease margins. Use **fullpage** package

If you would like to customise your margins, use geometry package

\usepackage[top=1in,bottom=1in,right=0.5in,left=0.5in]{geometry}, can also customise paperwidth\height using similar command

## Math Symbols

*amsfonts : used for set of numbers, use \mathbb{} command.
*amssymb
*amsmath

# Macros

Defined in preamble, used if you have some code that you keep on reusing, want to create a custom shortcut command for that code. 
eg.
$y=frac{x/x^2}$
To create a preamble for this, use : 
\def\eq1{y=frac{x/x^2}, then to call this macros, use $\eq1$ in your document.



