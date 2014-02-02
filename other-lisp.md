Differences from Other Lisps
============================

The official "Differences with other Lisps" list:

http://clojure.org/lisps

Syntax
------

* Clojure is case sensitive
* Clojure is lisp-1 (functions and variables share the same namespace)
* def creates a variable, defn defines a function
* no car or cdr, use first and rest or next
* (rest '()) returns the empty list. (next '()) returns nil
* nil means 'nothing' and is not the same as '() 
* conditionals distinguish nil or false / everything else
* , is whitespace
* Formal parameters are enclosed in square brackets:

    (defn add [x y] (+ x y))
    
* As well as let bindings

    (let [x 3 y 4]
      (+ x y))
    
* lambda is fn

    (fn [x] (* x x))
    
* loop sets up a recursion point, recur must be a tail call

    (loop [i 1]
      (if (<= i 10)
        (do (println "hello")
            (recur (+ i 1)))))
            
* Data structure literals

    * [1 2 3 4] ; vector
    * {"a" 1, "b" 2} ; map
    * \#{"a" "b"} ; set
    
* All built in data structures are immutable and persistent
