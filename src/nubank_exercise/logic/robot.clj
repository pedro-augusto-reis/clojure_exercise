(ns nubank-exercise.logic.robot
  (:require [nubank-exercise.logic.grid :as grid]
            [nubank-exercise.util.message :as message]))

(defn add-robot
  "Purpose of the function: generate a new grid with a robot facing a direction
  in the specified position"
  [position_informed grid_informed facing_side]
  (grid/occupy-position
    position_informed
    grid_informed
    (grid/position-information "robot" facing_side true)))

;; //////////////////////////
;; facing direction functions
;; //////////////////////////
(defn get-robot-facing-side
  "Purpose of the function: retrieve the facing_side attribute from a position
  in the given grid"
  [position_informed grid_informed]
  ((aget grid_informed
         (get position_informed :col)
         (get position_informed :row)) :facing_side))

(defn turn-left
  "Purpose of the function: calculate the new direction value when turning
  to the left"
  [facing_side]
  (if (= facing_side 1)
    4
    (dec facing_side)))

(defn turn-right
  "Purpose of the function: calculate the new direction value when turning
  to the right"
  [facing_side]
  (if (= facing_side 4)
    1
    (inc facing_side)))

(defn turn-robot-facing-side
  "Purpose of the function: generate a new grid inserting a robot in the
  position informed with a new direction based on the turn_direction"
  [position_informed grid_informed turn_direction]
  (cond
    (= "right" turn_direction) (grid/occupy-position
                                 position_informed
                                 grid_informed
                                 (grid/position-information
                                   "robot"
                                   (turn-right (get-robot-facing-side position_informed grid_informed))
                                   true))
    (= "left" turn_direction) (grid/occupy-position
                                position_informed
                                grid_informed
                                (grid/position-information
                                  "robot"
                                  (turn-left (get-robot-facing-side position_informed grid_informed))
                                  true))
    :else message/_07))

;; //////////////////
;; movement functions
;; //////////////////

(defn new-position-forward-movement
  "Purpose of the function: calculate the new position value"
  [position_informed grid_informed]
  (let [robot_facing_side_let (get-robot-facing-side position_informed grid_informed)
        row_let               (get position_informed :row)
        col_let               (get position_informed :col)]
    (cond
      (= robot_facing_side_let 1) {:col col_let :row (dec row_let)}
      (= robot_facing_side_let 2) {:col (inc col_let) :row row_let}
      (= robot_facing_side_let 3) {:col col_let :row (inc row_let)}
      (= robot_facing_side_let 4) {:col (dec col_let) :row row_let})))

(defn new-position-backward-movement
  "Purpose of the function: calculate the new position value"
  [position_informed grid_informed]
  (let [robot_facing_side_let (get-robot-facing-side position_informed grid_informed)
        row_let               (get position_informed :row)
        col_let               (get position_informed :col)]
    (cond
      (= robot_facing_side_let 1) {:col col_let :row (inc row_let)}
      (= robot_facing_side_let 2) {:col (dec col_let) :row row_let}
      (= robot_facing_side_let 3) {:col col_let :row (dec row_let)}
      (= robot_facing_side_let 4) {:col (inc col_let) :row row_let})))

(defn move-robot-forward
  "Purpose of the function: generate a new grid inserting a robot in the
  new position and deleting the old position"
  [position_informed grid_informed]
  (let [facing_side_let ((aget grid_informed
                               (get position_informed :col)
                               (get position_informed :row)) :facing_side)]
    (grid/occupy-position
      (new-position-forward-movement
        position_informed
        grid_informed)
      (grid/empty-position position_informed grid_informed)
      (grid/position-information "robot" facing_side_let true))))

(defn move-robot-backward
  "Purpose of the function: generate a new grid inserting a robot in the
  new position and deleting the old position"
  [position_informed grid_informed]
  (let [facing_side_let ((aget grid_informed
                               (get position_informed :col)
                               (get position_informed :row)) :facing_side)]
    (grid/occupy-position
      (new-position-backward-movement
        position_informed
        grid_informed)
      (grid/empty-position position_informed grid_informed)
      (grid/position-information "robot" facing_side_let true))))