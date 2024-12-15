
//#ifndef BITBOARD_H_INCLUDED
//#define BITBOARD_H_INCLUDED

import java.util.Arrays;

public class BitBoard {
// bitboard.h

//#include <algorithm>
//#include <cassert>
//#include <cmath>
//#include <cstdint>
//#include <cstdlib>
//#include <string>
//
//#include "types.h"
//
//    namespace Stockfish {
//
//        namespace Bitboards {
//
//            void        init();
//            std::string pretty(Bitboard b);
//
//        }  // namespace Stockfish::Bitboards

//        constexpr Bitboard FileABB = 0x0101010101010101ULL;
//        constexpr Bitboard FileBBB = FileABB << 1;
//        constexpr Bitboard FileCBB = FileABB << 2;
//        constexpr Bitboard FileDBB = FileABB << 3;
//        constexpr Bitboard FileEBB = FileABB << 4;
//        constexpr Bitboard FileFBB = FileABB << 5;
//        constexpr Bitboard FileGBB = FileABB << 6;
//        constexpr Bitboard FileHBB = FileABB << 7;

    public static final long FileABB = 0x0101010101010101L;
    public static final long FileBBB = FileABB << 1;
    public static final long FileCBB = FileABB << 2;
    public static final long FileDBB = FileABB << 3;
    public static final long FileEBB = FileABB << 4;
    public static final long FileFBB = FileABB << 5;
    public static final long FileGBB = FileABB << 6;
    public static final long FileHBB = FileABB << 7;

//        constexpr Bitboard Rank1BB = 0xFF;
//        constexpr Bitboard Rank2BB = Rank1BB << (8 * 1);
//        constexpr Bitboard Rank3BB = Rank1BB << (8 * 2);
//        constexpr Bitboard Rank4BB = Rank1BB << (8 * 3);
//        constexpr Bitboard Rank5BB = Rank1BB << (8 * 4);
//        constexpr Bitboard Rank6BB = Rank1BB << (8 * 5);
//        constexpr Bitboard Rank7BB = Rank1BB << (8 * 6);
//        constexpr Bitboard Rank8BB = Rank1BB << (8 * 7);

    public static final long Rank1BB = 0xFF;
    public static final long Rank2BB = Rank1BB << (8 * 1);
    public static final long Rank3BB = Rank1BB << (8 * 2);
    public static final long Rank4BB = Rank1BB << (8 * 3);
    public static final long Rank5BB = Rank1BB << (8 * 4);
    public static final long Rank6BB = Rank1BB << (8 * 5);
    public static final long Rank7BB = Rank1BB << (8 * 6);
    public static final long Rank8BB = Rank1BB << (8 * 7);

//        extern uint8_t PopCnt16[1 << 16];
//        extern uint8_t SquareDistance[SQUARE_NB][SQUARE_NB];

    public static final byte[] PopCnt16 = new byte[1 << 16];
    public static final byte[][] SquareDistance = new byte[Types.SQUARE_NB][Types.SQUARE_NB];

//        extern Bitboard BetweenBB[SQUARE_NB][SQUARE_NB];
//        extern Bitboard LineBB[SQUARE_NB][SQUARE_NB];
//        extern Bitboard PseudoAttacks[PIECE_TYPE_NB][SQUARE_NB];
//        extern Bitboard PawnAttacks[COLOR_NB][SQUARE_NB];

    public static final long[][] BetweenBB = new long[Types.SQUARE_NB][Types.SQUARE_NB];
    public static final long[][] LineBB = new long[Types.SQUARE_NB][Types.SQUARE_NB];
    public static final long[][] PseudoAttacks = new long[Types.PIECE_TYPE_NB][Types.SQUARE_NB];
    public static final long[][] PawnAttacks = new long[Types.COLOR_NB][Types.SQUARE_NB];

/*
// Magic holds all magic bitboards relevant data for a single square
struct Magic {
    Bitboard  mask;
    Bitboard* attacks;
#ifndef USE_PEXT
    Bitboard magic;
    unsigned shift;
#endif

    // Compute the attack's index using the 'magic bitboards' approach
    unsigned index(Bitboard occupied) const {

#ifdef USE_PEXT
        return unsigned(pext(occupied, mask));
#else
        if (Is64Bit)
            return unsigned(((occupied & mask) * magic) >> shift);

        unsigned lo = unsigned(occupied) & unsigned(mask);
        unsigned hi = unsigned(occupied >> 32) & unsigned(mask >> 32);
        return (lo * unsigned(magic) ^ hi * unsigned(magic >> 32)) >> shift;
#endif
    }

    Bitboard attacks_bb(Bitboard occupied) const { return attacks[index(occupied)]; }
};

extern Magic Magics[SQUARE_NB][2];

constexpr Bitboard square_bb(Square s) {
    assert(is_ok(s));
    return (1ULL << s);
}
     */

