import java.util.Random;

public class Main {
    public static void main(String[] args){
        boolean loop = true;
        int q = 0;
        int p = 0;
        while(loop){
            q = Cryptosystem.generatePrime();
            p = (2 * q) +1;
            loop = !Cryptosystem.isPrime(p);
        }
        System.out.println(q +" is prime and "+ p +" is a prime.");
    }
}
