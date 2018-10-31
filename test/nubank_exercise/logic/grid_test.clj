(ns nubank-exercise.logic.grid-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [nubank-exercise.logic.grid :as grid]))

(facts "Be able to create an empty simulation space - an empty 50 x 50 grid"
       (fact "Minor part is a position_information"
             ((grid/position-information) :tipo_elemento) => ""
             ((grid/position-information) :facing_side) => 0
             ((grid/position-information) :occupied?) => false
             ((grid/position-information "robot" 2 true) :tipo_elemento) => "robot"
             ((grid/position-information "robot" 2 true) :facing_side) => 2
             ((grid/position-information "robot" 2 true) :occupied?) => true
             ((grid/position-information "dinosaur" 0 true) :tipo_elemento) => "dinosaur"
             ((grid/position-information "dinosaur" 0 true) :facing_side) => 0
             ((grid/position-information "dinosaur" 0 true) :occupied?) => true)

       (fact "Must be a 50x50 grid"
             grid/cols => 50
             grid/rows => 50
             (alength (grid/new-grid)) => 50)

       (fact "Must be an empty simulation space"
             (for [x (range grid/cols)]
               (for [y (range grid/rows)]
                 ((aget (grid/new-grid) x y) :occupied?))) => (->> false (repeat 50) (repeat 50))))

(facts "Related functions to the grid"
       (fact "Creating the minor part to include a new element in the grid. No rules/validations applied, only needs to occupy a position with a position information"
             (def grid_01 (grid/occupy-position {:col 0 :row 0} (grid/new-grid) (grid/position-information "robot" 2 true)))
             ((aget grid_01 0 0) :tipo_elemento) => "robot"
             ((aget grid_01 0 0) :facing_side) => 2
             ((aget grid_01 0 0) :occupied?) => true
             ((aget grid_01 0 1) :tipo_elemento) => ""
             ((aget grid_01 0 1) :facing_side) => 0
             ((aget grid_01 0 1) :occupied?) => false)

       (fact "Creating the minor part to exclude an element in the grid. No rules/validations applied, only needs to take an element out and put an empty position information"
             (def grid_02 (grid/empty-position {:col 0 :row 0} (grid/occupy-position {:col 0 :row 0} (grid/new-grid) (grid/position-information "robot" 2 true))))
             ((aget grid_02 0 0) :tipo_elemento) => ""
             ((aget grid_02 0 0) :facing_side) => 0
             ((aget grid_02 0 0) :occupied?) => false)

       (fact "Needs to retrieve adjacent position from an specified position. Adjacents are up, right, down, left. No validations here."
             (grid/get-adjacent-positions {:col 0 :row 0}) => {:position_one   {:col 0 :row -1}
                                                               :position_two   {:col 1 :row 0}
                                                               :position_three {:col 0 :row 1}
                                                               :position_four  {:col -1 :row 0}}
             (grid/get-adjacent-positions {:col 27 :row 32}) => {:position_four  {:col 26 :row 32}
                                                                 :position_one   {:col 27 :row 31}
                                                                 :position_three {:col 27 :row 33}
                                                                 :position_two   {:col 28 :row 32}}))