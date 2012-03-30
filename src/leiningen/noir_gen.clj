(ns leiningen.noir-gen
  (:use [leiningen.utils]
        [clojure.java.io]
        [clostache.parser]))

(defn copy-resources []
  (->file (str "./resources/public/css/bootstrap.css") (slurp-resource (str "css/bootstrap.css")))
  (->file (str "./resources/public/css/default.css") (slurp-resource (str "css/default.css"))))

(defn print-help []
  (println "Usage:")
  (println "lein noir-gen setup")
  (println "lein noir-gen model entity.subentity1 field1 field2 ... fieldN")
  (println "lein noir-gen view entity.subentity1 field1 field2 ... fieldN")
  (println "in your noir project root")
  )
  
(defn copy-resources []
  (->file (str "./resources/public/css/bootstrap.css") (slurp-resource (str "css/bootstrap.css")))
  (->file (str "./resources/public/css/default.css") (slurp-resource (str "css/default.css"))))

(defn crud-setup [namespace database]
  (let [db (render (slurp-resource (str "templates/db.clj"))
                   {:namespace namespace,
                    :database database})
        srv (render (slurp-resource (str "templates/srv.clj"))
                    {:namespace namespace})
        default (render (slurp-resource (str "templates/default.clj"))
                    {:namespace namespace})]
    (->file (str "./src/" namespace "/db.clj") db)
    (->file (str "./src/" namespace "/server.clj") srv)
    (->file (str "./src/" namespace "/views/default.clj") default)
    (copy-resources))

  (println "Remember adding dependency to your 'project.clj' for congo-mongo like that:")
  (println "[congomongo \"0.1.7\"]"))

(defn crud-view [namespace entity fields]
  (let [field (map #(hash-map :name %) fields)]
    (let [pages (render (slurp-resource (str "templates/entity_pages.clj"))
                        {:namespace namespace,
                         :entity entity,
                         :entity-title (make-title entity),
                         :entity-path (make-path entity),
                         :param (make-form-name entity),
                         :fields field})
          templates (render (slurp-resource (str "templates/entity_templates.clj"))
                            {:namespace namespace,
                             :entity entity,
                              :entity-path (make-path entity),
                              :param (make-form-name entity),
                             :fields field})]
      (->file (str "./src/" namespace "/views/" (make-path entity) "_pages.clj") pages)
      (->file (str "./src/" namespace "/views/" (make-path entity) "_templates.clj") templates))))

(defn crud-model [namespace entity fields]
  (let [field (map #(hash-map :name %) fields)]
    (let [model (render (slurp-resource (str "templates/entity_model.clj"))
                        {:namespace namespace,
                         :entity entity,
                         :fields field})
                         ]
      (->file (str "./src/" namespace "/models/" (make-path entity) "_model.clj") model))))

(defn noir-gen 
  "Create CRUD-pages for a Noir project.
Uses the default namespace.
Options which you may set in project.clj:
:noir-gen {:namespace my_namespace, :database my_database}"
  [project task & args]
   (let [namespace
            (or (:namespace (:noir-gen project))
                (:group project))
         database
            (or (:database (:noir-gen project))
                (:group project))]

        (condp = task
          "setup" (crud-setup namespace database)
          "model" (crud-model namespace (first args) (next args))
          "view" (crud-view namespace (first args) (next args))
                 (print-help))))
