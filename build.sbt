import org.scalafmt.sbt.ScalafmtPlugin.autoImport.scalafmtOnCompile
val mainScala = "2.13.3"
val allScala  = Seq("2.12.12", mainScala)

enablePlugins(GitVersioning)

lazy val root = project
  .in(file("."))
  .aggregate(rezilience.js, rezilience.jvm)
  .settings(
    publish := {},
    publishLocal := {}
  )

lazy val rezilience = crossProject(JSPlatform, JVMPlatform)
  .in(file("rezilience"))
  .settings(
    name := "rezilience",
    organization := "nl.vroste",
    homepage := Some(url("https://github.com/svroonland/rezilience")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    scalaVersion := mainScala,
    crossScalaVersions := allScala,
    parallelExecution in Test := false,
    fork in Test := false,
    fork in run := true,
    publishMavenStyle := true,
    bintrayOrganization := Some("vroste"),
    bintrayPackageLabels := Seq("zio", "circuit-breaker"),
    scalafmtOnCompile := true,
    libraryDependencies ++= Seq(
      "dev.zio"                %%% "zio-streams"             % "1.0.1",
      "dev.zio"                %%% "zio-test"                % "1.0.1" % "test",
      "dev.zio"                %%% "zio-test-sbt"            % "1.0.1" % "test",
      "org.scala-lang.modules" %%% "scala-collection-compat" % "2.2.0"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")
