(ns nubank-exercise.logic.validation-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [nubank-exercise.logic.validation :as validation]
            [nubank-exercise.logic.grid :as grid]
            [nubank-exercise.logic.robot :as robot]
            [nubank-exercise.logic.dinosaur :as dinosaur]))

(facts "The logic needs to validate some cases"
       (fact "Needs to know if a position corresponds to a robot"
             (validation/is-position-robot? {:col 0 :row 0} (grid/new-grid)) => false
             (validation/is-position-robot? {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 2)) => true)

       (fact "Needs to know if a position corresponds to a dinosaur"
             (validation/is-position-dinosaur? {:col 0 :row 0} (grid/new-grid)) => false
             (validation/is-position-dinosaur? {:col 0 :row 0} (dinosaur/add-dinosaur {:col 0 :row 0} (grid/new-grid))) => true)

       (fact "Needs to know if a position is occupied"
             (validation/is-position-occupied? {:col 0 :row 0} (grid/new-grid)) => false
             (validation/is-position-occupied? {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 2)) => true
             (validation/is-position-occupied? {:col 0 :row 0} (dinosaur/add-dinosaur {:col 0 :row 0} (grid/new-grid))) => true)

       (fact "Needs to know if a position exists. Positions exists based on the grid's size"
             (validation/does-position-exist? {:col 0 :row 0}) => true
             (validation/does-position-exist? {:col 49 :row 49}) => true
             (validation/does-position-exist? {:col -1 :row 67}) => false
             (validation/does-position-exist? {:col 50 :row 50}) => false)

       (fact "Needs to know if a direction number is a valid one. This API was constructed based in the use of directions using numbers from 1 to 4"
             (validation/is-valid-direction? 1) => true
             (validation/is-valid-direction? 2) => true
             (validation/is-valid-direction? 3) => true
             (validation/is-valid-direction? 4) => true
             (validation/is-valid-direction? 5) => false
             (validation/is-valid-direction? 0) => false)

       (fact "Needs to validate if a position will be deleted (empty) based on the robot attack"
             (validation/validate-robot-attack-adjacent-positions (grid/get-adjacent-positions {:col 1 :row 1}) (grid/new-grid)) => '(0 0 0 0)

             (validation/validate-robot-attack-adjacent-positions
               (grid/get-adjacent-positions {:col 1 :row 1})
               (dinosaur/add-dinosaur
                 {:col 0 :row 1}
                 (dinosaur/add-dinosaur
                   {:col 2 :row 1}
                   (grid/new-grid)))) => '(0 {:col 2 :row 1} 0 {:col 0 :row 1})

             (validation/validate-robot-attack-adjacent-positions
               (grid/get-adjacent-positions {:col 1 :row 1})
               (dinosaur/add-dinosaur {:col 1 :row 0}
                                      (dinosaur/add-dinosaur {:col 2 :row 1}
                                                             (dinosaur/add-dinosaur {:col 1 :row 2}
                                                                                    (dinosaur/add-dinosaur {:col 0 :row 1} (grid/new-grid)))))) => '({:col 1 :row 0}
                                                                                                                                                      {:col 2 :row 1}
                                                                                                                                                      {:col 1 :row 2}
                                                                                                                                                      {:col 0 :row 1})))