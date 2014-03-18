(ns soundlab.core
  (:use overtone.live))

;; Oscillators
(definst sin-wav [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (sin-osc freq)
     vol))

(definst saw-wav [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

(definst square-wav [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-pulse:ar freq)
     vol))

;; Droplet with ADSR and multiple envelopes on different controls
(definst droplet [freq 440 vol 0.4]
  (* (env-gen (adsr 0.099 0.1 0 0) 1 1 0 1 FREE)
     (lpf (sin-osc (* freq (env-gen (adsr 0.23 0 0 0)))) 400)
     vol))

(definst c-hat [amp 0.8 t 0.04]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 880 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(droplet 750)

(defn beat3 [beat]
  (at (metro (+ 1 beat)) (droplet 750))
  (at (metro (+ 1.2 beat)) (droplet 750))
  (at (metro (+ 1.4 beat)) (droplet 750))
  (apply-at (metro (+ 2 beat)) #'beat3 (+ 2 beat) []))

(defn beat4 [beat]
  (at (metro (+ 0 beat)) (c-hat 1))
  (at (metro (+ 0.3 beat)) (c-hat 1))
  (apply-at (metro (+ 0.5 beat)) #'beat4 (+ 0.5 beat) []))

(beat4 (metro))
(bubble-pluck 300 40)

(defn beat5 [beat]
  (at (metro (+ 0.5 beat)) (kick))
  (at (metro (+ 3.55 beat)) (kick))
  (apply-at (metro (+ 4 beat)) #'beat5 (+ 4 beat) []))

(defn beat6 [beat]
  (at (metro (+ 0 beat)) (bubble-pluck 400))
  (at (metro (+ 0.5 beat)) (bubble-pluck 200))
  (at (metro (+ 0.7 beat)) (bubble-pluck 700))
  (apply-at (metro (+ 2 beat)) #'beat6 (+ 2 beat) []))

(defn beat7 [beat pitch delay]
  (at (metro (+ 0 beat)) (bubble-pluck pitch delay))
  (apply-at (metro (+ 0.5 beat)) #'beat7 (+ 0.5 beat) (+ 50 pitch) (+ 10 delay) []))

(beat7 (metro) 100 10)

(beat6 (metro))
(beat3 (metro))

(defn composition []
  (let [beat (metro)]
    (beat3 beat)
    (beat4 beat)
    (beat5 beat)))

(composition)

(definst trem [freq 440 depth 10 rate 6 length 0.2]
  (* 0.8
     (line:kr 0 1 length FREE)
     (saw (+ freq (* depth (sin-osc:kr rate))))))

(def kick (sample (freesound-path 2086)))
(def metro (metronome 97))

(defn looper [nome sound]
  (let [beat (nome)]
    (at (nome beat) (sound))
    (apply-at (nome (inc beat)) looper nome sound [])))

(defn beat1 [beat]
  (at (metro beat) (sin-wav))
  (at (metro beat) (kick))
  (at (metro (+ 1 beat)) (square-wav 30))
  (at (metro (+ 1.65 beat)) (square-wav 50))
  (apply-at (metro (+ 2 beat)) #'beat1 (+ 2 beat) []))

(beat1 (metro))

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
  ; (at (metro (+ 2.9 beat)) (saw-wav 500 0.1 0 0.4 50))
  ; (at (metro (+ 2.9 beat)) (trippy-swinger (metro) 1))
  (apply-at (metro (+ 4 beat)) #'beat2 (+ 4 beat) []))

(defn composition1 [beat])

(beat2 (metro))

;; filters
(demo 10 (lpf (saw 50) (mouse-x 40 5000 EXP)))
(demo 10 (hpf (saw 50) (mouse-x 40 5000 EXP)))
(demo 30 (bpf (saw 50) (mouse-x 40 5000 EXP) (mouse-y 0.01 1 LIN)))

(definst twang [freq 50 delay 1]
  (pluck (* (white-noise) (env-gen (perc 0.01 10) :action FREE)) 1 1 (/ delay freq) 1 0.5))

(definst bubble-pluck [freq 750 delay 100]
  (pluck (* (env-gen (adsr 0.099 0.1 0 0) 1 1 0 1 FREE)
            (lpf (sin-osc (* freq (env-gen (adsr 0.23 0 0 0)))) 400)
            1) 1 1 (/ delay freq) 0.7 0.01))

(bubble-pluck 750)

(defn trippy-swinger [beat rand]
  (at (metro beat) (twang 50 rand))
  (when (> rand 0.01) (apply-at (metro (+ 0.17 beat)) #'trippy-swinger (+ 0.17 beat) (/ rand 1.5) [])))

(trippy-swinger (metro) 2)

(defn swarm []
  (sin-wav)
  (kick))

(swarm)
