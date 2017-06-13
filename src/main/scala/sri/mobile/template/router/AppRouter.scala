package sri.mobile.template.router

import sri.core.ReactElement
import sri.mobile.template.components._
import sri.universal.components.{Button, View}
import sri.universal.router._
import sri.universal.styles.UniversalStyleSheet
import sri.mobile.template.utils.Player


object AppRouter {

  object HomePage extends StaticPage

  object ClientPage extends StaticPage
  object ServerPage extends StaticPage

  object PlayersSharedPage extends DynamicPage[(Player, Player)]
  object PlayerOwnPage extends StaticPage

  object Config extends UniversalRouterConfig {

    override val initialRoute = defineInitialRoute(HomePage, "Home", StartView())

    staticRoute(ClientPage, "Create a new game", RegistrationView())
    staticRoute(ServerPage, "Join a game", ServerView())


    staticRoute(PlayerOwnPage, "Player's own view", ClientGameView())
    dynamicRoute(PlayersSharedPage, { players: (Player, Player) => ServerGameView(players._1, players._2) })

    override val notFound = UniversalRouteNotFound(initialRoute._1)

    override def renderScene(route: NavigatorRoute,ctrl: UniversalRouterCtrl): ReactElement = {
      View(style = UniversalStyleSheet.wholeContainer)(
        super.renderScene(route,ctrl)
      )
    }
  }

  val router = UniversalRouter(Config)

}

