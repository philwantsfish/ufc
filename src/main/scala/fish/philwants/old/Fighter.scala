package fish.philwants.old

import scala.util.Try

/**
  * Created by okeefephil on 2017-01-02.
  */
case class Fighter(url: String, id: String, name: String, nick: String, birth: String, heightString: String, weightString: String, association: String, locality: String, country: String) {
  val height = Try(Integer.parseInt(heightString)).toOption
  val weight = Try(Integer.parseInt(weightString)).toOption
  val birthYear: Option[Int] = {
    val year = birth.split("/").last
    Try(Integer.parseInt(year)).toOption
  }
}
