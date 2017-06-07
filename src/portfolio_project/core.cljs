(ns portfolio-project.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "This text is printed from src/portfolio-project/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

;(defonce app-state (atom {:text "Hello world!"}))
;
;
;(defn hello-world []
;  [:h1 (:text @app-state)])


(defn home []
  [:main
    [:div.page-header 
      [:h1 "Fancy Hedgehog Parade"]
      [:h3 "Come see the fanciest hedgehogs!"]]
    [:div.gallery
      [:ul
        [:li [:a {:href "#"}
          [:img#first {:src "images/happy-birthday-hedgehog.jpg"}]]]
        [:li [:a {:href "#"}
          [:img#second {:src "images/hedgehog_mouth.jpeg"}]]]
        [:li [:a {:href "#"}
          [:img#third {:src "images/hedgehog-with-a-strawberry-hat-big.jpg"}]]]
        [:li [:a {:href "#"}
          [:img#fourth {:src "images/mustache_hedgehog.jpg"}]]]
        [:li [:a {:href "#"}
          [:img#fifth {:src "images/hedgehog-attack-dinosaur.jpg"}]]]
      ]]
    [:section
      [:p "These soothing hedgehogs slowly scroll by for your enjoyment."]]
    [:div.container
      [:div.headbang-images.frame_01]
      [:div.headbang-images.frame_02]
      [:div.headbang-images.frame_03]
      [:div.headbang-images.frame_04]
      [:div.headbang-images.frame_05]]
    [:section
      [:p "This man is less soothing."]]
    [:footer [:h3 "Brought to you by R Jezek."]]
    ])


(reagent/render-component [home]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
