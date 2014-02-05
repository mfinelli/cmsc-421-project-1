;; (load-file "dfs.clj")

(defn queue
  "Clojure lacks a queue literal; this is a hack.
   Create an empty queue with (queue); (queue 1 2) has elements 1 and 2"
  ([] clojure.lang.PersistentQueue/EMPTY)
  ([& items] (apply conj (queue) items)))

;; Use (vec some-queue) to convert queues to vectors.
;; Vectors have a *literal* representation in the repl for easy examination.
;;
;; Vectors act as stacks. Items get pushed on the end (right).
;;
;; Queues push on right and pop from (left)

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
  "Expand state. (In this case, lookup the key state in the hash-map graph.)"
  (get graph state))

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

;; initialize with (search-step (queue :a)) or (search-step [:a])

(defn dfs-depth-limited [initial-state max-depth]
  (let [frontier (vector initial-state)]
    (dorun max-depth
      (iterate search-step frontier))))

(defn bfs-depth-limited [initial-state max-depth]
  (let [frontier (queue initial-state)]
    (dorun max-depth
      (iterate search-step frontier))))

(def not-nil? (complement nil?))

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

;;;

(defn dfs [initial-state]
  (loop [frontier (vector initial-state)]
    (if (empty? frontier)
      nil
      (recur (search-step frontier)))))

;; (loop [bindings*] exprs*) acts as a recur target
;; The (recur) expression must match the arity of the recursion point,
;; execution will jump back to the recursion point with new bindings.
;; In this case, (vector initial-state) is the only initial binding
;;
;; Note that function definitions also act as recur target

(defn bfs [initial-state]
  (loop [frontier (queue initial-state)]
    (if (empty? frontier)
      nil
      (recur (search-step frontier)))))

