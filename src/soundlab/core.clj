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

(definst trem [freq 440 depth 10 rate 6 length 0.2]
  (* 0.8
     (line:kr 0 1 length FREE)
     (saw (+ freq (* depth (sin-osc:kr rate))))))

;; (def kick (sample (freesound-path 2086)))

(definst twang [freq 50 delay 1]
  (pluck (* (white-noise) (env-gen (perc 0.01 10) :action FREE)) 1 1 (/ delay freq) 1 0.5))

(definst bubble-pluck [freq 750 delay 100]
  (pluck (* (env-gen (adsr 0.099 0.1 0 0) 1 1 0 1 FREE)
            (lpf (sin-osc (* freq (env-gen (adsr 0.23 0 0 0)))) 400)
            1) 1 1 (/ delay freq) 0.7 0.01))

(defn swarm []
  (sin-wav)
  ;; (kick)
  )

(defn looper [nome sound]
  (let [beat (nome)]
    (at (nome beat) (sound))
    (apply-at (nome (inc beat)) looper nome sound [])))
