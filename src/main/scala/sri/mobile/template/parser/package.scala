package sri.mobile.template

import upickle.Js
import scala.scalajs.js

package object parser {
  type ComponentName = String
  type ComponentsMapper = Map[ComponentName, ComponentInfo]

  case class ComponentInfo(constructor: js.Any, propsDeserializer: Js.Value => Any )
}
