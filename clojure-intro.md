Clojure Guide
=============

> \[from "LISt Processing language", but mythically from "Lots of Irritating Superfluous Parentheses"\] AI's mother tongue, a language based on the ideas of (a) variable-length lists and trees as fundamental data types, and (b) the interpretation of code as data and vice-versa. Invented by John McCarthy at MIT in the late 1950s, it is actually older than any other HLL still in use except FORTRAN. Accordingly, it has undergone considerable adaptive radiation over the years; modern variants are quite different in detail from the original LISP 1.5. The dominant HLL among hackers until the early 1980s, LISP has since shared the throne with C. Its partisans claim it is the only language that is truly beautiful.
>
> -- The [Jargon File](http://www.catb.org/jargon/html/L/LISP.html) on LISP

Clojure is a modern version of LISP; in the event that you already know some Common Lisp or Scheme, a quick look at [http://clojure.org/lisps](http://clojure.org/lisps) and the "Getting Clojure" section below should be enough to get started.

Otherwise, keep reading...

Getting Clojure
---------------

Note: If you're running Windows and don't have [cygwin](http://cygwin.com/) installed, I *strongly* recommend installing cygwin first (including either the wget or curl package).

[Leiningen](http://leiningen.org/#install) is the easiest way to get/use Clojure. Download the script, place it somewhere on your $PATH and set it to be executable. Step by step terminal instructions below:

    mkdir ~/bin
    cd ~/bin
    
    # use either wget or curl to get lein
    wget https://raw.github.com/technomancy/leiningen/stable/bin/lein
    curl -o lein https://raw.github.com/technomancy/leiningen/stable/bin/lein
    
    chmod a+x ~/bin/lein
    lein self-install
    
Place the following in your ~/.lein/profiles.clj (create profiles.clj if necessary). You don't *need* to do this right away, but you might as well set it up now.

    {:user {:plugins [[lein-exec "0.3.1"]]
        :dependencies [[org.clojure/core.logic "0.8.5"]]}}

To see if everything worked, run

    lein repl
    
(It may be a bit slow the first time, as lein has to download all of it's dependencies.) When you see a prompt, try entering

    (println "Hello!")
    
You should see

    user=> (println "Hello!")
    Hello!
    nil
    
Use Control+D or (exit) or (quit) to leave the REPL.

Writing Clojure
---------------

Most experienced Clojurians use Emacs. Some use Vim. A few use Sublime Text or Textmate.

Just don't use Notepad.

In all seriousness, LISP programming is *much* easier with a good text editor - syntax highlighting and rainbow parentheses will make it much easier to understand Clojure code, so install appropriate plugins if you can.

Understanding Clojure
---------------------

### Syntax ###

Roughly speaking, everything in Clojure is of the following form

    (function-name arg1 arg2 ...)
    
and everything returns a value (functions that are called only for their side-effects typically return nil, which is similar to Java's null or Python's None). It may help to think of this as

    function-name(arg1, arg2, ...)
    
Clojure uses prefix notation, rather than typical infix notation. For example, in Java, one might write:

    a + b * c
    
which is really

    a + (b * c)
    
due to operator precedence. In Clojure, the equivalent is

    (+ a (* b c))
    
### REPL ###

To start a REPL, enter "lein repl" at the terminal. This will display a prompt of "user=> " (I recommend experimenting with all of these examples as you read this tutorial.)

Documentation can be called up interactively in the REPL. To view documentation on *name*, enter (doc name)

"def" is a special form that creates a var. For example:

    user=> (def n 5)
    
binds the value 5 to the new variable n, allowing the programmer to refer to it later.

    user=> (* n 3)
    15
    
Note that a variable bound by def is a global variable. For locals, use let

    user=> (let [x 1
                 y x]
             y + 1)
    2
    

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
    
you'll get back a list that contains the list (+ 1 2). To evaluate the elements of the list, use (list) instead:

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

Vectors are generally more useful then lists; they support efficient lookup by index. A vector literal uses square brackets, e.g. [1 1 2 3 5].

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

Maps support (count) (conj) (assoc) (get) (contains?) and are functions of their keys.

#### Sets ####

Sets are collections of unique values

    user=> (def s #{1 2 3})
    
Sets support 'removal' with disj

    user=> (disj s 1)
    #{2 3}

Sets support (conj) and (contains?)
    
More information on data structures here:

http://clojure.org/data_structures

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
    
(partial) is a higher-order function that creates a new function from an existing one with some of it's parameters instantiated. For example, suppose we want a function that always adds one

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

But don't try it at the REPL. (iterate) produces an *infinite* **lazy** sequence. Calling (iterate inc 1) is requesting the infinite sequence (1 (inc 1) (inc (inc 1)) ...) i.e. a list of all the natural numbers.

The REPL forces evaluation, so (iterate inc
    
Conditionals
------------

Clojure considers nil and false to be false. *Everything else* (including zero) is true. (if) much like you'd expect

    (def is-weekend true)
    
    (if is-weekend
      (println "play")
      (do (println "work")
          (println "sleep")))
          
Although the (do) form is necessary since (if) has to be of the form

    (if condition
      then-expr
      else-expr)

(do) "wraps" several expressions as a single expression.

### Predicates ###

Predicates are functions that test a condition. Examples include

    <, <=, =, not=, >, >=, ando, or, not, true?, false?, nil?
    
Some predicates work on sequences

    empty?, not-empty, every?, not-every?, some, not-any?
    
And some test numbers

    even?, neg?, odd?, pos?, zero?

See (doc a-predicate) for more information.

Iteration
---------

(for) performs list comprehension and supports optional filtering using :when and :while



Recursion
---------


Help with Clojure
-----------------

A nicer API (with examples) is available online:

http://clojuredocs.org/clojure_core

A more comprehensive introduction to Clojure:

http://java.ociweb.com/mark/clojure/article.html

A more concise guide to Clojure:

http://www.cis.upenn.edu/~matuszek/Concise%20Guides/Concise%20Clojure.html
