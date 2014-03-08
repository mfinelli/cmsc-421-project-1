;; Mario Finelli
;; CMSC 421 - Section 0201
;; Project 1 (Due 13 March 2014)

;; for each queen in the partial solution check if it is at the same row as
;; the new queen, then check if it conflicts vertically with the new queen
;; (number of columns to the end +/- current rank)
(defn qextends?
  "Returns true if a queen at rank extends partial-sol."
  [partial-sol rank]
    (let [queen (first partial-sol) 
          cnt (count partial-sol)
          rem (vec (rest partial-sol))]
      (if (zero? cnt)
        true
        (if (= queen rank)
          false
          (if (= (+ queen cnt) rank)
            false
            (if (= (- queen cnt) rank)
              false
              (qextends? rem rank)))))))

(defn qextend
  "Given a vector *partial-sol-list* of all partial solutions of length k,
  returns a vector of all partial solutions of length k + 1."
  [n partial-sol-list]
    nil)
  
(defn sol-count
  "Returns the total number of n-queens solutions on an n x n board."
  [n]
    nil)
  
(defn sol-density
  "Return the density of solutions on an n x n board."
  [n]
    nil)
