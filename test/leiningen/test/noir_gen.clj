(ns leiningen.test.noir-gen
  (:use [leiningen.noir-gen])
  (:use [clojure.test])
  (:use [midje.sweet]))

;to see the result, run $lein midje then look around this project directory. git-cola is a good tool for watching
(facts
(crud-model "namespace" "parent.sub" ["f1" "f2"])
=> "stupid string"
(crud-view "namespace" "parent.sub" ["f1" "f2"])
=> "stupid string"
)
