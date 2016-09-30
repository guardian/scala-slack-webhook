name := "scala-slack-webhook"

organization := "com.gu"

scalaVersion := "2.11.8"

description := "Scala library for pushing incoming webhooks to Slack"

scalacOptions ++= Seq("-feature", "-deprecation")

resolvers ++= Seq(
  "typesafe-repo" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.typesafe.play" %% "play-json" % "2.5.4",
  "com.typesafe.play" %% "play-ws" % "2.5.4",
  "com.typesafe" % "config" % "1.3.0"
)

publishMavenStyle := true
bintrayOrganization := Some("guardian")
bintrayRepository := "editorial-tools"
licenses += ("Apache-2.0", url("https://github.com/guardian/scala-slack-webhook/blob/master/LICENSE"))

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](version),
    buildInfoPackage := "com.gu.scalaslackwebhook"
  )