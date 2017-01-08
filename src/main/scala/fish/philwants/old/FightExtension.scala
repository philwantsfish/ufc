package fish.philwants.old

/**
  * Created by okeefephil on 2017-01-02.
  */
class FightExtension(fight: Fight) {
  def f1: Option[Fighter] = Data.fighters.find { f => f.id == fight.f1fid }
  def f2: Option[Fighter] = Data.fighters.find { f => f.id == fight.f2fid }
}