    public static class Magic {
        public long mask;
        public long[] attacks;
        public int attackIndex;
        //#ifndef USE_PEXT
        public long magic;
        public int shift;
//#endif

        // Compute the attack's index using the 'magic bitboards' approach
        int index(long occupied) {
//#ifdef USE_PEXT
//                return unsigned(pext(occupied, mask));
//#else
//                if (Types.Is64Bit)
            return (int) (((occupied & mask) * magic) >>> shift);

//                int lo = (int)(occupied) & (int)(mask);
//                int hi = (int)(occupied >> 32) & (int)(mask >> 32);
//                return (lo * (int)(magic) ^ hi * (int)(magic >> 32)) >> shift;
//#endif
        }

        long attacks_bb(long occupied) {
            return attacks[index(occupied) + attackIndex];
        }
    }

    public static Magic[][] Magics = new Magic[Types.SQUARE_NB][2];


    public static long square_bb(Types.Square s) {
        return (1L << s.ordinal());
    }

    public static long square_bb(int i) {
        return (1L << i);
    }


// Overloads of bitwise operators between a Bitboard and a Square for testing
// whether a given bit is set in a bitboard, and for setting and clearing bits.

//        inline Bitboard  operator&(Bitboard b, Square s) { return b & square_bb(s); }
//        inline Bitboard  operator|(Bitboard b, Square s) { return b | square_bb(s); }
//        inline Bitboard  operator^(Bitboard b, Square s) { return b ^ square_bb(s); }
//        inline Bitboard& operator|=(Bitboard& b, Square s) { return b |= square_bb(s); }
//        inline Bitboard& operator^=(Bitboard& b, Square s) { return b ^= square_bb(s); }

    public static long and(long b, Types.Square s) { return b & square_bb(s); }
    public static long or(long b, Types.Square s) { return b | square_bb(s); }
    public static long xor(long b, Types.Square s) { return b ^ square_bb(s); }

//        inline Bitboard operator&(Square s, Bitboard b) { return b & s; }
//        inline Bitboard operator|(Square s, Bitboard b) { return b | s; }
//        inline Bitboard operator^(Square s, Bitboard b) { return b ^ s; }

