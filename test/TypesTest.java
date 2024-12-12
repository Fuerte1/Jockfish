import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypesTest {

    @Test
    public void color() {
        assertEquals(Types.Color.BLACK, Types.Color.WHITE.toggle());
        assertEquals(Types.Color.WHITE, Types.Color.BLACK.toggle());
        assertEquals(Types.Direction.NORTH, Types.Color.WHITE.pawn_push());
        assertEquals(Types.Direction.SOUTH, Types.Color.BLACK.pawn_push());
        // white
        assertEquals(Types.Color.WHITE, Types.Color.of(Types.Piece.W_PAWN));
        assertEquals(Types.Color.WHITE, Types.Color.of(Types.Piece.W_KNIGHT));
        assertEquals(Types.Color.WHITE, Types.Color.of(Types.Piece.W_BISHOP));
        assertEquals(Types.Color.WHITE, Types.Color.of(Types.Piece.W_ROOK));
        assertEquals(Types.Color.WHITE, Types.Color.of(Types.Piece.W_QUEEN));
        assertEquals(Types.Color.WHITE, Types.Color.of(Types.Piece.W_KING));
        // black
        assertEquals(Types.Color.BLACK, Types.Color.of(Types.Piece.B_PAWN));
        assertEquals(Types.Color.BLACK, Types.Color.of(Types.Piece.B_KNIGHT));
        assertEquals(Types.Color.BLACK, Types.Color.of(Types.Piece.B_BISHOP));
        assertEquals(Types.Color.BLACK, Types.Color.of(Types.Piece.B_ROOK));
        assertEquals(Types.Color.BLACK, Types.Color.of(Types.Piece.B_QUEEN));
        assertEquals(Types.Color.BLACK, Types.Color.of(Types.Piece.B_KING));
    }

    @Test
    public void pieceType() {
        assertEquals(Types.PieceType.PAWN, Types.PieceType.of(Types.Piece.W_PAWN));
        assertEquals(Types.PieceType.KNIGHT, Types.PieceType.of(Types.Piece.W_KNIGHT));
        assertEquals(Types.PieceType.BISHOP, Types.PieceType.of(Types.Piece.W_BISHOP));
        assertEquals(Types.PieceType.ROOK, Types.PieceType.of(Types.Piece.W_ROOK));
        assertEquals(Types.PieceType.QUEEN, Types.PieceType.of(Types.Piece.W_QUEEN));
        assertEquals(Types.PieceType.KING, Types.PieceType.of(Types.Piece.W_KING));
        // black
        assertEquals(Types.PieceType.PAWN, Types.PieceType.of(Types.Piece.B_PAWN));
        assertEquals(Types.PieceType.KNIGHT, Types.PieceType.of(Types.Piece.B_KNIGHT));
        assertEquals(Types.PieceType.BISHOP, Types.PieceType.of(Types.Piece.B_BISHOP));
        assertEquals(Types.PieceType.ROOK, Types.PieceType.of(Types.Piece.B_ROOK));
        assertEquals(Types.PieceType.QUEEN, Types.PieceType.of(Types.Piece.B_QUEEN));
        assertEquals(Types.PieceType.KING, Types.PieceType.of(Types.Piece.B_KING));
    }

}