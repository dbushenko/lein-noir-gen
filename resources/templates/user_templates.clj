(ns {{namespace}}.views.user_templates
    (:use [noir.core]
          [hiccup.core]))

(alias 'entity '{{namespace}}.views.user_pages)

(defn login-form [url button-text & [login password & args]]
  [:form {:method "POST", :action url}
   [:label {:for "login"} "Login:"]
   [:input {:id "login", :name "login", :type "text", :value login}]
   [:br]
   [:label {:for "password"} "Password:"]
   [:input {:id "password", :name "password", :type "password", :value password}]
   [:br]
   [:input {:type "submit", :value button-text, :class "btn"}]])

(defn edit-form [url & [login password & args]]
  [:form {:method "POST", :action url}
   [:h3 login]
   [:br]
   [:label {:for "password"} "Password:"]
   [:input {:id "password", :name "password", :type "text", :value password}]
   [:br]
   [:input {:type "submit", :value "Update", :class "btn"}]])

(defn error [msg]
  [:div
   [:h2 "Error"]
   msg])
