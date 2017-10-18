name := "ultimate-testing-framework"

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
  val ScalaTest = "3.0.4"
  val Jackson = "2.9.1"
  val JsonPath = "2.4.0"
  val Gatling = "2.3.0"
  val Selenium = "3.6.0"
  val JavaFaker = "0.13"
  val ApacheHttpClient = "4.5.3"
  val Scalatra = "2.5.1"
}

lazy val `ultimate-testing-framework` = (project in file("."))
  .aggregate(`commons`)
  .aggregate(`commons-api`)
  .aggregate(`commons-mock`)
  .aggregate(`commons-perf`)
  .aggregate(`commons-web`)
  .aggregate(`petstore`)
  .aggregate(`petstore-api-tests`)
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
    "org.apache.httpcomponents" % "httpclient" % versions.ApacheHttpClient,
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % versions.Jackson,
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % versions.Jackson,
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % versions.Jackson,
    "com.github.javafaker" % "javafaker" % versions.JavaFaker,
    "com.jayway.jsonpath" % "json-path" % versions.JsonPath
  ))

lazy val `commons-api` = project
  .settings(commonSettings: _*)
  .dependsOn(`commons` % "compile->compile;test->test")

lazy val `commons-mock` = project
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "org.scalatra" %% "scalatra" % versions.Scalatra,
    "org.scalatra" %% "scalatra-json" % versions.Scalatra,
    "org.json4s" %% "json4s-jackson" % "3.5.3",
    "org.json4s" % "json4s-ext_2.12" % "3.5.3",
    "org.eclipse.jetty" % "jetty-webapp" % "9.4.7.v20170914"
  ))
  .dependsOn(`commons` % "compile->compile;test->test")

lazy val `commons-perf` = project
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "io.gatling.highcharts" % "gatling-charts-highcharts" % versions.Gatling
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