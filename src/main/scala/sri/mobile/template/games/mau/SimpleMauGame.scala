package sri.mobile.template.games.mau

import sri.mobile.template.games.{CardRank, CardSuit, PlayingCard, RankedCard}

case class SimpleMauGame(coveredStack: Seq[PlayingCard], uncoveredStack: Seq[PlayingCard], player1Hand: Seq[PlayingCard], player2Hand: Seq[PlayingCard]) {


}

object SimpleMauGame {
  val fullDeck = {
    val suitedCards = for{
      suit <- CardSuit.all
      rank <- CardRank.all
    } yield RankedCard(rank, suit)

    Seq.fill(2)(PlayingCard.Joker) ++ suitedCards
  }

  def newRandomGame() = {
    val shuffledDeck = scala.util.Random.shuffle(fullDeck).iterator

    val player1Hand = shuffledDeck.take(5).toSeq
    val player2Hand = shuffledDeck.take(5).toSeq
    val uncoveredStack = shuffledDeck.take(1).toSeq
    val coveredStack = shuffledDeck.toSeq

    SimpleMauGame(coveredStack, uncoveredStack, player1Hand, player2Hand)
  }
}
