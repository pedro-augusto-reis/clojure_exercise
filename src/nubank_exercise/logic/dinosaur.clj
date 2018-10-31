(ns nubank-exercise.logic.dinosaur
  (:require [nubank-exercise.logic.grid :as grid]))

(defn add-dinosaur
  "Function purpose: generate a new grid with a dinosaur
  in the specified position"
  [position_informed grid_informed]
  (grid/occupy-position
    position_informed
    grid_informed
    (grid/position-information "dinosaur" 0 true)))