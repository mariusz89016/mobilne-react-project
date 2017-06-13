package sri.mobile.template.components

import sri.core.ElementFactory._
import sri.core.ReactComponent
import sri.mobile.template.games.PlayingCard.Joker
import sri.mobile.template.games.{CardRank, CardSuit, PlayingCard, RankedCard}
import sri.mobile.template.images
import sri.universal.components.{Image, Text, View}
import sri.universal.styles.UniversalStyleSheet

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

object CardView {

  @ScalaJSDefined
  class Component extends ReactComponent[Props, Unit] {

    def render() = {
      val imageSource = if(props.uncovered){
        props.card match {
          case Joker => images.cards.Joker
          case RankedCard(rank, suit) =>

            val imagesBySuit = suit match {
              case CardSuit.Hearts => images.cards.hearts
              case CardSuit.Diamonds => images.cards.diamonds
              case CardSuit.Spades => images.cards.spades
              case CardSuit.Clubs => images.cards.clubs
            }

            rank match {
              case CardRank.Number(2) => imagesBySuit.Number2
              case CardRank.Number(3) => imagesBySuit.Number3
              case CardRank.Number(4) => imagesBySuit.Number4
              case CardRank.Number(5) => imagesBySuit.Number5
              case CardRank.Number(6) => imagesBySuit.Number6
              case CardRank.Number(7) => imagesBySuit.Number7
              case CardRank.Number(8) => imagesBySuit.Number8
              case CardRank.Number(9) => imagesBySuit.Number9
              case CardRank.Number(10) => imagesBySuit.Number10
              case CardRank.Jack => imagesBySuit.Jack
              case CardRank.Queen => imagesBySuit.Queen
              case CardRank.King => imagesBySuit.King
              case CardRank.Ace => imagesBySuit.Ace
              case _ => imagesBySuit.Ace
            }
        }
      } else {
        images.cards.Covered
      }


      View()(
        Image(sourceDynamic = imageSource, style = styles.default)()
      )
    }
  }

  case class Props(card: PlayingCard, uncovered: Boolean)

  def apply(card: PlayingCard, uncovered: Boolean) = makeElement[Component](Props(card, uncovered))

  object styles extends UniversalStyleSheet {
    val default = style(
      width := 100,
      height := 145
    )
  }
}
