(ns nubank-exercise.logic.core
  (:require [nubank-exercise.logic.robot :as robot]
            [nubank-exercise.logic.validation :as validation]
            [nubank-exercise.logic.dinosaur :as dinosaur]
            [nubank-exercise.logic.grid :as grid]
            [nubank-exercise.util.message :as message]))

;; ////////////////////////////////////
;; action to validate and move a robot
;; ///////////////////////////////////
(defn action-move-robot [position_informed grid_informed direction_informed]
  (if (and
        (validation/does-position-exist? position_informed)
        (validation/is-position-robot? position_informed grid_informed))
    (let [new_position_forward_movement_let  (robot/new-position-forward-movement position_informed grid_informed)
          new_position_backward_movement_let (robot/new-position-backward-movement position_informed grid_informed)
          validation_is_forward_possible?    (and (validation/does-position-exist? new_position_forward_movement_let)
                                                  (not (validation/is-position-occupied? new_position_forward_movement_let grid_informed)))
          validation_is_backward_possible?   (and (validation/does-position-exist? new_position_backward_movement_let)
                                                  (not (validation/is-position-occupied? new_position_backward_movement_let grid_informed)))]
      (cond
        (= "forward" direction_informed) (if validation_is_forward_possible?
                                           (robot/move-robot-forward position_informed grid_informed)
                                           message/_03)
        (= "backward" direction_informed) (if validation_is_backward_possible?
                                            (robot/move-robot-backward position_informed grid_informed)
                                            message/_03)
        :else message/_04))
    message/_01))


;; //////////////////////////////////////////
;; action to validate and make a robot attack
;; //////////////////////////////////////////
(defn action-robot-attack [position_informed grid_informed]
  (if (and
        (validation/does-position-exist? position_informed)
        (validation/is-position-robot? position_informed grid_informed))
    (let [adjacent_positions_let           (grid/get-adjacent-positions position_informed)
          validated_adjacent_positions_let (validation/validate-robot-attack-adjacent-positions adjacent_positions_let grid_informed)
          position_01_let                  (nth validated_adjacent_positions_let 0)
          position_02_let                  (nth validated_adjacent_positions_let 1)
          position_03_let                  (nth validated_adjacent_positions_let 2)
          position_04_let                  (nth validated_adjacent_positions_let 3)]
      (if (= 0 position_01_let position_02_let position_03_let position_04_let)
        message/_06
        (grid/empty-position position_01_let
                             (grid/empty-position
                               position_02_let
                               (grid/empty-position
                                 position_03_let
                                 (grid/empty-position
                                   position_04_let
                                   grid_informed))))))
    message/_01))

;; ////////////////////////////////////////////////
;; action to validate and then turn robot direction
;; ////////////////////////////////////////////////
(defn action-turn-robot
  "The turn direction action was made to work with numbers from 1 to 4
  The rotation must necessarily follow the numerical sequence 1 to 4, incrementally and clockwise, with position 1 pointing upwards
  RIGHT representation: 1 = up, 2 = right, 3 = down, 4 = left
  INCORRECT representation e.g. 1 = low, 2 = right, 3 = up, 4 = left
  |---1---|
  |-4   2-|
  |---3---|"
  [position_informed grid_informed turn_direction]
  (if (and
        (validation/does-position-exist? position_informed)
        (validation/is-position-robot? position_informed grid_informed))
    (robot/turn-robot-facing-side
      position_informed
      grid_informed
      turn_direction)
    message/_01))


;; ////////////////////////////////////////
;; action to validate and then create robot
;; ////////////////////////////////////////
(defn action-create-robot [position_informed grid_informed direction_informed]
  (if (and
        (validation/does-position-exist? position_informed)
        (not (validation/is-position-occupied? position_informed grid_informed))
        (validation/is-valid-direction? direction_informed))
    (robot/add-robot position_informed grid_informed direction_informed)
    message/_05))

;; ///////////////////////////////////////////
;; action to validate and then create dinosaur
;; ///////////////////////////////////////////
(defn action-create-dinosaur [position_informed grid_informed]
  (if (and
        (validation/does-position-exist? position_informed)
        (not (validation/is-position-occupied? position_informed grid_informed)))
    (dinosaur/add-dinosaur position_informed grid_informed)
    message/_02))