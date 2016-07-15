name := """scala-slack-webhook"""

organization := "com.guardian"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "typesafe-repo" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.typesafe.play" %% "play-json" % "2.3.4",
  "org.apache.httpcomponents" % "httpclient" % "4.3.4",
  "com.typesafe" % "config" % "1.3.0"
)

bintraySettings

com.typesafe.sbt.SbtGit.versionWithGit