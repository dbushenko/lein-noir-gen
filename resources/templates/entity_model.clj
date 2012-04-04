(ns {{namespace}}.models.{{entity}}_model
    (:require [somnium.congomongo :as mongo]
              [{{namespace}}.db :as db]))

(defn fetch-list
  ([] (mongo/fetch :{{entity}}))
  ([conditions-map]
     (mongo/fetch :{{entity}} :where conditions-map)))

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

