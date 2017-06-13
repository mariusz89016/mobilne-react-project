package sri.mobile.template.components

import sri.core.ElementFactory._
import sri.core.{JSProps, React, ReactComponent, ReactElement, ReactNode}
import sri.mobile.template.parser._

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

object JsxViewer {

  @ScalaJSDefined
  class Component extends ReactComponent[Properties, Unit] {
    override def render(): ReactElement = {
      showJsx(props.jsx, props.mapper).getOrElse(null)
    }
  }

  case class Properties(jsx: String, mapper: ComponentsMapper)

  def apply(
             jsx: String,
             mapper: ComponentsMapper
           ) = makeElement[Component](Properties(jsx, mapper))

  val parser = new JsxParser

  def showJsx(jsx: String, mapper: ComponentsMapper): Option[ReactElement] = {
    val parsed = parser.parseAll(parser.primaryExpression, jsx)

    parsed match {
      case parser.Success(rootElement: JsxTagElement, _) => {
        Some(toReactElement(rootElement, mapper))
      }
      case parser.NoSuccess(msg, _) => {
        println("Couldn't parse jsx")
        println(msg)

        None
      }
    }
  }

  def toReactElement(jsxElement: JsxTagElement, mapper: ComponentsMapper): ReactElement = {

    val attrs = jsxElement.attributes.map{ attr =>
      val value = attr.value match {
        case JsxTextValue(text) => text
        case JsxInterpretableValue(interpretableText) => {

          js.Dynamic.global.sayHello = () => println("hello")

          js.eval(s"($interpretableText)")
        }
      }
      (attr.name, value)
    }

    val attributes = js.Dictionary(attrs: _*)

    val children: Seq[ReactNode] = jsxElement.children.map{
      case element: JsxTagElement => toReactElement(element, mapper): ReactNode
      case JsxTextElement(text) => text: ReactNode
    }

    mapper.get(jsxElement.name).map{
      case ComponentInfo(constructor, propsDeserializer) =>

        val sprops= propsDeserializer(upickle.json.readJs(attributes))

        val props = JSProps[Any](sprops = sprops)

        React.createElement(constructor, props, children: _*)
    }.getOrElse{
      React.createElement(jsxElement.name, attributes, children: _*)
    }
  }
}
