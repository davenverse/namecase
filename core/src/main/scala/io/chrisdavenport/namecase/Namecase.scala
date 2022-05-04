package io.chrisdavenport.namecase

import scala.util.matching._
object NameCase {

  def nameCase(
    name: String,
    ignoreIfMixed: Boolean = true,
    irish: Boolean = true,
    roman: Boolean = true,
    spanish: Boolean = true,
    hebrew: Boolean = true,
    postnominal: Boolean = true,
    excludedPostnominals: Set[String] = Set()
  ): String = {
    if (ignoreIfMixed && skipMixed(name)){
      name
    } else {
      val transforms = List(
        lowercase(_),
        capitalizeFirstLetter(_),
        lowercaseFinalS(_),
        if (irish) updateIrish(_) else identity[String](_),
        fixKnownIncorrect,
        if (roman) updateRoman(_) else identity[String](_),
        if (spanish) updateSpanish(_) else updateNotSpanish(_),
        if (hebrew) updateHebrew(_) else identity[String](_),
        if (postnominal) updatePostNominals(_, excludedPostnominals) else identity[String](_),
      )
      transforms.foldLeft(name){ case (name, f) => f(name)}
    }
  }

  private def skipMixed(name: String): Boolean = {
    !(
      name.charAt(0) == name.charAt(0).toLower ||
      name.toLowerCase == name || 
      name.toUpperCase == name
    )
  }

  private def lowercase(name: String): String = name.toLowerCase()

  private def capitalizeFirstLetter(name: String): String = {
    "([\\s,.:;\"'(-]|^)([^\\s,.:;\"'(-])".r
      .replaceAllIn(name, m => m.group(1) ++ m.group(2).toUpperCase)
  }
  private def lowercaseFinalS(name: String): String = {
    "'([^\\s,.:;\"'(-])([\\s,.:;\"'(-]|$)".r
      .replaceAllIn(name, m => "'" ++ m.group(1).toLowerCase() ++ m.group(2))
  }

  private val irishExceptions: List[String => String] = List(
    "\\bMacEdo".r.replaceAllIn(_, "Macedo"),
    "\\bMacEvicius".r.replaceAllIn(_, "Macevicius"),
    "\\bMacHado".r.replaceAllIn(_, "Machado"),
    "\\bMacHar".r.replaceAllIn(_, "Machar"),
    "\\bMacHin".r.replaceAllIn(_, "Machin"),
    "\\bMacHlin".r.replaceAllIn(_, "Machlin"),
    "\\bMacIas".r.replaceAllIn(_, "Macias"),
    "\\bMacIulis".r.replaceAllIn(_, "Maciulis"),
    "\\bMacKie".r.replaceAllIn(_, "Mackie"),
    "\\bMacKle".r.replaceAllIn(_, "Mackle"),
    "\\bMacKlin".r.replaceAllIn(_, "Macklin"),
    "\\bMacKmin".r.replaceAllIn(_, "Mackmin"),
    "\\bMacQuarie".r.replaceAllIn(_, "Macquarie"),
    "\\bMacOmber".r.replaceAllIn(_, "Macomber"),
    "\\bMacIn".r.replaceAllIn(_, "Macin"),
    "\\bMacKintosh".r.replaceAllIn(_, "Mackintosh"),
    "\\bMacKen".r.replaceAllIn(_, "Macken"),
    "\\bMacHen".r.replaceAllIn(_, "Machen"),
    "\\bMacisaac".r.replaceAllIn(_, "MacIsaac"),
    "\\bMacHiel".r.replaceAllIn(_, "Machiel"),
    "\\bMacIol".r.replaceAllIn(_, "Maciol"),
    "\\bMacKell".r.replaceAllIn(_, "Mackell"),
    "\\bMacKlem".r.replaceAllIn(_, "Macklem"),
    "\\bMacKrell".r.replaceAllIn(_, "Mackrell"),
    "\\bMacLin".r.replaceAllIn(_, "Maclin"),
    "\\bMacKey".r.replaceAllIn(_, "Mackey"),
    "\\bMacKley".r.replaceAllIn(_, "Mackley"),
    "\\bMacHell".r.replaceAllIn(_, "Machell"),
    "\\bMacHon".r.replaceAllIn(_, "Machon"),
  )

  private val irishExtras: List[String => String] = List(
    "\\bMacmurdo".r.replaceAllIn(_, "MacMurdo"),
    "\\bMacisaac".r.replaceAllIn(_, "MacIsaac")
  )

