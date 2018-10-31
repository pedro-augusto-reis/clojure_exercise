(ns nubank-exercise.adapter.service
  (:require [clojure.data.json :as json]))

(defn convert-grid-json [grid_informed]
  (json/write-str grid_informed))

(defn convert-col-row-to-hashmap [col_informed row_informed]
  {:col (Integer. col_informed)
   :row (Integer. row_informed)})

(defn is-valid-input? [col_informed row_informed direction_informed]
   (let [is_col_valid_let?       (and
                                   (not= 0 (count col_informed))
                                   (nil? (re-find #"\D+" col_informed)))
         is_row_valid_let?       (and
                                   (not= 0 (count row_informed))
                                   (nil? (re-find #"\D+" row_informed)))
         is_direction_valid_let? (and
                                   (not= 0 (count direction_informed))
                                   (nil? (re-find #"\D+" direction_informed)))]
     (and is_col_valid_let? is_row_valid_let? is_direction_valid_let?)))