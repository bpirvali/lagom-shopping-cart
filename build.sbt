organization in ThisBuild := "com.nfl.de"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `shoppingcart` = (project in file("."))
  .aggregate(`shoppingcart-api`, `shoppingcart-impl`)

lazy val `shoppingcart-api` = (project in file("shoppingcart-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `shoppingcart-impl` = (project in file("shoppingcart-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`shoppingcart-api`)

//lagomCassandraCleanOnStart :=true
lagomCassandraCleanOnStart in ThisBuild := true
//lagomCassandraEnabled in ThisBuild := false
//lagomUnmanagedServices in ThisBuild := Map("cas_native" -> "http://localhost:9042")