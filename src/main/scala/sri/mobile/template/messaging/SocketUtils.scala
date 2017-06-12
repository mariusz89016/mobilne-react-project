package sri.mobile.template.messaging

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.typedarray.Uint8Array

@js.native
@JSImport("react-native-udp", JSImport.Namespace)
object Udp extends js.Object {
  def createSocket(options: String): Socket = js.native
}

@js.native
@JSImport("UdpSocket", "Socket")
class Socket extends js.Object {
  def bind(port: Int): Unit = js.native
  def send(buf: String, offset: Int, length: Int, port: Int, address: String, callback: js.Function0[Unit] = () => {}): Unit = js.native
  def on(what: String, callback: js.Function2[Uint8Array, js.Object, Unit]): Unit = js.native
}
