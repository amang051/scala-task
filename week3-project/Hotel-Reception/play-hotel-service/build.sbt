name := """play-hotel-service"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.15"

libraryDependencies += guice

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test,
  "org.slf4j" % "slf4j-api" % "1.7.30",
  "org.playframework" %% "play-slick"            % "6.1.0",
  "org.playframework" %% "play-slick-evolutions" % "6.1.0",
  "mysql" % "mysql-connector-java" % "8.0.26"
)

//libraryDependencies += ws
libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "3.0.0",
  "org.apache.kafka" % "kafka-clients" % "3.0.0",
  "com.auth0" % "java-jwt" % "4.3.0"
)

libraryDependencies += filters
