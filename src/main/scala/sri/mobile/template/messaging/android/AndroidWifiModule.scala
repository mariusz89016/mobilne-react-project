package sri.mobile.template.messaging.android

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("react-native-android-wifi", JSImport.Namespace)
object AndroidWifiModule extends js.Object {
  def getSSID(callback: js.Function1[String, Unit]): Unit = js.native
  def getIP(callback: js.Function1[String, Unit]): Unit = js.native
}