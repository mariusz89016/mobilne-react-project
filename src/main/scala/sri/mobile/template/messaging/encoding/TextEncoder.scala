package sri.mobile.template.messaging.encoding

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.typedarray.Uint8Array

@js.native
@JSImport("text-encoding", "TextEncoder")
class TextEncoder(x: String) extends js.Object {
  def encode(msg: String): Uint8Array = js.native
}
