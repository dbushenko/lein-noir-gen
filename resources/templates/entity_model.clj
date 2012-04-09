(ns {{namespace}}.models.{{entity}}_model
    (:require [somnium.congomongo :as mongo]
              [{{namespace}}.db :as db]))

(def ^:dynamic *limit* 5)

(defn fetch-list-count [& [conditions-map]]
  (mongo/fetch-count :article :where conditions-map))

;; Add sorting like that (sorting first by date next by title):
;; (mongo/fetch :{{entity}}, :sort {:date 1, :title 1}, :limit *limit*)
(defn fetch-list
  ([] (mongo/fetch :{{entity}} :limit *limit*))
  ([conditions-map]
     (mongo/fetch :{{entity}} :where conditions-map :limit *limit*))
  ([conditions-map skip]
     (mongo/fetch :{{entity}} :where conditions-map :limit *limit* :skip skip)))

(defn fetch [id]
  (mongo/fetch-one :{{entity}} :where {:_id (Long/parseLong id)}))

(defn create [{{#fields}}{{name}} {{/fields}}]
  (mongo/insert! :{{entity}} {:_id (db/next-id "{{entity}}"),{{#fields}} :{{name}} {{name}}{{/fields}}}))

(defn update [id {{#fields}}{{name}} {{/fields}}]
  (let [item (fetch id)]
    (mongo/update! :{{entity}} item (merge item {{{#fields}} :{{name}} {{name}}{{/fields}}}))))

(defn delete [id]
  (let [item (fetch id)]
    (mongo/destroy! :{{entity}} item)))

