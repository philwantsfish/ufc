package fish.philwants.crawler

sealed trait Organization
case object UFC extends Organization { override def toString() = "UFC" }
case object Bellator extends Organization { override def toString() = "Bellator" }
case object Rizin extends Organization { override def toString() = "Rizin" }
case object WSOF extends Organization { override def toString() = "WSOF" }
case object Pride extends Organization { override def toString() = "Pride" }
case object Unknown extends Organization { override def toString() = "Unknown" }

object Organization {
  /**
    * Try to create an `Organization` from a `String`
    *
    * @param org A `String` to convert
    * @return
    */
  def mkOrganization(org: String): Organization = {
    val ufc_pattern = "^UFC.*".r
    val wsof_pattern = "^WSOF.*".r
    val rizin_pattern = "^Rizin.*".r
    val bellator_pattern = "^Bellator.*".r
    val pride_pattern = "^Pride.*".r

    org match {
      case ufc_pattern() => UFC
      case wsof_pattern() => WSOF
      case rizin_pattern() => Rizin
      case bellator_pattern() => Bellator
      case pride_pattern() => Pride
      case _ => Unknown
    }
  }
}
