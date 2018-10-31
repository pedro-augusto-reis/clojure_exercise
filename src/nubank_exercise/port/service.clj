(ns nubank-exercise.port.service
  (:require [liberator.core :refer [resource defresource]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [nubank-exercise.controller :as controller]
            [ring.util.response :as resp]))

(defroutes routes
           (GET "/" []
             (resp/resource-response "index.html" {:root "public"}))

           (GET "/grid-new" []
             (controller/new-grid!))

           (GET "/grid-display" []
             (resource :available-media-types ["application/json"]
                       :handle-ok (controller/display-grid!)))

           (GET "/robot-add" [col row direction]
             (controller/add-robot col row direction))

           (GET "/dinosaur-add" [col row]
             (controller/add-dinosaur col row))

           (GET "/robot-turn-direction" [col row direction]
             (controller/turn-robot col row direction))

           (GET "/robot-move" [col row direction]
             (controller/move-robot col row direction))

           (GET "/robot-attack" [col row]
             (controller/robot-attack col row))

           (route/not-found "<html><h1>404 - Page Not Found</h1></html>"))