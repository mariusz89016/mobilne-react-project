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
import sri.universal.styles.UniversalStyleSheet
import sri.universal.{ReactEvent, TextInputEvent}

import scala.scalajs.js.JSON


object RegistrationView {
  case class State(serverIp: String, myIp: String, ssid: String)

  @ScalaJSDefined
  class Component extends ReactComponent[Unit, RegistrationView.State] {
    initialState(State("", "", ""))

    AndroidWifiModule.getIP((myIp: String) => setState(state.copy(myIp = if (myIp == "0.0.0.0") "192.168.43.1" else myIp)): Unit)
    AndroidWifiModule.getSSID((ssid: String) => setState(state.copy(ssid = ssid)): Unit)

    def onMessageCallback(msg: Uint8Array, rinfo: js.Object): Unit = {
      val receivedMsg = new TextDecoder("utf-8").decode(msg)
      val dynamicJSON = JSON.parse(receivedMsg)
      if(dynamicJSON.command == "registered") {
        global.alert("Registered!")
        //todo navigate to game screen
      }
    }
    val socket: Socket = Udp.createSocket("udp4")
    socket.bind(12345)
    socket.on("message", onMessageCallback _)


    def render(): ReactElement = {
      View()(
        TextInput(
          keyboardType = KeyboardType.NUMBER_PAD,
          placeholder = "Insert server IP...",
          onChange = (reactEvent: ReactEvent[TextInputEvent]) => setState(state.copy(serverIp = reactEvent.nativeEvent.text)): Unit)(),
        Text()(s"IP: ${state.myIp}"),
        Text()(s"SSID: ${state.ssid}"),
        Button(title = "Register", onPress = () => {
          val msg = s"""
                       |{
                       |  "command": "register",
                       |  "ip": "${state.myIp}"
                       |}
                       |""".stripMargin.getBytes.toBase64
          socket.send(msg, 0, msg.length, 12345, state.serverIp)
        })()
      )
    }
  }

  def apply() = makeElement[Component]
}
