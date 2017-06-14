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

  object PlayersSharedPage extends DynamicPage[(Player, Player, String)]
  object PlayerOwnPage extends DynamicPage[String]

  object Config extends UniversalRouterConfig {

    override val initialRoute = defineInitialRoute(HomePage, "Home", StartView())
//    override val initialRoute = defineInitialRoute(HomePage, "Home", ServerGameView(Player("player1", "192.168.1.3", ""), Player("player2", "192.168.1.3", "")))


    staticRoute(ClientPage, "Create a new game", RegistrationView())
    staticRoute(ServerPage, "Join a game", ServerView())


    dynamicRoute(PlayerOwnPage, { serverIp: String => ClientGameView(serverIp) })
    dynamicRoute(PlayersSharedPage, { props: (Player, Player, String) => ServerGameView(props._1, props._2, props._3) })

    override val notFound = UniversalRouteNotFound(initialRoute._1)

    override def renderScene(route: NavigatorRoute,ctrl: UniversalRouterCtrl): ReactElement = {
      View(style = UniversalStyleSheet.wholeContainer)(
        super.renderScene(route,ctrl)
      )
    }
  }

  val router = UniversalRouter(Config)

}

