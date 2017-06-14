package sri.mobile.template.games

import upickle.Js

sealed trait PlayingCard

object PlayingCard {
  case object Joker extends PlayingCard
  case object Covered extends PlayingCard

  implicit val playingCardWriter = upickle.default.Writer[PlayingCard]{
    case Joker => Js.Str("joker")
    case Covered => Js.Str("covered")
    case RankedCard(rank, suit) => Js.Str(s"$rank $suit")
  }

  implicit val playingCardReader = upickle.default.Reader[PlayingCard] {
    case Js.Str(value) => value match {
      case "joker" => Joker
      case "covered" => Covered
      case str =>
        val l = str.split(" ")
        val rankByName = CardRank.all.map(rank => rank.toString -> rank).toMap
        val suitByName = CardSuit.all.map(suit => suit.toString -> suit).toMap
        RankedCard(rankByName(l(0)), suitByName(l(1)))

    }
  }

}

sealed case class RankedCard(rank: CardRank, suit: CardSuit) extends PlayingCard

sealed trait CardRank

object CardRank {
  object Ace extends CardRank {
    override def toString: String = "A"}
  case class Number(n: Int) extends  CardRank {
    override def toString: String = n.toString}
  object Jack extends CardRank {
    override def toString: String = "J"}
  object Queen extends CardRank {
    override def toString: String = "Q" }
  object King extends CardRank {
    override def toString: String = "K"}

  val all = Seq(Ace) ++ (2 to 10).map(Number) ++ Seq(Jack, Queen, King)

}

sealed trait CardSuit

object CardSuit {
  object Hearts extends CardSuit {
    override def toString: String = "H"}
  object Diamonds extends CardSuit {
    override def toString: String = "D"}
  object Spades extends CardSuit {
    override def toString: String = "S"}
  object Clubs extends CardSuit {
    override def toString: String = "C"}

  val all = Seq(Hearts, Diamonds, Spades, Clubs)
}
