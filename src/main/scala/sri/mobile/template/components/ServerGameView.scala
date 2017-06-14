package sri.mobile.template.components

import sri.core.ElementFactory._
import com.github.marklister.base64.Base64._
import sri.core.ReactComponent
import sri.mobile.template.components.ClientGameView.State
import sri.mobile.template.games
import sri.mobile.template.games.PlayingCard.Joker
import sri.mobile.template.games.mau.{FinishedSimpleMauGame, SimpleMauGame, SimpleMauGameInProgress}
import sri.mobile.template.games.{CardRank, CardSuit, PlayingCard, RankedCard}
import sri.mobile.template.images.LikeImage
import sri.mobile.template.messaging.encoding.TextDecoder
import sri.mobile.template.messaging.{Socket, Udp}
import sri.mobile.template.utils.{EasySocket, Player}
import sri.universal.components.{Image, Text, View}

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.typedarray.Uint8Array

object ServerGameView {
  @ScalaJSDefined
  class Component() extends ReactComponent[Props, State] {
    initialState(State(SimpleMauGame.newRandomGame(2)))

    val socket = new EasySocket(onMessageCallback _)


    def generateHandViewJsx(cards: Seq[PlayingCard], serverIp: String): String = {
      s"""<HandView cards={${upickle.default.writeJs(cards)}} serverIp="${serverIp}"></HandView>"""
    }

    def generateTableViewJsx(uncoveredCard: PlayingCard): String = {
      s"""<TableView uncoveredCard={${upickle.default.writeJs(uncoveredCard)}}></TableView>"""
    }

    override def componentDidMount(): Unit = {
      state.game match {
        case SimpleMauGameInProgress(_, _, playerHands, _) =>
          val jsx1 = generateHandViewJsx(playerHands(0), props.serverIp).getBytes.toBase64
          socket.send(props.player1.ip)(
            s"""
               |{
               |  "command": "jsx",
               |  "message": "${jsx1}"
               |}
            """.stripMargin
          )

          val jsx2 = generateHandViewJsx(playerHands(1), props.serverIp).getBytes.toBase64
          socket.send(props.player2.ip)(
            s"""
               |{
               |  "command": "jsx",
               |  "message": "${jsx2}"
               |}
            """.stripMargin
          )

        case _ => {}
      }


    }

    def onMessageCallback(msg: Uint8Array, rinfo: js.Object): Unit = {
      val receivedMsg = new TextDecoder("utf-8").decode(msg)
      val dynamicJSON = JSON.parse(receivedMsg)

      dynamicJSON.command.toString match {
        case "pushCard" =>
          val card = dynamicJSON.card
          js.Dynamic.global.alert(s"pushed card: $card")
      }

//      if(dynamicJSON.command == "jsx") {
//        setState(State(dynamicJSON.message.toString))
//
//      }
    }

    def render() = {
      state.game match {
        case SimpleMauGameInProgress(_, uncoveredStack, _, _) =>
          View()(
            Text()("Game in progress"),
            JsxViewer(jsx = generateTableViewJsx(uncoveredStack.head), games.ComponentsMapper)
          )

        case FinishedSimpleMauGame(winnerNo) =>
          View()(
            Text()(s"The winner is player number ${winnerNo + 1}!")
          )
      }

    }
  }



  case class State(game: SimpleMauGame)
  case class Props(player1: Player, player2: Player, serverIp: String)

  def apply(player1: Player, player2: Player, serverIp: String) = makeElement[Component](Props(player1, player2, serverIp))
}
