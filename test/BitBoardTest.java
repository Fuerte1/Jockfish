import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BitBoardTest {

    @BeforeAll
    public static void beforeAll() {
        BitBoard.init();
    }

    @Test
    public void magic() {
        BitBoard.Magic magic = new BitBoard.Magic();
        magic.mask = 1;
        magic.magic = 2;
        magic.shift = 3;
        magic.attacks = new long[]{2};
        assertEquals(0, magic.index(1));
        assertEquals(2, magic.attacks_bb(1));
    }

    @Test
    public void functions() {
        assertEquals(1, BitBoard.square_bb(Types.Square.SQ_A1));
        assertEquals(256, BitBoard.square_bb(Types.Square.SQ_A2));
        assertEquals(65536, BitBoard.square_bb(Types.Square.SQ_A3));
        assertEquals(-9223372036854775808L, BitBoard.square_bb(Types.Square.SQ_H8));
        // and or xor
        assertEquals(1, BitBoard.and(-1L, Types.Square.SQ_A1));
        assertEquals(1, BitBoard.or(0L, Types.Square.SQ_A1));
        assertEquals(1, BitBoard.xor(0L, Types.Square.SQ_A1));
        assertEquals(-2L, BitBoard.xor(-1L, Types.Square.SQ_A1));
        // and or xor
        assertEquals(0, BitBoard.and(Types.Square.SQ_A1, -1L));
        assertEquals(0, BitBoard.or(Types.Square.SQ_A1, 0L));
        assertEquals(0, BitBoard.xor(Types.Square.SQ_A1, 0L));
        assertEquals(-1L, BitBoard.xor(Types.Square.SQ_A1, -1L));
        // or
        assertEquals(9, BitBoard.or(Types.Square.SQ_A1, Types.Square.SQ_A2));
        // more_than_one
        assertFalse(BitBoard.more_than_one(0));
        assertFalse(BitBoard.more_than_one(1));
        assertTrue(BitBoard.more_than_one(3));
        assertTrue(BitBoard.more_than_one(-1));
        // rank_bb
        assertEquals(255, BitBoard.rank_bb(Types.Rank.RANK_1));
        assertEquals(65280, BitBoard.rank_bb(Types.Rank.RANK_2));
        // rank_bb
        assertEquals(255, BitBoard.rank_bb(Types.Square.SQ_A1));
        assertEquals(65280, BitBoard.rank_bb(Types.Square.SQ_A2));
        // file_bb
        assertEquals(72340172838076673L, BitBoard.file_bb(Types.File.FILE_A));
        assertEquals(144680345676153346L, BitBoard.file_bb(Types.File.FILE_B));
        // file_bb
        assertEquals(72340172838076673L, BitBoard.file_bb(Types.Square.SQ_A1));
        assertEquals(144680345676153346L, BitBoard.file_bb(Types.Square.SQ_B1));
        // shift
        assertEquals(256, BitBoard.shift(Types.Direction.NORTH.value, 1));
        assertEquals(0, BitBoard.shift(Types.Direction.SOUTH.value, 1));
        assertEquals(65536, BitBoard.shift(Types.Direction.NORTH.value * 2, 1));
        assertEquals(2, BitBoard.shift(Types.Direction.EAST.value, 1));
        // pawn_attacks_bb
        assertEquals(256 + 1024, BitBoard.pawn_attacks_bb(Types.Color.WHITE, 2));
        assertEquals(0, BitBoard.pawn_attacks_bb(Types.Color.WHITE, Types.Square.SQ_B1)); // TODO
        // line_bb
        assertEquals(0, BitBoard.line_bb(Types.Square.SQ_A1, Types.Square.SQ_A2)); // TODO
        // between_bb
        assertEquals(0, BitBoard.between_bb(Types.Square.SQ_A1, Types.Square.SQ_A3)); // TODO
        // aligned
        assertFalse(BitBoard.aligned(Types.Square.SQ_A1, Types.Square.SQ_A2, Types.Square.SQ_A3)); // TODO
        assertFalse(BitBoard.aligned(Types.Square.SQ_A1, Types.Square.SQ_B1, Types.Square.SQ_C1)); // TODO
        assertFalse(BitBoard.aligned(Types.Square.SQ_A1, Types.Square.SQ_B2, Types.Square.SQ_C3)); // TODO
        assertFalse(BitBoard.aligned(Types.Square.SQ_A1, Types.Square.SQ_B2, Types.Square.SQ_C2));
        // distanceFile
        assertEquals(0, BitBoard.distanceFile(Types.Square.SQ_A1, Types.Square.SQ_A2));
        assertEquals(1, BitBoard.distanceFile(Types.Square.SQ_A1, Types.Square.SQ_B1));
        // distanceRank
        assertEquals(1, BitBoard.distanceRank(Types.Square.SQ_A1, Types.Square.SQ_A2));
        assertEquals(0, BitBoard.distanceRank(Types.Square.SQ_A1, Types.Square.SQ_B1));
        // distanceSquare
        assertEquals(0, BitBoard.distanceSquare(Types.Square.SQ_A1, Types.Square.SQ_A3)); // TODO
        assertEquals(0, BitBoard.distanceSquare(Types.Square.SQ_A1, Types.Square.SQ_C1)); // TODO
        // edge_distance
        assertEquals(0, BitBoard.edge_distance(Types.File.FILE_A));
        assertEquals(1, BitBoard.edge_distance(Types.File.FILE_B));
        assertEquals(0, BitBoard.edge_distance(Types.File.FILE_H));
        // attacks_bb
        assertEquals(0, BitBoard.attacks_bb(Types.PieceType.KNIGHT, Types.Square.SQ_A1)); // TODO
        assertEquals(0, BitBoard.attacks_bb(Types.PieceType.BISHOP, Types.Square.SQ_A1)); // TODO
        assertEquals(0, BitBoard.attacks_bb(Types.PieceType.ROOK, Types.Square.SQ_A1)); // TODO
        assertEquals(0, BitBoard.attacks_bb(Types.PieceType.QUEEN, Types.Square.SQ_A1)); // TODO
        assertEquals(0, BitBoard.attacks_bb(Types.PieceType.KING, Types.Square.SQ_A1)); // TODO
        // attacks_bb(Types.PieceType pt, Types.Square s, long occupied)
        assertEquals(0, BitBoard.attacks_bb(Types.PieceType.KNIGHT, Types.Square.SQ_A1, 1)); // TODO
//        assertEquals(0, BitBoard.attacks_bb(Types.PieceType.BISHOP, Types.Square.SQ_A1, 1)); // TODO
//        assertEquals(0, BitBoard.attacks_bb(Types.PieceType.ROOK, Types.Square.SQ_A1, 1)); // TODO
//        assertEquals(0, BitBoard.attacks_bb(Types.PieceType.QUEEN, Types.Square.SQ_A1, 1)); // TODO
        assertEquals(0, BitBoard.attacks_bb(Types.PieceType.KING, Types.Square.SQ_A1, 1)); // TODO
        // popcount
        assertEquals(0, BitBoard.popcount(0));
        assertEquals(1, BitBoard.popcount(1));
        assertEquals(1, BitBoard.popcount(2));
        assertEquals(2, BitBoard.popcount(3));
        assertEquals(64, BitBoard.popcount(-1));
        // lsb
        for (int i = 0; i < 64; i++) {
            assertEquals(Types.Square.of(i), BitBoard.lsb((1L << i) | (1L << 63)));
        }
        // msb
        for (int i = 0; i < 64; i++) {
            assertEquals(Types.Square.of(i), BitBoard.msb((1L << i) | 1));
        }
        // least_significant_square_bb
        assertEquals(0, BitBoard.least_significant_square_bb(0));
        assertEquals(1, BitBoard.least_significant_square_bb(1));
        assertEquals(1, BitBoard.least_significant_square_bb(-1));
        assertEquals(2, BitBoard.least_significant_square_bb(2 | 4 | 8));
        assertEquals(1L << 61, BitBoard.least_significant_square_bb((1L << 61) | (1L << 62)));
        // pop_lsb
        long[] pb = new long[1];
        pb[0] = 1;
        assertEquals(Types.Square.SQ_A1, BitBoard.pop_lsb(pb));
        assertEquals(0, pb[0]);
        pb[0] = 1 + 2;
        assertEquals(Types.Square.SQ_A1, BitBoard.pop_lsb(pb));
        assertEquals(2, pb[0]);
        pb[0] = 1L << 62 | 1L << 63;
        assertEquals(Types.Square.SQ_G8, BitBoard.pop_lsb(pb));
        assertEquals(1L << 63, pb[0]);
    }

}