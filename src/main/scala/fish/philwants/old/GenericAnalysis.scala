package fish.philwants.old


trait GenericAnalysis {
  def isCandidate(fight: Fight): Boolean
  def isCorrect(fight: Fight): Boolean

  def percent: Double = {
    val fights = Data.fights.filter { f => isCandidate(f) }
    val correct = fights.map { f => isCorrect(f) }
    (correct.count(r => r).toDouble / correct.size.toDouble) * 100
  }

  def percentString: String = {
    val fights = Data.fights.filter { f => isCandidate(f) }
    val results = fights.map { f => isCorrect(f) }
    val correct = results.count(r => r)
    val percent = (correct.toDouble / results.size.toDouble) * 100
    s"Percent: $percent, $correct/${fights.size}"
  }


  def percentForLast1000: Double = {
    val f = Data.fights.takeRight(1000)
    val fights = f.filter { f => isCandidate(f) }
    val correct = fights.map { f => isCorrect(f) }
    (correct.count(r => r).toDouble / correct.size.toDouble) * 100
  }

  def percentSringForLast1000: String = {
    val fights = Data.fights.takeRight(1000).filter { f => isCandidate(f) }
    val results = fights.map { f => isCorrect(f) }
    val correct = results.count(r => r)
    val percent = (correct.toDouble / results.size.toDouble) * 100
    s"$percent, $correct/${fights.size}"
  }
  def percentSringForLast1000Simple: String = {
    val fights = Data.fights.takeRight(1000).filter { f => isCandidate(f) }
    val results = fights.map { f => isCorrect(f) }
    val correct = results.count(r => r)
    val percent = (correct.toDouble / results.size.toDouble) * 100
    s"$percent"
  }
}

