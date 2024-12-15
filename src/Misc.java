import java.util.concurrent.atomic.AtomicLong;

public class Misc {

    /*
using TimePoint = std::chrono::milliseconds::rep;  // A value in milliseconds
static_assert(sizeof(TimePoint) == sizeof(int64_t), "TimePoint should be 64 bits");
inline TimePoint now() {
    return std::chrono::duration_cast<std::chrono::milliseconds>(
             std::chrono::steady_clock::now().time_since_epoch())
      .count();
}
     */
    public static long now() {
        return System.currentTimeMillis();
    }

    /*
inline std::vector<std::string_view> split(std::string_view s, std::string_view delimiter) {
    std::vector<std::string_view> res;

    if (s.empty())
        return res;

    size_t begin = 0;
    for (;;)
    {
        const size_t end = s.find(delimiter, begin);
        if (end == std::string::npos)
            break;

        res.emplace_back(s.substr(begin, end - begin));
        begin = end + delimiter.size();
    }

    res.emplace_back(s.substr(begin));

    return res;
}
     */
     public static String[] split(String s, String delimiter) {
         return s.split(delimiter);
     }

     /*
inline uint64_t mul_hi64(uint64_t a, uint64_t b) {
#if defined(__GNUC__) && defined(IS_64BIT)
    __extension__ using uint128 = unsigned __int128;
    return (uint128(a) * uint128(b)) >> 64;
#else
    uint64_t aL = uint32_t(a), aH = a >> 32;
    uint64_t bL = uint32_t(b), bH = b >> 32;
    uint64_t c1 = (aL * bL) >> 32;
    uint64_t c2 = aH * bL + c1;
    uint64_t c3 = aL * bH + uint32_t(c2);
    return aH * bH + (c2 >> 32) + (c3 >> 32);
#endif
}
      */

    public static long mul_hi64(long a, long b) {
        long aL = (int)a, aH = a >> 32;
        long bL = (int)b, bH = b >> 32;
        long c1 = (aL * bL) >> 32;
        long c2 = aH * bL + c1;
        long c3 = aL * bH + (int)c2;
        return aH * bH + (c2 >> 32) + (c3 >> 32);
    }

    public static void log(String s) { // TODO
        System.out.println(s);
    }

    /*
// Returns the full name of the current Stockfish version.
//
// For local dev compiles we try to append the commit SHA and
// commit date from git. If that fails only the local compilation
// date is set and "nogit" is specified:
//      Stockfish dev-YYYYMMDD-SHA
//      or
//      Stockfish dev-YYYYMMDD-nogit
//
// For releases (non-dev builds) we only include the version number:
//      Stockfish version
std::string engine_version_info() {
    std::stringstream ss;
    ss << "Stockfish " << version << std::setfill('0');

    if constexpr (version == "dev")
    {
        ss << "-";
#ifdef GIT_DATE
        ss << stringify(GIT_DATE);
#else
        constexpr std::string_view months("Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec");

        std::string       month, day, year;
        std::stringstream date(__DATE__);  // From compiler, format is "Sep 21 2008"

        date >> month >> day >> year;
        ss << year << std::setw(2) << std::setfill('0') << (1 + months.find(month) / 4)
           << std::setw(2) << std::setfill('0') << day;
#endif

        ss << "-";

#ifdef GIT_SHA
        ss << stringify(GIT_SHA);
#else
        ss << "nogit";
#endif
    }

    return ss.str();
}

std::string engine_info(bool to_uci) {
    return engine_version_info() + (to_uci ? "\nid author " : " by ")
         + "the Stockfish developers (see AUTHORS file)";
}
     */

    public static String engine_version_info() {
        return "JockFish 1";
    }

    public static String engine_info(boolean to_uci) {
        return engine_version_info() + (to_uci ? "\nid author " : " by ")
                + "teh fuerte";
    }

