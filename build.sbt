name := "Explorer"
version := "0.8.0"
scalaVersion := "2.12.6"
organization := "org.encryfoundation"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
PlayKeys.devSettings := Seq("play.server.http.port" -> "9053")

val akkaHttpVersion = "10.1.3"
val doobieVersion = "0.5.2"

val databaseDependencies = Seq(
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2" % doobieVersion,
  "org.tpolecat" %% "doobie-hikari" % doobieVersion
)

val apiDependencies = Seq(
  "com.dripower" %% "play-circe" % "2609.1" exclude("io.circe", "*"),
  "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.14.1",
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
)

val testingDependencies = Seq(
  "com.typesafe.akka" %% "akka-testkit" % "2.5.14" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % "test",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
  "org.mockito" % "mockito-core" % "2.19.1" % Test
)

val loggingDependencies = Seq(
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)
libraryDependencies += guice
libraryDependencies ++= (Seq(
  "net.codingwell" %% "scala-guice" % "4.2.1",
  "javax.xml.bind" % "jaxb-api" % "2.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "com.iheart" %% "ficus" % "1.4.2",
  "org.bouncycastle" % "bcprov-jdk15on" % "1.58",
  "org.whispersystems" % "curve25519-java" % "0.5.0",
  "org.rudogma" %% "supertagged" % "1.4",
  "org.encry" %% "encry-common" % "0.8.0",
  "de.heikoseeberger" %% "akka-http-circe" % "1.20.1",
) ++ databaseDependencies ++ apiDependencies ++ testingDependencies ++ loggingDependencies)
  .map(_ exclude("ch.qos.logback", "*") exclude("ch.qos.logback", "*"))