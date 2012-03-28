(ns {{namespace}}.views.{{entity}}_pages
    (:require [{{namespace}}.views.default :as default]
              [noir.response :as resp]
              [{{namespace}}.views.{{entity}}_templates :as {{param}}_view]
              [{{namespace}}.models.{{entity}}_model :as {{param}}_model])
    (:use [noir.core]))

(defpage {{param}}_list "/{{entity_path}}" []
  (default/layout
    [:h2 (str "{{entity-title}}" " list")]
    ({{param}}_view/show-list ({{param}}_model/fetch-list))))

(defpage view_{{param}} "/{{entity_path}}/view/:id" {id :id}
  (default/layout
    [:h2 (str "View {{entity}}")]
    ({{param}}_view/show ({{param}}_model/fetch id))))

(defpage edit_{{param}} "/{{entity_path}}/edit/:id" {id :id}
  (let [item ({{param}}_model/fetch id)]
    (default/layout
      [:h2 (str "Edit {{entity}}")]
      ({{param}}_view/form (url-for update_{{param}} {:id id}) "Update" {{#fields}}(:{{name}} item) {{/fields}}))))

(defpage update_{{param}} [:post "/{{entity_path}}/update/:id"] {:keys [id {{#fields}}{{name}} {{/fields}}]}
  ({{param}}_model/update id {{#fields}}{{name}} {{/fields}})
  (resp/redirect (url-for view_{{param}} {:id id})))

(defpage new_{{param}} "/{{entity_path}}/new" []
  (default/layout
    [:h2 (str "New {{entity}}")]
    ({{param}}_view/form (url-for create_{{param}}) "Create")))

(defpage create_{{param}} [:post "/{{entity_path}}/create"] {:keys [{{#fields}}{{name}} {{/fields}}]}
  ({{param}}_model/create {{#fields}}{{name}} {{/fields}})
  (resp/redirect (url-for {{param}}_list)))

(defpage delete_{{param}} [:post "/{{entity_path}}/delete/:id"] {id :id}
  ({{param}}_model/delete id)
  (resp/redirect (url-for {{param}}_list)))

