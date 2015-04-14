import sbt._
import sbt.Keys._
import android.Keys._

object AndroidProject extends Build {

  lazy val androidScala = Project(
    id = "android-test",
    base = file("."),
    settings = projectSettings ++ Seq(
      resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
      libraryDependencies ++= Seq(
        "com.android.support" % "support-v4" % "19.1.0",
        "org.scaloid" %% "scaloid" % "3.6.1-10" withSources() withJavadoc(),
        "io.spray" %%  "spray-json" % "1.3.1"
      )
    )
  )

  def projectSettings = commonSettings ++ droidSettings ++ Seq(
    organization := "hu.elte.inf",
    name := "AndroidTest",
    version := "0.0",
    scalaVersion := "2.11.6",
    scalacOptions in (Compile, doc) ++= Seq("-feature", "-deprecation", "-diagrams"),
    exportJars := true
  )

  def proguardSettings = Seq(
    useProguard in Android := true,
    proguardCache in Android ++= Seq(
      ProguardCache("android.support") % "com.android.support" % "support-v4",
      ProguardCache("org.scaloid") % "org.scaloid" % "scaloid",
      ProguardCache("spray") % "io.spray" %% "spray-json"
    ),
    proguardConfig in Android +=
      scala.io.Source.fromFile(baseDirectory.value / "project/proguard.cfg")
        .getLines.map(_.takeWhile(_ != '#')).filter(_ != "").mkString("\n")
  )

  def commonSettings = Defaults.defaultSettings ++ Seq(
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-feature",
      "-Xlint"
    )
  )

  def droidSettings = android.Plugin.androidBuild ++ proguardSettings ++ Seq(
    platformTarget in Android := "android-19",
    javacOptions in Compile ++= Seq("-encoding", "UTF-8", "-source", "1.6", "-target", "1.6", "-Xlint:deprecation", "-Xlint:unchecked")
  )
}