    public static long and(Types.Square s, long b) { return b & s.ordinal(); }
    public static long or(Types.Square s, long b) { return b | s.ordinal(); }
    public static long xor(Types.Square s, long b) { return b ^ s.ordinal(); }


//        inline Bitboard operator|(Square s1, Square s2) { return square_bb(s1) | s2; }
    public static long or(Types.Square s1, Types.Square s2) { return square_bb(s1) | s2.ordinal(); }

//        constexpr bool more_than_one(Bitboard b) { return b & (b - 1); }
    public static boolean more_than_one(long b) { return (b & (b - 1)) != 0; }


// rank_bb() and file_bb() return a bitboard representing all the squares on
// the given file or rank.

//        constexpr Bitboard rank_bb(Rank r) { return Rank1BB << (8 * r); }
    public static long rank_bb(Types.Rank r) { return Rank1BB << (8 * r.ordinal()); }

//        constexpr Bitboard rank_bb(Square s) { return rank_bb(rank_of(s)); }
    public static long rank_bb(Types.Square s) { return rank_bb(Types.Rank.of(s)); }

//        constexpr Bitboard file_bb(File f) { return FileABB << f; }
    public static long file_bb(Types.File f) { return FileABB << f.ordinal(); }

//        constexpr Bitboard file_bb(Square s) { return file_bb(file_of(s)); }
    public static long file_bb(Types.Square s) { return file_bb(Types.File.of(s)); }


// Moves a bitboard one or two steps as specified by the direction D
//        template<Direction D>
//        constexpr Bitboard shift(Bitboard b) {
//            return D == NORTH         ? b << 8
//                    : D == SOUTH         ? b >> 8
//                    : D == NORTH + NORTH ? b << 16
//                    : D == SOUTH + SOUTH ? b >> 16
//                    : D == EAST          ? (b & ~FileHBB) << 1
//                    : D == WEST          ? (b & ~FileABB) >> 1
//                    : D == NORTH_EAST    ? (b & ~FileHBB) << 9
//                    : D == NORTH_WEST    ? (b & ~FileABB) << 7
//                    : D == SOUTH_EAST    ? (b & ~FileHBB) >> 7
//                    : D == SOUTH_WEST    ? (b & ~FileABB) >> 9
//                    : 0;
//        }
    public static long shift(int d, long b) {
        return d == Types.Direction.NORTH.value         ? b << 8
                : d == Types.Direction.SOUTH.value         ? b >>> 8
                : d == Types.Direction.NORTH.value + Types.Direction.NORTH.value ? b << 16
                : d == Types.Direction.SOUTH.value + Types.Direction.SOUTH.value ? b >>> 16
                : d == Types.Direction.EAST.value          ? (b & ~FileHBB) << 1
                : d == Types.Direction.WEST.value          ? (b & ~FileABB) >>> 1
                : d == Types.Direction.NORTH_EAST.value    ? (b & ~FileHBB) << 9
                : d == Types.Direction.NORTH_WEST.value    ? (b & ~FileABB) << 7
                : d == Types.Direction.SOUTH_EAST.value    ? (b & ~FileHBB) >>> 7
                : d == Types.Direction.SOUTH_WEST.value    ? (b & ~FileABB) >>> 9
                : 0;
    }


// Returns the squares attacked by pawns of the given color
// from the squares in the given bitboard.
//        template<Color C>
//        constexpr Bitboard pawn_attacks_bb(Bitboard b) {
//            return C == WHITE ? shift<NORTH_WEST>(b) | shift<NORTH_EAST>(b)
//                    : shift<SOUTH_WEST>(b) | shift<SOUTH_EAST>(b);
//        }
    public static long pawn_attacks_bb(Types.Color c, long b) {
        return c == Types.Color.WHITE ?
                shift(Types.Direction.NORTH_WEST.value, b) | shift(Types.Direction.NORTH_EAST.value, b)
                : shift(Types.Direction.SOUTH_WEST.value, b) | shift(Types.Direction.SOUTH_EAST.value, b);
    }

//        inline Bitboard pawn_attacks_bb(Color c, Square s) {
//
//            assert(is_ok(s));
//            return PawnAttacks[c][s];
//        }
    public static long pawn_attacks_bb(Types.Color c, Types.Square s) {
        return PawnAttacks[c.ordinal()][s.ordinal()];
    }

// Returns a bitboard representing an entire line (from board edge
// to board edge) that intersects the two given squares. If the given squares
// are not on a same file/rank/diagonal, the function returns 0. For instance,
// line_bb(SQ_C4, SQ_F7) will return a bitboard with the A2-G8 diagonal.
//        inline Bitboard line_bb(Square s1, Square s2) {
//
//            assert(is_ok(s1) && is_ok(s2));
//            return LineBB[s1][s2];
//        }
    public static long line_bb(Types.Square s1, Types.Square s2) {
        return LineBB[s1.ordinal()][s2.ordinal()];
    }

// Returns a bitboard representing the squares in the semi-open
// segment between the squares s1 and s2 (excluding s1 but including s2). If the
// given squares are not on a same file/rank/diagonal, it returns s2. For instance,
// between_bb(SQ_C4, SQ_F7) will return a bitboard with squares D5, E6 and F7, but
// between_bb(SQ_E6, SQ_F8) will return a bitboard with the square F8. This trick
// allows to generate non-king evasion moves faster: the defending piece must either
// interpose itself to cover the check or capture the checking piece.
//        inline Bitboard between_bb(Square s1, Square s2) {
//
//            assert(is_ok(s1) && is_ok(s2));
//            return BetweenBB[s1][s2];
//        }
    public static long between_bb(Types.Square s1, Types.Square s2) {
        return BetweenBB[s1.ordinal()][s2.ordinal()];
    }

// Returns true if the squares s1, s2 and s3 are aligned either on a
// straight or on a diagonal line.
//        inline bool aligned(Square s1, Square s2, Square s3) { return line_bb(s1, s2) & s3; }
    public static boolean aligned(Types.Square s1, Types.Square s2, Types.Square s3) {
        return (line_bb(s1, s2) & square_bb(s3)) != 0;
    }


// distance() functions return the distance between x and y, defined as the
// number of steps for a king in x to reach y.

//        template<typename T1 = Square>
//                inline int distance(Square x, Square y);

//        template<>
//        inline int distance<File>(Square x, Square y) {
//            return std::abs(file_of(x) - file_of(y));
//        }
    public static int distanceFile(Types.Square x, Types.Square y) {
        return Math.abs(Types.File.of(x).ordinal() - Types.File.of(y).ordinal());
    }

    //        template<>
//        inline int distance<Rank>(Square x, Square y) {
//            return std::abs(rank_of(x) - rank_of(y));
//        }
    public static int distanceRank(Types.Square x, Types.Square y) {
        return Math.abs(Types.Rank.of(x).ordinal() - Types.Rank.of(y).ordinal());
    }

//        template<>
//        inline int distance<Square>(Square x, Square y) {
//            return SquareDistance[x][y];
//        }
    public static int distanceSquare(Types.Square x, Types.Square y) {
        return SquareDistance[x.ordinal()][y.ordinal()];
    }

    public static int distanceSquare(Types.Square x, int y) {
        return SquareDistance[x.ordinal()][y];
    }

