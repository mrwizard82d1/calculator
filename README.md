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

Finally, the bin directory contains a script to invoke the Clojure REPL with the appropriate CLASSPATH. (Although I expected lein to do this "automagically," I must have done something wrong since the command `lein repl` failed to find all the files.)



