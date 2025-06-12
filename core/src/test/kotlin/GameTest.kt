import com.lucasalfare.flothello.core.AIPlayer
import com.lucasalfare.flothello.core.Board
import com.lucasalfare.flothello.core.Piece
import com.lucasalfare.flothello.core.game.Game
import com.lucasalfare.flothello.core.game.State
import com.lucasalfare.flothello.core.game.Status
import kotlin.test.Test
import kotlin.test.assertEquals

class GameTest {

  @Test
  fun `test step advances round on successful move`() {
    val p1 = AIPlayer(Piece.Black)
    val p2 = AIPlayer(p1.piece)
    val game = Game(p1, p2)
    assertEquals(expected = 0, actual = game.state.round)
    game.step()
    assertEquals(expected = 1, actual = game.state.round)
  }

  @Test
  fun `test step alternates players`() {
    val p1 = AIPlayer(Piece.Black)
    val p2 = AIPlayer(p1.piece)
    val game = Game(p1, p2)
    assertEquals(expected = p1, actual = game.state.currentPlayer)
    game.step()
    assertEquals(expected = p2, actual = game.state.currentPlayer)
  }

  @Test
  fun `test step passes turn when no valid moves`() {
    val p1 = AIPlayer(Piece.Black)
    val p2 = AIPlayer(p1.piece.opposite())

    val auxBoard = Board()
    auxBoard.pieces.fill(Piece.White)
    auxBoard.set(Piece.Empty, 0, 0)
    auxBoard.set(Piece.Black, 0, 1)

    val game = Game(p1, p2, initialState = State(board = auxBoard, currentPlayer = p1, round = 1))
    game.step()
    assertEquals(expected = Status.Playing, game.state.status)
    assertEquals(expected = p2, game.state.currentPlayer)
  }

  @Test
  fun `test game ends when both players have no moves`() {
    val p1 = AIPlayer(Piece.Black)
    val p2 = AIPlayer(p1.piece.opposite())

    val auxBoard = Board()
    auxBoard.pieces.fill(Piece.White)
    auxBoard.set(Piece.Empty, 0, 0)

    val game = Game(p1, p2, initialState = State(board = auxBoard, currentPlayer = p1, round = 1))
    game.step()
    assertEquals(expected = Status.FinishedByNoMoves, game.state.status)
  }
}