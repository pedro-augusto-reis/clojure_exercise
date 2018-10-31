(ns nubank-exercise.controller
  (:require [nubank-exercise.port.persistence :as persistence]
            [nubank-exercise.logic.grid :as grid]
            [nubank-exercise.adapter.service :as service]
            [nubank-exercise.logic.core :as core]
            [nubank-exercise.port.logger :as logger]
            [taoensso.timbre :as log]
            [nubank-exercise.util.message :as message]))

(logger/appender)

(defn controller-core! [function_informed col_informed row_informed direction_informed]
  (try
    (if (service/is-valid-input? col_informed row_informed direction_informed)
      (if (not= 0 (deref persistence/global-grid))
        (let [new_grid_let (function_informed)]
          (if (= java.lang.String (type new_grid_let))
            new_grid_let
            (do
              (persistence/update-grid-atom new_grid_let)
              message/_11)))
        message/_10)
      message/_13)
    (catch Exception e
      (log/debug "Parameters: " col_informed ", " row_informed ", " direction_informed)
      (log/error e)
      message/_08)))

(defn new-grid! []
  (try
    (persistence/update-grid-atom (grid/new-grid))
    message/_09
    (catch Exception e
      (log/error e)
      message/_08)))

(defn display-grid! []
  (try
    (let [grid_converted_json_let (service/convert-grid-json (deref persistence/global-grid))]
      (if (= "0" grid_converted_json_let)
        message/_10
        grid_converted_json_let))
    (catch Exception e
      (log/error e)
      message/_08)))

(defn add-robot [col_informed row_informed direction_informed]
  (controller-core! #(core/action-create-robot
                         (service/convert-col-row-to-hashmap col_informed row_informed)
                         (deref persistence/global-grid)
                         (Integer. direction_informed))
                      col_informed row_informed direction_informed))

(defn add-dinosaur [col_informed row_informed]
  (controller-core! #(core/action-create-dinosaur
                         (service/convert-col-row-to-hashmap col_informed row_informed)
                         (deref persistence/global-grid))
                      col_informed row_informed "1"))

(defn turn-robot [col_informed row_informed turn_direction]
  (controller-core! #(core/action-turn-robot
                         (service/convert-col-row-to-hashmap col_informed row_informed)
                         (deref persistence/global-grid)
                         turn_direction)
                      col_informed row_informed "1"))

(defn move-robot [col_informed row_informed direction_informed]
  (controller-core! #(core/action-move-robot
                         (service/convert-col-row-to-hashmap col_informed row_informed)
                         (deref persistence/global-grid)
                         direction_informed)
                      col_informed row_informed "1"))

(defn robot-attack [col_informed row_informed]
  (controller-core! #(core/action-robot-attack
                         (service/convert-col-row-to-hashmap col_informed row_informed)
                         (deref persistence/global-grid))
                      col_informed row_informed "1"))