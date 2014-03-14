(ns soundlab.core
  (:use overtone.live))

(definst sin-wav [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (sin-osc freq)
     vol))

(definst square-wav [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

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

(defn swarm []
  (sin-wav)
  (square-wav 67 3 1 2)
  (square-wav 30 2 0.001 1)
  (kick))