    /*
// Debug functions used mainly to collect run-time statistics
constexpr int MaxDebugSlots = 32;

namespace {

template<size_t N>
struct DebugInfo {
    std::atomic<int64_t> data[N] = {0};

    constexpr std::atomic<int64_t>& operator[](int index) { return data[index]; }
};

struct DebugExtremes: public DebugInfo<3> {
    DebugExtremes() {
        data[1] = std::numeric_limits<int64_t>::min();
        data[2] = std::numeric_limits<int64_t>::max();
    }
};

DebugInfo<2>  hit[MaxDebugSlots];
DebugInfo<2>  mean[MaxDebugSlots];
DebugInfo<3>  stdev[MaxDebugSlots];
DebugInfo<6>  correl[MaxDebugSlots];
DebugExtremes extremes[MaxDebugSlots];

}  // namespace

void dbg_hit_on(bool cond, int slot) {

    ++hit[slot][0];
    if (cond)
        ++hit[slot][1];
}

void dbg_mean_of(int64_t value, int slot) {

    ++mean[slot][0];
    mean[slot][1] += value;
}

void dbg_stdev_of(int64_t value, int slot) {

    ++stdev[slot][0];
    stdev[slot][1] += value;
    stdev[slot][2] += value * value;
}

void dbg_extremes_of(int64_t value, int slot) {
    ++extremes[slot][0];

    int64_t current_max = extremes[slot][1].load();
    while (current_max < value && !extremes[slot][1].compare_exchange_weak(current_max, value))
    {}

    int64_t current_min = extremes[slot][2].load();
    while (current_min > value && !extremes[slot][2].compare_exchange_weak(current_min, value))
    {}
}

void dbg_correl_of(int64_t value1, int64_t value2, int slot) {

    ++correl[slot][0];
    correl[slot][1] += value1;
    correl[slot][2] += value1 * value1;
    correl[slot][3] += value2;
    correl[slot][4] += value2 * value2;
    correl[slot][5] += value1 * value2;
}

void dbg_print() {

    int64_t n;
    auto    E   = [&n](int64_t x) { return double(x) / n; };
    auto    sqr = [](double x) { return x * x; };

    for (int i = 0; i < MaxDebugSlots; ++i)
        if ((n = hit[i][0]))
            std::cerr << "Hit #" << i << ": Total " << n << " Hits " << hit[i][1]
                      << " Hit Rate (%) " << 100.0 * E(hit[i][1]) << std::endl;

    for (int i = 0; i < MaxDebugSlots; ++i)
        if ((n = mean[i][0]))
        {
            std::cerr << "Mean #" << i << ": Total " << n << " Mean " << E(mean[i][1]) << std::endl;
        }

    for (int i = 0; i < MaxDebugSlots; ++i)
        if ((n = stdev[i][0]))
        {
            double r = sqrt(E(stdev[i][2]) - sqr(E(stdev[i][1])));
            std::cerr << "Stdev #" << i << ": Total " << n << " Stdev " << r << std::endl;
        }

    for (int i = 0; i < MaxDebugSlots; ++i)
        if ((n = extremes[i][0]))
        {
            std::cerr << "Extremity #" << i << ": Total " << n << " Min " << extremes[i][2]
                      << " Max " << extremes[i][1] << std::endl;
        }

    for (int i = 0; i < MaxDebugSlots; ++i)
        if ((n = correl[i][0]))
        {
            double r = (E(correl[i][5]) - E(correl[i][1]) * E(correl[i][3]))
                     / (sqrt(E(correl[i][2]) - sqr(E(correl[i][1])))
                        * sqrt(E(correl[i][4]) - sqr(E(correl[i][3]))));
            std::cerr << "Correl. #" << i << ": Total " << n << " Coefficient " << r << std::endl;
        }
}
     */

    static class DebugInfo {
        AtomicLong[] data;

        DebugInfo(int n) {
            data = new AtomicLong[n];
            for (int i = 0; i < n; i++) {
                data[i] = new AtomicLong(0);
            }
        }

        static DebugInfo[] create(int m, int n) {
            DebugInfo[] infos = new DebugInfo[m];
            for (int i = 0; i < m; i++) {
                infos[i] =  new DebugInfo(n);
            }
            return infos;
        }

    }

