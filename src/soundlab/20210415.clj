(ns soundlab.20210415
  (:use overtone.core
        soundlab.core))

(def metro (metronome 133))

(defn beat-sqr [beat]
  (at (metro (+ 1 beat)) (square-wav 30 0.05 1))
  (at (metro (+ 1 beat)) (square-wav 1 1 1 0.1 1))
  (apply-at (metro (+ 2 beat)) #'beat-sqr (+ 2 beat) []))

(defn beat-hat [beat]
  (at (metro beat)         (c-hat))
  (at (metro (+ 0.1 beat)) (c-hat))
  (at (metro (+ 0.5 beat)) (c-hat))
  (at (metro (+ 0.6 beat)) (c-hat))
  (at (metro (+ 1.5 beat)) (c-hat))
  (at (metro (+ 1.6 beat)) (c-hat))
  (at (metro (+ 2 beat))   (c-hat))
  (at (metro (+ 2.7 beat)) (c-hat))
  (apply-at (metro (+ 4 beat)) #'beat-hat (+ 4 beat) []))

(defn beat-drop [beat]
  (at (metro (+ 2.4 beat)) (droplet 100 1))
  (at (metro (+ 2.5 beat)) (droplet 100 1))
  (at (metro (+ 2.6 beat)) (droplet 100 1))
  (at (metro (+ 2.7 beat)) (droplet 100 1))
  (at (metro (+ 2.8 beat)) (droplet 100 1))
  (at (metro (+ 2.9 beat)) (droplet 440))
  (at (metro (+ 3.0 beat)) (droplet 440))
  (at (metro (+ 3.1 beat)) (droplet 440))
  (at (metro (+ 3.2 beat)) (droplet 440))
  (at (metro (+ 3.3 beat)) (droplet 440))
  (at (metro (+ 3.4 beat)) (droplet 440))
  (at (metro (+ 3.5 beat)) (droplet 440))
  (at (metro (+ 3.6 beat)) (droplet 440))
  (at (metro (+ 3.7 beat)) (droplet 440))
  (at (metro (+ 3.8 beat)) (droplet 440))
  (at (metro (+ 3.9 beat)) (droplet 440))
  (apply-at (metro (+ 4.0 beat)) #'beat-drop (+ 4.0 beat) []))

(defn play []
  (beat-sqr (metro))
  (beat-hat (metro))
  (beat-drop (metro))
  )
