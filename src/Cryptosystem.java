public class Cryptosystem {

    /**
     * Generates a random number between 0 and X, and tests the number to see if its prime. If the number is prime it returns
     * the value
     * @return returns a prime value that is less than 1000000
     */
    public static int generatePrime(){
        while(true){
            int prime = (int)(Math.random()*1000000);
            if(isPrime(prime))
                return prime;
        }
        //fix variable names
    }

    /**
     * accepts a int value that you wish to test if it is prime. What this does is run miller rabin X amount of times to test whether the value is truly prime.
     * if the value is prime a boolean is returned based on the result.
     *
     * @param int prime: is the value that you are looking to test to see if it is a prime value
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
