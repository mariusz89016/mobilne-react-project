package sri.mobile.template.components

import sri.core
import sri.core.ElementFactory.makeElementNoPropsWithChildren
import sri.core.{ReactComponent, ReactElement}
import sri.mobile.template.router.AppRouter.{ClientPage, ServerPage}
import sri.universal.components.{Button, View}
import sri.universal.{ReactEvent, SyntheticEvent, router}
import sri.universal.router.UniversalRouterComponent

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

object ViewComp{
  @ScalaJSDefined
  class Component extends ReactComponent[Unit, Unit] {
    override def render(): ReactElement = View()(children)
  }

  def apply()(children: core.ReactNode*) = makeElementNoPropsWithChildren[Component]()(children: _*)
}
