public class Util {
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
        for(int i=0; i<=s && total != prime-1;i++){
            int power = (int) Math.pow(2,i);
            total = modularexponetiation(a,power*q,prime);
        }
        if(total == prime -1)
            return true;
        return false;
    }

    public static int modularexponetiation(int base,int exponent,int modulus){
        int value = 1;
        for(int i=1;i<=exponent;i++)
            value = (value * base)% modulus;
        return value;
    }
}
