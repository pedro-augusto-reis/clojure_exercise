(ns nubank-exercise.port.logger
  (:require [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.core :as appenders]))

(defn appender []
  (timbre/merge-config!
    {:appenders {:println {:enabled? false}}})

  (timbre/merge-config!
    {:appenders {:spit (appenders/spit-appender {:fname "nubank-exercise.log"})}})

  (timbre/set-level! :error))