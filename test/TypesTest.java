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

    @Test
    public void piece() {
        assertEquals(Types.Piece.W_PAWN, Types.Piece.of(Types.Color.WHITE, Types.PieceType.PAWN));
        assertEquals(Types.Piece.W_KNIGHT, Types.Piece.of(Types.Color.WHITE, Types.PieceType.KNIGHT));
        assertEquals(Types.Piece.W_BISHOP, Types.Piece.of(Types.Color.WHITE, Types.PieceType.BISHOP));
        assertEquals(Types.Piece.W_ROOK, Types.Piece.of(Types.Color.WHITE, Types.PieceType.ROOK));
        assertEquals(Types.Piece.W_QUEEN, Types.Piece.of(Types.Color.WHITE, Types.PieceType.QUEEN));
        assertEquals(Types.Piece.W_KING, Types.Piece.of(Types.Color.WHITE, Types.PieceType.KING));
        // black
        assertEquals(Types.Piece.B_PAWN, Types.Piece.of(Types.Color.BLACK, Types.PieceType.PAWN));
        assertEquals(Types.Piece.B_KNIGHT, Types.Piece.of(Types.Color.BLACK, Types.PieceType.KNIGHT));
        assertEquals(Types.Piece.B_BISHOP, Types.Piece.of(Types.Color.BLACK, Types.PieceType.BISHOP));
        assertEquals(Types.Piece.B_ROOK, Types.Piece.of(Types.Color.BLACK, Types.PieceType.ROOK));
        assertEquals(Types.Piece.B_QUEEN, Types.Piece.of(Types.Color.BLACK, Types.PieceType.QUEEN));
        assertEquals(Types.Piece.B_KING, Types.Piece.of(Types.Color.BLACK, Types.PieceType.KING));
        // toggle
        assertEquals(Types.Piece.B_PAWN, Types.Piece.W_PAWN.toggle());
        assertEquals(Types.Piece.B_KING, Types.Piece.W_KING.toggle());
        assertEquals(Types.Piece.W_PAWN, Types.Piece.B_PAWN.toggle());
        assertEquals(Types.Piece.W_KING, Types.Piece.B_KING.toggle());
    }

    @Test
    public void square() {
        assertEquals(Types.Square.SQ_A1, Types.Square.of(0));
        assertEquals(Types.Square.SQ_H8, Types.Square.of(63));
        // of File, Rank
        assertEquals(Types.Square.SQ_A1, Types.Square.of(Types.File.FILE_A, Types.Rank.RANK_1));
        assertEquals(Types.Square.SQ_H8, Types.Square.of(Types.File.FILE_H, Types.Rank.RANK_8));
        // is_ok
        assertFalse(Types.Square.is_ok(-1));
        assertTrue(Types.Square.is_ok(0));
        assertTrue(Types.Square.is_ok(63));
        assertFalse(Types.Square.is_ok(64));
        // relative
        assertEquals(Types.Square.SQ_A1, Types.Square.relative(Types.Color.WHITE, Types.Square.SQ_A1));
        assertEquals(Types.Square.SQ_A8, Types.Square.relative(Types.Color.BLACK, Types.Square.SQ_A1));
        assertEquals(Types.Square.SQ_A8, Types.Square.relative(Types.Color.WHITE, Types.Square.SQ_A8));
        assertEquals(Types.Square.SQ_A1, Types.Square.relative(Types.Color.BLACK, Types.Square.SQ_A8));
        assertEquals(Types.Square.SQ_H1, Types.Square.relative(Types.Color.WHITE, Types.Square.SQ_H1));
        assertEquals(Types.Square.SQ_H8, Types.Square.relative(Types.Color.BLACK, Types.Square.SQ_H1));
        assertEquals(Types.Square.SQ_H8, Types.Square.relative(Types.Color.WHITE, Types.Square.SQ_H8));
        assertEquals(Types.Square.SQ_H1, Types.Square.relative(Types.Color.BLACK, Types.Square.SQ_H8));
        // flip_rank
        assertEquals(Types.Square.SQ_A8, Types.Square.SQ_A1.flip_rank());
        // flip_file
        assertEquals(Types.Square.SQ_H1, Types.Square.SQ_A1.flip_file());
        // plus
        assertEquals(Types.Square.SQ_A2, Types.Square.SQ_A1.plus(Types.Direction.NORTH));
        // minus
        assertEquals(Types.Square.SQ_A7, Types.Square.SQ_A8.minus(Types.Direction.NORTH));
    }

    @Test
    public void direction() {
        assertEquals(8, Types.Direction.NORTH.value);
        assertEquals(1, Types.Direction.EAST.value);
        assertEquals(-8, Types.Direction.SOUTH.value);
        assertEquals(-1, Types.Direction.WEST.value);
        assertEquals(9, Types.Direction.NORTH_EAST.value);
        assertEquals(-7, Types.Direction.SOUTH_EAST.value);
        assertEquals(-9, Types.Direction.SOUTH_WEST.value);
        assertEquals(7, Types.Direction.NORTH_WEST.value);
        // plus
        assertEquals(9, Types.DirectionPlus(Types.Direction.NORTH, Types.Direction.EAST));
        // multiply
        assertEquals(24, Types.DirectionMultiply(3, Types.Direction.NORTH));
        // SquarePlus
        assertEquals(Types.Square.SQ_A2.ordinal(), Types.SquarePlus(Types.Square.SQ_A1, Types.Direction.NORTH));
        // SquareMinus
        assertEquals(Types.Square.SQ_A7.ordinal(), Types.SquareMinus(Types.Square.SQ_A8, Types.Direction.NORTH));
    }

    @Test
    public void file() {
        assertEquals(Types.File.FILE_A, Types.File.of(Types.Square.SQ_A1));
        assertEquals(Types.File.FILE_A, Types.File.of(Types.Square.SQ_A8));
        assertEquals(Types.File.FILE_H, Types.File.of(Types.Square.SQ_H1));
        assertEquals(Types.File.FILE_H, Types.File.of(Types.Square.SQ_H8));
    }

    @Test
    public void rank() {
        assertEquals(Types.Rank.RANK_1, Types.Rank.of(Types.Square.SQ_A1));
        assertEquals(Types.Rank.RANK_1, Types.Rank.of(Types.Square.SQ_H1));
        assertEquals(Types.Rank.RANK_8, Types.Rank.of(Types.Square.SQ_A8));
        assertEquals(Types.Rank.RANK_8, Types.Rank.of(Types.Square.SQ_H8));
        // relative
        assertEquals(Types.Rank.RANK_1, Types.Rank.relative(Types.Color.WHITE, Types.Rank.RANK_1));
        assertEquals(Types.Rank.RANK_8, Types.Rank.relative(Types.Color.BLACK, Types.Rank.RANK_1));
        assertEquals(Types.Rank.RANK_8, Types.Rank.relative(Types.Color.WHITE, Types.Rank.RANK_8));
        assertEquals(Types.Rank.RANK_1, Types.Rank.relative(Types.Color.BLACK, Types.Rank.RANK_8));
        // relative Square
        assertEquals(Types.Rank.RANK_1, Types.Rank.relative(Types.Color.WHITE, Types.Square.SQ_A1));
        assertEquals(Types.Rank.RANK_8, Types.Rank.relative(Types.Color.BLACK, Types.Square.SQ_A1));
        assertEquals(Types.Rank.RANK_8, Types.Rank.relative(Types.Color.WHITE, Types.Square.SQ_H8));
        assertEquals(Types.Rank.RANK_1, Types.Rank.relative(Types.Color.BLACK, Types.Square.SQ_H8));
    }

    @Test
    public void castlingRights() {
        assertEquals(Types.CastlingRights.NO_CASTLING.value,
                Types.CastlingRightsCheck(Types.Color.WHITE, Types.CastlingRights.NO_CASTLING));
        assertEquals(Types.CastlingRights.WHITE_CASTLING.value,
                Types.CastlingRightsCheck(Types.Color.WHITE, Types.CastlingRights.ANY_CASTLING));
        assertEquals(Types.CastlingRights.NO_CASTLING.value,
                Types.CastlingRightsCheck(Types.Color.BLACK, Types.CastlingRights.NO_CASTLING));
        assertEquals(Types.CastlingRights.BLACK_CASTLING.value,
                Types.CastlingRightsCheck(Types.Color.BLACK, Types.CastlingRights.ANY_CASTLING));
    }

    @Test
    public void functions() {
        assertEquals(Types.VALUE_MATE, Types.mate_in(0));
        assertEquals(Types.VALUE_MATE - 1, Types.mate_in(1));
        assertEquals(-Types.VALUE_MATE, Types.mated_in(0));
        assertEquals(-Types.VALUE_MATE + 1, Types.mated_in(1));
        assertEquals(1442695040888963407L, Types.make_key(0));
        assertEquals(7806831264735756412L, Types.make_key(1));
    }

    @Test
    public void moveType() {
        assertEquals(Types.MoveType.NORMAL, Types.MoveType.of(Types.MoveType.NORMAL.value));
        assertEquals(Types.MoveType.PROMOTION, Types.MoveType.of(Types.MoveType.PROMOTION.value));
        assertEquals(Types.MoveType.EN_PASSANT, Types.MoveType.of(Types.MoveType.EN_PASSANT.value));
        assertEquals(Types.MoveType.CASTLING, Types.MoveType.of(Types.MoveType.CASTLING.value));
    }

    @Test
    public void move() {
        assertEquals(8, Types.Move.move(Types.Square.SQ_A1, Types.Square.SQ_A2));
        assertEquals(512, Types.Move.move(Types.Square.SQ_A2, Types.Square.SQ_A1));
        assertEquals(528, Types.Move.move(Types.Square.SQ_A2, Types.Square.SQ_A3));
        assertEquals(1032, Types.Move.move(Types.Square.SQ_A3, Types.Square.SQ_A2));
        assertEquals(3583, Types.Move.move(Types.Square.SQ_H7, Types.Square.SQ_H8));
        assertEquals(4087, Types.Move.move(Types.Square.SQ_H8, Types.Square.SQ_H7));
        // make
        assertEquals(8,
                Types.Move.make(Types.MoveType.NORMAL, Types.Square.SQ_A1, Types.Square.SQ_A2,
                        Types.PieceType.KNIGHT));
        assertEquals(24567,
                Types.Move.make(Types.MoveType.PROMOTION, Types.Square.SQ_H8, Types.Square.SQ_H7,
                        Types.PieceType.BISHOP));
        assertEquals(-20489,
                Types.Move.make(Types.MoveType.EN_PASSANT, Types.Square.SQ_H8, Types.Square.SQ_H7,
                        Types.PieceType.ROOK));
        assertEquals(-9,
                Types.Move.make(Types.MoveType.CASTLING, Types.Square.SQ_H8, Types.Square.SQ_H7,
                        Types.PieceType.QUEEN));
        // from_sq
        assertEquals(Types.Square.SQ_A1, Types.Move.from_sq((short) 8));
        assertEquals(Types.Square.SQ_H8, Types.Move.from_sq((short) 24567));
        assertEquals(Types.Square.SQ_H8, Types.Move.from_sq((short) -20489));
        assertEquals(Types.Square.SQ_H8, Types.Move.from_sq((short) -9));
        // to_sq
        assertEquals(Types.Square.SQ_A2, Types.Move.to_sq((short) 8));
        assertEquals(Types.Square.SQ_H7, Types.Move.to_sq((short) 24567));
        assertEquals(Types.Square.SQ_H7, Types.Move.to_sq((short) -20489));
        assertEquals(Types.Square.SQ_H7, Types.Move.to_sq((short) -9));
        // from_to
        assertEquals(8, Types.Move.from_to((short) 8));
        assertEquals(4087, Types.Move.from_to((short) -9));
        // type_of
        assertEquals(Types.MoveType.NORMAL, Types.Move.type_of((short) 8));
        assertEquals(Types.MoveType.PROMOTION, Types.Move.type_of((short) 24567));
        assertEquals(Types.MoveType.EN_PASSANT, Types.Move.type_of((short) -20489));
        assertEquals(Types.MoveType.CASTLING, Types.Move.type_of((short) -9));
        // promotion_type
        assertEquals(Types.PieceType.KNIGHT, Types.Move.promotion_type((short) 8));
        assertEquals(Types.PieceType.BISHOP, Types.Move.promotion_type((short) 24567));
        assertEquals(Types.PieceType.ROOK, Types.Move.promotion_type((short) -20489));
        assertEquals(Types.PieceType.QUEEN, Types.Move.promotion_type((short) -9));
        // is_ok
        assertFalse(Types.Move.is_ok(Types.Move.none()));
        assertFalse(Types.Move.is_ok(Types.Move.Null()));
        assertTrue(Types.Move.is_ok((short) 1));
        assertTrue(Types.Move.is_ok((short) -1));
    }

}