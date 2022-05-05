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

// Special Cases
nameCase("AP LLWYD DAFYDD")
nameCase("DICK VAN DYKE")
nameCase("guillermo del toro")
// But not surname forms
nameCase("VAN WILDER")

// Spanish
nameCase("RUIZ Y PICASSO")

// Hebrew
nameCase("RON BEN ISRAEL")
// But not surname forms
nameCase("BEN ROETHLSBERGER")

// Post Nominals
nameCase("SHAQUILLE O'NEAL PHD")

// Ignores Mixed Case
nameCase("Chris DAvenport")
```
