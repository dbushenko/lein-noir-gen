(ns {{namespace}}.views.default
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5]]
        [clojure.string]))

(defpartial layout [& content]
  (html5
   [:head
    [:title "{{namespace}}"]
    (include-css "/css/bootstrap.css")
    (include-css "/css/default.css")]
   [:body {:class "default-body"}
    [:div {:class "container default-header"}
     [:div {:class "row"}
      [:div {:class "span12"}
       [:h1 {:style "color:#fff"} (capitalize "{{namespace}}")]]]]
     
    [:div {:class "container default-content"}
     [:div {:class "row"}
      [:div {:class "span2"}]
      [:div {:class "span8"}
       content]
      [:div {:class "span2"}]]]]))
