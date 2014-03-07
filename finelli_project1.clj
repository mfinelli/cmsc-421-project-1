;; Mario Finelli
;; CMSC 421 - Section 0201
;; Project 1 (Due 13 March 2014)

;; for each queen in the partial solution check if it is at the same row as
;; the new queen, then check if it conflicts vertically with the new queen
;; (number of columns to the end +/- current rank)
(defn qextends?
  "Returns true if a queen at rank extends partial-sol."
  [partial-sol rank]
    (for [queen partial-sol
          pos (range 1 (count partial-sol)) :while (< pos (count partial-sol))]
      (if (= queen rank)
        false
        (if (= (+ queen (- (count partial-sol) pos)) rank)
          false
          (if (= (- queen (- (count partial-sol) pos)) rank)
            false
            true)))))

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
