package playground.crypto.signature;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import playground.Helpers;

import java.security.*;

public class EdDSA {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("Ed25519");

        KeyPair keyPair = keyGenerator.generateKeyPair();
        Signature signature = Signature.getInstance("EdDSA");

        System.out.println("Provider: " + signature.getProvider().getName());
        System.out.println("Algorithm: " + signature.getAlgorithm());

        signature.initSign(keyPair.getPrivate());

        byte[] message = "sample".getBytes();
        signature.update(message);

        byte[] signatureBytes = signature.sign();
//        signatureBytes[0]++;
        System.out.println("Signature: " + Helpers.hexDump("Signature", signatureBytes));
        Helpers.fileDump("eddsa.sgn", signatureBytes);

        signature.initVerify(keyPair.getPublic());
        signature.update(message);
        System.out.println(signature.verify(signatureBytes));
    }
}
