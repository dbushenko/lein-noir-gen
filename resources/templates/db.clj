(ns {{namespace}}.db
    (:use [somnium.congomongo]
          [somnium.congomongo.config :only [*mongo-config*]]))

(defn split-mongo-url [url]
  "Parses mongodb url from heroku, eg. mongodb://user:pass@localhost:1234/db"
  (let [matcher (re-matcher #"^.*://(.*?):(.*?)@(.*?):(\d+)/(.*)$" url)] ;; Setup the regex.
    (when (.find matcher) ;; Check if it matches.
      (zipmap [:match :user :pass :host :port :db] (re-groups matcher))))) ;; Construct an options map.

(defn connect-db []
  (let [mongo-url (get (System/getenv) "MONGOHQ_URL")]
    (if mongo-url
      (let [config (split-mongo-url mongo-url)]
        (mongo! :db (:db config)
                :host (:host config)
                :port (Integer. (:port config)))
        (authenticate (:user config) (:pass config)))
      (mongo! :db {{database}}))))


(defn next-id [name]
  (:value (fetch-and-modify :counters
                            {:name name}
                            {:$inc {:value 1}}
                            :upsert? true
                            :return-new? true)))
