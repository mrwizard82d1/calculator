;; This module implements the calculate function used by the Clojure
;; Houston Users Group to investigate solving common problems in
;; Clojure.
;;
;; As much as I would like to take credit for this entire
;; implementation, the vast majority of the credit must go to others:
;;
;; * The article "ANTLR 3" by R. Mark Volkmann found at http://jnb.ociweb.com/jnb/jnbJun2008.html.
;; * The article "ANTLR via Clojure" by Brian Carper found at http://briancarper.net/blog/554/antlr-via-clojure.
;; * The 'lein antlr' plugin by Alex Hall found at https://github.com/alexhall/lein-antlr.
;; * And finally, ANTLR itself and "The Definitive ANTLR Reference" by Terrence Parr.


(ns calculator.core
  (:use clojure.contrib.str-utils)
  (:use [clojure.contrib.str-utils2 :only [split-lines]])
  (:import (org.antlr.runtime ANTLRStringStream
			      CommonTokenStream)
	   (lein_antlr ExprLexer ExprParser)))


(defn AST [node]
  "Convert an ANTLR AST into a Clojure AST (an s-exp)."
  (if (zero? (.getChildCount node))
    ;; If the node has no children, return its text
    (read-string (.getText node))
    ;; Otherwise, return either
    (let [children (map AST (.getChildren node))
	  node-text (read-string (.getText node))]
      (if node-text
	;; The text of the node concatenated to the AST of the
	;; children
	(if (not= (symbol "/") node-text)
	  (apply list node-text children)
	  (apply list 'quot children))
	;; or the AST of the children only
	children))))

			
(defn node-seq [x]
  "Return a sequence containing all texts in the AST."
  (map #(.getText %)
       ;; The function tree-seq flattens a tree structure. It takes
       ;; three arguments. The first argument is a predicate that
       ;; returns true if the node has children. The second argument
       ;; is a function that returns the children of the current
       ;; node. The final argument is the root node of the tree (where
       ;; to start walking the tree).
       (tree-seq #(not (zero? (.getChildCount %)))
		 #(.getChildren %)
		 x)))


(defn parse-expr [a-string]
  "Parse a string using the generated lexer and parser."
  (let [lexer (ExprLexer. (ANTLRStringStream. a-string))
        tokens (CommonTokenStream. lexer)
        parser (ExprParser. tokens)]
    (.. parser expr getTree)))



(defn translate-error [antlr-error-string]
  "Translates an ANTLR error string into the form expected by our tests."
  (let [error-strings (split-lines antlr-error-string)
        error (first error-strings)
        matcher (re-matcher #"line (\d+):(-?\d+) (.*)" error)
        matches (re-find matcher)
        column (Integer/parseInt (nth matches 2))
        char-pos (if (neg? column)
                   1
                   (+ column 1))]
    (str "Error at character " char-pos
         ": expected number or ( ; got eol")))


(defn calculate [expr-text]
  ;; (try
  ;;   (eval (AST (parse-expr expr-text)))
  ;;   (catch Exception e
  ;; 	(println "CAUGHT EXCEPTION: " e))))
  (let [errorBuffer (java.io.ByteArrayOutputStream.)
        errorStream (java.io.PrintStream. errorBuffer)]
    (System/setErr errorStream)
    (try
      (eval (AST (parse-expr expr-text)))
      (catch Exception e
        (translate-error (.toString errorBuffer "UTF-8"))))))


