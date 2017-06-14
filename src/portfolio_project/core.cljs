(ns portfolio-project.core
  (:require [reagent.core :as reagent :refer [atom]]
            [portfolio-project.horizontal-tabs :refer [horizontal-tabs]]))

(enable-console-print!)

(println "This text is printed from src/portfolio-project/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

;(defonce app-state (atom {:text "Hello world!"}))
;
;
;(defn hello-world []
;  [:h1 (:text @app-state)])

(def state-atm (reagent/atom {:id :test1}))

(defn change-content-state [state]
  (reset! @state-atm state))



(defn concentric-circles []
  [:svg {:style {:border "1px solid"
                 :background "white"
                 :width "150px"
                 :height "150px"}}
    [:circle {:r 50, :cx 75, :cy 75, :fill "green"}]
    [:circle {:r 25, :cx 75, :cy 75, :fill "blue"}]
    [:path {:stroke-width 12
            :stroke "white"
            :fill "none"
            :d "M 30,40 C 100,40 50,110 120,110"}]])

(defn many-circles []
  (into
    [:svg {:style {:border "1px solid"
                   :background "white"
                   :width "600px"
                   :height "600px"}}]
      (for [i (range 12)]
        [:g
          {:transform (str
                        "translate(300,300) "
                        "rotate(" (* i 30) ") "
                        "translate(100)")}
          [concentric-circles]])))

(defn wheres-waldo []
    (let [show? (reagent/atom false)]
      (fn waldo []
        [:div
          (if @show?
            [:div
              [:h3 "You found me!"]
              [:img
                {:src "https://goo.gl/EzvMNp"
                 :style {:height "320px"}}]]
            [:div
              [:h3 "Where are you now?"]
              [:img
                {:src "https://i.ytimg.com/vi/HKMlPDwmTYM/maxresdefault.jpg"
                 :style {:height "320px"}}]])
          [:button
            {:on-click
              (fn [e]
                (swap! show? not))}
              (if @show? "reset" "search")]]))
  )

(defn hedgehog-slideshow []
  [:div.page-header 
    [:h1 "Fancy Hedgehog Parade"]
    [:h3 "Come see the fanciest hedgehogs!"]
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
      [:p "These soothing hedgehogs slowly scroll by for your enjoyment."]]])

(defn frightening-images []
  [:div.frightening-container
    [:div.headbang-images.frame_01]
    [:div.headbang-images.frame_02]
    [:div.headbang-images.frame_03]
    [:div.headbang-images.frame_04]
    [:div.headbang-images.frame_05]
    [:section
      [:p "This man is less soothing."]]]
  )



(def c
  (reagent/atom 1))

(defn counter []
  [:div
    [:div "Current counter value: " @c]
    [:button
      {:disabled ( > @c 4)
       :on-click
        (fn clicked [e]
          (swap! c inc))}
        "inc"]
    [:button
      {:disabled ( < @c 2)
       :on-click
        (fn clicked [e]
          (swap! c dec))}
        "dec"]
    (into [:div] (repeat @c [concentric-circles]))])


(def definition 
    [{:id "01" :label "Hedgehog Slideshow" :tab-class "video-list"}
     {:id "02" :label "Frightening Images" :tab-class "image-list"}
     {:id "03" :label "Where's Waldo" :tab-class "gallery-list"}])


(defn nav-tabs []
  (let [selected-tab-id (reagent/atom (:id (first definition)))]
    (fn []
      [:div.project-tabs
        [:ul.project-tabs-ul
          (for [t definition]
            (let [id (:id t)
                  label (:label t)
                  tab-class (:tab-class t)
                  selected? (reagent/atom (= id @selected-tab-id))]
              [:li.project-tabs-li
                {:class (when @selected? "active")}
                [:a
                  {:on-click #(reset! selected-tab-id id)} label]]))]
          (cond
            (= "01" @selected-tab-id) [hedgehog-slideshow]
            (= "02" @selected-tab-id) [frightening-images]
            (= "03" @selected-tab-id) [wheres-waldo])])))


(defn home []
    [:div.main-container
      [:h2.header-intro "Rhonda Jezek: Web Developer"]
      [nav-tabs]
      [:footer [:h3 "Brought to you by R Jezek."]]]
      ;[:form
      ;  {:on-submit
      ;    (fn [e]
      ;      (.preventDefault e)
      ;      (js/alert
      ;        (str "You said: " (.. e -target -elements -message -value))))}
      ;  [:label
      ;    "Say something:"
      ;    [:input
      ;      {:name "message"
      ;       :type "text"
      ;       :default-value "Hello"}]]
      ;  [:input {:type "submit"}]]
      ;[many-circles]
      ;[counter]
)


(reagent/render-component [home]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
