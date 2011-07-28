(defproject calculator "0.0.1-SNAPSHOT"
  :description "A simple calculator api implemented using ANTLR"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
		 [org.antlr/antlr "3.2"]]
  :dev-dependencies [[lein-antlr "0.1.0"]]
  :javac-options {:destdir "classes/"}
  :java-source-path "src"
  :antlr-src-dir "src/lein_antlr"
  :antlr-dest-dir "src/lein_antlr")
