package sri.mobile.template

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

package object images {
  @js.native
  @JSImport("./images/like.png", JSImport.Default)
  object LikeImage extends js.Object


  object cards {
    @js.native
    @JSImport("./images/cards/back.png", JSImport.Default)
    object Covered extends js.Object

    @js.native
    @JSImport("./images/cards/black_joker.png", JSImport.Default)
    object Joker extends js.Object

    trait Suit {
      val Number2: js.Object
      val Number3: js.Object
      val Number4: js.Object
      val Number5: js.Object
      val Number6: js.Object
      val Number7: js.Object
      val Number8: js.Object
      val Number9: js.Object
      val Number10: js.Object
      val Jack: js.Object
      val Queen: js.Object
      val King: js.Object
      val Ace: js.Object
    }

    object hearts extends Suit {
      val Number2 = Hearts2
      val Number3 = Hearts3
      val Number4 = Hearts4
      val Number5 = Hearts5
      val Number6 = Hearts6
      val Number7 = Hearts7
      val Number8 = Hearts8
      val Number9 = Hearts9
      val Number10 = Hearts10
      val Jack = HeartsJack
      val Queen = HeartsQueen
      val King = HeartsKing
      val Ace = HeartsAce
    }

    object diamonds extends Suit {
      val Number2 = Diamonds2
      val Number3 = Diamonds3
      val Number4 = Diamonds4
      val Number5 = Diamonds5
      val Number6 = Diamonds6
      val Number7 = Diamonds7
      val Number8 = Diamonds8
      val Number9 = Diamonds9
      val Number10 = Diamonds10
      val Jack = DiamondsJack
      val Queen = DiamondsQueen
      val King = DiamondsKing
      val Ace = DiamondsAce
    }

    object spades extends Suit {
      val Number2 = Spades2
      val Number3 = Spades3
      val Number4 = Spades4
      val Number5 = Spades5
      val Number6 = Spades6
      val Number7 = Spades7
      val Number8 = Spades8
      val Number9 = Spades9
      val Number10 = Spades10
      val Jack = SpadesJack
      val Queen = SpadesQueen
      val King = SpadesKing
      val Ace = SpadesAce
    }

    object clubs extends Suit {
      val Number2 = Clubs2
      val Number3 = Clubs3
      val Number4 = Clubs4
      val Number5 = Clubs5
      val Number6 = Clubs6
      val Number7 = Clubs7
      val Number8 = Clubs8
      val Number9 = Clubs9
      val Number10 = Clubs10
      val Jack = ClubsJack
      val Queen = ClubsQueen
      val King = ClubsKing
      val Ace = ClubsAce
    }
  }


  @js.native
  @JSImport("./images/cards/2_of_hearts.png", JSImport.Default)
  object Hearts2 extends js.Object

  @js.native
  @JSImport("./images/cards/3_of_hearts.png", JSImport.Default)
  object Hearts3 extends js.Object

  @js.native
  @JSImport("./images/cards/4_of_hearts.png", JSImport.Default)
  object Hearts4 extends js.Object

  @js.native
  @JSImport("./images/cards/5_of_hearts.png", JSImport.Default)
  object Hearts5 extends js.Object

  @js.native
  @JSImport("./images/cards/6_of_hearts.png", JSImport.Default)
  object Hearts6 extends js.Object

  @js.native
  @JSImport("./images/cards/7_of_hearts.png", JSImport.Default)
  object Hearts7 extends js.Object

  @js.native
  @JSImport("./images/cards/8_of_hearts.png", JSImport.Default)
  object Hearts8 extends js.Object

  @js.native
  @JSImport("./images/cards/9_of_hearts.png", JSImport.Default)
  object Hearts9 extends js.Object

  @js.native
  @JSImport("./images/cards/10_of_hearts.png", JSImport.Default)
  object Hearts10 extends js.Object

  @js.native
  @JSImport("./images/cards/jack_of_hearts.png", JSImport.Default)
  object HeartsJack extends js.Object

  @js.native
  @JSImport("./images/cards/queen_of_hearts.png", JSImport.Default)
  object HeartsQueen extends js.Object

  @js.native
  @JSImport("./images/cards/king_of_hearts.png", JSImport.Default)
  object HeartsKing extends js.Object

  @js.native
  @JSImport("./images/cards/ace_of_hearts.png", JSImport.Default)
  object HeartsAce extends js.Object


  @js.native
  @JSImport("./images/cards/2_of_spades.png", JSImport.Default)
  object Spades2 extends js.Object

  @js.native
  @JSImport("./images/cards/3_of_spades.png", JSImport.Default)
  object Spades3 extends js.Object

  @js.native
  @JSImport("./images/cards/4_of_spades.png", JSImport.Default)
  object Spades4 extends js.Object

  @js.native
  @JSImport("./images/cards/5_of_spades.png", JSImport.Default)
  object Spades5 extends js.Object

  @js.native
  @JSImport("./images/cards/6_of_spades.png", JSImport.Default)
  object Spades6 extends js.Object

  @js.native
  @JSImport("./images/cards/7_of_spades.png", JSImport.Default)
  object Spades7 extends js.Object

  @js.native
  @JSImport("./images/cards/8_of_spades.png", JSImport.Default)
  object Spades8 extends js.Object

  @js.native
  @JSImport("./images/cards/9_of_spades.png", JSImport.Default)
  object Spades9 extends js.Object

  @js.native
  @JSImport("./images/cards/10_of_spades.png", JSImport.Default)
  object Spades10 extends js.Object

  @js.native
  @JSImport("./images/cards/jack_of_spades.png", JSImport.Default)
  object SpadesJack extends js.Object

  @js.native
  @JSImport("./images/cards/queen_of_spades.png", JSImport.Default)
  object SpadesQueen extends js.Object

  @js.native
  @JSImport("./images/cards/king_of_spades.png", JSImport.Default)
  object SpadesKing extends js.Object

  @js.native
  @JSImport("./images/cards/ace_of_spades.png", JSImport.Default)
  object SpadesAce extends js.Object

  @js.native
  @JSImport("./images/cards/2_of_diamonds.png", JSImport.Default)
  object Diamonds2 extends js.Object

  @js.native
  @JSImport("./images/cards/3_of_diamonds.png", JSImport.Default)
  object Diamonds3 extends js.Object

  @js.native
  @JSImport("./images/cards/4_of_diamonds.png", JSImport.Default)
  object Diamonds4 extends js.Object

  @js.native
  @JSImport("./images/cards/5_of_diamonds.png", JSImport.Default)
  object Diamonds5 extends js.Object

  @js.native
  @JSImport("./images/cards/6_of_diamonds.png", JSImport.Default)
  object Diamonds6 extends js.Object

  @js.native
  @JSImport("./images/cards/7_of_diamonds.png", JSImport.Default)
  object Diamonds7 extends js.Object

  @js.native
  @JSImport("./images/cards/8_of_diamonds.png", JSImport.Default)
  object Diamonds8 extends js.Object

  @js.native
  @JSImport("./images/cards/9_of_diamonds.png", JSImport.Default)
  object Diamonds9 extends js.Object

  @js.native
  @JSImport("./images/cards/10_of_diamonds.png", JSImport.Default)
  object Diamonds10 extends js.Object

  @js.native
  @JSImport("./images/cards/jack_of_diamonds.png", JSImport.Default)
  object DiamondsJack extends js.Object

  @js.native
  @JSImport("./images/cards/queen_of_diamonds.png", JSImport.Default)
  object DiamondsQueen extends js.Object

  @js.native
  @JSImport("./images/cards/king_of_diamonds.png", JSImport.Default)
  object DiamondsKing extends js.Object

  @js.native
  @JSImport("./images/cards/ace_of_diamonds.png", JSImport.Default)
  object DiamondsAce extends js.Object

  @js.native
  @JSImport("./images/cards/2_of_clubs.png", JSImport.Default)
  object Clubs2 extends js.Object

  @js.native
  @JSImport("./images/cards/3_of_clubs.png", JSImport.Default)
  object Clubs3 extends js.Object

  @js.native
  @JSImport("./images/cards/4_of_clubs.png", JSImport.Default)
  object Clubs4 extends js.Object

  @js.native
  @JSImport("./images/cards/5_of_clubs.png", JSImport.Default)
  object Clubs5 extends js.Object

  @js.native
  @JSImport("./images/cards/6_of_clubs.png", JSImport.Default)
  object Clubs6 extends js.Object

  @js.native
  @JSImport("./images/cards/7_of_clubs.png", JSImport.Default)
  object Clubs7 extends js.Object

  @js.native
  @JSImport("./images/cards/8_of_clubs.png", JSImport.Default)
  object Clubs8 extends js.Object

  @js.native
  @JSImport("./images/cards/9_of_clubs.png", JSImport.Default)
  object Clubs9 extends js.Object

  @js.native
  @JSImport("./images/cards/10_of_clubs.png", JSImport.Default)
  object Clubs10 extends js.Object

  @js.native
  @JSImport("./images/cards/jack_of_clubs.png", JSImport.Default)
  object ClubsJack extends js.Object

  @js.native
  @JSImport("./images/cards/queen_of_clubs.png", JSImport.Default)
  object ClubsQueen extends js.Object

  @js.native
  @JSImport("./images/cards/king_of_clubs.png", JSImport.Default)
  object ClubsKing extends js.Object

  @js.native
  @JSImport("./images/cards/ace_of_clubs.png", JSImport.Default)
  object ClubsAce extends js.Object
}
