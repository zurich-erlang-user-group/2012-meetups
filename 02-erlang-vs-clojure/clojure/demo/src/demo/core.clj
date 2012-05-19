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

