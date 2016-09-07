name := """top-10-hashtags"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies += "org.twitter4j" % "twitter4j" % "4.0.4"
libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.4"
