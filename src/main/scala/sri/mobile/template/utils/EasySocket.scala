package sri.mobile.template.utils

import sri.mobile.template.messaging.{Socket, Udp}
import com.github.marklister.base64.Base64._

import scala.scalajs.js
import scala.scalajs.js.typedarray.Uint8Array

class EasySocket(onMessageCallback: (Uint8Array, js.Object) => Unit, port: Int = 12345) {
  private val socket = Udp.createSocket("udp4")
  socket.bind(port)
  socket.on("message", onMessageCallback)

  def send(ip: String)(msg: String) = {
    val encodedMessage = msg.getBytes.toBase64
    socket.send(encodedMessage, 0, encodedMessage.length, 12345, ip)
  }
}
