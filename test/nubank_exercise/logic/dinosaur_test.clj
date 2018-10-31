(ns nubank-exercise.logic.dinosaur-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [nubank-exercise.logic.dinosaur :as dinosaur]
            [nubank-exercise.logic.grid :as grid]))

(facts "Be able to create a dinosaur in a certain position"
       (fact "No validations here, only the function to occupy a position with a dinosaur"
             (def grid_01 (dinosaur/add-dinosaur {:col 0 :row 0} (grid/new-grid)))
             (def grid_02 (dinosaur/add-dinosaur {:col 49 :row 49} (grid/new-grid)))
             ((aget grid_01 0 0) :tipo_elemento) => "dinosaur"
             ((aget grid_01 0 0) :facing_side) => 0
             ((aget grid_01 0 0) :occupied?) => true
             ((aget grid_02 49 49) :tipo_elemento) => "dinosaur"
             ((aget grid_02 49 49) :facing_side) => 0
             ((aget grid_02 49 49) :occupied?) => true
             ((aget grid_01 20 20) :tipo_elemento) => ""
             ((aget grid_01 20 20) :facing_side) => 0
             ((aget grid_01 20 20) :occupied?) => false))