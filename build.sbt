name := """Explorer"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.6"

organization := "org.encryfoundation"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

val akkaHttpVersion = "10.1.3"
val circeVersion = "0.9.3"
val doobieVersion = "0.5.2"

val databaseDependencies = Seq(
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion,
  "org.tpolecat" %% "doobie-hikari"   % doobieVersion
)

val apiDependencies = Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.14.1",
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
)

val testingDependencies = Seq(
  "com.typesafe.akka" %% "akka-testkit" % "2.4.+" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % "test",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.+" % "test"
)

libraryDependencies += guice
libraryDependencies ++= Seq(
  "javax.xml.bind" % "jaxb-api" % "2.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)
