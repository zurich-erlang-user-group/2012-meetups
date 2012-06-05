(ns demo.datagrams)


(def datagram-packet "183da2599ab4e0469a4e78f8080045000049716700006c11963bdd805f1a0a000067b6366296003555e16d5202bded2cda9e7a0912b1338fb701f6eb1e3df55559a0e45dd05065535348368848249417a7c15431958c25")

(defn hex-to-int-seq [hex-str]
  (map (zipmap "0123456789abcdef" (range)) hex-str))

(hex-to-int-seq datagram-packet)

(defn int-to-bit-seq [int]
  (case int
	0 [0]
	1 [1]
	(cons (bit-and 1 int) (int-to-bit-seq (bit-shift-right int 1)))))

(map int-to-bit-seq (range 16))

(defn padded-int-to-bit-seq [digits int]
  (let [bit-seq (int-to-bit-seq int)
	padding (repeat (- digits (count bit-seq)) 0)]
    (concat padding bit-seq)))

(map (partial padded-int-to-bit-seq 4) (range 16))

(defn int-seq-to-bit-seq [int-seq]
  (mapcat (partial padded-int-to-bit-seq 4) int-seq))

(->> datagram-packet
     hex-to-int-seq
     int-seq-to-bit-seq)

(defn strip-bits [{bit-seq :bit-seq :as result} name length]
  (assoc result
    name (take length bit-seq)
    :bit-seq (drop length bit-seq)))

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

(let [bit-seq (->> datagram-packet hex-to-int-seq int-seq-to-bit-seq)]
  (-> {:bit-seq bit-seq}
      (strip-bits :ip-version 4)
      (strip-bits :hlen 4)
      ))
 

(comment datagram datagram-packet)