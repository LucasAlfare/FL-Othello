import com.lucasalfare.flothello.core.Board
import com.lucasalfare.flothello.core.HumanPlayer
import com.lucasalfare.flothello.core.Piece
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HumanPlayerTest {

  @Test
  fun `test human player do move success`() {
    val board = Board()
    val human = HumanPlayer(Piece.Black)
    val (x, y) = 4 to 2
    human.targetCoordsDefiner = { x to y }
    assertTrue(human.doMove(board))
  }

  @Test
  fun `test human player do move failure`() {
    val board = Board()
    val human = HumanPlayer(Piece.Black)
    val (x, y) = 3 to 2
    human.targetCoordsDefiner = { x to y }
    assertFalse(human.doMove(board))
  }
}