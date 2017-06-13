package sri.mobile.template.components

import sri.core.ElementFactory._
import com.github.marklister.base64.Base64._
import sri.core.ReactComponent
import sri.mobile.template.components.ClientGameView.State
import sri.mobile.template.games
import sri.mobile.template.games.PlayingCard.Joker
import sri.mobile.template.games.mau.SimpleMauGame
import sri.mobile.template.games.{CardRank, CardSuit, PlayingCard, RankedCard}
import sri.mobile.template.images.LikeImage
import sri.mobile.template.messaging.encoding.TextDecoder
import sri.mobile.template.messaging.{Socket, Udp}
import sri.mobile.template.utils.Player
import sri.universal.components.{Image, Text, View}

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.typedarray.Uint8Array

object ServerGameView {
  @ScalaJSDefined
  class Component() extends ReactComponent[Props, State] {
    initialState(State(SimpleMauGame.newRandomGame()))

    val socket: Socket = Udp.createSocket("udp4")
    socket.bind(12345)
    socket.on("message", onMessageCallback _)


    def generateJsx(cards: Seq[PlayingCard]): String = {
      s"""<HandComponent cards={${cards}}></HandComponent>"""
//      cards.map{ card => s"""
//                         |<CardView card={${upickle.default.writeJs(card)}} uncovered={true}>
//                         |</CardView>
//       """.stripMargin}.mkString("<ViewComp>", "", "</ViewComp>")
    }

    override def componentDidMount(): Unit = {
      val jsx1 = generateJsx(state.game.player1Hand).getBytes.toBase64
      val msg1 =
        s"""
           |{
           |  "command": "jsx",
           |  "message": "${jsx1}"
           |}
         """.stripMargin.getBytes.toBase64

      socket.send(msg1, 0, msg1.length, 12345, props.player1.ip)

      val jsx2 = generateJsx(state.game.player2Hand).getBytes.toBase64
      val msg2 =
        s"""
           |{
           |  "command": "jsx",
           |  "message": "${jsx2}"
           |}
         """.stripMargin.getBytes.toBase64

      socket.send(msg2, 0, msg2.length, 12345, props.player2.ip)


    }

    def onMessageCallback(msg: Uint8Array, rinfo: js.Object): Unit = {
      val receivedMsg = new TextDecoder("utf-8").decode(msg)
      val dynamicJSON = JSON.parse(receivedMsg)
//      if(dynamicJSON.command == "jsx") {
//        setState(State(dynamicJSON.message.toString))
//
//      }
    }

    def render() = {
      View()(
        Text()("Server game view"),
        JsxViewer(jsx = getServerStateJsx(), games.ComponentsMapper)
//        CardView(RankedCard(CardRank.King, CardSuit.Hearts), uncovered = true)
      )
    }

    def getServerStateJsx() = {
      generateJsx(state.game.uncoveredStack.head :: Nil)
    }


  }



  case class State(game: SimpleMauGame)
  case class Props(player1: Player, player2: Player)

  def apply(player1: Player, player2: Player) = makeElement[Component](Props(player1, player2))
}
