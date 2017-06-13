package sri.mobile.template.components

import sri.core.ElementFactory._
import com.github.marklister.base64.Base64._
import sri.core.ReactComponent
import sri.mobile.template.games
import sri.mobile.template.games.{CardRank, CardSuit, PlayingCard, RankedCard}
import sri.mobile.template.messaging.{Socket, Udp}
import sri.mobile.template.messaging.encoding.TextDecoder
import sri.mobile.template.router.AppRouter.PlayerOwnPage
import sri.universal.components.{Text, View}

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.typedarray.Uint8Array

object ClientGameView {
  @ScalaJSDefined
  class Component() extends ReactComponent[Unit, State] {
    initialState(State(
      s"""
         |<CardView card={${upickle.default.writeJs[PlayingCard](PlayingCard.Joker)}} uncovered={true}>
         |
         |</CardView>
       """.stripMargin))

    def onMessageCallback(msg: Uint8Array, rinfo: js.Object): Unit = {
      val receivedMsg = new TextDecoder("utf-8").decode(msg)
      val dynamicJSON = JSON.parse(receivedMsg)
//      global.alert(CardRank.all.map(rank => rank.toString -> rank).mkString(","))
      global.alert(CardSuit.all.map(rank => rank.toString -> rank).mkString(","))
//      global.alert
      if(dynamicJSON.command == "jsx") {
        val msg = new String(dynamicJSON.message.toString.toByteArray)
        global.alert(msg)
        setState(State(msg))
      }
    }



    val socket: Socket = Udp.createSocket("udp4")
    socket.bind(12345)
    socket.on("message", onMessageCallback _)

    def render() = {
      View()(
        Text()("Client game view"),
        JsxViewer(jsx = state.jsx, games.ComponentsMapper),
        CardView(RankedCard(CardRank.King, CardSuit.Hearts), true)
      )
    }
  }

  case class State(jsx: String)

  def apply() = makeElement[Component]
}
