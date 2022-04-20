ThisBuild / scalaVersion     := "2.12.15"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "JSI"

Compile / scalaSource := baseDirectory.value / "scala" / "main"

val chiselVersion = "3.5.2"

lazy val root = (project in file("."))
  .settings(
    name := "chisel4ml",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3"          % chiselVersion,
      "edu.berkeley.cs" %% "chiseltest"       % "0.5.1" % "test",
      "edu.berkeley.cs" %% "dsptools"         % "1.5.2",
      "edu.berkeley.cs" %% "rocketchip"       % "1.2.6",
      "edu.berkeley.cs" %% "rocket-dsptools"  % "1.2.6"
    ),
    scalacOptions ++= Seq(
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit",
      "-P:chiselplugin:genBundleElements",
    ),
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % chiselVersion cross CrossVersion.full),
  )
