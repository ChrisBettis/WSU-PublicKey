public class Cryptosystem {

    /**
     * Generrates the public and private key bundles
     *
     * @param seed int: a seed that the user gave to the program.
     */
    public static void gererateKey(int seed){
        int p = generateThePrime(seed);
        int d = (int) (Math.random()*p);
        int e = Util.modularexponetiation(2,d,p);

        System.out.println("public: "+p+" 2 "+e);
        System.out.println("private: "+p+" 2 "+d);
    }

    /**
     * Generates the prime value that is used for the public key system. First it generates a prime then generates a second
     * value and tests to see if its a prime. If that second value is a prime it is used as the modulus for the public
     * key system.
     *
     * @param seed int: a seed that the user gave to the program.
     * @return the prime that will be used as the modulus
     */
    private static int generateThePrime(int seed){
        boolean loop = true;
        int p = 0;
        while(loop){
            int q = generatePrime(seed);
            p = (2 * q) +1;
            loop = !isPrime(p);
        }

        return p;
    }

    /**
     * Generates a random number between 0 and X, and tests the number to see if its prime. If the number is prime it returns
     * the value
     * @param seed int: a seed that the user gave to the program.
     * @return returns a prime value that is less than 1000000
     */
    private static int generatePrime(int seed){
        while(true){
            int prime = (int)(Math.random()*seed);
            if(isPrime(prime))
                return prime;
        }

        //fix variable names
    }

    /**
     * accepts a int value that you wish to test if it is prime. What this does is run miller rabin X amount of times to test whether the value is truly prime.
     * if the value is prime a boolean is returned based on the result.
     *
     * @param prime int: is the value that you are looking to test to see if it is a prime value
     * @return returns a boolean, true if it is a prime, false if it is a composite.
     */
    public static boolean isPrime(int prime){
        boolean isPrime = true;
        for(int i=0;i < 4 && isPrime;i++){
            int value = (int)(Math.random() * (prime-1));
            isPrime = Util.millerRabin(value,prime);
        }
        return isPrime;
    }
}
