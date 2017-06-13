package sri.mobile.template.components

import sri.core.ElementFactory._
import sri.core.ReactComponent
import sri.mobile.template.games.{CardRank, CardSuit, RankedCard}
import sri.universal.components.{Text, View}

import scala.scalajs.js.annotation.ScalaJSDefined

object ClientGameView {
  @ScalaJSDefined
  class Component() extends ReactComponent[Unit, State] {
    def render() = {
      View()(
        Text()("Server game view"),
        //JsxViewer(jsx = state.jsx, games.ComponentsMapper),
        CardView(RankedCard(CardRank.King, CardSuit.Hearts), true)
      )
    }
  }

  case class State(jsx: String)

  def apply() = makeElement[Component]
}
