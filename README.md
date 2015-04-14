# Setting up the project

1. First we need to create an `sbt` project. `Sbt` stands for `Simple Build Tool`, and is the
    go to build tool when writing scala projects. It can handle both Java and Scala source files
    mixed together very easily. You can add both `Maven` and `Ivy` dependencies to your project very easily.
    A very neat thing about `sbt` is that it uses Scala as it's "configuration" language, so you don't need
    to bother with bloated XML or {insert current flavor of the month markup language here} files, instead
    you can do everything in Scala. `Sbt` takes some learning and getting used to, I don't want to go into
    much detail about that, just take a look ath `project\AndroidTest.scala`.
    The config file always goes into the `project` folder. We can also see a `build.proeprties` file here.
    With this we can pass some config to `sbt` but for now we only need to set what version to use.

2. Let's add the android `sbt` plugin which will take care of most of the heavy lifting for us when compiling to
    Android (after proper and painful configuration that is). To add plugins we need to create a `plugins.sbt` file
    in the `project` folder and add our `sbt` plugins there. (See the file in the folder for the proepr syntax.)
    You could add the plugin as a global one by putting it in `~/.sbt/0.13/plugins` but we rarely want this particular
    one to be added to every project so I'd advise against doing that.

3. Next step is installing Android ADT. This is a not a very complicated task with a good help over at the ADT's official
    site, but everyone has to suffer through it at least once if they want to develop for Android.
    I would not like to go into much detail regarding this point. (The `sbt` plugin will warn us either way if something's
    set up incorrectly).

4. After we got our empty project laid out we need to make it an android project. Now for this you need to have
    `android` and the ADT in your path. If you have that you can issue an `android create project` command like so:

    ```bash
    android create project --path android-test -n AndroidTest -a Main -k hu.inf.elte.androidtest -t android-19
    ```
    Path is the directory you want to be your android project, `-n` is the name of the project, `-a` is the name if the
    main activity, `-k` is the package name and `-t` is the preferred android version. (API level 19 is 4.4.2). For more info
    on the command check its help.

<!--
   -5. Now we have to do some housekeeping. First of all we have a `Main.java` file in our `src` folder. We are working with Scala
   -    so we don't really need that. Also the folder structure is not quite what we want. By default we get `src/package/Main.java`
   -    but in case of scala projects we need something more complicated. My usual folder layout is:
   -    ```
   -    src
   -    |-main
   -      |-resources
   -      |-scala
   -      |-java (if there are java sources)
   -    |-test
   -      |-resources
   -      |-scala
   -      |-java
   -    ```
   -    So let's do that instead.
   -
   -->
6. Now we have to set up our `android-sdk-plugin` project properly. We can do that by making our config object extend
    `android.AutoBuild` but we will do some fine tuning with our config. See this in `project/AndroidProject.scala`.

7. Finally we have our project set up so now we can start hacking.

# Using IDEA

Scala is really not a language we want to use only a text editor for, so we will set up IntelliJ IDEA to work on our project.
You can go grab the community edition on their website or if you are a student you can get a non-commercial license
for the ultimate edition. We will also need the Scala and SBT plugins.
After this we can import (use auto import) our project as an SBT project. (I had to still add `android.jar` as a library because IDEA did not
pick it up for some reason.)
File -> Import Project... -> select project root folder-> OK -> Import project from external model -> SBT Project -> 
Check "Use auto-import" & for Project SDK, select an Android API platform -> Finish. 
Choose to configure the android project when IDEA asks.

<!--
   -http://www.scala-sbt.org/download.html
   -https://github.com/pfn/android-sdk-plugin
   -->

