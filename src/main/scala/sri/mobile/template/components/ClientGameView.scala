package sri.mobile.template.components

import sri.core.ElementFactory._
import com.github.marklister.base64.Base64._
import sri.core.ReactComponent
import sri.mobile.template.games
import sri.mobile.template.games.{CardRank, CardSuit, PlayingCard, RankedCard}
import sri.mobile.template.messaging.{Socket, Udp}
import sri.mobile.template.messaging.encoding.TextDecoder
import sri.mobile.template.router.AppRouter.PlayerOwnPage
import sri.mobile.template.utils.EasySocket
import sri.universal.components.{Text, View}

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.typedarray.Uint8Array

object ClientGameView {
  @ScalaJSDefined
  class Component() extends ReactComponent[Props, State] {
    initialState(State(
      s"""
         |<HandView cards={[]} serverIp="0.0.0.0">
         |
         |</HandView>
       """.stripMargin))

    def onMessageCallback(msg: Uint8Array, rinfo: js.Object): Unit = {
      val receivedMsg = new TextDecoder("utf-8").decode(msg)
      val dynamicJSON = JSON.parse(receivedMsg)
      if(dynamicJSON.command == "jsx") {
        val msg = new String(dynamicJSON.message.toString.toByteArray)
        setState(State(msg))
      }
    }

    val socket = new EasySocket(onMessageCallback _)

    def sendCardToServer(stringifiedCard: String) = {

      socket.send(props.serverIp)(
        s"""
           |{
           |  "command": "pushCard",
           |  "card": ${stringifiedCard}
           |}
         """.stripMargin
      )

      global.alert(s"sending card: ${stringifiedCard}")
    }

    global.sendToServer = sendCardToServer _

    def render() = {
      View()(
        Text()("Cards in your hand:"),
        JsxViewer(jsx = state.jsx, games.ComponentsMapper)
      )
    }
  }

  case class State(jsx: String)
  case class Props(serverIp: String)

  def apply(serverIp: String) = makeElement[Component](Props(serverIp))
}
