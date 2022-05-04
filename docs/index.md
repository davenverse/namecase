# namecase - Fix Capitalization of Peoples Names [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/namecase_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/namecase_2.13)
## Quick Start

To use namecase in an existing SBT project with Scala 2.11 or a later version, add the following dependencies to your
`build.sbt` depending on your needs:

```scala
libraryDependencies ++= Seq(
  "io.chrisdavenport" %% "namecase" % "<version>"
)
```

```scala mdoc
import io.chrisdavenport.namecase.NameCase.nameCase

nameCase("KEITH")
nameCase("LEIGH-WILLIAMS")

// Irish
nameCase("MCCARTHY")
nameCase("O'CALLAGHAN")

// Joiners
nameCase("VAN DYKE")

// Spanish
nameCase("RUIZ Y PICASSO")

// Hebrew
nameCase("RON BEN ISRAEL")

// Post Nominals
nameCase("SHAQUILLE O'NEAL PHD")
```