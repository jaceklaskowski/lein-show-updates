# lein-show-updates

A Leiningen plugin to show available dependencies updates of the current project.

## Usage

Put `[lein-show-updates "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your
`:user` profile.

Should you need to have the plugin available for any project, add the following
to the `~/.lein/profiles.clj`

	{:user
	  {:plugins [[lein-show-updates "0.1.0-SNAPSHOT"]]}}

Either way should let you run the plugin as follows:

    $ lein show-updates

## License

Copyright © 2012 Jacek Laskowski

Distributed under the Eclipse Public License, the same as Clojure.
