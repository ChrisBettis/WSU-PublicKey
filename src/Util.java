import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

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

    /**
     * takes a String variable path, and then creates a print writer. Will exit with -1 status if the print writer errors
     * out. The Null should never be reached if everything works out. If it errors out system exits, if it succeeds a print
     * writer is returned.
     *
     * @param path string: the path of the file you are looking to write to
     * @return the constructed printerwriter
     */
    public static PrintWriter writetoFile(String path){
        try{
            PrintWriter writer = new PrintWriter(new File(path));
            return writer;
        }catch(FileNotFoundException e){
            System.out.println(e);
            System.exit(-1);
        }

        //should never reach this due to either the print writer working, or it fails and system exits.
        return null;
    }

    /**
     * creates a Scanner object based on the path passed. Exits with status -1 if the a error happens along the way.
     * The Null should never be reached, is there to make the compiler happy.
     *
     * @param path
     * @return
     */
    public static Scanner createScanner(String path){
        try{
            Scanner scanner = new Scanner((new File(path)));
            return scanner;
        }catch (FileNotFoundException e){
            System.out.println(e);
            System.exit(-1);
        }

        return null;
    }

    /**
     * TAkes the string converts it to hex and then to a decimal.
     *
     * @param word string: the plaintext in char format.
     * @return returns the converted long.
     */
    public static long stringtoDecimal(String word){
        String hex = String.format("%x",new BigInteger(word.getBytes()));
        char[] hexList = hex.toCharArray();
        long total = 0;
        for(int i=hexList.length-1;i>=0;i--){
            int exponent = 7 - i;
            int value = hexchartoint(hexList[i]);
            total += value * (int)(Math.pow(16,exponent));
        }
        return total;
    }

    /**
     * Takes a char that is a hex value and returns the corresponding hex value. returns a -1 if a non hex value was passed.
     *
     * @param letter char: the hex value.
     * @return a int value from 0-15.
     */
    private static int hexchartoint(char letter){
        if(letter == '0') return 0;
        if(letter == '1') return 1;
        if(letter == '2') return 2;
        if(letter == '3') return 3;
        if(letter == '4') return 4;
        if(letter == '5') return 5;
        if(letter == '6') return 6;
        if(letter == '7') return 7;
        if(letter == '8') return 8;
        if(letter == '9') return 9;
        if(letter == 'a' || letter == 'A') return 10;
        if(letter == 'b' || letter == 'B') return 11;
        if(letter == 'c' || letter == 'C') return 12;
        if(letter == 'd' || letter == 'D') return 13;
        if(letter == 'e' || letter == 'E') return 14;
        if(letter == 'f' || letter == 'F') return 15;
        return -1;
    }

    /**
     * Takes the decimal and converts it to its hex value. You then take the 8 bit chunks at a time and convert those back
     * to their corresponding characters. What is returned is the converted string.
     *
     * @param decimal string: the decimal you wish to convert to a string.
     * @return returns a string of chars that you converted.
     */
    public static String decimaltostring(String decimal){
        String hex = Integer.toHexString(Integer.parseInt(decimal));
        String result = "";
        for(int i=0;i<hex.length()-1;i+=2){
            String temp = hex.substring(i,i+2);
            int hexValue = (int) Long.parseLong(temp,16);
            char plainCharacter = (char) hexValue;
            result+=plainCharacter;
        }
        return result;

    }
}
