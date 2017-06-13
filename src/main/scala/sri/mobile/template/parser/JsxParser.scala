package sri.mobile.template.parser

import scala.util.parsing.combinator.RegexParsers

class JsxParser extends RegexParsers {
  lazy val primaryExpression = element

  lazy val element = selfClosingElement | properlyClosedElement

  lazy val properlyClosedElement = openingElement ~ rep(child) ~ closingElement >> {
    case (openingName ~ attributes) ~ children ~ closingName =>
      if(openingName == closingName) {
        success(JsxTagElement(openingName, attributes, children))
      } else {
        failure(s"tag '$openingName' not closed properly")
      }
  }

  lazy val selfClosingElement = "<" ~> elementName ~ rep(attribute) <~ "/" <~ ">" ^^ {
    case name ~ attributes => JsxTagElement(name, attributes, children = Seq.empty)
  }

  lazy val openingElement = "<" ~> elementName ~ rep(attribute) <~ ">"

  lazy val closingElement = "<" ~> "/" ~> elementName <~ ">"

  lazy val elementName = identifier

  lazy val identifier = """[a-zA-Z_][a-zA-Z_0-9]*""".r

  lazy val attribute = attributeName ~ "=" ~ attributeValue ^^ { case name ~ _ ~ value => JsxAttribute(name, value) }

  lazy val attributeName = identifier

  lazy val attributeValue: Parser[JsxValue] = (
    doubleQuotedStringValue
      | singleQuotedStringValue
      | bracedInterpretableValue
    )

  lazy val doubleQuotedStringValue = "\"" ~> """[^"]*""".r <~ "\"" ^^ { str => JsxTextValue(str)}

  lazy val singleQuotedStringValue =  "\'" ~> """[^']*""".r <~ "\'" ^^ { str => JsxTextValue(str) }

  lazy val bracedInterpretableValue = "{" ~> balancedBracesText <~ "}" ^^ { text => JsxInterpretableValue(text) }

  lazy val balancedBracesText: Parser[String] = {
    rep(
      """[^{}]+""".r
        | ("{" ~> balancedBracesText <~ "}" ^^ { str => s"{$str}" } )
    ) ^^ { _.mkString("")}
  }


  lazy val child: Parser[JsxChild] = text | element

  lazy val text = rep1(textCharacter) ^^ { chars => JsxTextElement(chars.mkString("")) }

  lazy val textCharacter: Parser[String] = """[^{<>}]""".r
}
