import sbt._
import Keys._

trait BuildSettings {

  val githubUrl = "https://github.com/sullivan-/musette"

  val nonConsoleScalacOptions = Seq(
    "-Xfatal-warnings",
    "-Ywarn-unused-import")

  val otherScalacOptions = Seq(
    "-Xfuture",
    "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
    "-Ywarn-unused-import",
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked")

  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "net.jsmscs",
    version := "0.0-SNAPSHOT",
    scalaVersion := "2.11.7",

    // compile
    scalacOptions ++= nonConsoleScalacOptions ++ otherScalacOptions,

    // console
    scalacOptions in (Compile, console) ~= (_ filterNot (nonConsoleScalacOptions.contains(_))),
    scalacOptions in (Test, console) ~= (_ filterNot (nonConsoleScalacOptions.contains(_))),

    // scaladoc
    scalacOptions in (Compile, doc) ++= Seq("-groups", "-implicits", "-encoding", "UTF-8"),
    scalacOptions in (Compile, doc) ++= {
      val projectName = (name in (Compile, doc)).value
      val projectVersion = (version in (Compile, doc)).value
      Seq("-doc-title", s"$projectName $projectVersion API")
    },
    scalacOptions in (Compile, doc) <++= (baseDirectory in LocalProject("musette"), version) map { (bd, v) =>
      val tagOrBranch = if (v endsWith "SNAPSHOT") gitHash else ("v" + v)
      Seq("-sourcepath", bd.getAbsolutePath,
          "-doc-source-url", s"$githubUrl/tree/$tagOrBranch€{FILE_PATH}.scala")
    },
    autoAPIMappings := true,
    apiMappings += (scalaInstance.value.libraryJar ->
                    url(s"http://www.scala-lang.org/api/${scalaVersion.value}/")),

    // test
    logLevel in test := Level.Info, // switch to warn to get less output from scalatest
    testOptions in Test += Tests.Argument("-oF"),

    // dependencies
    resolvers += Resolver.typesafeRepo("releases"),
    resolvers += Resolver.sonatypeRepo("releases"),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "1.0.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % Test,

    // publish
    publishMavenStyle := true,
    pomIncludeRepository := { _ => false },
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    licenses := Seq("Apache License, Version 2.0" ->
                    url("http://www.apache.org/licenses/LICENSE-2.0"))
    
  )

  private def gitHash = sys.process.Process("git rev-parse HEAD").lines_!.head

}

object LongevityBuild extends Build with BuildSettings {

  lazy val musette = Project(
    id = "musette",
    base = file("."),
    settings = buildSettings ++ Seq(
      libraryDependencies += "org.mongodb" %% "casbah" % "3.0.0",
      libraryDependencies += "net.jsmscs" %% "longevity" % "0.3-SNAPSHOT",
      libraryDependencies += "net.jsmscs" %% "emblem" % "0.3-SNAPSHOT"
    )
  )

}