    public static int distanceSquare(int x, int y) {
        return SquareDistance[x][y];
    }

    //        inline int edge_distance(File f) { return std::min(f, File(FILE_H - f)); }
    public static int edge_distance(Types.File f) { return Math.min(f.ordinal(), Types.File.FILE_H.ordinal() - f.ordinal()); }

// Returns the pseudo attacks of the given piece type
// assuming an empty board.
//        template<PieceType Pt>
//        inline Bitboard attacks_bb(Square s) {
//
//            assert((Pt != PAWN) && (is_ok(s)));
//            return PseudoAttacks[Pt][s];
//        }
    public static long attacks_bb(Types.PieceType pt, Types.Square s) {
//        assert((Pt != PAWN) && (is_ok(s)));
        return PseudoAttacks[pt.ordinal()][s.ordinal()];
    }


// Returns the attacks by the given piece
// assuming the board is occupied according to the passed Bitboard.
// Sliding piece attacks do not continue passed an occupied square.
//        template<PieceType Pt>
//        inline Bitboard attacks_bb(Square s, Bitboard occupied) {
//
//            assert((Pt != PAWN) && (is_ok(s)));
//
//            switch (Pt)
//            {
//                case BISHOP :
//                case ROOK :
//                    return Magics[s][Pt - BISHOP].attacks_bb(occupied);
//                case QUEEN :
//                    return attacks_bb<BISHOP>(s, occupied) | attacks_bb<ROOK>(s, occupied);
//                default :
//                    return PseudoAttacks[Pt][s];
//            }
//        }
    public static long attacks_bb(Types.PieceType pt, Types.Square s, long occupied) {
    //        assert((Pt != PAWN) && (is_ok(s)));
        switch (pt) {
            case BISHOP:
            case ROOK:
                return Magics[s.ordinal()][pt.ordinal() - Types.PieceType.BISHOP.ordinal()].attacks_bb(occupied);
            case QUEEN:
                return attacks_bb(Types.PieceType.BISHOP, s,occupied) | attacks_bb(Types.PieceType.ROOK, s, occupied);
            default:
                return PseudoAttacks[pt.ordinal()][s.ordinal()];
        }
    }

// Returns the attacks by the given piece
// assuming the board is occupied according to the passed Bitboard.
// Sliding piece attacks do not continue passed an occupied square.
//        inline Bitboard attacks_bb(PieceType pt, Square s, Bitboard occupied) {
//
//            assert((pt != PAWN) && (is_ok(s)));
//
//            switch (pt)
//            {
//                case BISHOP :
//                    return attacks_bb<BISHOP>(s, occupied);
//                case ROOK :
//                    return attacks_bb<ROOK>(s, occupied);
//                case QUEEN :
//                    return attacks_bb<BISHOP>(s, occupied) | attacks_bb<ROOK>(s, occupied);
//                default :
//                    return PseudoAttacks[pt][s];
//            }
//        }
    public static long attacks_bb2(Types.PieceType pt, Types.Square s, long occupied) {
//        assert((pt != PAWN) && (is_ok(s)));
        switch (pt) {
            case BISHOP :
                return attacks_bb(Types.PieceType.BISHOP, s, occupied);
            case ROOK :
                return attacks_bb(Types.PieceType.ROOK, s, occupied);
            case QUEEN :
                return attacks_bb(Types.PieceType.BISHOP, s, occupied) | attacks_bb(Types.PieceType.ROOK, s, occupied);
            default :
                return PseudoAttacks[pt.ordinal()][s.ordinal()];
        }
    }


// Counts the number of non-zero bits in a bitboard.
//        inline int popcount(Bitboard b) {
//
//#ifndef USE_POPCNT
//
//            union {
//                Bitboard bb;
//                uint16_t u[4];
//            } v = {b};
//            return PopCnt16[v.u[0]] + PopCnt16[v.u[1]] + PopCnt16[v.u[2]] + PopCnt16[v.u[3]];
//
//#elif defined(_MSC_VER)
//
//            return int(_mm_popcnt_u64(b));
//
//#else  // Assumed gcc or compatible compiler
//
//            return __builtin_popcountll(b);
//
//#endif
//        }

