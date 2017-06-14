package sri.mobile.template.games.mau

import sri.mobile.template.games.PlayingCard.Joker
import sri.mobile.template.games.{CardRank, CardSuit, PlayingCard, RankedCard}

sealed trait SimpleMauGame

case class SimpleMauGameInProgress(
                          coveredStack: Seq[PlayingCard],
                          uncoveredStack: Seq[PlayingCard],
                          playerHands: Seq[Seq[PlayingCard]],
                          currentPlayerNo: Int
                        ) extends SimpleMauGame{

  private def cardsMatch(stack: Seq[PlayingCard], card: PlayingCard): Boolean = {
    (stack.head, card) match {
      case (_, Joker) => true
      case (Joker, _) => cardsMatch(stack.tail, card)
      case (RankedCard(_, suit1), RankedCard(_, suit2)) if suit1 == suit2 => true
      case (RankedCard(rank1, _), RankedCard(rank2, _)) if rank1 == rank2 => true
      case _ => false
    }
  }

  def acceptMove(move: PlayerMove, playerNo: Int): Option[SimpleMauGame] = {
    move match {
      case PullCard =>
        Some(this.copy(
          coveredStack = coveredStack.tail,
          currentPlayerNo = (currentPlayerNo + 1) % playerHands.length,
          playerHands = playerHands.updated(currentPlayerNo, playerHands(currentPlayerNo) :+ coveredStack.head)
        ))

      case PushCard(card) =>
        if(playerNo == currentPlayerNo && cardsMatch(uncoveredStack, card)) {
          Some(this.copy(
            uncoveredStack = card +: uncoveredStack,
            currentPlayerNo = (currentPlayerNo + 1) % playerHands.length,
            playerHands = playerHands.updated(currentPlayerNo, playerHands(currentPlayerNo).diff(Seq(card)))
          ))
        } else {
          None
        }
    }
  }
}

case class FinishedSimpleMauGame(winnerNo: Int) extends SimpleMauGame

object SimpleMauGame {
  val fullDeck = {
    val suitedCards = for{
      suit <- CardSuit.all
      rank <- CardRank.all
    } yield RankedCard(rank, suit)

    Seq.fill(2)(PlayingCard.Joker) ++ suitedCards
  }

  def newRandomGame(playersCount: Int): SimpleMauGameInProgress = {
    val shuffledDeck = scala.util.Random.shuffle(fullDeck).iterator

    val playerHands = Seq.fill(playersCount)(shuffledDeck.take(5).toSeq)

    val uncoveredStack = shuffledDeck.take(1).toSeq
    val coveredStack = shuffledDeck.toSeq

    if(uncoveredStack.head != Joker) {
      SimpleMauGameInProgress(coveredStack, uncoveredStack, playerHands, currentPlayerNo = 0)
    } else {
      newRandomGame(playersCount)
    }
  }
}

sealed trait PlayerMove

case class PushCard(card: PlayingCard) extends PlayerMove
object PullCard extends PlayerMove
