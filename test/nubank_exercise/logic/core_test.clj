(ns nubank-exercise.logic.core-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [nubank-exercise.logic.core :as core]
            [nubank-exercise.logic.grid :as grid]
            [clojure.string :as str]))

(facts "Be able to create a robot in a certain position and facing direction"
       (fact "Occupy an empty and valid position with a robot"
             ((aget (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2) 0 0) :tipo_elemento) => "robot"
             ((aget (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2) 0 0) :facing_side) => 2
             ((aget (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2) 0 0) :occupied?) => true)

       (fact "A position already occupied cannot be used to create a robot"
             (get (str/split (core/action-create-robot
                               {:row 0 :col 0}
                               (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2)
                               2) #" ") 0) => "Error:")

       (fact "The position must exist"
             (get (str/split (core/action-create-robot {:row -1 :col 77} (grid/new-grid) 2) #" ") 0) => "Error:")

       (fact "The direction must be a valid one"
             (get (str/split (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 5) #" ") 0) => "Error:"))

(facts "Be able to create a dinosaur in a certain position"
       (fact "Occupy an empty and valid position with a dinosaur"
             ((aget (core/action-create-dinosaur {:col 0 :row 0} (grid/new-grid)) 0 0) :tipo_elemento) => "dinosaur"
             ((aget (core/action-create-dinosaur {:col 0 :row 0} (grid/new-grid)) 0 0) :facing_side) => 0
             ((aget (core/action-create-dinosaur {:col 0 :row 0} (grid/new-grid)) 0 0) :occupied?) => true)

       (fact "A position already occupied cannot be used to create a dinosaur"
             (get (str/split (core/action-create-dinosaur
                               {:col 0 :row 0}
                               (core/action-create-dinosaur {:col 0 :row 0} (grid/new-grid))) #" ") 0) => "Error:")

       (fact "The position must exist"
             (get (str/split (core/action-create-dinosaur {:col 78 :row -45} (grid/new-grid)) #" ") 0) => "Error:"))

(facts "Robot - turning direction"
       (fact "Change direction from a robot"
             ((aget (core/action-turn-robot {:col 0 :row 0} (core/action-create-robot {:col 0 :row 0} (grid/new-grid) 2) "right") 0 0) :tipo_elemento) => "robot"
             ((aget (core/action-turn-robot {:col 0 :row 0} (core/action-create-robot {:col 0 :row 0} (grid/new-grid) 2) "right") 0 0) :facing_side) => 3
             ((aget (core/action-turn-robot {:col 0 :row 0} (core/action-create-robot {:col 0 :row 0} (grid/new-grid) 2) "left") 0 0) :facing_side) => 1
             ((aget (core/action-turn-robot {:col 0 :row 0} (core/action-create-robot {:col 0 :row 0} (grid/new-grid) 2) "right") 0 0) :occupied?) => true)

       (fact "It must be a robot to turn direction"
             (get (str/split (core/action-turn-robot {:col 0 :row 0} (grid/new-grid) "right") #" ") 0) => "Error:"
             (get (str/split (core/action-turn-robot {:col 0 :row 0} (core/action-create-dinosaur {:col 0 :row 0} (grid/new-grid)) "right") #" ") 0) => "Error:")

       (fact "It must be a valid position"
             (get (str/split (core/action-turn-robot {:col -49 :row 124} (core/action-create-robot {:col 0 :row 0} (grid/new-grid) 2) "right") #" ") 0) => "Error:")

       (fact "It must be a valid direction informed to turn"
             (get (str/split (core/action-turn-robot {:col 0 :row 0} (core/action-create-robot {:col 0 :row 0} (grid/new-grid) 2) "invalid_direction") #" ") 0) => "Error:"))

(facts "Robot - movement forward and backward"
       (fact "Move a robot, the action must be given to a robot position"
             ((aget (core/action-move-robot {:col 0 :row 0} (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2) "forward") 1 0) :tipo_elemento) => "robot"
             ((aget (core/action-move-robot {:col 0 :row 0} (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2) "forward") 1 0) :facing_side) => 2
             ((aget (core/action-move-robot {:col 0 :row 0} (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2) "forward") 1 0) :occupied?) => true
             ((aget (core/action-move-robot {:col 0 :row 0} (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2) "forward") 0 0) :tipo_elemento) => ""
             ((aget (core/action-move-robot {:col 0 :row 0} (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2) "forward") 0 0) :facing_side) => 0
             ((aget (core/action-move-robot {:col 0 :row 0} (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2) "forward") 0 0) :occupied?) => false)

       (fact "It must be a robot to move, dinosaur don't move"
             (get (str/split (core/action-move-robot {:col 0 :row 0} (grid/new-grid) "forward") #" ") 0) => "Error:"
             (get (str/split (core/action-move-robot {:col 0 :row 0} (core/action-create-dinosaur {:col 0 :row 0} (grid/new-grid)) "forward") #" ") 0) => "Error:")

       (fact "It must be a valid position"
             (get (str/split (core/action-move-robot {:col -7 :row 98} (grid/new-grid) "forward") #" ") 0) => "Error:")

       (fact "The new position that the robot will move must exist or be empty"
             (get (str/split (core/action-move-robot {:col 0 :row 0} (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 1) "forward") #" ") 0) => "Error:"
             (get (str/split (core/action-move-robot {:col 0 :row 0} (core/action-create-robot {:col 0 :row 0} (core/action-create-dinosaur {:col 1 :row 0} (grid/new-grid)) 2) "forward") #" ") 0) => "Error:"
             (get (str/split (core/action-move-robot {:col 0 :row 0} (core/action-create-robot {:col 0 :row 0} (core/action-create-robot {:col 1 :row 0} (grid/new-grid) 2) 2) "forward") #" ") 0) => "Error:")

       (fact "The direction must be a valid one"
             (get (str/split (core/action-move-robot {:col 0 :row 0} (core/action-create-robot {:row 0 :col 0} (grid/new-grid) 2) "invalid_direction") #" ") 0) => "Error:"))

(facts "Robot - attack"
       (fact "A robot attack destroys dinosaurs around it (in front, to the left, to the right or behind"
             ((aget (core/action-robot-attack {:col 1 :row 1} (core/action-create-robot {:col 1 :row 1} (core/action-create-robot {:col 1 :row 2} (core/action-create-dinosaur {:col 1 :row 0} (core/action-create-dinosaur {:col 2 :row 1} (core/action-create-dinosaur {:col 0 :row 1} (grid/new-grid)))) 2) 2)) 1 1) :tipo_elemento) => "robot"
             ((aget (core/action-robot-attack {:col 1 :row 1} (core/action-create-robot {:col 1 :row 1} (core/action-create-robot {:col 1 :row 2} (core/action-create-dinosaur {:col 1 :row 0} (core/action-create-dinosaur {:col 2 :row 1} (core/action-create-dinosaur {:col 0 :row 1} (grid/new-grid)))) 2) 2)) 1 0) :tipo_elemento) => ""
             ((aget (core/action-robot-attack {:col 1 :row 1} (core/action-create-robot {:col 1 :row 1} (core/action-create-robot {:col 1 :row 2} (core/action-create-dinosaur {:col 1 :row 0} (core/action-create-dinosaur {:col 2 :row 1} (core/action-create-dinosaur {:col 0 :row 1} (grid/new-grid)))) 2) 2)) 0 1) :tipo_elemento) => ""
             ((aget (core/action-robot-attack {:col 1 :row 1} (core/action-create-robot {:col 1 :row 1} (core/action-create-robot {:col 1 :row 2} (core/action-create-dinosaur {:col 1 :row 0} (core/action-create-dinosaur {:col 2 :row 1} (core/action-create-dinosaur {:col 0 :row 1} (grid/new-grid)))) 2) 2)) 2 1) :tipo_elemento) => ""
             ((aget (core/action-robot-attack {:col 1 :row 1} (core/action-create-robot {:col 1 :row 1} (core/action-create-robot {:col 1 :row 2} (core/action-create-dinosaur {:col 1 :row 0} (core/action-create-dinosaur {:col 2 :row 1} (core/action-create-dinosaur {:col 0 :row 1} (grid/new-grid)))) 2) 2)) 1 2) :tipo_elemento) => "robot")

       (fact "It must be a robot to attack"
             (get (str/split (core/action-robot-attack {:col 0 :row 0} (grid/new-grid)) #" ") 0) => "Error:")
       (get (str/split (core/action-robot-attack {:col 0 :row 0} (core/action-create-dinosaur {:col 0 :row 0} (grid/new-grid))) #" ") 0) => "Error:"

       (fact "The position informed to attack must be a valid one"
             (get (str/split (core/action-robot-attack {:col -1 :row 76} (grid/new-grid)) #" ") 0) => "Error:"))