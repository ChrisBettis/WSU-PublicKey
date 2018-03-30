import java.io.PrintWriter;
import java.util.Scanner;

public class Crypto {
    /**
     * The actual encryption process C1 = g^k mod p and C2 = (e1^k x m) mod p returns c1 and c2.
     *
     * @param prime long: the 32 bit value prime used as a modulus
     * @param g: the generator
     * @param e1: g^d mod p
     * @return the c1 and c2 set as a string.
     */
    private static void encryptionProcess(long prime, long g, long e1){

        Scanner reader = Util.createScanner("ptext.txt");
        PrintWriter writer = Util.writetoFile("ctext.txt");
        while(reader.hasNext()){
            String line = reader.nextLine();
            for(int i=0;i < line.length();i+=4) {
                String substring = "";
                if(line.length() - i < 4)
                    substring = line.substring(i,line.length());
                else
                    substring = line.substring(i,i+4);
                if(substring.length()<4){
                    while(substring.length()<4)
                        substring+="0";
                }
                long hexValue = Util.stringtoDecimal(substring);
                long k = (long) (Math.random() * prime);
                long c1 = Util.modularexponetiation(g, k, prime);
                long part1 = Util.modularexponetiation(e1, k, prime);
                long part2 = hexValue % prime;
                long c2 = (part1 * part2) % prime;
                System.out.println(c1+" "+c2+" ");
                writer.print(c1+" "+c2+" ");
            }



        }
        reader.close();
        writer.close();
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
        encryptionProcess(prime, generator, e2);
        fileReader.close();
    }

    /**
     * This method is the driver for the decryption process. Pulls all the required information needed for the decryption
     * process. Stores all the required information in the correct spaces as well.
     */
    public static void decryption(){
        Scanner encryption = Util.createScanner("ctext.txt");
        Scanner privateKey = Util.createScanner("prikey.txt");
        PrintWriter writer = Util.writetoFile("ptext.txt");


        long prime = privateKey.nextLong();
        privateKey.nextInt();
        long d = privateKey.nextLong();

        while(encryption.hasNextLong()){
            long c1 = encryption.nextLong();
            long c2 = encryption.nextLong();
            String result = Crypto.decryptionProcess(c1,c2,prime,d);
            System.out.print(result);
            writer.print(result);
        }

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
    private static String decryptionProcess(long c1, long c2, long prime, long d){

        long exponent = prime - 1 - d;
        long part1 = Util.modularexponetiation(c1,exponent,prime);
        long part2 = c2 % prime;
        long message = (part1 * part2) % prime;

        String plaintext = Util.decimaltostring(message+"");
        return plaintext;
    }
}
