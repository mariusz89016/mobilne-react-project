package sri.mobile.template.components

import sri.core.ElementFactory.makeElement
import sri.core.{ReactComponent, ReactElement}
import sri.mobile.template.games.PlayingCard
import sri.universal.components._

import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.annotation.ScalaJSDefined

object TableView{
  @ScalaJSDefined
  class Component extends ReactComponent[Props, Unit] {
    override def render(): ReactElement = {
      TouchableOpacity(/*onPress = () => global.alert(props.uncoveredCard.toString)*/)(
        CardView(props.uncoveredCard, true)
      )
    }
  }

  case class Props(uncoveredCard: PlayingCard)

  def apply(uncoveredCard: PlayingCard) = makeElement[Component](Props(uncoveredCard))
}
