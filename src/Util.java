import java.util.Random;

public class Util {

    private static Random random;

    /**
     * Uses the Miller-Rabin algorithm to determine the value passed is a true prime. This test is best run more than once
     * with different bases ('a') each time to get better repesentation on whether the prime is truly a prime or a strong psudeoprime.
     *
     * @param a     int: the value you want to use test the primality of your supposed 'prime'. 'a' is implied to be postive and less than 32 bits.
     * @param prime long: the supposed prime value. Implied to be a positive value, and that the value is <= 32bits.
     * @return a boolean is returned. True means that based on the 'a' passed this number is not a composite. if a false is
     * returned that means the prime given is a composite value.
     */
    public static boolean millerRabin(int a, long prime) {
        long q = prime - 1;
        int s = 0;
        while (q % 2 == 0) {
            s++;
            q = q / 2;
        }
        if (modularexponetiation(a, q, prime) == 1)
            return true;
        long total = 0;
        for (int i = 0; i < s && total != prime - 1; i++) {
            int power = (int) Math.pow(2, i);
            total = modularexponetiation(a, power * q, prime);
        }
        if (total == prime - 1)
            return true;
        return false;
    }

    /**
     * Creates the random number generation with the seed that was passed.
     *
     * @param seed long: a seed that the user gave to the program.
     */
    public static void buildRandomGenerator(long seed) {
        random = new Random(seed);
    }

    /**
     * The Generator generates a random number, of type long. This makes sure that the value returned is 1. postive and
     * 2. that the value is greater than 2^30.
     *
     * @return a long created by the generator.
     */
    public static long getNextNumber() {
        long value = random.nextInt();
        while (value < Math.pow(2, 30))
            value = random.nextInt();
        if (value < 0)
            value *= -1;
        return value;
    }

    /**
     * The idea of this is that something like 2^3 mod n is  (2^2 * 2^1)mod n, etc. You break it down to its smallest parts
     * so that you can do large exponents  in very small steps.
     * <p>
     * Got the code from here:
     * https://www.geeksforgeeks.org/modular-exponentiation-power-in-modular-arithmetic/
     * <p>
     * My original attempt worked, it however did it in N time (n being the size of the exponenet). While that worked it
     * takes too long when your N is large. This algorithim works in logN time, which is much faster.
     *
     * @param base     long: this is int value that is getting raised by the exponent. Value has to be less than 32 bits, and positive
     * @param exponent long: the exponent that the base is getting raised too. Value has to be less than 32 bits, and positive
     * @param modulus  long: the modulus to make this whole thing work. Value has to be less than 32 bits, and positive
     * @return returns the value of base^exponent mod modulus
     */
    public static long modularexponetiation(long base, long exponent, long modulus) {
        long result = 1;
        base = base % modulus;
        while (exponent > 0) {
            if ((exponent & 1) == 1)
                result = (result * base) % modulus;
            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }

        return result;
    }
}
