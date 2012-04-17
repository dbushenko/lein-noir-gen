(ns {{rawns}}.server
  (:require [noir.server :as server]
            [{{namespace}}.db :as db]))

(server/load-views "src/{{namespace}}/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (db/connect-db)
    (server/start port {:mode mode
                        :ns '{{namespace}}})))

