(ns soundlab.20210416
  (:use overtone.core
        soundlab.core)
  (:require [leipzig.melody :as m]
            [leipzig.live :as l]
            [leipzig.scale :as s]))

;; Hat

(def hat
  (->>
   (m/phrase (repeat 15 1.5)
             (repeat 15 0))
   (m/all :part :hat)))

(defmethod l/play-note :hat [{midi :pitch seconds :duration}]
  (when midi (c-hat)))

;; Bass

(def bass
  (->>
   (m/phrase (repeat 30 0.7)
             (repeat 30 0))
   (m/all :part :bass)))

(defmethod l/play-note :bass [{midi :pitch seconds :duration}]
  (when midi (-> midi midi->hz (droplet 3))))

;; Melody

(def melody
  (->>
   (m/phrase [1/8  1/8  1  5    3  1/4  1/4  1  3  1/4  1/4  1  5]
             [  0    1  3  4  nil    3    1  0  1    3    1  0  1])
   (m/all :part :melody)))

(definst electric-sheep [freq 440 dur 1.0]
  (-> freq
      saw
      (* (env-gen (perc 0.01 dur 0.5 5) :action FREE))))

(defmethod l/play-note :melody [{midi :pitch seconds :duration}]
  (when midi (-> midi midi->hz (electric-sheep seconds))))

;; Drones

(def drones
  (->>
   (m/phrase [10 10 10 10]
             [ 4  5  4  5])
   (m/all :part :drone)))

(definst drone [freq 440 dur 1.0]
  (-> freq
      saw
      (* (env-gen (perc 0.5 dur 0.05 25) :action FREE))))

(defmethod l/play-note :drone [{midi :pitch seconds :duration}]
  (when midi (-> midi midi->hz (drone seconds))))

;; Composition

(defn play []
  (->>
   hat
   (m/then (m/with hat bass drones))
   (m/then (m/with hat bass melody drones))
   (m/then (m/with hat bass drones))
   (m/then (m/with hat bass melody drones))
   (m/wherever :pitch, :pitch (comp s/lower))
   (m/wherever :pitch, :pitch (comp s/C s/major))
   (m/tempo (m/bpm 130))
   l/play))
