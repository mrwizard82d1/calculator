#! bash

# Invokes the Clojure REPL with the correct environment to debug.

CLOJURE_JAR=lib/clojure-1.2.1.jar
CLOJURE_CONTRIB_JAR=lib/clojure-contrib-1.2.0.jar
ANTLR_27_JAR=lib/antlr-2.7.7.jar
ANTLR_33_JAR=lib/antlr-3.3.jar
ANTLR_33_RT_JAR=lib/antlr-runtime-3.3.jar
ST_321_JAR=lib/stringtemplate-3.2.1.jar


CPS=''
for f in lib/*.jar
do
    CPS=$CPS\;$(cygpath -w $f)
done
java -cp $CPS\;src\;classes clojure.main
