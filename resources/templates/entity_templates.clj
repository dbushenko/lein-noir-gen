(ns {{namespace}}.views.{{entity}}_templates
    (:use [noir.core]
          [hiccup.core]))

(alias 'entity '{{namespace}}.views.{{entity}}_pages)

(defn list-item-actions [id]
  [:form {:id (str "form-" id) :method "POST", :action (url-for entity/delete_{{param}} {:id id})}
   [:div
    [:a {:href (url-for entity/view_{{param}} {:id id})} "View"] " | "
    [:a {:href (url-for entity/edit_{{param}} {:id id})} "Edit"] " | "
    [:a {:href "#", :onclick (str "document.forms['form-" id "'].submit()")} "Delete"]]])

(defn list-item [item]
  (let [id (:_id item)]
    [:div
     {{#fields}}[:div (:{{name}} item)]{{/fields}}
     (list-item-actions id)
     [:br]]))

(defn show-list [{{param}}_list & [nx & [prev]]]
  [:div
   (for [item {{param}}_list] 
     (list-item item))
   [:a {:href (url-for entity/new_{{param}}) :class "btn btn-info"} "New"]
   [:div
    (if-not (nil? prev)
      [:a {:href prev} "<< Prev"])
    (if-not (or (nil? prev) (nil? nx))  " | ")
    (if-not (nil? nx)
      [:a {:href nx} "Next >>"])]])

(defn show [item]
  [:div
   {{#fields}}[:div (:{{name}} item)]
   {{/fields}}[:br]
   [:a {:href (url-for entity/{{param}}_list)} "Back"]])

(defn form [__form-action-url button-text & [{{#fields}}{{name}} {{/fields}}& args]]
  [:form {:method "POST", :action __form-action-url}
   {{#fields}}[:label {:for "{{name}}"} (str (clojure.string/capitalize "{{name}}") ":")]
   [:input {:id "{{name}}", :name "{{name}}", :type "text", :value {{name}}}]
   [:br]
   {{/fields}}[:input {:type "submit", :value button-text, :class "btn"}]
   [:br]
   [:a {:href (url-for entity/{{param}}_list)} "Back"]])

