(ns soundlab.core
  (:use overtone.live))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(definst bar [freq 220] (* 0.8 (saw freq)))

(definst trem [freq 440 depth 10 rate 6 length 0.2]
  (* 0.8
     (line:kr 0 1 length FREE)
     (saw (+ freq (* depth (sin-osc:kr rate))))))

(def kick (sample (freesound-path 2086)))
(def one-twenty-bpm (metronome 120))

(defn looper [nome sound]
  (let [beat (nome)]
    (at (nome beat) (sound))
    (apply-at (nome (inc beat)) looper nome sound [])))

(defn song []
  (trem 220)
  (trem 440))
