package playground.crypto.digest;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import playground.Helpers;

import java.security.MessageDigest;
import java.security.Security;

public class Digest {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // SHA-256, SHA-384, SHA-512, SHA-512/224, SHA-512/256, SHA3-256, Keccak-256
        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        System.out.println("Provider: " + md.getProvider().getName());

        byte[] input = "data to digest".getBytes();
        md.update(input);
//        input = "next part of data".getBytes();
//        md.update(input);
        byte[] hash = md.digest();

        System.out.println(Helpers.hexDump("Input", input));
        System.out.println(Helpers.hexDump("Hash", hash));

        // one-shot digest
        System.out.println(Helpers.hexDump("Hash (one-shot)", md.digest(input)));
    }
}
