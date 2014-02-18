;; Clojure dfs and bfs search example.
;; To play with this example, execute:
;; (load-file "search.clj")
;; which will load search.clj into the repl

(defn queue
  "Clojure lacks a queue literal; this will act similar to (vector).
   Create an empty queue with (queue); e.g. (queue 1 2) has elements 1 and 2"
  ([] clojure.lang.PersistentQueue/EMPTY)
  ([& items] (apply conj (queue) items)))

;; Use (vec some-queue) to convert a queue to a vector.
;; Vectors have a literal representation in the repl for easy examination.
;; Queues look like: #<PersistentQueue clojure.lang.PersistentQueue@1>
;; (repl representation does not display elements.)
;;
;; Vectors can act as stacks. Items get pushed/popped from the end (right).
;; (vector 1 2 3) creates a vector. However, it's more convenient to type:
;; [1 2 3]
;; which is how vectors are normally displayed.
;;
;; Queues push items onto the end and pop from the front (left).
;; 
;; Lists (not used in this file) can also act as stacks.
;; Unlike vectors, lists push/pop from the left.
;;
;; (doc some-function) will provide more information on some-function.
;; http://clojuredocs.org/quickref/Clojure%20Core has docs and examples.

(def graph
  "A simple binary tree"
  {:a [:b :c]
   :b [:d :e]
   :c [:f :g]
   :d [:h :i]
   :e [:j :k]
   :f [:l :m]
   :g [:n :o]})

(defn expand [state]
  "Expand state. In this case, lookup the state in the hash-map graph."
  (get graph state))


;; There's a lot going on in this function, so I've added some comments below.
(defn search-step [frontier]
  "Conduct a single search step and print results.
   Behavior depends on whether frontier is a vector (stack) or queue."
  
  (let [state (peek frontier)
        new-frontier (if (empty? frontier)
                       nil
                       (pop frontier))
        new-states (expand state)]
    
    (println "Current frontier:" (vec frontier))
    (println "Pop:" state)
    (println "Expand:" new-states)
    (println "")

    (into new-frontier new-states)))

;; (let [bindings*] exprs*) creates local immutables. In the example above,
;; the local "state" gets assigned the value of (peek frontier)
;; (i.e. we are looking at the first item on the frontier)
;;
;; new-frontier gets assigned the value of the (if) expression. (Everything in
;; LISP/Clojure is an expression.) new-states is assigned (expand state)
;; (let) forms create bindings in sequence, so we can refer to earlier ones.
;;
;; (let) bindings are visible until the closing paren of the let form.
;; A rainbow parenthesis plugin for your text editor vcan help here, but good
;; indentation is often enough to follow the logic.
;;
;; Important note: (let) bindings are immutable! Once assigned, they cannot
;; be changed.


;; Recursive search (dfs and bfs) below:

(defn dfs [initial-state]
  (loop [frontier (vector initial-state)]
    (if (empty? frontier)
      nil
      (recur (search-step frontier)))))

;; (loop [bindings*] exprs*) is exactly like a let, but also acts as a recur
;; target. (recur) is like calling the original function recursively (but is
;; memory efficient).
;;
;; The (recur) expression must match the arity of the recursion point,
;; execution will jump back to the recursion point with new bindings.
;; In this case, (vector initial-state) is the only initial binding
;;
;; Note that function definitions can also act as recur target.

(defn bfs [initial-state]
  (loop [frontier (queue initial-state)]
    (if (empty? frontier)
      nil
      (recur (search-step frontier)))))

;; As you know, the only difference between bfs and dfs is how we take 
;; elements from the frontier. Since bfs starts the search with a queue,
;; the (pop frontier) will remove the first-in element. 


;; Some alternate ways of coding bfs/dfs are below. They involve a more 
;; advanced concept of 'laziness' (non-strict evaluation).
;; You are not expected to know this, but the code is provided in case you are
;; interested.


;; (iterate f x) returns the sequence x, f(x), f(f(x)), f(f(f(x))), ...
;; (iterate) generates lazy lists (lists that are not evaluated until needed)
;; (dorun) forces evaluation (for side-effects, e.g. printing)

;; dfs and bfs with a hard-coded depth of search

(defn dfs-depth-limited [initial-state max-depth]
  (let [frontier (vector initial-state)]
    (dorun max-depth
      (iterate search-step frontier))))

(defn bfs-depth-limited [initial-state max-depth]
  (let [frontier (queue initial-state)]
    (dorun max-depth
      (iterate search-step frontier))))

(def not-nil? (complement nil?))

;; dfs that stops searching when you hit nil
(defn dfs-lazy [initial-state]
  (let [frontier (vector initial-state)]
    (dorun
      (take-while not-nil?
        (iterate search-step frontier)))))


(defn bfs-lazy [initial-state]
  (let [frontier (queue initial-state)]
    (dorun
      (take-while not-nil?
        (iterate search-step frontier)))))