  private val knownIncorrect: List[String => String] = List(
    "\\b(Al)(\\s+\\w)".r.replaceAllIn(_, m => "al" ++ m.group(2)), // al Arabic or forename Al.
    "\\b(Ap)\\b".r.replaceAllIn(_, "ap"),  // ap Welsh.
    "\\b(Bin)\\b".r.replaceAllIn(_, "bin"), // bin, binti, binte Arabic
    "\\b(Binti)\\b".r.replaceAllIn(_, "binti"),
    "\\b(Binte)\\b".r.replaceAllIn(_, "binte"),
    "\\bDell([ae])\\b".r.replaceAllIn(_, m => "dell" ++ m.group(1)), // della and delle Italian.
    "\\bD([aeiou])\\b".r.replaceAllIn(_, m => "d" ++ m.group(1)), // da, de, di Italian; du French; do Brasil.
    "\\bD([ao]s)\\b".r.replaceAllIn(_, m => "d" ++ m.group(1)), // das, dos Brasileiros.
    "\\bDe([lrn])\\b".r.replaceAllIn(_, m => "de" ++ m.group(1)), // del Italian; der/den Dutch/Flemish
    "\\bL([eo])\\b".r.replaceAllIn(_, m => "l" ++ m.group(1)), // lo Italian; le French
    "\\b(Te)([rn])\\b".r.replaceAllIn(_, m => "te" ++ m.group(2)), // ten, ter Dutch Flemish
    "(\\s+)\\b(Van)(\\s+\\w)".r.replaceAllIn(_, m => m.group(1) ++ "van" ++ m.group(3)), // van German / Forename Van
    "\\b(Von)\\b".r.replaceAllIn(_, "von") // von Dutch / Flemish
  )

  private def updateMac(name: String): String = {
    val initial: String => String = "\\b(Ma?c)([A-Za-z]+)".r.replaceAllIn(_, {(m: Regex.Match) => 
      m.group(1) ++ m.group(2).charAt(0).toString.toUpperCase() ++ m.group(2).substring(1)
    })

    (initial :: irishExceptions).foldLeft(name){ case (name, f) => f(name)}
  }

  private def updateIrish(name: String): String = {
    val mac = "\\bMac[A-Za-z]{2,}[^aciozj]\b".r.unanchored
    val mc = "\\bMc".r.unanchored
    val temp = if (mac.findFirstIn(name).isDefined || mc.findFirstIn(name).isDefined){
      updateMac(name)
    } else name

    irishExtras.foldLeft(temp){ case (name, f) => f(name)}
  }

  private val fixKnownIncorrect: String => String = knownIncorrect.foldLeft(_){ case (n, f)=> f(n) }

  private def updateRoman(name: String): String = {
    "\\b((?:[Xx]{1,3}|[Xx][Ll]|[Ll][Xx]{0,3})?(?:[Ii]{1,3}|[Ii][VvXx]|[Vv][Ii]{0,3})?)\\b".r
      .replaceAllIn(name, {m => 
        val group = m.group(0)
        if (group == "Li") "Li" // I aplogize to all the 51st's out there.
        else group.toUpperCase()
      })
  }

  private val spanishConjunctions = List("Y", "E", "I" )

  private def updateSpanish(name: String): String = {
    spanishConjunctions.map(conjunction => {(s: String) => 
      ("\\b" ++ conjunction ++ "\\b")
        .r.replaceAllIn(s, conjunction.toLowerCase())
        // TODO Figure out how this is different
        // ("([\\\\s,.:;\"'-(]|^)" ++ conjunction ++ "([\\\\s,.:;\"'-(]|$)")
        // .r.replaceAllIn(s, m => m.group(1) ++ conjunction.toLowerCase() ++ m.group(2))
    }).foldLeft(name){ case (name, f) => f(name)}
  }

  private val notSpanish: List[String => String] = List(
    "\\b(El)\\b".r.replaceAllIn(_, "el"), // el Greek or el Spanish
    "\\b(La)\\b".r.replaceAllIn(_, "la"), // la French or la Spanish
  )

  private def updateNotSpanish(name: String): String = {
    notSpanish.foldLeft(name){ case (name, f) => f(name)}
  }

  // (\\s+) means it only updates ben/bat in Middle position, rather than crushing Ben XYZ
  private val hebrew = List[String => String](
    "(\\S\\s+)\\b(Ben)(\\s+\\w)".r.replaceAllIn(_, m => m.group(1) ++ "ben" ++ m.group(3)),
    "(\\S\\s+)\\b(Bat)(\\s+\\w)".r.replaceAllIn(_, m => m.group(1) ++ "bat" ++ m.group(3))
  )

  private def updateHebrew(name: String): String = {
    hebrew.foldLeft(name){ case (name, f) => f(name)}
  }

