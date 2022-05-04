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
    assertEquals(nameCase("O'CALLAGHAN"), "O'Callaghan")
    assertEquals(nameCase("ST. JOHN"), "St. John")
  }

  test("Should case appelations"){
    assertEquals(nameCase("VON STREIT"), "von Streit")
    assertEquals(nameCase("AP LLWYD DAFYDD"), "ap Llwyd Dafydd")
    assertEquals(nameCase("VAN DYKE"), "van Dyke")
  }

  test("Should case roman numerals"){
    assertEquals(nameCase("HENRY VIII"), "Henry VIII")
  }

}
