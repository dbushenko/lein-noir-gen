(ns {{namespace}}.views.user_pages
    (:require [{{namespace}}.views.default :as default]
              [noir.response :as resp]
              [noir.session :as session]
              [{{namespace}}.views.user_templates :as user_view]
              [{{namespace}}.models.user_model :as user_model])
    (:use [noir.core]))

(defn root-exists? []
  (not (empty? (user_model/fetch-root))))

(defpage login [:post "/login"] {:keys [login password]}
  (let [user (user_model/fetch login password)]
    (if-not (empty? user)
      (do
        (session/put! :username login)
        (resp/redirect (url-for "/")))
      (default/layout (user_view/error "Invalid user or password!")))))

(defpage "/login" {}
  (default/layout
    [:h2 (str "Login")]
    (user_view/login-form (url-for login) "Login" "root" "")))

(defpage logout "/logout" {}
  (session/put! :username nil)
  (resp/redirect (url-for "/")))


(defpage login-edit [:post "/user/edit/:login"] {:keys [login password]}
  (if (root-exists?)
    (user_model/update login password)
    (user_model/create login password))
  (session/put! :username login)
  (resp/redirect (url-for login-view)))

(defpage login-view "/user/edit" {}
  (let [user-name (session/get :username)]
    (default/layout
      (if (and (empty? user-name) (root-exists?))
        (user_view/error "You have to login first!")
        (let [user (if (empty? user-name) "root" user-name)]
          (user_view/edit-form (url-for login-edit {:login user}) user ""))))))

