name := "EncryExplorer"

version := "0.1"

scalaVersion := "2.12.6"

organization := "org.encryfoundation"

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

val loggingDependencies = Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.+",
  "ch.qos.logback" % "logback-classic" % "1.+",
  "ch.qos.logback" % "logback-core" % "1.+"
)

val testingDependencies = Seq(
  "com.typesafe.akka" %% "akka-testkit" % "2.4.+" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % "test",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.+" % "test"
)

libraryDependencies ++= Seq(
  "javax.xml.bind" % "jaxb-api" % "2.+",
  "com.google.guava" % "guava" % "21.+",
  "com.iheart" %% "ficus" % "1.4.2",
  "org.slf4j" % "slf4j-api" % "1.7.+",
  "org.bouncycastle" % "bcprov-jdk15on" % "1.58",
  "org.whispersystems" % "curve25519-java" % "+",
  "org.rudogma" %% "supertagged" % "1.+",
  "org.scorexfoundation" %% "scrypto" % "2.1.1",
  "com.github.oskin1" %% "prism" % "0.2.2",
  "de.heikoseeberger" %% "akka-http-circe" % "1.20.1",
) ++ databaseDependencies ++ apiDependencies ++ loggingDependencies ++ testingDependencies
