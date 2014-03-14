(load-file "finelli_project1.clj")
(use 'clojure.test)

;; problem 1: qextends?
(deftest qextends?-test1
  (is (qextends? [1 3] 5)))

(deftest qextends?-test2
  (is (qextends? [1 4] 2)))

(deftest qextends?-test3
  (is (not (qextends? [1 3] 2))))

;; problem 2: qextend
(deftest qextend-test1
  (is (= [[3 1]] (qextend 4 [[3]]))))

(deftest qextend-test2
  (is (= [[1 4 2] [1 3 5]] (qextend 5 [[1 3] [1 4]]))))

(deftest qextend-test3
  (is (= [] (qextend 4 [[1 3]]))))

;; problem 3: sol-count
(deftest sol-count-test1
  (is (= 1 (sol-count 1))))

(deftest sol-count-test2
  (is (= 0 (sol-count 2))))

(deftest sol-count-test3
  (is (= 0 (sol-count 3))))

(deftest sol-count-test4
  (is (= 10 (sol-count 5))))

(deftest sol-count-test5
  (is (= 92 (sol-count 8))))

;; problem 4: sol-density
(deftest sol-density-test1
  (is (= 0.0032 (sol-density 5))))

(run-tests)