    public static int popcount(long b) {
        return Long.bitCount(b);
    }


// Returns the least significant bit in a non-zero bitboard.
//        inline Square lsb(Bitboard b) {
//            assert(b);
//
//#if defined(__GNUC__)  // GCC, Clang, ICX
//
//            return Square(__builtin_ctzll(b));
//
//#elif defined(_MSC_VER)
//    #ifdef _WIN64  // MSVC, WIN64
//
//            unsigned long idx;
//            _BitScanForward64(&idx, b);
//            return Square(idx);
//
//    #else  // MSVC, WIN32
//            unsigned long idx;
//
//            if (b & 0xffffffff)
//            {
//                _BitScanForward(&idx, int32_t(b));
//                return Square(idx);
//            }
//            else
//            {
//                _BitScanForward(&idx, int32_t(b >> 32));
//                return Square(idx + 32);
//            }
//    #endif
//#else  // Compiler is neither GCC nor MSVC compatible
//    #error "Compiler not supported."
//#endif
//        }
    public static Types.Square lsb(long b) {
        for (int i = 0; i < 64; i++) {
            if ((b & 1) != 0) {
                return Types.Square.of(i);
            }
            b >>>= 1;
        }
        throw new IllegalArgumentException("BitBoard.lsb " + b);
    }


// Returns the most significant bit in a non-zero bitboard.
//        inline Square msb(Bitboard b) {
//            assert(b);
//
//#if defined(__GNUC__)  // GCC, Clang, ICX
//
//            return Square(63 ^ __builtin_clzll(b));
//
//#elif defined(_MSC_VER)
//    #ifdef _WIN64  // MSVC, WIN64
//
//            unsigned long idx;
//            _BitScanReverse64(&idx, b);
//            return Square(idx);
//
//    #else  // MSVC, WIN32
//
//            unsigned long idx;
//
//            if (b >> 32)
//            {
//                _BitScanReverse(&idx, int32_t(b >> 32));
//                return Square(idx + 32);
//            }
//            else
//            {
//                _BitScanReverse(&idx, int32_t(b));
//                return Square(idx);
//            }
//    #endif
//#else  // Compiler is neither GCC nor MSVC compatible
//    #error "Compiler not supported."
//#endif
//        }
    public static Types.Square msb(long b) {
        for (int i = 63; i >= 0; i--) {
            if ((b & (1L << 63)) != 0) {
                return Types.Square.of(i);
            }
            b <<= 1;
        }
        throw new IllegalArgumentException("BitBoard.msb " + b);
    }


// Returns the bitboard of the least significant
// square of a non-zero bitboard. It is equivalent to square_bb(lsb(bb)).
//        inline Bitboard least_significant_square_bb(Bitboard b) {
//            assert(b);
//            return b & -b;
//        }
    public static long least_significant_square_bb(long b) {
//        assert(b);
        return b & -b;
    }

// Finds and clears the least significant bit in a non-zero bitboard.
//        inline Square pop_lsb(Bitboard& b) {
//            assert(b);
//    const Square s = lsb(b);
//            b &= b - 1;
//            return s;
//        }
    public static Types.Square pop_lsb(long[] pb) {
        long b = pb[0]; // pass by reference
//        assert(b);
        Types.Square s = lsb(b);
        b &= b - 1;
        pb[0] = b;
        return s;
    }

//    }  // namespace Stockfish

// bitboard.cpp

//    RookTable[0x19000];   // To store rook attacks
//    Bitboard BishopTable[0x1480];  // To store bishop attacks

    private static long[] RookTable = new long[0x19000];   // To store rook attacks
    private static long[] BishopTable = new long[0x1480];  // To store bishop attacks

// Returns the bitboard of target square for the given step
// from the given square. If the step is off the board, returns empty bitboard.
//    Bitboard safe_destination(Square s, int step) {
//        Square to = Square(s + step);
//        return is_ok(to) && distance(s, to) <= 2 ? square_bb(to) : Bitboard(0);
//    }

    public static long safe_destination(Types.Square s, int step) {
        int to = s.ordinal() + step;
        return Types.Square.is_ok(to) && distanceSquare(s, to) <= 2
                ? square_bb(to) : 0;
    }

    public static long safe_destination(int s, int step) {
        int to = s + step;
        return Types.Square.is_ok(to) && distanceSquare(s, to) <= 2
                ? square_bb(to) : 0;
    }

// Returns an ASCII representation of a bitboard suitable
// to be printed to standard output. Useful for debugging.
//std::string Bitboards::pretty(Bitboard b) {
//
//    std::string s = "+---+---+---+---+---+---+---+---+\n";
//
//    for (Rank r = RANK_8; r >= RANK_1; --r)
//    {
//        for (File f = FILE_A; f <= FILE_H; ++f)
//            s += b & make_square(f, r) ? "| X " : "|   ";
//
//        s += "| " + std::to_string(1 + r) + "\n+---+---+---+---+---+---+---+---+\n";
//    }
//    s += "  a   b   c   d   e   f   g   h\n";
//
//    return s;
//}

