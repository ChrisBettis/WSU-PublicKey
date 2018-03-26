public class Main {
    public static void main(String[] args){
        if(args.length > 1)
            System.out.println("To many arguments provided.");
        else if(args[0].equals("-k"))
            KeyGeneration.gererateKey(System.currentTimeMillis());
        else if(args[0].equals("-e"))
            Crypto.encryption();
        else if(args[0].equals("-d"))
            Crypto.decryption();
        else if(args[0].equals("help"))
            System.out.println("-k for key generation \n-e for encryption \n-d for decryption");
        else
            System.out.println(args[0] + " is not a proper argument. Use the argument \"help\" to see the correct arguments.");
    }
}
