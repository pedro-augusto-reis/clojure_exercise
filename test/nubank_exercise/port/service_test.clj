(ns nubank-exercise.port.service-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [nubank-exercise.server :as server]
            [nubank-exercise.util.message :as message]))

(deftest actions-flow
  (testing "Grid creation -> robot add -> dinosaur add -> robot created turn direction -> move robot created -> robot attack")
  (is (= (server/handler (mock/request :get "/grid-new"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_09}))
  (is (= (server/handler (mock/request :get "/robot-add?col=1&row=1&direction=1"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_11}))
  (is (= (server/handler (mock/request :get "/dinosaur-add?col=3&row=1"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_11}))
  (is (= (server/handler (mock/request :get "/robot-turn-direction?col=1&row=1&direction=right"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_11}))
  (is (= (server/handler (mock/request :get "/robot-move?col=1&row=1&direction=forward"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_11}))
  (is (= (server/handler (mock/request :get "/robot-attack?col=2&row=1"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_11}))

  (testing "second attack at the same position")
  (is (= (server/handler (mock/request :get "/robot-attack?col=2&row=1"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_06}))

  (is (= (server/handler (mock/request :get "/robot-move?col=2&row=1&direction=backward"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_11}))
  (server/handler (mock/request :get "/robot-move?col=1&row=1&direction=forward"))

  ; /////////////////////
  ; robot invalid actions
  ; /////////////////////
  (testing "turn direction to a non-robot and validate that old robot position (1,1) is empty after the movement move")
  (is (= (server/handler (mock/request :get "/robot-turn-direction?col=1&row=1&direction=right"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_01}))

  (testing "invalid direction to turn direction")
  (is (= (server/handler (mock/request :get "/robot-turn-direction?col=2&row=1&direction=364value"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_07}))

  (testing "move command to a non robot position")
  (is (= (server/handler (mock/request :get "/robot-move?col=1&row=1&direction=forward"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_01}))

  (testing "invalid direction to movement")
  (is (= (server/handler (mock/request :get "/robot-move?col=2&row=1&direction=878value"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_04}))

  (testing "giving attack command to a non robot position")
  (is (= (server/handler (mock/request :get "/robot-attack?col=1&row=1"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_01}))


  ; //////////////////////////////
  ; invalid add robot and dinosaur
  ; //////////////////////////////
  (testing "add robot to invalid positions and direction")
  (is (= (server/handler (mock/request :get "/robot-add?col=1&row=78&direction=1"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_05}))
  (is (= (server/handler (mock/request :get "/robot-add?col=2&row=3&direction=5"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_05}))
  (is (= (server/handler (mock/request :get "/robot-add?col=value&row=&direction=value"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_13}))
  (is (= (server/handler (mock/request :get "/robot-add?col=2&row=1&direction=2"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_05}))

  (testing "add dinosaur to invalid positions")
  (is (= (server/handler (mock/request :get "/dinosaur-add?col=1&row=99"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_02}))
  (is (= (server/handler (mock/request :get "/dinosaur-add?col=value&row=value"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_13}))
  (is (= (server/handler (mock/request :get "/dinosaur-add?col=2&row=1"))
         {:status  200
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body    message/_02})))