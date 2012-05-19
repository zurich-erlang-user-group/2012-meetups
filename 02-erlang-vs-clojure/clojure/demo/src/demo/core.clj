(ns demo.core
  (:use [clojure.core.match :only [match]]))

;; Pattern matching

(defn numbers [x]
  (match [x]
	 [1] :one
	 [2] :two
	 [_] :many))

;; Definer defm macro 
(defmacro defm [name args & body]
  `(defn ~name ~args (match ~args ~@body)))

(defm numbers-2 [x]
  [1] :one
  [2] :two
  [_] :many)

(defn numbers-3 [x]
  (case x
	1 :one
	2 :two
	:many))

;; Destructering / pattern matching

(let [list [[1 :banan] [:liste [:1 :tupel]]]
      [_ [_ [_ what]]] list]
  what)

(defn foo [[_ y _]] (inc y))

(foo [10 20 30])

(let [m {:foo [10 [1 2]] :bar "Hello"}
      {[_ deep-value] :foo} m]
  deep-value)

;; Producers and consumers - push solution
(def food-agent (agent nil))

(defn consumer [key ref old new]
  (println
   (case new
	 :meat "Yummy that tasted good"
	 "I do not want to consume that")))

(add-watch food-agent "a key representing the watch" consumer)

(defn change-food-stuff [current-food new-food]
  new-food)

#_(send food-agent change-food-stuff :meat)
#_(send food-agent change-food-stuff :banana)

;; Telegram parsning