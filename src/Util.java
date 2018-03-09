public class Util {
    /**
     * Uses the Miller-Rabin algorithm to determine the value passed is a true prime. This test is best run more than once
     * with different bases ('a') each time to get better repesentation on whether the prime is truly a prime or a strong psudeoprime.
     *
     * @param int a: the value you want to use test the primality of your supposed 'prime'. 'a' is implied to be postive and less than 32 bits.
     * @param int prime: the supposed prime value. Implied to be a positive value, and that the value is <= 32bits.
     * @return a boolean is returned. True means that based on the 'a' passed this number is not a composite. if a false is
     *  returned that means the prime given is a composite value.
     */
    public static boolean millerRabin(int a,int prime){
        int q = prime - 1;
        int s = 0;
        while(q%2==0){
            s++;
            q = q / 2;
        }
        if(modularexponetiation(a,q,prime)==1)
            return true;
        int total = 0;
        for(int i=0; i < s && total != prime-1; i++){
            int power = (int) Math.pow(2,i);
            total = modularexponetiation(a,power*q,prime);
        }
        if(total == prime -1)
            return true;
        return false;
    }

    /**
     * The idea of this is that something like 2^3 mod n is  (2^2 * 2^1)mod n, etc. You break it down to its smallest parts
     * so that you can do large exponents  in very small steps.
     *
     * @param int base: this is int value that is getting raised by the exponent. Value has to be less than 32 bits, and positive
     * @param int exponent: the exponent that the base is getting raised too. Value has to be less than 32 bits, and positive
     * @param int modulus: the modulus to make this whole thing work. Value has to be less than 32 bits, and positive
     * @return returns the value of base^exponent mod modulus
     */
    public static int modularexponetiation(int base,int exponent,int modulus){
        // the value of base^0 mod modulus
        int value = 1;
        for(int i=1;i<=exponent;i++)
            value = (value * base)% modulus;
        return value;
    }
}
