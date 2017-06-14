package sri.mobile.template.components

import sri.core.ElementFactory.makeElement
import sri.core.{ReactComponent, ReactElement, ReactElementU}
import sri.mobile.ReactNative
import sri.mobile.template.messaging.{Socket, Udp}
import sri.mobile.template.messaging.android.AndroidWifiModule
import sri.mobile.template.messaging.encoding.TextDecoder
import sri.universal.components._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.typedarray.Uint8Array
import com.github.marklister.base64.Base64._
import sri.mobile.template.components.RegistrationView.State
import sri.mobile.template.router.AppRouter.PlayerOwnPage
import sri.mobile.template.utils.{EasySocket, Routing}
import sri.universal.router.UniversalRouterComponent
import sri.universal.styles.UniversalStyleSheet
import sri.universal.{ReactEvent, TextInputEvent, router}

import scala.scalajs.js.JSON


object RegistrationView {
  case class State(serverIp: String, myIp: String, ssid: String)

  @ScalaJSDefined
  class Component extends UniversalRouterComponent[Unit, RegistrationView.State] {
    initialState(State("192.168.1.6", "", ""))

    AndroidWifiModule.getIP((myIp: String) => setState(state.copy(myIp = if (myIp == "0.0.0.0") "192.168.43.1" else myIp)): Unit)
    AndroidWifiModule.getSSID((ssid: String) => setState(state.copy(ssid = ssid)): Unit)

    def onMessageCallback(msg: Uint8Array, rinfo: js.Object): Unit = {
      val receivedMsg = new TextDecoder("utf-8").decode(msg)
      val dynamicJSON = JSON.parse(receivedMsg)

      dynamicJSON.command.toString match {
        case "start" =>
          Routing.replace(getRouterCtrl, PlayerOwnPage, dynamicJSON.serverIp.toString, "Player's view")
        //case "registered" => {}
        case _ => {global.alert(s"unknown message: ${JSON.stringify(dynamicJSON)}")}
      }
    }

    val socket = new EasySocket(onMessageCallback _)

    def render(): ReactElement = {
      View()(
        TextInput(
          keyboardType = KeyboardType.NUMBER_PAD,
          defaultValue = state.serverIp,
          placeholder = "Insert server IP...",
          onChange = (reactEvent: ReactEvent[TextInputEvent]) => setState(state.copy(serverIp = reactEvent.nativeEvent.text)): Unit)(),
        Text()(s"IP: ${state.myIp}"),
        Text()(s"SSID: ${state.ssid}"),
        Button(title = "Register", onPress = () => {
          socket.send(state.serverIp)(
            s"""
               |{
               |  "command": "register",
               |  "ip": "${state.myIp}"
               |}
               |""".stripMargin
          )
        })()
      )
    }
  }
  js.constructorOf[Component].contextTypes = router.routerContextTypes

  def apply() = makeElement[Component]
}
