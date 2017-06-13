package sri.mobile.template.games

sealed trait PlayingCard {
  def name: String
}

object PlayingCard {
  case object Joker extends PlayingCard {
    def name = "black_joker"
  }


}

case class RankedCard(rank: CardRank, suit: CardSuit) extends PlayingCard {
  def name = s"${rank.name}_of_${suit.name}"
}

sealed abstract class CardRank(val name: String)

object CardRank {
  object Ace extends CardRank("ace")
  case class Number(n: Int) extends CardRank(n.toString)
  object Jack extends CardRank("jack")
  object Queen extends CardRank("queen")
  object King extends CardRank("king")
}

sealed abstract class CardSuit(val name: String)

object CardSuit {
  object Hearts extends CardSuit("hearts")
  object Diamonds extends CardSuit("diamonds")
  object Spades extends CardSuit("spades")
  object Clubs extends CardSuit("clubs")
}
