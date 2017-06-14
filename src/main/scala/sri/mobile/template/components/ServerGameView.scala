package sri.mobile.template.components

import sri.core.ElementFactory._
import com.github.marklister.base64.Base64._
import sri.core.ReactComponent
import sri.mobile.template.components.ClientGameView.State
import sri.mobile.template.games
import sri.mobile.template.games.PlayingCard.Joker
import sri.mobile.template.games.mau.{FinishedSimpleMauGame, PushCard, SimpleMauGame, SimpleMauGameInProgress}
import sri.mobile.template.games.{CardRank, CardSuit, PlayingCard, RankedCard}
import sri.mobile.template.images.LikeImage
import sri.mobile.template.messaging.encoding.TextDecoder
import sri.mobile.template.messaging.{Socket, Udp}
import sri.mobile.template.utils.{EasySocket, Player}
import sri.universal.components.{Image, Text, View}
import upickle.Js

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.typedarray.Uint8Array

object ServerGameView {
  @ScalaJSDefined
  class Component() extends ReactComponent[Props, State] {
    initialState(State(SimpleMauGame.newRandomGame(2)))

    val socket = new EasySocket(onMessageCallback _, 12346)
    val socketH = new EasySocket(onMessageCallback _, 12347)


    def generateHandViewJsx(cards: Seq[PlayingCard], serverIp: String, playerNo: Int): String = {
      s"""<HandView cards={${upickle.default.writeJs(cards)}} serverIp="$serverIp" id={$playerNo}></HandView>"""
    }

    def generateTableViewJsx(uncoveredCard: PlayingCard): String = {
      s"""<TableView uncoveredCard={${upickle.default.writeJs(uncoveredCard)}}></TableView>"""
    }

    override def componentDidMount(): Unit = {
      state.game match {
        case SimpleMauGameInProgress(_, _, playerHands, _) =>
          val jsx1 = generateHandViewJsx(playerHands(0), props.serverIp, 0).getBytes.toBase64
          socket.send(props.player1.ip)(
            s"""
               |{
               |  "command": "jsx",
               |  "message": "${jsx1}"
               |}
            """.stripMargin
          )

          val jsx2 = generateHandViewJsx(playerHands(1), props.serverIp, 1).getBytes.toBase64
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
//      global.alert(receivedMsg)

      dynamicJSON.command.toString match {
        case "pushCard" =>
          val card = dynamicJSON.card
          state.game match {
            case g@SimpleMauGameInProgress(_, _, _, _) => {
              val receivedCard = upickle.default.readJs[PlayingCard](Js.Str(card.toString))
              val outputGame = g.acceptMove(PushCard(receivedCard), dynamicJSON.id.toString.toInt)
              outputGame match {
                case Some(a@SimpleMauGameInProgress(_, _, _, _)) =>
                  val jsx1 = generateHandViewJsx(a.playerHands(0), props.serverIp, 0).getBytes.toBase64

                  socket.send(props.player1.ip)(
                    s"""
                       |{
                       |  "command": "jsx",
                       |  "message": "${jsx1}"
                       |}
            """.stripMargin
                  )
                  socketH.send(props.player1.ip)(
                    s"""
                       |{
                       |  "command": "jsx",
                       |  "message": "${jsx1}"
                       |}
            """.stripMargin
                  )


                  val jsx2 = generateHandViewJsx(a.playerHands(1), props.serverIp, 1).getBytes.toBase64
                  socket.send(props.player2.ip)(
                    s"""
                       |{
                       |  "command": "jsx",
                       |  "message": "${jsx2}"
                       |}
              """.stripMargin
                  )
                  socket.send(props.player2.ip)(
                    s"""
                       |{
                       |  "command": "jsx",
                       |  "message": "${jsx2}"
                       |}
              """.stripMargin
                  )

                  //              setState(state.copy(game = outputGame.getOrElse(state.game)))
                  setState(state.copy(game = a))
                case None =>
                  setState(state.copy(game = FinishedSimpleMauGame((dynamicJSON.id.toString.toInt+1)%2)))
              }
            }
          }
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