    static final int MaxDebugSlots = 32;
    static DebugInfo[] hit = DebugInfo.create(MaxDebugSlots, 2);
    static DebugInfo[] mean = DebugInfo.create(MaxDebugSlots, 2);
    static DebugInfo[] stdev = DebugInfo.create(MaxDebugSlots, 3);
    static DebugInfo[] correl = DebugInfo.create(MaxDebugSlots, 6);
    static DebugInfo[] extremes = DebugInfo.create(MaxDebugSlots, 3);
    static {
        for (int i = 0; i < MaxDebugSlots; i++) {
            extremes[i].data[1].set(Long.MIN_VALUE);
            extremes[i].data[2].set(Long.MAX_VALUE);
        }
    }

    public static void dbg_hit_on(boolean cond, int slot) {
        hit[slot].data[0].incrementAndGet();
        if (cond)
            hit[slot].data[1].incrementAndGet();
    }

    public static void dbg_mean_of(long value, int slot) {
        mean[slot].data[0].incrementAndGet();
        mean[slot].data[1].addAndGet(value);
    }

    public static void dbg_stdev_of(long value, int slot) {
        stdev[slot].data[0].incrementAndGet();
        stdev[slot].data[1].addAndGet(value);
        stdev[slot].data[2].addAndGet(value * value);
    }

    public static void dbg_extremes_of(long value, int slot) {
        extremes[slot].data[0].incrementAndGet();
        for (;;) {
            long current_max = extremes[slot].data[1].get();
            if (current_max >= value) {
                break;
            }
            if (extremes[slot].data[1].compareAndSet(current_max, value)) {
                break;
            }
        }
        for (;;) {
            long current_min = extremes[slot].data[2].get();
            if (current_min <= value) {
                break;
            }
            if (extremes[slot].data[2].compareAndSet(current_min, value)) {
                break;
            }
        }
    }

    void dbg_correl_of(long value1, long value2, int slot) {
        correl[slot].data[0].incrementAndGet();
        correl[slot].data[1].addAndGet(value1);
        correl[slot].data[2].addAndGet(value1 * value1);
        correl[slot].data[3].addAndGet(value2);
        correl[slot].data[4].addAndGet(value2 * value2);
        correl[slot].data[5].addAndGet(value1 * value2);
    }

    /*
// xorshift64star Pseudo-Random Number Generator
// This class is based on original code written and dedicated
// to the public domain by Sebastiano Vigna (2014).
// It has the following characteristics:
//
//  -  Outputs 64-bit numbers
//  -  Passes Dieharder and SmallCrush test batteries
//  -  Does not require warm-up, no zeroland to escape
//  -  Internal state is a single 64-bit integer
//  -  Period is 2^64 - 1
//  -  Speed: 1.60 ns/call (Core i7 @3.40GHz)
//
// For further analysis see
//   <http://vigna.di.unimi.it/ftp/papers/xorshift.pdf>

class PRNG {

    uint64_t s;

    uint64_t rand64() {

        s ^= s >> 12, s ^= s << 25, s ^= s >> 27;
        return s * 2685821657736338717LL;
    }

   public:
    PRNG(uint64_t seed) :
        s(seed) {
        assert(seed);
    }

    template<typename T>
    T rand() {
        return T(rand64());
    }

    // Special generator used to fast init magic numbers.
    // Output values only have 1/8th of their bits set on average.
    template<typename T>
    T sparse_rand() {
        return T(rand64() & rand64() & rand64());
    }
};
     */

    public static class PRNG {

        public long s;

        public long rand64() {
            s ^= s >>> 12;
            s ^= s << 25;
            s ^= s >>> 27;
            return s * 2685821657736338717L;
        }

        public PRNG(long seed) {
            s = seed;
        }

        // Special generator used to fast init magic numbers.
        // Output values only have 1/8th of their bits set on average.
        long sparse_rand() {
            return rand64() & rand64() & rand64();
        }

    }

}
