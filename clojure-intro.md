Clojure Guide
=============

> \[from "LISt Processing language", but mythically from "Lots of Irritating Superfluous Parentheses"\] AI's mother tongue, a language based on the ideas of (a) variable-length lists and trees as fundamental data types, and (b) the interpretation of code as data and vice-versa. Invented by John McCarthy at MIT in the late 1950s, it is actually older than any other HLL still in use except FORTRAN. Accordingly, it has undergone considerable adaptive radiation over the years; modern variants are quite different in detail from the original LISP 1.5. The dominant HLL among hackers until the early 1980s, LISP has since shared the throne with C. Its partisans claim it is the only language that is truly beautiful.
>
> -- The [Jargon File](http://www.catb.org/jargon/html/L/LISP.html) on LISP

Clojure is a modern version of LISP; in the event that you already know some Common Lisp or Scheme, a quick look at [http://clojure.org/lisps](http://clojure.org/lisps) and the "Getting Clojure" section below should be enough to get started.

Otherwise, keep reading...

Getting Clojure
---------------

Note: If you're running Windows and don't have [cygwin](http://cygwin.com/) installed, I *strongly* recommend installing cygwin first (including either the wget or curl package) and running all of the following commands from the cygwin terminal.

[Leiningen](http://leiningen.org/#install) is the easiest way to get/use Clojure. Download the script, place it somewhere on your $PATH, set it to be executable and run "lein self-install". Step by step terminal instructions below:

    mkdir ~/bin
    cd ~/bin
    
    # use either wget or curl to get lein
    wget https://raw.github.com/technomancy/leiningen/stable/bin/lein
    curl -o lein https://raw.github.com/technomancy/leiningen/stable/bin/lein
    
    chmod a+x ~/bin/lein
    lein self-install
    
To check the installation, start up a read-eval-print-loop with

    lein repl
    
(It may be a bit slow the first time, as lein has to download all of it's dependencies.) When you see a prompt, try entering

    (println "Hello!")
    
You should see

    user=> (println "Hello!")
    Hello!
    nil
    
Use Control+D or (exit) or (quit) to leave the REPL.
    
In addition, place the following in your ~/.lein/profiles.clj (create profiles.clj if necessary). You don't *need* to do this right away, but it will be useful later.

    {:user {:plugins [[lein-exec "0.3.1"]]
            :dependencies [[org.clojure/core.logic "0.8.5"]]}}

To check if dependencies are working correctly, try:
    
    user=> (require 'clojure.core.logic)
    nil


Writing Clojure
---------------

Most experienced Clojurians use Emacs (clojure-mode) or Vim (vim-clojure-static, which ships with Vim versions 7.3.803 and later). Other popular text editors include Sublime Text and Textmate.

LISP programming is *much* easier with good text editor support. You may also want to consider installing a rainbow parentheses plugin for your editor.

Understanding Clojure
---------------------

### Syntax ###

Roughly speaking, everything in Clojure is of the following form

    (function-name arg1 arg2 ...)
    
and everything returns a value (functions that are called only for their side-effects typically return nil, which is similar to Java's null or Python's None). It may help to think of the above as

    function-name(arg1, arg2, ...)
    
Clojure uses prefix notation, rather than typical infix notation. For example, in Java, one might write:

    a + b * c
    
which is really

    a + (b * c)
    
due to operator precedence. In Clojure, the equivalent is

    (+ a (* b c))
    
### REPL ###

To start a REPL, enter "lein repl" at the terminal. This will display a user prompt: "user=>" (I recommend experimenting with all of these examples as you read this tutorial.)

Documentation can be called up interactively in the REPL. To view documentation on *name*, enter (doc name)

(def) is a special form that creates a var. For example:

    user=> (def n 5)
    
binds the value 5 to the new variable n, allowing the programmer to refer to it later.

    user=> (* n 3)
    15
    
Note that a variable bound by def is a global variable. For locals, use let

    user=> (let [x 1
                 y x]
             y + 1)
    2
    
However, locals created in a let are not variables. Once created, their values never change (similar to a Java final variable).
    
### Data Structures ###

Clojure has literal support for (singly-linked) lists, vectors, sets and maps. Unlike most languages, all of Clojure's built-in collections are *immutable* (i.e. they can't be modified). Functions that act on Clojure's data structures actually return *new* data structures representing the appropriate action. Thanks to the programming sorcery of [persistence](https://en.wikipedia.org/wiki/Persistent_data_structure), this is all done in a time and memory-efficient manner.

#### Lists ####

You've actually already seen lists

    (+ 1 2)

is a list containing the + function, 1 and 2 (list elements don't have to have the same type in Clojure). However, when you enter this at the REPL, Clojure attempts to evaluate it. To hold evaluation, use a single quote

    user=> '(+ 1 2)
    (+ 1 2)

A word of warning though, quote (') *completely* holds evaluation, so if you quote

    user=> '(1 (+ 1 2) 3)
    (1 (+ 1 2) 3)
    
you'll get back a list that *contains* the list (+ 1 2). To evaluate the elements of the list, use (list) instead:

    user=> (list 1 (+ 1 2) 3)
    (1 3 3)

To get the size of the list, use (count)

    user=> (count '(1 2 3))
    3
    
To 'add' to a list (remember, you're not actually modifying the list, but rather creating a *new* list, with the new element added), use (conj) which adds to the front of a list

    user=> (conj '(1 2 3) 4)
    (4 1 2 3)
    
(peek) and (pop) return the first element of a list and a list with it's first element removed, respectively.
    
#### Vectors ####

Vectors are generally more useful then lists; they support efficient lookup by index (Clojure vectors are indexed from 0). A vector literal uses square brackets.

    user=> (nth [1 2 3 5] 2)
    3
    
Vectors are also *functions* of their indices and look up in themselves as if by nth, e.g.

    user=> ([2 4 6 8] 0)
    2

To 'change' a vector at a certain index, use (assoc)

    user=> (assoc [1 2 3] 2 4)
    [1 2 4]
    
Vectors also support (conj) (peek) and (pop) but act on the end of the vector instead of the front.

#### Maps ####

Maps associate keys with values. Map literals use curly braces

    {"a" 1, "b" 2}

Note that in Clojure, the comma is actually *whitespace* and is only used to make code easier to read.

Some of the functions maps support include (count) (conj) (assoc) (dissoc) (get) (contains?) Maps are also functions of their keys.

#### Sets ####

Sets are collections of unique values

    user=> (def s #{1 2 3})
    
Sets support 'removal' with disj

    user=> (disj s 1)
    #{2 3}

Sets support (conj) and (contains?)

#### More Information ####

More information on data structures:

[http://clojure.org/data_structures](http://clojure.org/data_structures)

### Functions ###

(defn) defines a function.

    (defn say-hello
      "Documentation string"
      [name]
      (println "Hello," name))
      
    user=> (say-hello "Josh")
    Hello, Josh!
    nil
      
Note that all functions return a value. In this case, nil, since (println) returns nil.

(fn) defines an anonymous function. Anonymous functions can be passed as arguments to higher order functions. For example

    user=> (def some-numbers [1 1 2 3 5 8 11])
    user=> (filter (fn [num] (even? num)) some-numbers)
    (2 8)
    
(partial) is a higher-order function that creates a new function from an existing one with some of its parameters instantiated. For example, suppose we want a function that always adds one

    user=> (def add-one (partial + 1))
    user=> (add-one 5)
    6

This is actually already implemented in clojure as (inc). Other useful higher order functions include (map)

    user=> (map inc [1 2 3 4 5])
    (2 3 4 5 6)

And (reduce) which folds a binary function across a sequence

    user=> (reduce + [1 2 3 4])
    10
    user=> (reduce * [1 2 3 4])
    24

Of particular interest is (iterate). (iterate f x) returns a sequence of x, (f x), (f (f x)), etc...

But don't try it at the REPL. (iterate) produces an *infinite* **lazy** sequence. Calling (iterate inc 1) is requesting the infinite sequence 1, (inc 1), (inc (inc 1)) ... i.e. a list of all the natural numbers.

The REPL forces evaluation (so it can print results), so this is an endless loop and the REPL will hang. However, you can (take) the first few elements of the list

    user=> (take 5 (iterate inc 1))
    (1 2 3 4 5)
    
Or just request the (nth)

    user=> (nth (iterate inc 1) 5)
    6
    
Of course, (iterate) isn't *actually* producing an infinitely long list. Think of it more as a "promise" to create elements as necessary.

(range) works similarly. (range start end) produces a lazy list starting at *start* and increasing by 1 until *end*. But (range) with *no* arguments results in a lazy list that goes from 0 to infinity. (Again, don't try it at the REPL).

A lot of Clojure's built-in functions actually return lazy sequences. However, (nth) forces evaluation, and lazy sequences can be converted to a particular implementation as needed.

More information:

[http://clojure.org/sequences](http://clojure.org/sequences)
    
Conditionals
------------

Clojure considers nil and false to be false. *Everything else* (including zero) is true. (if) works much like you'd expect

    (def is-weekend true)
    
    (if is-weekend
      (println "play")
      (do (println "work")
          (println "sleep")))
          
Here, the (do) form is necessary since (if) has to be of the form

    (if condition
      then-expr
      else-expr)

(do) "wraps" several expressions as a single expression and executes them in order. It returns the value of the last expression evaluated

### Predicates ###

Predicates are functions that test a condition. Examples include

    <, <=, =, not=, >, >=, ando, or, not, true?, false?, nil?
    
Some predicates work on sequences

    empty?, not-empty, every?, not-every?, some, not-any?
    
And some test numbers

    even?, neg?, odd?, pos?, zero?

See (doc some-predicate) for more information.

Iteration
---------

(for) performs list comprehension and supports optional filtering using :when and :while. Consider the following code:

    (def files ["a" "b" "c" "d" "e" "f" "g" "h"])
    (def ranks (range 1 (inc 8)))
    
    (for [file files :when (not= file "c")
          rank ranks :while (< rank 3)]
      (str file rank))
      
This produces a *lazy* sequence (although it you'll see an actual result in the REPL, since the REPL forces evaluation). But if we want a 'concrete' sequence (for, say, efficient random access), we can convert it to a vector with vec

    (vec
      (for [file files :when (not= file "c")
            rank ranks :while (< rank 3)]
        (str file rank)))

This returns:

    ["a1" "a2" "b1" "b2" "d1" "d2" "e1" "e2" "f1" "f2" "g1" "g2" "h1" "h2"]
        
Recursion
---------

See [recursion](http://java.ociweb.com/mark/clojure/article.html#Recursion).

(That link *actually* links to a good explanation of how recursion works in Clojure. But you shouldn't need it for projects 1 or 2 and projects 3 and 4 will probably be in Python.)

Help with Clojure
-----------------

A nicer API (with examples) is available online:

[http://clojuredocs.org/clojure_core](http://clojuredocs.org/clojure_core)

A more comprehensive introduction to Clojure:

[http://java.ociweb.com/mark/clojure/article.html](http://java.ociweb.com/mark/clojure/article.html)

A more concise introduction to Clojure:

[http://www.cis.upenn.edu/~matuszek/Concise%20Guides/Concise%20Clojure.html](http://www.cis.upenn.edu/~matuszek/Concise%20Guides/Concise%20Clojure.html)

The official Clojure cheet sheet:

[http://clojure.org/cheatsheet](http://clojure.org/cheatsheet)
