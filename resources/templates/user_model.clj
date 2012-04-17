(ns {{namespace}}.models.user_model
    (:require [somnium.congomongo :as mongo]
              [{{namespace}}.db :as db]))

(def ^:dynamic *limit* 5)

(defn fetch-list-count [& [conditions-map]]
  (mongo/fetch-count :article :where conditions-map))

(defn fetch-list
  ([] (mongo/fetch :user :limit *limit*))
  ([conditions-map]
     (mongo/fetch :user :where conditions-map :limit *limit*))
  ([conditions-map skip]
     (mongo/fetch :user :where conditions-map :limit *limit* :skip skip)))

(defn fetch-root []
  (mongo/fetch-one :user :where {:login "root"}))

(defn fetch
  ([login password]
     (mongo/fetch-one :user :where {:login login, :password password}))
  ([login]
     (mongo/fetch-one :user :where {:login login})))

(defn create [login password ]
  (mongo/insert! :user {:_id (db/next-id "user"), :login login :password password}))

(defn update [login password]
  (let [item (fetch login)]
    (mongo/update! :user item (merge item {:login login :password password}))))

(defn delete [id]
  (let [item (fetch id)]
    (mongo/destroy! :user item)))
