(ns demo.core
  (:use [clojure.core.match :only [match]])
  (:use clojure.core.match.bits))

;; Basic stuff

(defn f [x y]
  (+ x y))

(f 1 10)

(if (> 10 5) :foo :bar)

(let [a (range 10)]
  (rest (rest a)))

(#(+ 10 %) 42)

(->> "Hello world" reverse (apply str))

;; Type system

(class 1)
(class "Hello world")

;; Not everything is comparable

(> 10 :foo)

(defn equals-class? [clazz]
  (fn [obj]
    (= clazz (class obj))))

(filter (equals-class? java.lang.String) [10 "foo" 42 "bar"])
(filter (equals-class? java.lang.Long) [10 "foo" 42 "bar"])

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

;; Datastructures and trees

;;;; Sets
(#{:foo :bar :baz} :not-here)

;;;; Maps
({:foo 1, :bar 42} :foo)

;;;; Vectors
[1 2 42]

;;;; Lists
'(3 2 1)

;;;; All sequence functions work the same, e.g. map

(let [a {:foo 1, :bar 42}
      b [["abc" 1] ["def" 42]]
      f (fn [[left right]] [left (inc right)])]
  (map f a))

;;;; Why are structs needed
(defstruct test-struct :member1 :member2 :member3)

(assoc (struct test-struct :foo :bar :baz) :test :fest)

(assoc {}
  :foo "bar"
  :test "fest"
  1 42
  [1 2 :foo] :dsgnfdsk)

(into (sorted-map) [[2 3] [1 42]])

(defrecord TestRecord [member1 member2 member3])

(TestRecord. :foo :bar :baz)

{:uri "http://..."
 :input "dsfgsgsfd"
 :type :POST}

;; TCO

;;;; works
#_(loop [i 1]
  (println i)
  (if (> i 10)
    :done
    (if (> i 5)
      (do (recur (inc i)) (println "Hej"))
      (recur (+ i 2)))))

(defn loop-fn [i]
  (if (> i 10)
    :done
    (recur (inc i))))

(loop-fn 1)

;; Infinit sequences

(def evens (filter even? (range)))

;; List comprehensions

(for [i (range 100) :when (< i 10)] (* 2 i))

