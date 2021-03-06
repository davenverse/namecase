# namecase - Fix Capitalization of Peoples Names [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/namecase_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/namecase_2.13)
## Quick Start

To use namecase in an existing SBT project with Scala 2.12 or a later version, add the following dependencies to your
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

// Special Cases
nameCase("AP LLWYD DAFYDD")
nameCase("guillermo del toro")
nameCase("DICK VAN DYKE")
// But not given name forms
nameCase("VAN WILDER")

// Spanish
nameCase("RUIZ Y PICASSO")
nameCase("FRANCISCO GOMEZ DE QUEVEDO VILLEGAS Y SANTIBANEZ")

// Hebrew
nameCase("RON BEN ISRAEL")
// But not given name forms
nameCase("BEN ROETHLISBERGER")

// Post Nominals
nameCase("SHAQUILLE O'NEAL PHD")

// Ignores Mixed Case
nameCase("Chris DAVenport")
// Applies if additional flag set
nameCase("Chris DAVenport", ignoreIfMixed = false)
```
