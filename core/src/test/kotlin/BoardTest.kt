import com.lucasalfare.flothello.core.Board
import com.lucasalfare.flothello.core.Piece
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class BoardTest {
  @Test
  fun `initial board setup`() {
    val board = Board()
    assertEquals(Piece.Black, board.get(3, 3))
    assertEquals(Piece.White, board.get(4, 3))
    assertEquals(Piece.White, board.get(3, 4))
    assertEquals(Piece.Black, board.get(4, 4))
    assertEquals(Piece.Empty, board.get(0, 0))
  }

  @Test
  fun `inBounds checks`() {
    val board = Board()
    assertTrue(board.inBounds(0, 0))
    assertTrue(board.inBounds(7, 7))
    assertFalse(board.inBounds(-1, 0))
    assertFalse(board.inBounds(8, 8))
  }

  @Test
  fun `valid move detection`() {
    val board = Board()
    assertTrue(board.isValidMove(Piece.Black, 4, 2))
    assertFalse(board.isValidMove(Piece.Black, 2, 3))
    assertFalse(board.isValidMove(Piece.Black, 3, 2))
    assertFalse(board.isValidMove(Piece.Black, 3, 3))
    assertFalse(board.isValidMove(Piece.White, 0, 0))
  }

  @Test
  fun `getValidMoves for black initially`() {
    val board = Board()
    val moves = board.getValidMoves(Piece.Black)
    assertEquals(4, moves.size)
    assertTrue(moves.containsAll(listOf(4 to 2, 5 to 3, 3 to 5, 2 to 4)))
  }

  @Test
  fun `findAffectedPositions horizontal`() {
    val board = Board().apply {
      set(Piece.Black, 3, 3)
      set(Piece.White, 3, 4)
      set(Piece.White, 3, 5) // B-W-W
    }
    val affected = board.findAffectedPositions(Piece.Black, 3, 6)
    assertEquals(listOf(3 to 5, 3 to 4), affected)
  }

  @Test
  fun `applyMove with capture`() {
    val board = Board().apply {
      set(Piece.Black, 3, 3)
      set(Piece.White, 3, 4)
    }
    assertTrue(board.applyMove(Piece.Black, 3, 5))
    assertEquals(Piece.Black, board.get(3, 5))
    assertEquals(Piece.Black, board.get(3, 4))
  }

  @Test
  fun `invalid move application`() {
    val board = Board()
    assertFalse(board.applyMove(Piece.Black, 3, 3))
    assertFalse(board.applyMove(Piece.White, 0, 0))
  }

  @Test
  fun `exploreDirection stopping conditions`() {
    val board = Board().apply {
      set(Piece.White, 1, 1)
      set(Piece.White, 2, 2)
    }

    val result1 = board.exploreDirection(Piece.White, 1, 1, 1, 1)
    assertTrue(result1.isEmpty())

    val result2 = board.exploreDirection(Piece.Black, 1, 1, 1, 1)
    assertEquals(listOf(1 to 1, 2 to 2), result2)
  }

  @Test
  fun `deepCopy does not reset center`() {
    val board = Board()
    board.applyMove(Piece.White, 3, 2)
    val copy = board.deepCopy()
    assertEquals(board.get(3, 3), copy.get(3, 3))
    assertNotEquals(Piece.Black, copy.get(3, 3))
  }
}