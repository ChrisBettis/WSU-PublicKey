import java.io.PrintWriter;

public class KeyGeneration {

    /**
     * Generrates the public and private key bundles
     *
     * @param seed long: a seed that the user gave to the program.
     */
    public static void gererateKey(long seed){
        long p = generateThePrime(seed);
        int d = (int) (Math.random()*p);
        long e =  Util.modularexponetiation(2,d,p);

        PrintWriter pubKey = Util.writetoFile("pubkey.txt");
        pubKey.println(p+" 2 "+e);

        PrintWriter priKey = Util.writetoFile("prikey.txt");
        priKey.println(p+" 2 "+d);

        pubKey.close();
        priKey.close();

        System.out.println("public: "+p+" 2 "+e);
        System.out.println("private: "+p+" 2 "+d);
    }

    /**
     * Generates the prime value that is used for the public key system. First it generates a prime then generates a second
     * value and tests to see if its a prime. If that second value is a prime it is used as the modulus for the public
     * key system.
     *
     * @param seed long: a seed that the user gave to the program.
     * @return the prime that will be used as the modulus
     */
    private static long generateThePrime(long seed){
        Util.buildRandomGenerator(seed);
        boolean loop = true;
        long p = 0;
        while(loop){
            long q = generatePrime();
            if(q % 12 == 5) {
                p = (2 * q) + 1;
                loop = !isPrime(p);
            }
        }

        return p;
    }

    /**
     * Generates a random number between 0 and X, and tests the number to see if its prime. If the number is prime it returns
     * the value
     * @return returns a prime value that is supposed to be 32 bits in size
     */
    private static long generatePrime(){
        while(true){
            long prime = Util.getNextNumber();
            if(isPrime(prime))
                return prime;
        }
    }

    /**
     * accepts a int value that you wish to test if it is prime. What this does is run miller rabin X amount of times to test whether the value is truly prime.
     * if the value is prime a boolean is returned based on the result.
     *
     * @param prime long: is the value that you are looking to test to see if it is a prime value
     * @return returns a boolean, true if it is a prime, false if it is a composite.
     */
    public static boolean isPrime(long prime){
        boolean isPrime = true;
        for(int i=0;i < 6 && isPrime;i++){
            int value = (int)(Math.random() * (prime-1));
            isPrime = Util.millerRabin(value,prime);
        }
        return isPrime;
    }
}
