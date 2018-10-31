(ns nubank-exercise.logic.validation
  (:require [nubank-exercise.logic.grid :as grid]))

(defn is-position-robot? [position_informed grid_informed]
  (= "robot"
     ((aget grid_informed
            (get position_informed :col)
            (get position_informed :row)) :tipo_elemento)))

(defn is-position-dinosaur? [position_informed grid_informed]
  (= "dinosaur"
     ((aget grid_informed
            (get position_informed :col)
            (get position_informed :row)) :tipo_elemento)))

(defn is-position-occupied? [position_informed grid_informed]
  ((aget grid_informed
         (get position_informed :col)
         (get position_informed :row)) :occupied?))

(defn does-position-exist? [position_informed]
  (let [col_let (get position_informed :col)
        row_let (get position_informed :row)]
    (=
      (<= col_let (dec grid/cols))
      (<= row_let (dec grid/rows))
      (>= col_let 0)
      (>= row_let 0))))

(defn validate-robot-attack-adjacent-positions
  "Purpose of the function: validate positions received to determine
  if the positions can be deleted"
  [positions_informed grid_informed]
  (for [index_for [:position_one :position_two :position_three :position_four]]
    (if (and
          (does-position-exist? (get positions_informed index_for))
          (is-position-dinosaur? (get positions_informed index_for) grid_informed))
      (get positions_informed index_for)
      0)))

(defn is-valid-direction? [direction_informed]
  (or
    (= 1 direction_informed)
    (= 2 direction_informed)
    (= 3 direction_informed)
    (= 4 direction_informed)))