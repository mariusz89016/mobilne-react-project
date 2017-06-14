package sri.mobile.template.components

import sri.core.ElementFactory.makeElement
import sri.core.{ReactComponent, ReactElement}
import sri.mobile.template.components.ClientGameView.State
import sri.mobile.template.games.PlayingCard
import sri.mobile.template.messaging.encoding.TextDecoder
import sri.mobile.template.utils.EasySocket
import sri.universal.components._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.typedarray.Uint8Array

object HandView{
  @ScalaJSDefined
  class Component extends ReactComponent[Props, Unit] {

    def onMessageCallback(msg: Uint8Array, rinfo: js.Object): Unit = {

    }

    val socket = new EasySocket(onMessageCallback _)

    def sendCardToServer(card: PlayingCard) = {
      socket.send(props.serverIp)(
        s"""
           |{
           |  "command": "pushCard",
           |  "card": ${upickle.default.writeJs(card)}
           |}
         """.stripMargin
      )
    }

    override def render(): ReactElement = {
      ScrollView()(props.cards.zipWithIndex.map{ case (card, idx) =>
        TouchableOpacity(key = idx.toString, onPress = () => {
          sendCardToServer(card)
        })(
          CardView(card, true)
        )
      })
    }
  }

  case class Props(cards: Seq[PlayingCard], serverIp: String)

  def apply(cards: Seq[PlayingCard], serverIp: String) = makeElement[Component](Props(cards, serverIp))
}