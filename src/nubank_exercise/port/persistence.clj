(ns nubank-exercise.port.persistence)

(def global-grid (atom 0))

(defn update-grid-atom [grid_informed]
  (reset! global-grid grid_informed))