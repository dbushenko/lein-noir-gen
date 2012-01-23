(ns {{namespace}}.db
  (:use [somnium.congomongo]))

(defn connect-db []
  (set-connection! (make-connection "{{database}}"
                                    :host "127.0.0.1"
                                    :port 27017)))

(defn next-id [name]
  (fetch-and-modify :counters
                    {:name name}
                    {:$inc {:value 1}}
                    :upsert? true)
  (:value (fetch-one :counters
                     :where {:name name})))