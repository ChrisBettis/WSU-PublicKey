import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

//possibile encoding https://stackoverflow.com/questions/19743851/base64-java-encode-and-decode-a-string?rq=1
public class Crypto {
    /**
     * The actual encryption process C1 = g^k mod p and C2 = (e1^k x m) mod p returns c1 and c2.
     *
     * @param prime long: the 32 bit value prime used as a modulus
     * @param g: the generator
     * @param e1: g^d mod p
     * @return the c1 and c2 set as a string.
     */
    private static String encryptionProcess(long prime, long g, long e1){

        Scanner plaintextSC = Util.createScanner("ptext.txt");
        long hexValue = stringtoHex(plaintextSC.next());
        long k  = (long)(Math.random() * prime);
        long c1 = Util.modularexponetiation(g,k,prime);
        long part1 = Util.modularexponetiation(e1,k,prime);
        long part2 = hexValue % prime;
        long c2 = (part1 * part2) % prime;
        plaintextSC.close();

        return c1 +" " + c2;
    }

    /**
     * This method is what drives the encryption process. Grabs all the info needed to run the encryption process. Also
     * puts all the information collected in the correct spots.
     */
    public static void encryption(){
        Scanner fileReader = Util.createScanner("pubkey.txt");
        long prime = fileReader.nextLong();
        int generator = fileReader.nextInt();
        long e2 = fileReader.nextLong();
        String result = Crypto.encryptionProcess(prime, generator, e2);


        System.out.println(result);
        PrintWriter writer = Util.writetoFile("ctext.txt");
        writer.println(result);

        writer.close();
        fileReader.close();
    }

    /**
     * This method is the driver for the decryption process. Pulls all the required information needed for the decryption
     * process. Stores all the required information in the correct spaces as well.
     */
    public static void decryption(){
        Scanner encryption = Util.createScanner("ctext.txt");
        Scanner privateKey = Util.createScanner("prikey.txt");

        long c1 = encryption.nextLong();
        long c2 = encryption.nextLong();

        long prime = privateKey.nextLong();
        privateKey.nextInt();
        long d = privateKey.nextLong();
        String result = Crypto.decryptionProcess(c1,c2,prime,d);
        System.out.println(result);
        PrintWriter writer = Util.writetoFile("ptext.txt");
        writer.println(result);

        writer.close();
        encryption.close();
        privateKey.close();
    }

    /**
     * Is the process of decrypting the c1 and c2 pair back to the plaintext. (C1^(p-1-d))(C2) mod p
     *
     * @param c1 long: the first cipher text pain, g^k mod p
     * @param c2 long: e^k x m mod p
     * @param prime long: the 32 bit prime used as a modulus
     * @param d: the priavate key value.
     * @return: returns the value of the plaintext.
     */
    public static String decryptionProcess(long c1, long c2, long prime, long d){

        long exponent = prime - 1 - d;
        long part1 = Util.modularexponetiation(c1,exponent,prime);
        long part2 = c2 % prime;
        long message = (part1 * part2) % prime;

        String plaintext = hextostring(message+"");
        return plaintext;
    }

    /**
     * Takes the string of characters converts it to hex and then back to a string. What is returned is the long value of
     * the hex string.
     *
     * @param word string: the plaintext in char format.
     * @return returns the converted long.
     */
    public static long stringtoHex(String word){
        String hex = String.format("%x",new BigInteger(word.getBytes()));
        return Long.parseLong(hex);
    }

    /**
     * takes a string hex and breaks it down into 8 bit sections. Converts the 8 bit section to int and then converts the
     * int back to a char to then rebuild the plaintext string.
     *
     * @param hex string: the string of hex values.
     * @return returns the converted string.
     */
    public static String hextostring(String hex){
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
