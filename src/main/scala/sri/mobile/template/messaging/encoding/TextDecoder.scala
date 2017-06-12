package sri.mobile.template.messaging.encoding

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.typedarray.Uint8Array

@js.native
@JSImport("text-encoding", "TextDecoder")
class TextDecoder(x: String) extends js.Object {
  def decode(msg: Uint8Array): String = js.native
}
