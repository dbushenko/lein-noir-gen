(ns {{namespace}}.views.{{entity}}_pages
    (:require [{{namespace}}.views.default :as default]
              [noir.response :as resp]
              [{{namespace}}.views.{{entity}}_templates :as {{param}}_view]
              [{{namespace}}.models.{{model}}_model :as {{model}}_model])
    (:use [noir.core]))

(defpage {{param}}_list "/{{entity-path}}" []
  (default/layout
    [:h2 (str "{{entity-title}}" " list")]
    ({{param}}_view/show-list ({{model}}_model/fetch-list))))

(defpage view_{{param}} "/{{entity-path}}/view/:id" {id :id}
  (default/layout
    [:h2 (str "View {{entity}}")]
    ({{param}}_view/show ({{model}}_model/fetch id))))

(defpage edit_{{param}} "/{{entity-path}}/edit/:id" {id :id}
  (let [item ({{model}}_model/fetch id)]
    (default/layout
      [:h2 (str "Edit {{entity}}")]
      ({{param}}_view/form (url-for update_{{param}} {:id id}) "Update" {{#fields}}(:{{name}} item) {{/fields}}))))

(defpage update_{{param}} [:post "/{{entity-path}}/update/:id"] {:keys [id {{#fields}}{{name}} {{/fields}}]}
  ({{model}}_model/update id {{#fields}}{{name}} {{/fields}})
  (resp/redirect (url-for view_{{param}} {:id id})))

(defpage new_{{param}} "/{{entity-path}}/new" []
  (default/layout
    [:h2 (str "New {{entity}}")]
    ({{param}}_view/form (url-for create_{{param}}) "Create")))

(defpage create_{{param}} [:post "/{{entity-path}}/create"] {:keys [{{#fields}}{{name}} {{/fields}}]}
  ({{model}}_model/create {{#fields}}{{name}} {{/fields}})
  (resp/redirect (url-for {{param}}_list)))

(defpage delete_{{param}} [:post "/{{entity-path}}/delete/:id"] {id :id}
  ({{model}}_model/delete id)
  (resp/redirect (url-for {{param}}_list)))

