(ns nubank-exercise.server
  (:require [ring.middleware.params :refer [wrap-params]]
            [ring.adapter.jetty :refer [run-jetty]]
            [nubank-exercise.port.service :as service])
  (:gen-class))

(def handler
  (-> service/routes
      wrap-params))

(defn -main [& args]
  (run-jetty handler {:port (Integer/valueOf (or (System/getenv "port") "3000"))}))