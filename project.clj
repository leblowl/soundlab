(defproject soundlab "0.1.0-SNAPSHOT"
  :description "Overtone drawing board"
  :url "https://github.com/leblowl/soundlab"
  :license {:name "MIT"
            :url "https://github.com/leblowl/soundlab/blob/main/LICENSE"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [overtone "0.10.6"]]
  :repl-options {:init-ns soundlab.user})
