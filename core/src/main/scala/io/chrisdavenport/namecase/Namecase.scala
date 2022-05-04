package io.chrisdavenport.namecase

object NameCase {

  def nameCase(name: String): String = {
    val transforms = List(
      lowercase(_),
      capitalizeFirstLetter(_),
      lowercaseFinalS(_)
    )
    transforms.foldLeft(name){ case (name, f) => f(name)}
  }

  def lowercase(name: String): String = name.toLowerCase()

  def capitalizeFirstLetter(name: String): String = {
    "([\\s,.:;\"'(-]|^)([^\\s,.:;\"'(-])".r
      .replaceAllIn(name, m => m.group(1) ++ m.group(2).toUpperCase)
  }
  def lowercaseFinalS(name: String): String = {
    "'([^\\s,.:;\"'(-])([\\s,.:;\"'(-]|$)".r
      .replaceAllIn(name, m => "'" ++ m.group(1).toLowerCase() ++ m.group(2))
  }


}