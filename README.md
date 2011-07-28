# calculator

This is a project to be done for one of the Houston Clojure group meetings.

Build a function `calculate` that parses and evaluates a string.

Keeping track of the input string and the operator stack might be just stateful enough to be problematic.

Some ideas:

* Write a java library to solve the problem and bind it to clojure
* Write a parser to translate the input into clojure and eval it
* Inject an infix macro around the input and eval it
* Use mutable state in clojure (var/ref/atom/agent)
* Track state as function params
* Applicative Functors/Monads for parsing and state management

## Usage

A basic structure and test file is provided.  It can be run using `lein test`.  I assume cake should work similarly.

## Build Instructions

This solution uses ANTLR (Another Tool for Language Recognition) available at http://www.antlr.org. This tool generates a recognizer/parser/translator from a grammar. The generated parser is a set of .java files.

In a typical application, one would compile the generated .java files and combine them into an application with outher .java files. Because this model does not "fit" with the Clojure model of application development, I used a lein plugin, lein-antlr, to automate the tasks of generating the .java files implementing the lexer and parser and to build the .class files from these generated files.

To use this plug-in, run `lein deps` to download the many needed files. After a successful download, run `lein antlr` to generate the .java files from the grammar (`src/lein_antlr/Expr.g`) and then run `lein compile` to compile the generated java files. 

Once you have completed these steps, run `lein test` to test the solution.

Finally, the bin directory contains a script to invoke the Clojure REPL with the appropriate CLASSPATH. (Although I expected lein to do this "automagically," I must have done something wrong since the command `lein repl` failed to find all the files.)

## Acknowledgements

I must acknowledge three different sources from which I heavily borrowed for this solution:

* The article "ANTLR 3" by R. Mark Volkmann found at http://jnb.ociweb.com/jnb/jnbJun2008.html.
* The article "ANTLR via Clojure" by Brian Carper found at http://briancarper.net/blog/554/antlr-via-clojure.
* The 'lein antlr' plugin by Alex Hall found at https://github.com/alexhall/lein-antlr.
* And finally, ANTLR itself and "The Definitive ANTLR Reference" by Terrence Parr.





