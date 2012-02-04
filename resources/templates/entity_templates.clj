(ns {{namespace}}.views.{{entity}}_templates
    (:use [noir.core]
          [hiccup.core]
          [clojure.string]))

(alias 'entity '{{namespace}}.views.{{entity}}_pages)

(defn list-item-actions [id]
  [:form {:id (str "form-" id) :method "POST", :action (url-for entity/delete-{{entity}} {:id id})}
   [:div
    [:a {:href (url-for entity/view-{{entity}} {:id id})} "View"] " | "
    [:a {:href (url-for entity/edit-{{entity}} {:id id})} "Edit"] " | "
    [:a {:href "#", :onclick (str "document.forms['form-" id "'].submit()")} "Delete"]]])

(defn list-item [item]
  (let [id (:_id item)]
    [:div
     {{#fields}}[:div (:{{name}} item)]{{/fields}}
     (list-item-actions id)
     [:br]]))

(defn show-list [{{entity}}-list]
  [:div
   (for [item {{entity}}-list] 
     (list-item item))
   [:a {:href (url-for entity/new-{{entity}}) :class "btn btn-info"} "New"]])

(defn show [item]
  [:div
   {{#fields}}[:div (:{{name}} item)]
   {{/fields}}[:br]
   [:a {:href (url-for entity/{{entity}}-list)} "Back"]])

(defn form [url button-text & [{{#fields}}{{name}} {{/fields}}& rest]]
  [:form {:method "POST", :action url}
   {{#fields}}[:label {:for "{{name}}"} (str (capitalize "{{name}}") ":")]
   [:input {:id "{{name}}", :name "{{name}}", :type "text", :value {{name}}}]
   [:br]
   {{/fields}}[:input {:type "submit", :value button-text, :class "btn"}]
   [:br]
   [:a {:href (url-for entity/{{entity}}-list)} "Back"]])

