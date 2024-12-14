ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.10"

lazy val root = (project in file("."))
  .settings(
    name := "CS2"
  )

val sparkVersion = "3.2.1"
val akkaVersion = "2.6.20"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.google.cloud.bigdataoss" % "gcs-connector" % "hadoop3-2.2.5",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % sparkVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-protobuf-v3" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-kafka" % "2.1.1",
  "org.apache.kafka" % "kafka-clients" % "3.2.0",
  "io.spray" %% "spray-json" % "1.3.6",
  "org.apache.commons" % "commons-csv" % "1.10.0",
  "com.typesafe.akka" %% "akka-http" % "10.2.10",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.10",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "ch.qos.logback" % "logback-classic" % "1.2.13",
  "org.slf4j" % "slf4j-api" % "1.7.36",
  "ch.megard" %% "akka-http-cors" % "1.1.3"
)


