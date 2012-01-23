(ns leiningen.noir-gen
    (:use [clojure.java.io]
          [clostache.parser]))


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
  (spit
   (file path-to-file) content))


(defn generate [namespace entity fields database]
  (let [pages (render (slurp-resource (str "templates/entity_pages.clj"))
                      {:namespace namespace,
                       :entity entity,
                       :fields fields})
        templates (render (slurp-resource (str "templates/entity_templates.clj"))
                          {:namespace namespace,
                           :entity entity,
                           :fields fields})
        model (render (slurp-resource (str "templates/entity_model.clj"))
                      {:namespace namespace,
                       :entity entity,
                       :fields fields})
        db (render (slurp-resource (str "templates/db.clj"))
                   {:namespace namespace,
                    :database database})
        srv (render (slurp-resource (str "templates/srv.clj"))
                    {:namespace namespace})]
    (->file (str "./src/" namespace "/views/" entity "_pages.clj") pages)
    (->file (str "./src/" namespace "/views/" entity "_templates.clj") templates)
    (->file (str "./src/" namespace "/models/" entity "_model.clj") model)
    (->file (str "./src/" namespace "/db.clj") db)
    (->file (str "./src/" namespace "/server.clj") srv)))

(defn noir-gen 
  "Create CRUD-pages for a Noir project.
Uses the default namespace.
Options which you may set in project.clj:
:noir-gen {:namespace my_namespace, :database my_database}"
  ([project entity & fields]
     (let [namespace (or (:namespace (:noir-gen project)) (:group project))
           database (or (:database (:noir-gen project)) (:group project))]
    (generate namespace entity (map #(hash-map :name %) fields) database))
     (println (str "The entity '" entity "' was successfully generated!"))
     (println "Add dependency to your 'project.clj' for congo-mongo like that:")
     (println "[congomongo \"0.1.7\"]")))
