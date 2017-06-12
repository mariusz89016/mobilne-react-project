package sri.mobile.template.router

import sri.core.ReactElement
import sri.mobile.template.components.{RegistrationComponent, ServerComponent}
import sri.universal.components.View
import sri.universal.router._
import sri.universal.styles.UniversalStyleSheet


object AppRouter {

  object HomePage extends StaticPage

  object Config extends UniversalRouterConfig {

//    override val initialRoute = defineInitialRoute(HomePage, "Home", RegistrationComponent(RegistrationComponent.Props("nazwa")))
    override val initialRoute = defineInitialRoute(HomePage, "Home", ServerComponent(ServerComponent.Props("nazwa")))

    override val notFound = UniversalRouteNotFound(initialRoute._1)

    override def renderScene(route: NavigatorRoute,ctrl: UniversalRouterCtrl): ReactElement = {
      View(style = UniversalStyleSheet.wholeContainer)(
        super.renderScene(route,ctrl)
      )
    }
  }

  val router = UniversalRouter(Config)

}

