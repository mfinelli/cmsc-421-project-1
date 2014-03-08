;; Mario Finelli
;; CMSC 421 - Section 0201
;; Project 1 (Due 13 March 2014)

;; for each queen in the partial solution check if it is at the same row as
;; the new queen, then check if it conflicts vertically with the new queen
;; (number of columns to the end +/- current rank)
(defn qextends?
  "Returns true if a queen at rank extends partial-sol."
  [partial-sol rank]
    (let [queen (first partial-sol)     ;; the queen we are checking against
          cnt (count partial-sol)       ;; how many queens in the solution
          rmn (vec (rest partial-sol))] ;; remaining queens after the first
      (if (zero? cnt) ;; we didn't run into any errors: return true
        true
        (if (= queen rank) ;; we can't have two queens in the same row
          false
          (if (= (+ queen cnt) rank) ;; conflict on the upper diagonal
            false
            (if (= (- queen cnt) rank) ;; conflict on the lower diagonal
              false
              (qextends? rmn rank))))))) ;; recurse the remaining queens

;; loop through the partial solutions and then through 1->n if that qextends
;; the partial solution then add it to the new partial solution list
(defn qextend
  "Given a vector *partial-sol-list* of all partial solutions of length k,
  returns a vector of all partial solutions of length k + 1."
  [n partial-sol-list]
    (vec 
      (remove nil? 
        (reduce into
          (reduce
            (fn [solutions partial-sol]
              (conj solutions 
                (map
                  (fn [i]
                    (if (qextends? partial-sol i)
                      (conj partial-sol i)
                      nil))
                  (range 1 (+ 1 n)))))
            []
            partial-sol-list)))))
  
(defn sol-count
  "Returns the total number of n-queens solutions on an n x n board."
  [n]
    nil)
  
(defn sol-density
  "Return the density of solutions on an n x n board."
  [n]
    nil)
