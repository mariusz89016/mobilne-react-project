package sri.mobile.template

import sri.mobile.template.components.{CardView, HandView, TableView, ViewComp}
import sri.mobile.template.parser.ComponentsMapping
import sri.universal.components.{Text, View}

package object games {
  val ComponentsMapper  = ComponentsMapping(CardView, ViewComp, HandView, TableView)
}