  private val postNominals = List(
    "ACILEx", "ACSM", "ADC", "AEPC", "AFC", "AFM", "AICSM", "AKC", "AM", "ARBRIBA", "ARCS", "ARRC", "ARSM", "AUH", "AUS",
    "BA", "BArch", "BCh", "BChir", "BCL", "BDS", "BEd", "BEM", "BEng", "BM", "BS", "BSc", "BSW", "BVM&S", "BVScBVetMed",
    "CB", "CBE", "CEng", "CertHE", "CGC", "CGM", "CH", "CIE", "CMarEngCMarSci", "CMarTech", "CMG", "CMILT", "CML", "CPhT", "CPLCTP", "CPM", "CQSW", "CSciTeach", "CSI", "CTL", "CVO",
    "DBE", "DBEnv", "DC", "DCB", "DCM", "DCMG", "DConstMgt", "DCVO", "DD", "DEM", "DFC", "DFM", "DIC", "Dip", "DipHE", "DipLP", "DipSW", "DL", "DLitt", "DLP", "DPhil", "DProf", "DPT", "DREst", "DSC", "DSM", "DSO", "DSocSci",
    "ED", "EdD", "EJLog", "EMLog", "EN", "EngD", "EngTech", "ERD", "ESLog",
    "FADO", "FAWM", "FBDOFCOptom", "FCEM", "FCILEx", "FCILT", "FCSP.", "FdAFdSc", "FdEng", "FFHOM", "FFPM", "FRCAFFPMRCA", "FRCGP", "FRCOG", "FRCP", "FRCPsych", "FRCS", "FRCVS", "FSCR.",
    "GBE", "GC", "GCB", "GCIE", "GCILEx", "GCMG", "GCSI", "GCVO", "GM",
    "HNC", "HNCert", "HND", "HNDip",
    "ICTTech", "IDSM", "IEng", "IMarEng", "IOMCPM", "ISO",
    "J", "JP", "JrLog",
    "KBE", "KC", "KCB", "KCIE", "KCMmG", "KCSI", "KCVO", "KG", "KP", "KT",
    "LFHOM", "LG", "LJ", "LLB", "LLD", "LLM", "Log", "LPE", "LT", "LVO",
    "MA", "MAcc", "MAnth", "MArch", "MarEngTech", "MB", "MBA", "MBChB", "MBE", "MBEIOM", "MBiochem", "MC", "MCEM", "MCGI", "MCh.", "MChem", "MChiro", "MClinRes", "MComp", "MCOptom", "MCSM", "MCSP", "MD", "MEarthSc", "MEng", "MEnt", "MEP", "MFHOM", "MFin", "MFPM", "MGeol", "MILT", "MJur", "MLA", "MLitt", "MM", "MMath", "MMathStat", "MMORSE", "MMus", "MOst", "MP", "MPAMEd", "MPharm", "MPhil", "MPhys", "MRCGP", "MRCOG", "MRCP", "MRCPath", "MRCPCHFRCPCH", "MRCPsych", "MRCS", "MRCVS", "MRes", "MS", "MSc", "MScChiro", "MSci", "MSCR", "MSM", "MSocSc", "MSP", "MSt", "MSW", "MSYP", "MVO",
    "NPQH",
    "OBE", "OBI", "OM", "OND",
    "PgC", "PGCAP", "PGCE", "PgCert", "PGCHE", "PgCLTHE", "PgD", "PGDE", "PgDip", "PhD", "PLog", "PLS",
    "QAM", "QC", "QFSM", "QGM", "QHC", "QHDS", "QHNS", "QHP", "QHS", "QPM", "QS", "QTSCSci",
    "RD", "RFHN", "RGN", "RHV", "RIAI", "RIAS", "RM", "RMN", "RN", "RN1RNA", "RN2", "RN3", "RN4", "RN5", "RN6", "RN7", "RN8", "RN9", "RNC", "RNLD", "RNMH", "ROH", "RRC", "RSAW", "RSci", "RSciTech", "RSCN", "RSN", "RVM", "RVN",
    "SCHM", "SCJ", "SCLD", "SEN", "SGM", "SL", "SPANSPMH", "SPCC", "SPCN", "SPDN", "SPHP", "SPLD", "SrLog", "SRN", "SROT",
    "TD",
    "UD",
    "V100", "V200", "V300", "VC", "VD", "VetMB", "VN", "VRD"
  )
    .map(nominal => (nominal, {(s: String)=> 
      ("\\b" ++ capitalizeFirstLetter(lowercase(nominal)) ++ "\\b")
      .r.replaceAllIn(s, nominal)
    }))

  private def updatePostNominals(name: String, excluded: Set[String]): String = {
    postNominals.foldLeft(name){
      case (name, (nominal, f)) => if (excluded.contains(nominal)) name else f(name)
    }
  }

}