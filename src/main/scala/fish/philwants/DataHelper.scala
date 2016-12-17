package fish.philwants

object DataHelper {
  implicit def FightImplicit(fight: Fight): FightExtension = new FightExtension(fight)
}

class FightExtension(fight: Fight) {
  def f1(): Option[Fighter] = Data.fighters.find { f => f.id == fight.f1fid }
  def f2(): Option[Fighter] = Data.fighters.find { f => f.id == fight.f2fid }
}

