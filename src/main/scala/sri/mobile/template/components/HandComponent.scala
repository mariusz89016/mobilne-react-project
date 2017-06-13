package sri.mobile.template.components

import sri.core
import sri.core.ElementFactory.makeElement
import sri.core.{ReactComponent, ReactElement}
import sri.mobile.template.games.PlayingCard
import sri.universal.components._
import scala.scalajs.js.Dynamic.global

import scala.scalajs.js.annotation.ScalaJSDefined

object HandComponent{
  @ScalaJSDefined
  class Component extends ReactComponent[Props, Unit] {
    override def render(): ReactElement = {
      ScrollView()(props.cards.map{card =>
        TouchableHighlight(onPress = () => global.alert(card.toString))(CardView(card, true))
      })
    }
  }

  case class Props(cards: Seq[PlayingCard])

  def apply(cards: Seq[PlayingCard]) = makeElement[Component](Props(cards))
}