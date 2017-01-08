package fish.philwants.old

/**
  * Having 1-4 fights more than your opponent is ~55% more likely to win
  * Having 15+ more fights thatn your opponet is ~41 more likely to lose
  *
  * But taking only last 1000 fights, this trend changes
  * Having 1-4 fights more than your opponent is ~54-55% more likely to win
  * Having 16+ more fights thatn your opponet is ~61 more likely to WIN
  *
  * @param countDiffMin
  */
case class FightCountAnalysis(countDiffMin: Int = 1) extends GenericAnalysis {
  def f1FightCount(fight: Fight): Int = {
    val fights = Data.fights.filter { f => f.date.before(fight.date) }
    fights.count { f => f.f1name.equals(fight.f1name) || f.f2name.equals(fight.f1name) }
  }

  def f2FightCount(fight: Fight): Int = {
    val fights = Data.fights.filter { f => f.date.before(fight.date) }
    fights.count { f => f.f1name.equals(fight.f2name) || f.f2name.equals(fight.f2name) }
  }

  def isCandidate(fight: Fight): Boolean = {
    Math.abs(f1FightCount(fight) - f2FightCount(fight)) >= countDiffMin
  }

  def isCorrect(fight: Fight): Boolean = {
    if (f1FightCount(fight) > f2FightCount(fight)) fight.f1result.equals("win")
    else fight.f2result.equals("win")
  }
}