    public static String pretty(long b) {
        StringBuilder s = new StringBuilder("+---+---+---+---+---+---+---+---+\n");
        for (int r = Types.Rank.RANK_8.ordinal(); r >= Types.Rank.RANK_1.ordinal(); --r) {
            for (int f = Types.File.FILE_A.ordinal(); f <= Types.File.FILE_H.ordinal(); ++f) {
                s.append((b & Types.Square.of(f, r).ordinal()) != 0 ? "| X " : "|   ");
            }
            s.append("| ");
            s.append(1 + r);
            s.append("\n+---+---+---+---+---+---+---+---+\n");
        }
        s.append("  a   b   c   d   e   f   g   h\n");
        return s.toString();
    }

    /*
// Initializes various bitboard tables. It is called at
// startup and relies on global objects to be already zero-initialized.
void Bitboards::init() {

    for (unsigned i = 0; i < (1 << 16); ++i)
        PopCnt16[i] = uint8_t(std::bitset<16>(i).count());

    for (Square s1 = SQ_A1; s1 <= SQ_H8; ++s1)
        for (Square s2 = SQ_A1; s2 <= SQ_H8; ++s2)
            SquareDistance[s1][s2] = std::max(distance<File>(s1, s2), distance<Rank>(s1, s2));

    init_magics(ROOK, RookTable, Magics);
    init_magics(BISHOP, BishopTable, Magics);

    for (Square s1 = SQ_A1; s1 <= SQ_H8; ++s1)
    {
        PawnAttacks[WHITE][s1] = pawn_attacks_bb<WHITE>(square_bb(s1));
        PawnAttacks[BLACK][s1] = pawn_attacks_bb<BLACK>(square_bb(s1));

        for (int step : {-9, -8, -7, -1, 1, 7, 8, 9})
            PseudoAttacks[KING][s1] |= safe_destination(s1, step);

        for (int step : {-17, -15, -10, -6, 6, 10, 15, 17})
            PseudoAttacks[KNIGHT][s1] |= safe_destination(s1, step);

        PseudoAttacks[QUEEN][s1] = PseudoAttacks[BISHOP][s1] = attacks_bb<BISHOP>(s1, 0);
        PseudoAttacks[QUEEN][s1] |= PseudoAttacks[ROOK][s1]  = attacks_bb<ROOK>(s1, 0);

        for (PieceType pt : {BISHOP, ROOK})
            for (Square s2 = SQ_A1; s2 <= SQ_H8; ++s2)
            {
                if (PseudoAttacks[pt][s1] & s2)
                {
                    LineBB[s1][s2] = (attacks_bb(pt, s1, 0) & attacks_bb(pt, s2, 0)) | s1 | s2;
                    BetweenBB[s1][s2] =
                      (attacks_bb(pt, s1, square_bb(s2)) & attacks_bb(pt, s2, square_bb(s1)));
                }
                BetweenBB[s1][s2] |= s2;
            }
    }
}
     */

    public static void init() {
        for (int i = 0; i < (1 << 16); ++i)
            PopCnt16[i] = (byte) Integer.bitCount(i);
        for (int i1 = Types.Square.SQ_A1.ordinal(); i1 <= Types.Square.SQ_H8.ordinal(); ++i1) {
            Types.Square s1 = Types.Square.of(i1);
            for (int i2 = Types.Square.SQ_A1.ordinal(); i2 <= Types.Square.SQ_H8.ordinal(); ++i2) {
                Types.Square s2 = Types.Square.of(i2);
                SquareDistance[i1][i2] = (byte) Math.max(distanceFile(s1, s2), distanceRank(s1, s2));
            }
        }
        init_magics(Types.PieceType.ROOK, RookTable, Magics);
        init_magics(Types.PieceType.BISHOP, BishopTable, Magics);
        int[] kingSteps = new int[]{-9, -8, -7, -1, 1, 7, 8, 9};
        int[] knightSteps = new int[]{-17, -15, -10, -6, 6, 10, 15, 17};
        Types.PieceType[] bishopRook = new Types.PieceType[]{Types.PieceType.BISHOP, Types.PieceType.ROOK};
        for (int i1 = Types.Square.SQ_A1.ordinal(); i1 <= Types.Square.SQ_H8.ordinal(); ++i1) {
            Types.Square s1 = Types.Square.of(i1);
            PawnAttacks[Types.Color.WHITE.ordinal()][i1] = pawn_attacks_bb(Types.Color.WHITE, square_bb(s1));
            PawnAttacks[Types.Color.BLACK.ordinal()][i1] = pawn_attacks_bb(Types.Color.BLACK, square_bb(s1));
            for (int step : kingSteps) {
                PseudoAttacks[Types.PieceType.KING.ordinal()][i1] |= safe_destination(s1, step);
            }
            for (int step : knightSteps) {
                PseudoAttacks[Types.PieceType.KNIGHT.ordinal()][i1] |= safe_destination(s1, step);
            }
            PseudoAttacks[Types.PieceType.QUEEN.ordinal()][i1] = PseudoAttacks[Types.PieceType.BISHOP.ordinal()][i1] =
                    attacks_bb(Types.PieceType.BISHOP, s1, 0);
            PseudoAttacks[Types.PieceType.QUEEN.ordinal()][i1] |= PseudoAttacks[Types.PieceType.ROOK.ordinal()][i1] =
                    attacks_bb(Types.PieceType.ROOK, s1, 0);
            for (Types.PieceType pt : bishopRook) {
                for (int i2 = Types.Square.SQ_A1.ordinal(); i2 <= Types.Square.SQ_H8.ordinal(); ++i2) {
                    Types.Square s2 = Types.Square.of(i2);
                    if ((PseudoAttacks[pt.ordinal()][i1] & square_bb(i2)) != 0) {
                        LineBB[i1][i2] = (attacks_bb(pt, s1, 0) & attacks_bb(pt, s2, 0))
                                | square_bb(i1) | square_bb(i2);
                        BetweenBB[i1][i2] =
                                (attacks_bb(pt, s1, square_bb(s2)) & attacks_bb(pt, s2, square_bb(s1)));
                    }
                    BetweenBB[i1][i2] |= square_bb(i2);
                }
            }
        }
    }

