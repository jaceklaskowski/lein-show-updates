(ns leiningen.show-updates
  "Show available dependencies updates of the current project."
  (:require [cheshire.core :as json]))

(defn- search-maven-org
	[gid aid]
	(let [url (format "http://search.maven.org/solrsearch/select?wt=json&q=g:%s+AND+a:%s" gid aid)]
  	(slurp url)))

(defn- check-update
	[[dp-name dp-ver]]
	(let [dp-gid (namespace dp-name)
        dp-aid (name dp-name)
        mvn-response (search-maven-org dp-gid dp-aid)
        mvn-response-json (json/decode mvn-response true)
        response-attr (:response mvn-response-json)
        docs-attr (:docs response-attr)
        latest-version (:latestVersion (first docs-attr))]
    {:group-id dp-gid
     :artifact-id dp-aid
     :current-version dp-ver
     :latest-version latest-version}))

(defn- format-dep
  [{:keys [group-id artifact-id current-version latest-version]}]
  ;; display deps in the form of
  ;; [org.clojure/clojure 1.4.0] -> [org.clojure/clojure latest-version]
  (format "[%s/%s %s] => %s" group-id artifact-id current-version (or latest-version "?")))

(defn show-updates
  [project & args]
  (let [deps (:dependencies project)]
    (->> deps
         (map check-update)
         (map format-dep)
         (clojure.string/join "\n")
         (println))))