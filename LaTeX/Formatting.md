
# Text Formatting

## Italicize
    
Use \textit{text}

## Bold

Use \textbf{text}

## Font Size
    
Use \begin{fontsize}text\end{fontsize}

Can also use \fontsize to make everything after command specified font size

Available Font Sizes are : 

*Huge
*huge
*LARGE
*Large
*large
*normalsize
*small
*footnotesize
*scriptsize
*tiny

## Centering

Use \begin{center}text\end{center}
\\Can also use **flushleft** or **flushright**

Can also use \centering to center everything after command \\no end command

# Document Formatting
    
## Title, Author, Date

Before \begindocument, use \title{titlename}, \author{authorname}, \date{date}
Then after \begindocument command, use \maketitle command

## Sections

Use \section{sectionname} command to create a section, can also create \subsection and \subsubsection
If you do not want your section to be numbered, add * after section. eg. \section*{sectionname}

If you want to create a content table, use \tableofcontents command.
