This repo contains some very basic scala syntax and examples with explanation of common patterns/pitfalls, etc. 

A minimal sbt project requires a `build.sbt` file in your project root (I think also a `build.properties` in a `project` subfolder)

Some notes on `sbt` + `sbt console` commands: 

- `sbt` starts the sbt program (you can also type `sbt run` if you just want to run your main and exit)
Everything below assumes you are inside sbt
- `compile` compiles your project, whereas `~compile` compiles the projects and listen to code changes -- if you change
the code, it recompiles directly.
- `console` starts the scala console where you can write scala code, including using code from your project
- `consoleQuick` starts the scala console without your current project (but still with the project dependencies). Useful 
if your project doesn't compile.
- `:paste` in the console allows you to write multiple lines
- `ctr+r` allows you to reverse search in `sbt` and the `console`
- `arrow up/down` shows you your previous (next) commands one by one (both in sbt and console)
- `reload` reloads the sbt stuff -- can be used for example if you add library dependencies
- `exit` exits the sbt, `:q` exits the console

Useful Intellij commands (just by name since the shortcuts might be different, but it might be helpful to know they exist - you search for them in the intellij keymap and update if you want)
- `cmd + ,` opens the keymap editor where you can check shortcuts and change them
- `Declaration or Usages` goes to the declaration of the variable the curser is at or list the usages (you can go back with `Back` in `Navigate`)
- `Type Info` shows the type info of the variable the cursor is at
- `Find Usages` lists all the usages of a variable
- `Refactor` in `Main Menu` to change names in the whole project