    /*
Bitboard sliding_attack(PieceType pt, Square sq, Bitboard occupied) {

    Bitboard  attacks             = 0;
    Direction RookDirections[4]   = {NORTH, SOUTH, EAST, WEST};
    Direction BishopDirections[4] = {NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST};

    for (Direction d : (pt == ROOK ? RookDirections : BishopDirections))
    {
        Square s = sq;
        while (safe_destination(s, d))
        {
            attacks |= (s += d);
            if (occupied & s)
            {
                break;
            }
        }
    }

    return attacks;
}
     */

    private static Types.Direction[] RookDirections =
            new Types.Direction[]{Types.Direction.NORTH, Types.Direction.SOUTH, Types.Direction.EAST, Types.Direction.WEST};
    private static Types.Direction[] BishopDirections =
            new Types.Direction[]{Types.Direction.NORTH_EAST, Types.Direction.SOUTH_EAST, Types.Direction.SOUTH_WEST, Types.Direction.NORTH_WEST};

    public static long sliding_attack(Types.PieceType pt, Types.Square sq, long occupied) {
        long attacks = 0;
        for (Types.Direction d : (pt == Types.PieceType.ROOK ? RookDirections : BishopDirections)) {
            int s = sq.ordinal();
            while (safe_destination(s, d.value) != 0) {
                long square_bb = square_bb(s += d.value);
                attacks |= square_bb;
                if ((occupied & square_bb) != 0) {
                    break;
                }
            }
        }
        return attacks;
    }

    /*
// Computes all rook and bishop attacks at startup. Magic
// bitboards are used to look up attacks of sliding pieces. As a reference see
// https://www.chessprogramming.org/Magic_Bitboards. In particular, here we use
// the so called "fancy" approach.
void init_magics(PieceType pt, Bitboard table[], Magic magics[][2]) {

#ifndef USE_PEXT
    // Optimal PRNG seeds to pick the correct magics in the shortest time
    int seeds[][RANK_NB] = {{8977, 44560, 54343, 38998, 5731, 95205, 104912, 17020},
                            {728, 10316, 55013, 32803, 12281, 15100, 16645, 255}};

    Bitboard occupancy[4096];
    int      epoch[4096] = {}, cnt = 0;
#endif
    Bitboard reference[4096];
    int      size = 0;

    for (Square s = SQ_A1; s <= SQ_H8; ++s)
    {
        // Board edges are not considered in the relevant occupancies
        Bitboard edges = ((Rank1BB | Rank8BB) & ~rank_bb(s)) | ((FileABB | FileHBB) & ~file_bb(s));

        // Given a square 's', the mask is the bitboard of sliding attacks from
        // 's' computed on an empty board. The index must be big enough to contain
        // all the attacks for each possible subset of the mask and so is 2 power
        // the number of 1s of the mask. Hence we deduce the size of the shift to
        // apply to the 64 or 32 bits word to get the index.
        Magic& m = magics[s][pt - BISHOP];
        m.mask   = sliding_attack(pt, s, 0) & ~edges;
#ifndef USE_PEXT
        m.shift = (Is64Bit ? 64 : 32) - popcount(m.mask);
#endif
        // Set the offset for the attacks table of the square. We have individual
        // table sizes for each square with "Fancy Magic Bitboards".
        m.attacks = s == SQ_A1 ? table : magics[s - 1][pt - BISHOP].attacks + size;
        size      = 0;

        // Use Carry-Rippler trick to enumerate all subsets of masks[s] and
        // store the corresponding sliding attack bitboard in reference[].
        Bitboard b = 0;
        do
        {
#ifndef USE_PEXT
            occupancy[size] = b;
#endif
            reference[size] = sliding_attack(pt, s, b);

            if (HasPext)
                m.attacks[pext(b, m.mask)] = reference[size];

            size++;
            b = (b - m.mask) & m.mask;
        } while (b);

#ifndef USE_PEXT
        PRNG rng(seeds[Is64Bit][rank_of(s)]);

        // Find a magic for square 's' picking up an (almost) random number
        // until we find the one that passes the verification test.
        for (int i = 0; i < size;)
        {
            for (m.magic = 0; popcount((m.magic * m.mask) >> 56) < 6;)
                m.magic = rng.sparse_rand<Bitboard>();

            // A good magic must map every possible occupancy to an index that
            // looks up the correct sliding attack in the attacks[s] database.
            // Note that we build up the database for square 's' as a side
            // effect of verifying the magic. Keep track of the attempt count
            // and save it in epoch[], little speed-up trick to avoid resetting
            // m.attacks[] after every failed attempt.
            for (++cnt, i = 0; i < size; ++i)
            {
                unsigned idx = m.index(occupancy[i]);

                if (epoch[idx] < cnt)
                {
                    epoch[idx]     = cnt;
                    m.attacks[idx] = reference[i];
                }
                else if (m.attacks[idx] != reference[i])
                    break;
            }
        }
#endif
    }
}
     */

