import com.lucasalfare.flothello.core.AIPlayer
import com.lucasalfare.flothello.core.Board
import com.lucasalfare.flothello.core.Piece
import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.core.game.State
import com.lucasalfare.flothello.core.game.Status
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GameTest {

  @Test
  fun `test step advances round on successful move`() {
    val p1 = AIPlayer(Piece.Black)
    val p2 = AIPlayer(p1.piece.opposite())
    val game = Game(p1, p2)
    assertEquals(expected = 0, actual = game.state.round)
    val stepResult = game.step()
    assertTrue(actual = stepResult)
    assertEquals(expected = 1, actual = game.state.round)
  }

  @Test
  fun `test step alternates players when both have moves`() {
    val p1 = AIPlayer(Piece.Black)
    val p2 = AIPlayer(p1.piece.opposite())
    val game = Game(p1, p2)
    assertEquals(expected = p1, actual = game.state.currentPlayer)
    val stepResult = game.step()
    assertTrue(actual = stepResult)
    assertEquals(expected = p2, actual = game.state.currentPlayer)
  }

  @Test
  fun `test step passes turn when current player has no moves`() {
    val p1 = AIPlayer(Piece.Black)
    val p2 = AIPlayer(p1.piece.opposite())

    // Board where only p2 can move at (0,0)
    val auxBoard = Board().apply {
      pieces.fill(Piece.Black)
      set(Piece.Empty, 0, 0)
      set(Piece.White, 0, 1)
    }

    val game = Game(p1, p2, initialState = State(board = auxBoard, currentPlayer = p2, round = 1))
    val stepResult = game.step()
    assertTrue(actual = stepResult)

    assertEquals(expected = Status.Playing, actual = game.state.status)
    assertEquals(expected = p1, actual = game.state.currentPlayer)
    assertEquals(expected = 2, actual = game.state.round)
  }

  @Test
  fun `test step skips opponent when opponent has no moves but mover still can`() {
    val p1 = AIPlayer(Piece.Black)
    val p2 = AIPlayer(p1.piece.opposite())

    // Board where only p1 has moves and p2 has none
    val auxBoard = Board().apply {
      pieces.fill(Piece.White)
      set(Piece.Empty, 0, 0)
      set(Piece.Empty, 0, 1)
      set(Piece.Black, 0, 3)
      set(Piece.Black, 2, 0)
    }

    val game = Game(p1, p2, initialState = State(board = auxBoard, currentPlayer = p1, round = 5))
    val stepResult = game.step()
    assertTrue(actual = stepResult)

    // p1 moves, p2 has no moves, p1 keeps turn
    assertEquals(expected = Status.Playing, actual = game.state.status)
    assertEquals(expected = p1, actual = game.state.currentPlayer)
    assertEquals(expected = 6, actual = game.state.round)
  }

  @Test
  fun `test game ends when both players have no moves`() {
    val p1 = AIPlayer(Piece.Black)
    val p2 = AIPlayer(p1.piece.opposite())

    // Board full of white except one empty, but no flipping possible
    val auxBoard = Board().apply {
      pieces.fill(Piece.White)
      set(Piece.Empty, 0, 0)
    }

    val game = Game(p1, p2, initialState = State(board = auxBoard, currentPlayer = p1, round = 10))
    val next = game.step()
    assertEquals(expected = false, actual = next)
    assertEquals(expected = Status.FinishedByNoMoves, actual = game.state.status)
  }
}