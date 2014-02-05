;; (load-file "dfs.clj")

(defn queue
  "Clojure lacks a queue literal; this is a hack.
   Create an empty queue with (queue); (queue 1 2) has elements 1 and 2"
  ([] clojure.lang.PersistentQueue/EMPTY)
  ([& items] (apply conj (queue) items)))

;; Use (vec some-queue) to convert queues to vectors
;; Vectors have a *literal* representation in the repl for easy examination.

;; Vectors act as stacks. Items get pushed on the end (right) and popped from the end.
;;
;; For queues, items get pushed on the end and popped from the front (left)

(def graph
  "A simple binary tree"
  {:a [:b :c]
   :b [:d :e]
   :c [:f :g]
   :d [:h :i]
   :e [:j :k]
   :f [:l :m]
   :g [:n :o]})

(defn expand
  "Lookup children of state in graph."
  [state]
    (graph state))

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

(defn dfs [initial-state max-depth]
  (let [frontier (vector initial-state)]
    (dorun max-depth
      (iterate search-step frontier))))

(defn bfs [initial-state max-depth]
  (let [frontier (queue initial-state)]
    (dorun max-depth
      (iterate search-step frontier))))

