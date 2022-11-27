package playground.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.SecureRandom;
import java.security.Security;

public class Providers {
    public static void main(String[] args) throws Exception {
//        Security.addProvider(new BouncyCastleProvider());
//        Security.insertProviderAt(new BouncyCastleProvider(), 1);

        if (Security.getProvider("BC") == null) {
            System.out.println("Provider is NOT installed");
        } else {
            System.out.println("Provider installed");
        }

        Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");

        System.out.println(cipher.getProvider());

        cipher = Cipher.getInstance("Blowfish/ECB/NoPadding", "BC");

        System.out.println(cipher.getProvider());

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

        System.out.println(secureRandom.getProvider());
    }
}
