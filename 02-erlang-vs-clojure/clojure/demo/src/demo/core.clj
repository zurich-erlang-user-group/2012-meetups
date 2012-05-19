(ns demo.core
  (:use [clojure.core.match :only [match]])
  (:use clojure.core.match.bits))

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

;; Telegram parsing

;;;; Following found in clojure.core.match.bits but is not working. A ticket (MATCH-59) has been opened

  (comment match [dgram]
    [([(ip-version 4)
       ((hlen 4) :when [#(>= % 5) #(<= (* 4 %) drgramsize)])
       (srvc-type 8)
       (totlen 16)
       (id 16)
       (flgs 3)
       (fragoff 13)
       (ttl 8)
       (proto 8)
       (hdrchksum 16)
       (srcip 32)
       (destip 32)
       & restdgram] ::bits)])
