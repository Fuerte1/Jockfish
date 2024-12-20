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
        assertEquals(0b11111111L, BitBoard.rank_bb(Types.Rank.RANK_1));
        assertEquals(0b11111111_00000000L, BitBoard.rank_bb(Types.Rank.RANK_2));
        // rank_bb
        assertEquals(0b11111111L, BitBoard.rank_bb(Types.Square.SQ_A1));
        assertEquals(0b11111111_00000000L, BitBoard.rank_bb(Types.Square.SQ_A2));
        // file_bb
        assertEquals(0b00000001_00000001_00000001_00000001_00000001_00000001_00000001_00000001L, BitBoard.file_bb(Types.File.FILE_A));
        assertEquals(0b00000010_00000010_00000010_00000010_00000010_00000010_00000010_00000010L, BitBoard.file_bb(Types.File.FILE_B));
        // file_bb
        assertEquals(0b00000001_00000001_00000001_00000001_00000001_00000001_00000001_00000001L, BitBoard.file_bb(Types.Square.SQ_A1));
        assertEquals(0b00000010_00000010_00000010_00000010_00000010_00000010_00000010_00000010L, BitBoard.file_bb(Types.Square.SQ_B1));
        // shift
        assertEquals(0b00000001_00000000L, BitBoard.shift(Types.Direction.NORTH.value, 1));
        assertEquals(0, BitBoard.shift(Types.Direction.SOUTH.value, 1));
        assertEquals(0b00000001_00000000_00000000L, BitBoard.shift(Types.Direction.NORTH.value * 2, 1));
        assertEquals(0b10L, BitBoard.shift(Types.Direction.EAST.value, 1));
        // pawn_attacks_bb
        assertEquals(0b00000101_00000000L, BitBoard.pawn_attacks_bb(Types.Color.WHITE, 2));
        assertEquals(0b0101_00000000_00000000L, BitBoard.pawn_attacks_bb(Types.Color.WHITE, Types.Square.SQ_B2));
        assertEquals(0b00101_00000000_00000000_00000000_00000000_00000000L,
                BitBoard.pawn_attacks_bb(Types.Color.BLACK, Types.Square.SQ_B7));
        // line_bb
        assertEquals(0b11111111L,
                BitBoard.line_bb(Types.Square.SQ_A1, Types.Square.SQ_B1));
        assertEquals(0b00000001_00000001_00000001_00000001_00000001_00000001_00000001_00000001L,
                BitBoard.line_bb(Types.Square.SQ_A1, Types.Square.SQ_A2));
        assertEquals(0b10000000_01000000_00100000_00010000_00001000_00000100_00000010_00000001L,
                BitBoard.line_bb(Types.Square.SQ_A1, Types.Square.SQ_B2));
        // between_bb
        // between_bb(SQ_C4, SQ_F7) will return a bitboard with squares D5, E6 and F7, but
        assertEquals(0b00100000_00010000_00001000_00000000_00000000_00000000_00000000L,
                BitBoard.between_bb(Types.Square.SQ_C4, Types.Square.SQ_F7));
        // between_bb(SQ_E6, SQ_F8) will return a bitboard with the square F8. This trick
        assertEquals(0b00100000_00000000_00000000_00000000_00000000_00000000_00000000_00000000L,
                BitBoard.between_bb(Types.Square.SQ_E6, Types.Square.SQ_F8));
        assertEquals(0b0001_00000001_00000000L, BitBoard.between_bb(Types.Square.SQ_A1, Types.Square.SQ_A3));
        // aligned
        assertTrue(BitBoard.aligned(Types.Square.SQ_A1, Types.Square.SQ_A2, Types.Square.SQ_A3));
        assertTrue(BitBoard.aligned(Types.Square.SQ_A1, Types.Square.SQ_B1, Types.Square.SQ_C1));
        assertTrue(BitBoard.aligned(Types.Square.SQ_A1, Types.Square.SQ_B2, Types.Square.SQ_C3));
        assertFalse(BitBoard.aligned(Types.Square.SQ_A1, Types.Square.SQ_B2, Types.Square.SQ_C2));
        // distanceFile
        assertEquals(0, BitBoard.distanceFile(Types.Square.SQ_A1, Types.Square.SQ_A2));
        assertEquals(1, BitBoard.distanceFile(Types.Square.SQ_A1, Types.Square.SQ_B1));
        // distanceRank
        assertEquals(1, BitBoard.distanceRank(Types.Square.SQ_A1, Types.Square.SQ_A2));
        assertEquals(0, BitBoard.distanceRank(Types.Square.SQ_A1, Types.Square.SQ_B1));
        // distanceSquare
        assertEquals(2, BitBoard.distanceSquare(Types.Square.SQ_A1, Types.Square.SQ_A3));
        assertEquals(2, BitBoard.distanceSquare(Types.Square.SQ_A1, Types.Square.SQ_C1));
        // edge_distance
        assertEquals(0, BitBoard.edge_distance(Types.File.FILE_A));
        assertEquals(1, BitBoard.edge_distance(Types.File.FILE_B));
        assertEquals(0, BitBoard.edge_distance(Types.File.FILE_H));
        // attacks_bb
        assertEquals(0b0010_00000100_00000000L, BitBoard.attacks_bb(Types.PieceType.KNIGHT, Types.Square.SQ_A1));
        assertEquals(0b10000000_01000000_00100000_00010000_00001000_00000100_00000010_00000000L,
                BitBoard.attacks_bb(Types.PieceType.BISHOP, Types.Square.SQ_A1));
        assertEquals(0b00000001_00000001_00000001_00000001_00000001_00000001_00000001_11111110L,
                BitBoard.attacks_bb(Types.PieceType.ROOK, Types.Square.SQ_A1));
        assertEquals(0b10000001_01000001_00100001_00010001_00001001_00000101_00000011_11111110L,
                BitBoard.attacks_bb(Types.PieceType.QUEEN, Types.Square.SQ_A1));
        assertEquals(0b0011_00000010L, BitBoard.attacks_bb(Types.PieceType.KING, Types.Square.SQ_A1));
        // attacks_bb(Types.PieceType pt, Types.Square s, long occupied)
        assertEquals(0b0010_00000100_00000000L,
                BitBoard.attacks_bb(Types.PieceType.KNIGHT, Types.Square.SQ_A1, 1));
        assertEquals(0b10000000_01000000_00100000_00010000_00001000_00000100_00000010_00000000L,
                BitBoard.attacks_bb(Types.PieceType.BISHOP, Types.Square.SQ_A1, 1));
        assertEquals(0b00000001_00000001_00000001_00000001_00000001_00000001_00000001_11111110L,
                BitBoard.attacks_bb(Types.PieceType.ROOK, Types.Square.SQ_A1, 1));
        assertEquals(0b10000001_01000001_00100001_00010001_00001001_00000101_00000011_11111110L,
                BitBoard.attacks_bb(Types.PieceType.QUEEN, Types.Square.SQ_A1, 1));
        assertEquals(0b0011_00000010L, BitBoard.attacks_bb(Types.PieceType.KING, Types.Square.SQ_A1, 1));
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
        // pretty
        assertEquals(
                "+---+---+---+---+---+---+---+---+\n" +
                "|   |   |   |   |   |   |   |   | 8\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "|   |   |   |   |   |   |   |   | 7\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "|   |   |   |   |   |   |   |   | 6\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "|   |   |   |   |   |   |   |   | 5\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "|   |   |   |   |   |   |   |   | 4\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "|   |   |   |   |   |   |   |   | 3\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "|   |   |   |   |   |   |   |   | 2\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "|   |   |   |   |   |   |   |   | 1\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "  a   b   c   d   e   f   g   h\n", BitBoard.pretty(0));
        assertEquals(
                "+---+---+---+---+---+---+---+---+\n" +
                "| X | X | X | X | X | X | X | X | 8\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "| X | X | X | X | X | X | X | X | 7\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "| X | X | X | X | X | X | X | X | 6\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "| X | X | X | X | X | X | X | X | 5\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "| X | X | X | X | X | X | X | X | 4\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "| X | X | X | X | X | X | X | X | 3\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "| X | X | X | X | X | X | X | X | 2\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "| X | X | X | X | X | X | X | X | 1\n" +
                "+---+---+---+---+---+---+---+---+\n" +
                "  a   b   c   d   e   f   g   h\n", BitBoard.pretty(-1L));
    }

}