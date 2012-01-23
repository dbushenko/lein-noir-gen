(ns {{namespace}}.views.{{entity}}_pages
  (:require [{{namespace}}.views.common :as common]
            [noir.response :as resp]
            [{{namespace}}.views.{{entity}}_templates :as {{entity}}-view]
            [{{namespace}}.models.{{entity}}_model :as {{entity}}-model])
  (:use [noir.core]))

(defpage {{entity}}-list "/{{entity}}" []
  (common/layout
   ({{entity}}-view/show-list ({{entity}}-model/fetch-list))))

(defpage view-{{entity}} "/{{entity}}/view/:id" {id :id}
  (common/layout
   ({{entity}}-view/show ({{entity}}-model/fetch id))))

(defpage edit-{{entity}} "/{{entity}}/edit/:id" {id :id}
  (let [item ({{entity}}-model/fetch id)]
    (common/layout
     ({{entity}}-view/form (url-for update-{{entity}} {:id id}) "Update" {{#fields}}(:{{name}} item) {{/fields}}))))

(defpage update-{{entity}} [:post "/{{entity}}/update/:id"] {:keys [id {{#fields}}{{name}} {{/fields}}]}
  ({{entity}}-model/update id {{#fields}}{{name}} {{/fields}})
  (resp/redirect (url-for view-{{entity}} {:id id})))

(defpage new-{{entity}} "/{{entity}}/new" []
  (common/layout
   ({{entity}}-view/form (url-for create-{{entity}}) "Create")))

(defpage create-{{entity}} [:post "/{{entity}}/create"] {:keys [{{#fields}}{{name}} {{/fields}}]}
  ({{entity}}-model/create {{#fields}}{{name}} {{/fields}})
  (resp/redirect (url-for {{entity}}-list)))

(defpage delete-{{entity}} [:post "/{{entity}}/delete/:id"] {id :id}
  ({{entity}}-model/delete id)
  (resp/redirect (url-for {{entity}}-list)))

