(ns nubank-exercise.logic.robot-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [nubank-exercise.logic.robot :as robot]
            [nubank-exercise.logic.grid :as grid]))

(facts "Be able to create a robot in a certain position and facing direction"
       (fact "No validations here, only the function to occupy a position with a robot facing a direction"
             (def grid_01 (robot/add-robot {:col 0 :row 0} (grid/new-grid) 2))
             ((aget grid_01 0 0) :tipo_elemento) => "robot"
             ((aget grid_01 0 0) :facing_side) => 2
             ((aget grid_01 0 0) :occupied?) => true
             ((aget grid_01 0 1) :tipo_elemento) => ""
             ((aget grid_01 0 1) :facing_side) => 0
             ((aget grid_01 0 1) :occupied?) => false))

(facts "Issue instructions to a robot - a robot can turn left, turn right"
       (fact "Need to retrieve the actual direction from a robot. No validations here, will get the facing_side position_information"
             (robot/get-robot-facing-side {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 2)) => 2
             (robot/get-robot-facing-side {:col 0 :row 1} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 2)) => 0)

       (fact "Need to know how to calculate if turned to the right"
             (robot/turn-right 1) => 2
             (robot/turn-right 2) => 3
             (robot/turn-right 3) => 4
             (robot/turn-right 4) => 1)
       (fact "Need to know how to calculate if turned to the lef"

             (robot/turn-left 1) => 4
             (robot/turn-left 2) => 1
             (robot/turn-left 3) => 2
             (robot/turn-left 4) => 3)

       (fact "Need to turn a robot direction. Will validate if the informed direction is left or right"
             ((aget (robot/turn-robot-facing-side {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 2) "right") 0 0) :facing_side) => 3
             ((aget (robot/turn-robot-facing-side {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 3) "right") 0 0) :facing_side) => 4
             ((aget (robot/turn-robot-facing-side {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 4) "left") 0 0) :facing_side) => 3
             ((aget (robot/turn-robot-facing-side {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 1) "left") 0 0) :facing_side) => 4
             ((aget (robot/turn-robot-facing-side {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 1) "right") 0 1) :facing_side) => 0
             (robot/turn-robot-facing-side {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 2) "WRONG_DIRECTION") => "Error: no valid direction informed. Accepted options -> [left|right]"))

(facts "Issue instructions to a robot - a robot can move forward, move backwards.
        Need to know the new position based on the direction the robot is facing and the movement direction. No validations here.
        The movement action needs to erase the old position, making it empty, and occupy the new position with the same position_information
        from the old position"
       (fact "Need to calculate the new position if movement is forward"
             (robot/new-position-forward-movement {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 2)) => {:col 1 :row 0}
             (robot/new-position-forward-movement {:col 23 :row 47} (robot/add-robot {:col 23 :row 47} (grid/new-grid) 3)) => {:col 23 :row 48}
             (robot/new-position-forward-movement {:col 38 :row 12} (robot/add-robot {:col 38 :row 12} (grid/new-grid) 1)) => {:col 38 :row 11}
             (robot/new-position-forward-movement {:col 48 :row 5} (robot/add-robot {:col 48 :row 5} (grid/new-grid) 4)) => {:col 47 :row 5}
             (robot/new-position-forward-movement {:col 48 :row 5} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 4)) => nil)

       (fact "Need to calculate the new position if movement is backward"
             (robot/new-position-backward-movement {:col 3 :row 7} (robot/add-robot {:col 3 :row 7} (grid/new-grid) 2)) => {:col 2 :row 7}
             (robot/new-position-backward-movement {:col 30 :row 6} (robot/add-robot {:col 30 :row 6} (grid/new-grid) 4)) => {:col 31 :row 6}
             (robot/new-position-backward-movement {:col 27 :row 17} (robot/add-robot {:col 27 :row 17} (grid/new-grid) 1)) => {:col 27 :row 18}
             (robot/new-position-backward-movement {:col 49 :row 49} (robot/add-robot {:col 49 :row 49} (grid/new-grid) 3)) => {:col 49 :row 48}
             (robot/new-position-backward-movement {:col 49 :row 49} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 3)) => nil)

       (fact "Need to move forward"
             ((aget (robot/move-robot-forward {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 2)) 1 0) :tipo_elemento) => "robot"
             ((aget (robot/move-robot-forward {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 2)) 0 0) :tipo_elemento) => "")

       (fact "Need to move backward"
             ((aget (robot/move-robot-backward {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 4)) 1 0) :tipo_elemento) => "robot"
             ((aget (robot/move-robot-backward {:col 0 :row 0} (robot/add-robot {:col 0 :row 0} (grid/new-grid) 4)) 0 0) :tipo_elemento) => ""))