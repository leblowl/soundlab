(ns soundlab.20140316
  (:use overtone.core
        soundlab.core))

(def metro (metronome 97))

(defn trippy-swinger [beat rand]
  (at (metro beat) (twang 50 rand))
  (when (> rand 0.01) (apply-at (metro (+ 0.17 beat)) #'trippy-swinger (+ 0.17 beat) (/ rand 1.5) [])))

(defn beat1 [beat]
  (at (metro beat) (sin-wav))
  (at (metro (+ 1 beat)) (square-wav 30))
  (at (metro (+ 1.65 beat)) (square-wav 50))
  (apply-at (metro (+ 2 beat)) #'beat1 (+ 2 beat) []))

(defn beat2 [beat]
  (at (metro beat) (c-hat))
  (at (metro (+ 0.1 beat)) (c-hat))
  (at (metro (+ 0.5 beat)) (c-hat))
  (at (metro (+ 0.6 beat)) (c-hat))
  (at (metro (+ 1.5 beat)) (c-hat))
  (at (metro (+ 1.6 beat)) (c-hat))
  (at (metro (+ 2 beat)) (c-hat))
  (at (metro (+ 2.7 beat)) (c-hat))
  (at (metro (+ 2.9 beat)) (droplet 750))
  (at (metro (+ 2.9 beat)) (trippy-swinger (metro) 1))
  (apply-at (metro (+ 4 beat)) #'beat2 (+ 4 beat) []))

(defn play []
  (beat1 (metro))
  (beat2 (metro)))
