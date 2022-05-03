ThisBuild / scalaVersion     := "2.12.12"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "JSI"

Compile / scalaSource := baseDirectory.value / "scala" / "main"

lazy val root = (project in file("."))
  .settings(
    name := "chisel4ml",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3"          % "3.4.3",
      "edu.berkeley.cs" %% "chiseltest"       % "0.3.4" % "test",
      "edu.berkeley.cs" %% "dsptools"         % "1.5.3",
      //"edu.berkeley.cs" %% "rocketchip"       % "1.2.6", // replaced by lib/rocketchip.jar
      //"edu.berkeley.cs" %% "rocket-dsptools"  % "1.2.6" // not needed
    ),
    scalacOptions ++= Seq(
      "-Xsource:2.11",
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit",
      "-P:chiselplugin:useBundlePlugin"
    ),
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.4.3" cross CrossVersion.full),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
  )
