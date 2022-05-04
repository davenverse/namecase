ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization := "io.chrisdavenport"
ThisBuild / organizationName := "Christopher Davenport"
ThisBuild / licenses := Seq(License.MIT)
ThisBuild / developers := List(
  tlGitHubDev("christopherdavenport", "Christopher Davenport")
)
ThisBuild / tlCiReleaseBranches := Seq("main")
ThisBuild / tlSonatypeUseLegacyHost := true


val Scala213 = "2.13.7"

ThisBuild / crossScalaVersions := Seq("2.12.15", Scala213)
ThisBuild / scalaVersion := Scala213

ThisBuild / testFrameworks += new TestFramework("munit.Framework")

val catsV = "2.7.0"
val munitCatsEffectV = "1.0.7"


// Projects
lazy val `namecase` = tlCrossRootProject
  .aggregate(core)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "namecase",
    libraryDependencies ++= Seq(
      "org.typelevel"               %%% "cats-core"                  % catsV,
      "org.typelevel"               %%% "munit-cats-effect-3"        % munitCatsEffectV         % Test,
    )
  ).jsSettings(
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule)},
  )

lazy val site = project.in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .dependsOn(core.jvm)
