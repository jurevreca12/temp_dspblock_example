ThisBuild / scalaVersion     := "2.12.15"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "JSI"

Compile / scalaSource := baseDirectory.value / "src" / "main" 

lazy val root = (project in file("."))
  .settings(
    name := "chisel4ml",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3"          % "3.5.6",
      "edu.berkeley.cs" %% "chiseltest"       % "0.5.3" % "test",
      "edu.berkeley.cs" %% "dsptools"         % "1.5.3",
      //"edu.berkeley.cs" %% "rocketchip"       % "1.2.6", // replaced by lib/rocketchip.jar
    ),
    scalacOptions ++= Seq(
      "-Xsource:2.11",
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit",
      "-P:chiselplugin:genBundleElements"
    ),
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.5.3" cross CrossVersion.full),
  )
