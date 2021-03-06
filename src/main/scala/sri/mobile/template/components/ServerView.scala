package sri.mobile.template.components

import sri.core.ElementFactory.makeElement
import com.github.marklister.base64.Base64._
import sri.core.{ReactComponent, ReactElement, ReactElementU}
import sri.mobile.template.components.ServerView.State
import sri.mobile.template.messaging.{Socket, Udp}
import sri.mobile.template.messaging.android.AndroidWifiModule
import sri.mobile.template.messaging.encoding.TextDecoder
import sri.mobile.template.router.AppRouter.PlayersSharedPage
import sri.mobile.template.utils.Player
import sri.universal.{ReactEvent, TextInputEvent, router}
import sri.universal.components._
import sri.universal.router.UniversalRouterComponent

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.{JSON, annotation}
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.typedarray.Uint8Array

object ServerView {
  case class State(myIp: String, ssid: String, player1: Player, player2: Player, pl1Joined: Boolean, pl2Joined: Boolean)

  @ScalaJSDefined
  class Component extends UniversalRouterComponent[Unit, State] {
    initialState(State("myIp", "ssid", Player("player1", "pl1Ip", "<none>"), Player("player2", "pl2Ip", "<none>"), false, false))

    val socket: Socket = Udp.createSocket("udp4")

    def onMessageCallback(msg: Uint8Array, rinfo: js.Object): Unit = {
      val receivedMsg = new TextDecoder("utf-8").decode(msg)
      val dynamicJSON = JSON.parse(receivedMsg)

      dynamicJSON.command.toString match {
        case "register" =>
          if(state.pl1Joined == false) {
           /* val msg = s"""
                         |{
                         |  "command": "registered",
                         |  "id": "player1"
                         |}
                         |""".stripMargin.getBytes.toBase64
            socket.send(msg, 0, msg.length, 12345, dynamicJSON.ip.toString)*/
            setState(State(state.myIp, state.ssid, Player("player1", dynamicJSON.ip.toString, "<none>"), state.player2, true, false))
          } else if(state.pl2Joined == false) {
            /*val msg = s"""
                         |{
                         |  "command": "registered",
                         |  "id": "player2"
                         |}
                         |""".stripMargin.getBytes.toBase64
            socket.send(msg, 0, msg.length, 12345, dynamicJSON.ip.toString)*/
            setState(State(state.myIp, state.ssid, state.player1, Player("player2", dynamicJSON.ip.toString, "<none>"), state.pl1Joined, true))

            val msg21 = s"""
                          |{
                          |  "command": "start",
                          |  "id": 0,
                          |  "serverIp": "${state.myIp}"
                          |}
                          |""".stripMargin.getBytes.toBase64
            val msg22 = s"""
                           |{
                           |  "command": "start",
                           |  "id": 1,
                           |  "serverIp": "${state.myIp}"
                           |}
                           |""".stripMargin.getBytes.toBase64
            socket.send(msg21, 0, msg21.length, 12345, state.player1.ip)
            socket.send(msg22, 0, msg21.length, 12345, state.player2.ip)
            navigateTo(PlayersSharedPage, (state.player1, state.player2, state.myIp), "table view")
          }

        case "message" =>
          if(dynamicJSON.id == "player1") {
            setState(state.copy(player1 = state.player1.copy(lastMessage = dynamicJSON.message.toString)))
          }
          else if(dynamicJSON.id == "player2") {
            setState(state.copy(player2 = state.player2.copy(lastMessage = dynamicJSON.message.toString)))
          }

        case x => global.alert(s"Unknown message: ${x}")
      }
    }
    override def componentDidMount(): Unit = {
      socket.bind(12345)

      AndroidWifiModule.getIP((myIp: String) => setState(state.copy(myIp = myIp)): Unit)
      AndroidWifiModule.getSSID((ssid: String) => setState(state.copy(ssid = ssid)): Unit)

      socket.on("message", onMessageCallback _)
    }

    def render(): ReactElement = {
      View()(
        Text()(s"IP: ${state.myIp}"),
        Text()(s"SSID: ${state.ssid}"),
        Text()("\n"),
        Text()("\n"),
        Text()(s"Player 1 IP: ${state.player1.ip}"),
        Text()(s"Last message: ${state.player1.lastMessage}"),
        Text()("\n"),
        Text()(s"Player 2 IP: ${state.player2.ip}"),
        Text()(s"Last message: ${state.player2.lastMessage}"),
        Text()("\n")
//        Button(title="Start game!", disabled = !(state.pl1Joined && state.pl2Joined), onPress = ()
      )
    }
  }
  js.constructorOf[Component].contextTypes = router.routerContextTypes

  def apply() = makeElement[Component]
}

