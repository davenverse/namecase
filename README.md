# namecase - Fix Capitalization of Peoples Names [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/namecase_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/namecase_2.13) ![Code of Conduct](https://img.shields.io/badge/Code%20of%20Conduct-Scala-blue.svg)

## [Head on over to the microsite](https://ChristopherDavenport.github.io/namecase)

## Quick Start

To use namecase in an existing SBT project with Scala 2.12 or a later version, add the following dependencies to your
`build.sbt` depending on your needs:

```scala
libraryDependencies ++= Seq(
  "io.chrisdavenport" %% "namecase" % "<version>"
)
```

### Acknowledgements

This library is a port of the typescript library @foundernest/namecase by Daniel Seijo whis is also a port of the PHP package by Yuri Tkachenko which is also a port of the Perl library and owes most of its functionality to the Perl version by Mark Summerfield. Any bugs in the Scala port are my fault.
Credits

Original PERL `Lingua::EN::NameCase` Version:

    Copyright © Mark Summerfield 1998-2014. All Rights Reserved.
    Copyright © Barbie 2014-2020. All Rights Reserved.

Ruby Version:

    Copyright © Aaron Patterson 2006. All Rights Reserved.

PHP Version:

    Copyright © Yuri Tkachenko 2016-2020. All Rights Reserved.

Typescript version:

    Copyright © Daniel Seijo

Scala version:

    Copyright © Christopher Davenport
