package sri.mobile.template.parser

sealed trait JsxAst

sealed trait JsxAstNode

sealed trait JsxChild extends JsxAstNode

sealed trait JsxValue extends JsxAstNode

case class JsxTagElement(name: String, attributes: Seq[JsxAttribute], children: Seq[JsxChild]) extends JsxChild

case class JsxAttribute(name: String, value: JsxValue) extends JsxAstNode

case class JsxTextElement(text: String) extends JsxChild

case class JsxTextValue(text: String) extends JsxValue

case class JsxInterpretableValue(interpretableText: String) extends JsxValue