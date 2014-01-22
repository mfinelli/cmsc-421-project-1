(load-file "lastname_project1.clj")
(use 'clojure.test)

(deftest sol-count-test
  (is (= 10 (sol-count 5))))

(deftest extends?-test
  (is (qextends? [1 3] 5)))
  
#! Write your own tests here...
  
(run-tests)
