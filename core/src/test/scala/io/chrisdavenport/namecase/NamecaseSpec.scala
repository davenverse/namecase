package io.chrisdavenport.namecase

import NameCase.nameCase
import munit.FunSuite

class NameCaseSpec extends FunSuite {

  test("Should case correctly") {
    assertEquals(nameCase("KEITH"), "Keith")
    assertEquals(nameCase("LEIGH-WILLIAMS"), "Leigh-Williams") 
  }

  test("Should case irish correctly"){
    assertEquals(nameCase("MCCARTHY"), "McCarthy")
    // Exception 
    assertEquals(nameCase("MACKINTOSH"), "Mackintosh")
    assertEquals(nameCase("O'CALLAGHAN"), "O'Callaghan")
    assertEquals(nameCase("ST. JOHN"), "St. John")
  }

  test("Should case exceptions"){
    assertEquals(nameCase("AL FOO"), "al Foo")
    assertEquals(nameCase("AP WELSH"), "ap Welsh")
    assertEquals(nameCase("bin, binti, binte Arabic".toUpperCase()), "bin, binti, binte Arabic")
    assertEquals(nameCase("della delle Italian".toUpperCase()), "della delle Italian")
    assertEquals(nameCase("da, de, di Italian; du French; do Brasil".toUpperCase()), "da, de, di Italian; du French; do Brasil")
    assertEquals(nameCase("das, dos Brasileiros".toUpperCase()), "das, dos Brasileiros")
    assertEquals(nameCase("del Italian; der/den Dutch Flemish".toUpperCase()), "del Italian; der/den Dutch Flemish")
    assertEquals(nameCase("lo Italian; le French".toUpperCase()), "lo Italian; le French")
    assertEquals(nameCase("el Greek".toUpperCase(), spanish = false), "el Greek")
    assertEquals(nameCase("El Spanish".toUpperCase()), "El Spanish")
    assertEquals(nameCase("la French".toUpperCase(), spanish = false), "la French")
    assertEquals(nameCase("LA SPANISH"), "La Spanish")
    assertEquals(nameCase("ten, ter Dutch Flemish".toUpperCase()), "ten, ter Dutch Flemish")
    assertEquals(nameCase("Base van German / Forename Van".toUpperCase()), "Base van German / Forename Van")
    assertEquals(nameCase("von Dutch / Flemish".toUpperCase()), "von Dutch / Flemish")
    assertEquals(nameCase("VON STREIT"), "von Streit")
    assertEquals(nameCase("AP LLWYD DAFYDD"), "ap Llwyd Dafydd")
    assertEquals(nameCase("DICK VAN DYKE"), "Dick van Dyke")
    assertEquals(nameCase("VAN WILDER"), "Van Wilder")
  }

  test("Should case roman numerals"){
    assertEquals(nameCase("HENRY VIII"), "Henry VIII")
    // Leave the Li surname alone
    assertEquals(nameCase("AMY LI"), "Amy Li")
  }

  test("Should case spanish conjunctions"){
    assertEquals(nameCase("IGNACIO Y ALBERTO"), "Ignacio y Alberto")
    List("Ruiz y Picasso", "Dato e Iradier", "Mas i Gavarró")
      .foreach(name => 
        assertEquals(nameCase(name.toUpperCase()), name)
      )
  }

  test("Should case hebrew names") {
    assertEquals(nameCase("Ron ben Israel".toUpperCase()), "Ron ben Israel")
    // Leave Ben alone 
    assertEquals(nameCase("Ben Roethlisberger".toUpperCase()), "Ben Roethlisberger")
    assertEquals(nameCase("Aharon ben Amram Ha-Kohein".toUpperCase()), "Aharon ben Amram Ha-Kohein")
  }

  test("Should case post-nominals"){
    assertEquals(nameCase("ADISA AZAPAGIC MBE FRENG FRSC FICHEME"), "Adisa Azapagic MBE Freng Frsc Ficheme")

    val exclude1 = Set("MOst")
    assertEquals(nameCase("ČERNÝ MOST", excludedPostnominals = exclude1), "Černý Most")
    assertEquals(nameCase("tam phd"), "Tam PhD")
  }

}
