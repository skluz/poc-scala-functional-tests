name := "functional-tests-in-scala"

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.3",
  scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8", "-language:reflectiveCalls", "-language:postfixOps"),
  crossPaths := false
)

lazy val versions = new {
  val TypesafeConfig = "1.3.1"
  val Logback = "1.2.3"
  val ScalaLogging = "3.7.2"
  val ScalaTest = "3.0.1"
  val OkHttp = "3.8.1"
  val Jackson = "2.9.0"
  val Gatling = "2.3.0"
  val Selenium = "3.5.3"
  val SpringBoot = "1.5.6.RELEASE"
}

lazy val `functional-tests-in-scala` = (project in file("."))
  .aggregate(`commons`)
  .aggregate(`commons-api`)
  .aggregate(`commons-mock`)
  .aggregate(`commons-perf`)
  .aggregate(`commons-web`)
  .aggregate(`petstore`)
  .aggregate(`petstore-api-tests`)
  .aggregate(`petstore-app`)
  .aggregate(`petstore-mock`)
  .aggregate(`petstore-perf-tests`)
  .aggregate(`petstore-web-tests`)

lazy val `commons` = project
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "com.typesafe" % "config" % versions.TypesafeConfig,
    "ch.qos.logback" % "logback-classic" % versions.Logback,
    "com.typesafe.scala-logging" %% "scala-logging" % versions.ScalaLogging,
    "org.scalatest" %% "scalatest" % versions.ScalaTest % Test,
    "com.squareup.okhttp3" % "okhttp" % versions.OkHttp,
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % versions.Jackson,
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % versions.Jackson
  ))

lazy val `commons-api` = project
  .settings(commonSettings: _*)
  .dependsOn(`commons` % "compile->compile;test->test")

lazy val `commons-mock` = project
  .settings(commonSettings: _*)
  .dependsOn(`commons` % "compile->compile;test->test")

lazy val `commons-perf` = project
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "io.gatling" % "gatling-core" % versions.Gatling
  ))
  .dependsOn(`commons` % "compile->compile;test->test")

lazy val `commons-web` = project
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "org.seleniumhq.selenium" % "selenium-chrome-driver" % versions.Selenium
  ))
  .dependsOn(`commons` % "compile->compile;test->test")


lazy val `petstore` = project
  .settings(commonSettings: _*)
  .dependsOn(`commons` % "compile->compile;test->test")

lazy val `petstore-api-tests` = project
  .settings(commonSettings: _*)
  .dependsOn(`petstore`, `commons-api` % "compile->compile;test->test")

lazy val `petstore-app` = project
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "org.springframework.boot" % "spring-boot-starter-web" % versions.SpringBoot,
    "org.springframework.boot" % "spring-boot-starter-data-jpa" % versions.SpringBoot,
    "org.springframework.boot" % "spring-boot-starter-actuator" % versions.SpringBoot,
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % versions.Jackson,
    "org.jadira.usertype" % "usertype.extended" % "5.0.0.GA",
    "com.h2database" % "h2" % "1.4.195"
  ))

lazy val `petstore-mock` = project
  .settings(commonSettings: _*)
  .dependsOn(`petstore`, `commons-mock` % "compile->compile;test->test")

lazy val `petstore-perf-tests` = project
  .settings(commonSettings: _*)
  .dependsOn(`petstore`, `commons-perf` % "compile->compile;test->test")

lazy val `petstore-web-tests` = project
  .settings(commonSettings: _*)
  .dependsOn(`petstore`, `commons-web` % "compile->compile;test->test")

commonSettings