(ns Clojurithms.unionfind)

; this implements the Weighted Quick Union UF algorithm
(defrecord unionfind [ids sizes])

(defn with-length [n]
  (let [ids (vec (range n))
        sizes (vec (repeat n 1))]
    (->unionfind ids sizes)))

; finds the root of p
(defn find-root [uf p]
   (loop [p p]
      (if (= p ((:ids uf) p))
         p
         (recur ((:ids uf) p)))))

; is p connected to q?
(defn connected? [uf p q]
  (= (find-root uf p) (find-root uf q)))

; connect p and q, returning the result
(defn union [uf p q]
  (let [i (find-root uf p) j (find-root uf q)]
    (if (= i j)
        uf
        (if (< ((:sizes uf) i) ((:sizes uf) j))
            (-> uf (assoc-in [:ids i] j) 
                   (assoc-in [:sizes j] (+ ((:sizes uf) i) ((:sizes uf) j))))
            (-> uf (assoc-in [:ids j] i)
                   (assoc-in [:sizes i] (+ ((:sizes uf) i) ((:sizes uf) j))))
        )
    )
  )
)
