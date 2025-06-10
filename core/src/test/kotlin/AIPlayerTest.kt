import com.lucasalfare.flothello.core.AIPlayer
import com.lucasalfare.flothello.core.BOARD_SIZE
import com.lucasalfare.flothello.core.Board
import com.lucasalfare.flothello.core.Piece
import org.junit.jupiter.api.Assertions.assertFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AIPlayerTest {

  @Test
  fun `bot should make a valid move`() {
    val board = Board()
    val ai = AIPlayer(Piece.Black)
    val success = ai.doMove(board)
    assertTrue(success)
    val totalPieces = board.pieces.count { it != Piece.Empty }
    assertEquals(5, totalPieces) // 4 init + 1 move
  }

  @Test
  fun `bot should do nothing if no valid move`() {
    val board = Board(Array(BOARD_SIZE * BOARD_SIZE) { Piece.White }) // filled board
    val ai = AIPlayer(Piece.Black)
    val success = ai.doMove(board)
    assertFalse(success)
  }
}