    public static void init_magics(Types.PieceType pt, long[] table, Magic[][] magics) {
        // Optimal PRNG seeds to pick the correct magics in the shortest time
        int[][] seeds = {
                {8977, 44560, 54343, 38998, 5731, 95205, 104912, 17020},
                {728, 10316, 55013, 32803, 12281, 15100, 16645, 255}
        };
        long[] occupancy = new long[4096];
        int[] epoch = new int[4096];
        int cnt = 0;
        long[] reference = new long[4096];
        int size = 0;
        Misc.PRNG rng = new Misc.PRNG(0);
        for (int si = Types.Square.SQ_A1.ordinal(); si <= Types.Square.SQ_H8.ordinal(); ++si) {
            Types.Square s = Types.Square.of(si);
            // Board edges are not considered in the relevant occupancies
            long edges = ((Rank1BB | Rank8BB) & ~rank_bb(s)) | ((FileABB | FileHBB) & ~file_bb(s));
            // Given a square 's', the mask is the bitboard of sliding attacks from
            // 's' computed on an empty board. The index must be big enough to contain
            // all the attacks for each possible subset of the mask and so is 2 power
            // the number of 1s of the mask. Hence we deduce the size of the shift to
            // apply to the 64 or 32 bits word to get the index.
            Magic m = new Magic();
            magics[si][pt.ordinal() - Types.PieceType.BISHOP.ordinal()] = m;
            long sliding_attack = sliding_attack(pt, s, 0);
            m.mask = sliding_attack & ~edges;
            m.shift = 64 - popcount(m.mask);
            // Set the offset for the attacks table of the square. We have individual
            // table sizes for each square with "Fancy Magic Bitboards".
            m.attacks = table;
            if (s == Types.Square.SQ_A1) {
                m.attackIndex = 0;
            } else {
                Magic prev = magics[si - 1][pt.ordinal() - Types.PieceType.BISHOP.ordinal()];
                m.attackIndex = prev.attackIndex + size;
            }
            size = 0;
            // Use Carry-Rippler trick to enumerate all subsets of masks[s] and
            // store the corresponding sliding attack bitboard in reference[].
            long b = 0;
            do
            {
                occupancy[size] = b;
                reference[size] = sliding_attack(pt, s, b);
                size++;
                b = (b - m.mask) & m.mask;
            } while (b != 0);
            rng.s = seeds[1][Types.Rank.of(s).ordinal()];

            // Find a magic for square 's' picking up an (almost) random number
            // until we find the one that passes the verification test.
            for (int i = 0; i < size;) {
                for (m.magic = 0; popcount((m.magic * m.mask) >>> 56) < 6;) {
                    m.magic = rng.sparse_rand();
                }
                // A good magic must map every possible occupancy to an index that
                // looks up the correct sliding attack in the attacks[s] database.
                // Note that we build up the database for square 's' as a side
                // effect of verifying the magic. Keep track of the attempt count
                // and save it in epoch[], little speed-up trick to avoid resetting
                // m.attacks[] after every failed attempt.
                for (++cnt, i = 0; i < size; ++i) {
                    int idx = m.index(occupancy[i]);
                    if (epoch[idx] < cnt) {
                        epoch[idx]     = cnt;
                        m.attacks[idx + m.attackIndex] = reference[i];
                    } else if (m.attacks[idx + m.attackIndex] != reference[i])
                        break;
                }
            }
        }
    }

}

//#endif  // #ifndef BITBOARD_H_INCLUDED
