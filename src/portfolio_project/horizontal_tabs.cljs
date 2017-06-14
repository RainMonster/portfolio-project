(ns portfolio-project.horizontal-tabs
  (:require [reagent.core :as reagent]))

(defmacro handler-fn
  ([& body]
    `(fn [~'event] ~@body nil)))

(defn deref-or-value
  "Takes a value or an atom
  If it's a value, returns it
  If it's a Reagent object that supports IDeref, returns the value inside it by derefing
  "
  [val-or-atom]
  (if (satisfies? IDeref val-or-atom)
    @val-or-atom
    val-or-atom))

(def tabs-args-desc
  [{:name :model     :required true                  :type "unique-id | atom"         :description "the unique identifier of the currently selected tab"}
   {:name :tabs      :required true                  :type "vector of tabs | atom"    :description "one element in the vector for each tab. Typically, each element is a map with :id and :label keys"}
   {:name :on-change :required true                  :type "unique-id -> nil"         :description "called when user alters the selection. Passed the unique identifier of the selection"}
   {:name :id-fn     :required false :default :id    :type "tab -> anything"          :description [:span "given an element of " [:code ":tabs"] ", returns its unique identifier (aka id)"]}
   {:name :label-fn  :required false :default :label :type "tab -> string | hiccup"   :description [:span "given an element of " [:code ":tabs"] ", returns its displayable label"]}
   {:name :style     :required false                 :type "CSS style map"            :description "CSS styles to add or override (for each individual tab rather than the container)"}])


(defn horizontal-tabs
  [& {:keys [model tabs on-change id-fn label-fn style]
      :or   {id-fn :id label-fn :label}
      :as   args}]
  ;{:pre [(validate-args-macro tabs-args-desc args "tabs")]}
  (let [current  model
        tabs     tabs
        _        (assert (not-empty (filter #(= current (id-fn %)) tabs)) "model not found in tabs vector")]
    [:ul
     {:class "rc-tabs nav nav-tabs noselect"}
     (for [t tabs]
       (let [id        (id-fn  t)
             label     (label-fn  t)
             selected? (= id current)]                   ;; must use current instead of @model to avoid reagent warnings
         [:li
          {:class (if selected? "active")
           :key   (str id)}
          [:a
           {:style    (merge {:cursor "pointer"}
                             style)
            ;:on-click (when on-change (handler-fn (on-change id)))
            :on-click on-change
          }
           label]]))]))