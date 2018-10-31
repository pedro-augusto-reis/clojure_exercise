(ns nubank-exercise.logic.grid)

(def rows 50)
(def cols 50)

(defn position-information
  "Purpose of the function: create the minor part of all the
  structure to be used in the grid"
  ([]
   (position-information "" 0 false))
  ([tipo_elemento facing_side occupied?]
   {:tipo_elemento tipo_elemento
    :facing_side   facing_side
    :occupied?     occupied?}))

(defn new-grid []
  (->> (position-information) (repeat cols) (repeat rows) (to-array-2d)))

(defn occupy-position
  "Purpose of the function: generate a new grid inserting the
  position_information in the specified position"
  [position_informed grid_informed position_information]
  (let [grid_let grid_informed]
    (aset grid_let
          (get position_informed :col)
          (get position_informed :row)
          (position-information
            (get position_information :tipo_elemento)
            (get position_information :facing_side)
            true))
    grid_let))

(defn empty-position
  "Purpose of the function: generate a new grid deleting the
  specified position to an empty position"
  [position_informed grid_informed]
  (if (= position_informed 0)
    grid_informed
    (let [grid_let grid_informed]
      (aset grid_let
            (get position_informed :col)
            (get position_informed :row)
            (position-information))
      grid_let)))

(defn get-adjacent-positions
  "Purpose of the function: calculate the adjacents positions,
  up, down, left and right, from a informed position"
  [position_informed]
  (let [col_let (get position_informed :col)
        row_let (get position_informed :row)]
    {:position_one   {:col col_let
                      :row (dec row_let)}
     :position_two   {:col (inc col_let)
                      :row row_let}
     :position_three {:col col_let
                      :row (inc row_let)}
     :position_four  {:col (dec col_let)
                      :row row_let}}))