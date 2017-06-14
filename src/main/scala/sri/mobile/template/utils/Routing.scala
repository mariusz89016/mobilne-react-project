package sri.mobile.template.utils

import sri.universal.router.{DynamicPage, UniversalRouterCtrl}

import scala.scalajs.js

object Routing {
  def replace[T](ctrl: UniversalRouterCtrl, page: DynamicPage[T], props: T, title: String ) = {
    val config = ctrl.config
    val navigator = ctrl.navigator

    config.routes.get(page) match {
      case Some(route) =>
        val obj = route.asInstanceOf[js.Dynamic]
        obj.updateDynamic("props")(props.asInstanceOf[js.Any])
        obj.updateDynamic("title")(title)
        navigator.replace(obj.asInstanceOf[js.Object])

      case None => {}
    }
  }
}
