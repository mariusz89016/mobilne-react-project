package sri.mobile.template.router

import sri.core.ReactElement
import sri.mobile.template.components._
import sri.universal.components.{Button, View}
import sri.universal.router._
import sri.universal.styles.UniversalStyleSheet


object AppRouter {

  object HomePage extends StaticPage

  object ClientPage extends StaticPage
  object ServerPage extends StaticPage

  object PlayersSharedPage extends StaticPage
  object PlayerOwnPage extends StaticPage

  object Config extends UniversalRouterConfig {

    override val initialRoute = defineInitialRoute(HomePage, "Home", StartView())

    staticRoute(ClientPage, "Create a new game", RegistrationView())
    staticRoute(ServerPage, "Join a game", ServerView())


    staticRoute(PlayerOwnPage, "Player's own view", ClientGameView())
    staticRoute(PlayersSharedPage, "Players' shared view", ServerGameView())

    override val notFound = UniversalRouteNotFound(initialRoute._1)

    override def renderScene(route: NavigatorRoute,ctrl: UniversalRouterCtrl): ReactElement = {
      View(style = UniversalStyleSheet.wholeContainer)(
        super.renderScene(route,ctrl)
      )
    }
  }

  val router = UniversalRouter(Config)

}

