(defproject nubank_exercise "0.1.0-SNAPSHOT"
  :description "Nubank job application exercise - Robots VS Dinosaurs"
  :url "http://localhost:3000/"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]

                 ;; server and rest service API
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-devel "1.6.3"]
                 [compojure "1.6.1"]
                 [liberator "0.15.2"]
                 [org.clojure/data.json "0.2.6"]

                 ;; log
                 [com.taoensso/timbre "4.10.0"]

                 ;; unit test
                 [midje "1.9.2"]

                 ;; integration test
                 [ring/ring-mock "0.3.2"]]
  :plugins [[lein-ring "0.12.4"]
            [lein-midje "3.2.1"]]
  :ring {:handler nubank-exercise.server/handler}
  :profiles {
             :uberjar {
                       :aot  :all
                       :main nubank-exercise.server}})
