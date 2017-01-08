package fish.philwants.old

/**
  * Created by okeefephil on 2017-01-02.
  */
object DataHelper {
  implicit def FightImplicit(fight: Fight): FightExtension = new FightExtension(fight)
}
