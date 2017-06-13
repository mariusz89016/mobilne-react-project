package sri.mobile.template.components

import sri.core.ElementFactory._
import sri.core.ReactElement
import sri.mobile.template.router.AppRouter.{ClientPage, ServerPage}
import sri.universal.components.{Button, View}
import sri.universal.{ReactEvent, SyntheticEvent, router}
import sri.universal.router.UniversalRouterComponent

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

object StartView {
  @ScalaJSDefined
  class Component extends UniversalRouterComponent[Unit, Unit] {
    override def render(): ReactElement = View()(
      Button(title = "New game", onPress = () => navigateTo(ServerPage))(),
      Button(title = "Join game", onPress = () => navigateTo(ClientPage))()
    )

    def onTextClick(e: ReactEvent[SyntheticEvent]) = {
      navigateToHome()
    }
  }

  js.constructorOf[Component].contextTypes = router.routerContextTypes

  def apply() = makeElement[Component]
}
