(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[adzerk/bootlaces   "0.1.9" :scope "test"]
                  [cljsjs/boot-cljsjs "0.4.6" :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +version+ "0.12.2-7-2-new-tags")
(bootlaces! +version+)

(task-options!
 pom  {:project     'precursor/react
       :version     +version+
       :description "React.js packaged up with Google Closure externs"
       :url         "http://facebook.github.io/react/"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"BSD" "http://opensource.org/licenses/BSD-3-Clause"}})

(deftask download-react []
  (download :url "https://github.com/PrecursorApp/react/releases/download/v0.12.2-new-tags/react-0.12.2-new-tags.zip"
            :checksum "3da60135537fcc5bebf615fe2f787797"
            :unzip true))

(deftask package []
  (comp
    (download-react)
    (sift :move {#"^react-.*/build/react.js" "cljsjs/production/react.inc.js"
                 #"^react-.*/build/react.min.js" "cljsjs/production/react.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.react")))

(deftask package-with-addons []
  (task-options! pom {:project 'precursorapp/react-with-addons
                      :description "React.js with addons packaged up with Google Closure externs"})
  (comp
    (download-react)
    (sift :move {#"^react-.*/build/react-with-addons.js" "cljsjs/production/react-with-addons.inc.js"
                 #"^react-.*/build/react-with-addons.min.js" "cljsjs/production/react-with-addons.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.react")))
