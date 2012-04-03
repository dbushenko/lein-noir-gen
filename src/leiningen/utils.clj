(ns leiningen.utils
  (:require [clojure.string])
  (:use [clojure.java.io]))

;;From the maginalia source: http://fogus.me/fun/marginalia/
(defn slurp-resource
  [resource-name]
  (try
    (-> (.getContextClassLoader (Thread/currentThread))
        (.getResourceAsStream resource-name)
        (java.io.InputStreamReader.)
        (slurp))
    (catch java.lang.NullPointerException npe
      (println (str "Could not locate resources at " resource-name))
      (println "    ... attempting to fix.")
      (let [resource-name (str "./resources/" resource-name)]
        (try
          (-> (.getContextClassLoader (Thread/currentThread))
              (.getResourceAsStream resource-name)
              (java.io.InputStreamReader.)
              (slurp))
          (catch java.lang.NullPointerException npe
            (println (str "    STILL could not locate resources at " resource-name ". Giving up!"))))))))

(defn ->file [path-to-file content]
  "Quick create new file with directory structure"
  (do
    (.mkdirs (.getParentFile (file path-to-file)))
    (spit
     (file path-to-file) content)))

(defn path-to-entity [entity]
  (-> (str entity)
      (clojure.string/replace "." "/")
      (clojure.string/replace "-" "_")))

(defn parameterize-entity [entity]
  (-> (str entity)
      (clojure.string/replace "-" "_")
      (clojure.string/replace "." "-")))

(defn make-title [entity]
  (-> (str entity)
      (clojure.string/replace "." " >> ")
      (clojure.string/replace "-" " > ")
      (clojure.string/capitalize)))
