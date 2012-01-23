(ns {{namespace}}.views.{{entity}}_templates
  (:use [noir.core]
        [hiccup.core]))

(alias 'entity '{{namespace}}.views.{{entity}}_pages)

(defn show [item]
  [:div
   {{#fields}}[:div (:{{name}} item)]
   {{/fields}}[:br]
   [:a {:href (url-for entity/{{entity}}-list)} "Back"]])

(defn list-item [item]
  (let [id (:_id item)]
    [:div
     {{#fields}}[:div (:{{name}} item)]{{/fields}}
     [:form {:id (str "form-" id) :method "POST", :action (url-for entity/delete-{{entity}} {:id id})}
      [:div
       [:a {:href (url-for entity/view-{{entity}} {:id id})} "View"] " | "
       [:a {:href (url-for entity/edit-{{entity}} {:id id})} "Edit"] " | "
       [:a {:href "#", :onclick (str "document.forms['form-" id "'].submit()")} "Delete"]]]
     [:br][:br]]))

(defn show-list [{{entity}}-list]
  [:div
   (for [item {{entity}}-list] 
     (list-item item))
   [:a {:href (url-for entity/new-{{entity}})} "New"]])

(defn form [url button-text & [{{#fields}}{{name}} {{/fields}}& rest]]
  [:form {:method "POST", :action url}
   {{#fields}}[:input {:name "{{name}}", :type "text", :value {{name}}}]
   [:br]
   {{/fields}}[:input {:type "submit", :value button-text}]
   [:br]
   [:a {:href (url-for entity/{{entity}}-list)} "Back"]])

