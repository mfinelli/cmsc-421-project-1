;; (load-file "dfs.clj")

(defn queue
  "Clojure lacks a queue literal; this is a hack."
  ([] clojure.lang.PersistentQueue/EMPTY)
  ([& items] (apply conj (queue) items)))

;; Use (vec some-queue) to convert queues to vectors
;; (Let's you examine queue elements at repl.)

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
  (graph state))

(defn search-step [frontier]

  (println "Current frontier:" (vec frontier))

  (let [state (peek frontier)
        new-frontier (if (empty? frontier)
                       nil
                       (pop frontier))
        new-states (expand state)]

    (println "Pop:" state)
    (println "Expand" new-states)
    (println "---")

    (into new-frontier new-states)))

;; Run by (search-step (queue :a)) or (search-step [a])

(defn dfs [initial-state max-depth]
  (let [frontier (vector initial-state)]
    (dorun max-depth
      (iterate search-step frontier))))

(defn bfs [initial-state max-depth]
  (let [frontier (queue initial-state)]
    (dorun max-depth
      (iterate search-step frontier))))

